package exception;

public class ExitException extends MyException{
    @Override
    public String getMessage() {
        return "[종료]";
    }
}
