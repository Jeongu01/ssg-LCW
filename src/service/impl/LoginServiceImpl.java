package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lib.ConnectionPool;
import service.LoginService;
import threadClass.ThreadPool;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class LoginServiceImpl implements LoginService {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private ConnectionPool connectionPool = ConnectionPool.getInstance();
  private Connection connection = null;
  private ResultSet rs = null;
  private String query;
  private PreparedStatement pstmt;
  private boolean quit = false;

  private UserManagementDAO dao = null;
  private ThreadPool threadPool = null;

  public LoginServiceImpl() {
    dao = new UserManagementDAO();
  }

  @Override
  public void showMenu() throws IOException, SQLException, InterruptedException {
    while (!quit) {
      System.out.println("1. 로그인");
      System.out.println("2. 회원가입");
      System.out.println("3. 아이디 찾기");
      System.out.println("4. 비밀번호 찾기");
      System.out.println("5. 종료");
      int sel = Integer.parseInt(br.readLine());
      switch (sel) {
        case 1:
          UserVO vo = login();
          System.out.println(vo);
          break;
        case 2:
          boolean b = registerUser();
          System.out.println(b);
          break;
        case 3:
          String id = findId();
          System.out.println(id);
          break;
        case 4:
          String pwd = findPwd();
          System.out.println(pwd);
          break;
        case 5:
          quit = true;
          ThreadPool threadPool = ThreadPool.getInstance();
          threadPool.shutdown();
          break;
        default:
          System.out.println("잘못된 선택입니다.");
          break;
      }
    }

  }

  /*
   * 로그인
   * */
  @Override
  public UserVO login() throws IOException, SQLException, InterruptedException {

    UserVO userVO = null;

    System.out.println("1.회원");
    System.out.println("2.비회원");
    System.out.println("3.돌아가기");
    int sel = Integer.parseInt(br.readLine());

    switch (sel) {
      case 1:
        System.out.println("-".repeat(20));
        System.out.print("아이디: ");
        String id = br.readLine();
        System.out.print("비밀번호: ");
        String pwd = br.readLine();

        userVO = dao.selectUser(id, pwd);

        connectionPool.releaseConnection(connection);

        break;
      case 2:
        break;
      case 3:
        break;
      default:
        break;
    }
    return userVO;
  }

  /*
   * 회원가입
   * */
  @Override
  public boolean registerUser() throws IOException {
    // TODO : Print 쪽으로 빼기
    System.out.println("-".repeat(20));
    System.out.println("가입하시려는 회원 유형을 선택해주세요");
    System.out.println("1.직원");
    System.out.println("2.창고관리자");
    System.out.println("3.운전기사");
    System.out.println("4.공급자");
    System.out.println("5.쇼핑몰 운영자");
    int sellectRole = Integer.parseInt(br.readLine());

    Role role = switch (sellectRole) {
      case 1 -> Role.EMPLOYEE;
      case 2 -> Role.WH_MANAGER;
      case 3 -> Role.DELIVERY_DRIVER;
      case 4 -> Role.SUPPLIER;
      case 5 -> Role.STORE_OPERATOR;
      default -> Role.GUEST; // TODO : 수정 필요
    };

    System.out.print("아이디: ");
    String userId = br.readLine();
    System.out.print("비밀번호: ");
    String password = br.readLine();
    System.out.print("이름: ");
    String name = br.readLine();
    System.out.print("생년월일: ");
    java.sql.Date birth = java.sql.Date.valueOf(br.readLine());
    System.out.print("이메일: ");
    String email = br.readLine();
    System.out.print("전화번호: ");
    String tel = br.readLine();
    System.out.print("주소: ");
    String address = br.readLine();
    System.out.print("사업자명: ");
    String businessName = br.readLine();
    System.out.print("사업자번호: ");
    String businessNumber = br.readLine();

    // TODO : 여기부터 DAO
    UserVO userVO = new UserVO(userId, password, name, birth, email, tel, role, UserStatus.WAITING, address, businessName, businessNumber);
    return dao.insertUser(userVO);
  }

  /*
   * 아이디 찾기
   * */
  @Override
  public String findId()
      throws IOException, SQLException, InterruptedException {  // TODO : email 유니크로 바꿔주기
    String userId = "";
    System.out.println("이메일 주소를 입력해주세요.");
    String email = br.readLine();

    userId = dao.selectUserId(email);
    return userId;
  }

  /*
  * 비밀번호 찾기
  * */
  @Override
  public String findPwd() throws IOException, SQLException, InterruptedException {
    String password = "";
    System.out.println("아이디를 입력해주세요.");
    String userId = br.readLine();

    password = dao.selectUserPwd(userId);
    return password;
  }
}
