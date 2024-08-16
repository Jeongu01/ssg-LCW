package threadClass;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

  private final int nThreads; // 스레드풀의 크기
  private final List<WorkerThread> threads; // 스레드를 관리하는 리스트
  private final LinkedList<Runnable> taskQueue; // 작업을 저장하는 큐
  private static ThreadPool instance = null;

  private ThreadPool(int nThreads) {
    this.nThreads = nThreads;
    taskQueue = new LinkedList<>();
    threads = new LinkedList<>();

    // 스레드풀 내의 스레드 초기화 및 시작
    for (int i = 0; i < nThreads; i++) {
      WorkerThread thread = new WorkerThread();
      thread.start();
      threads.add(thread);
    }
  }

  public static synchronized ThreadPool getInstance() {
    if (instance == null) {
      instance = new ThreadPool(5);
    }
    return instance;
  }

  // 작업을 큐에 제출하는 메소드
  public void execute(Runnable task) {
    synchronized (taskQueue) {
      taskQueue.addLast(task);
      taskQueue.notify(); // 작업이 추가되었음을 스레드에게 알림
    }
  }

  // 스레드풀을 종료하는 메소드
  public void shutdown() {
    for (WorkerThread thread : threads) {
      thread.shutdown();
    }
  }

  // 스레드를 정의하는 내부 클래스
  private class WorkerThread extends Thread {

    private boolean isRunning = true;

    @Override
    public void run() {
      Runnable task;

      while (isRunning) {
        synchronized (taskQueue) {
          while (taskQueue.isEmpty()) {
            try {
              taskQueue.wait(); // 작업이 들어올 때까지 대기
            } catch (InterruptedException e) {
              System.out.println("Thread interrupted");
            }
          }
          task = taskQueue.removeFirst(); // 작업을 큐에서 꺼냄
        }

        // 작업 실행
        try {
          task.run();
        } catch (RuntimeException e) {
          System.out.println("Thread pool encountered an exception: " + e.getMessage());
        }
      }
    }

    // 스레드를 종료하는 메소드
    public void shutdown() {
      isRunning = false;
      this.interrupt(); // 대기 중인 스레드를 깨움
    }
  }
}
