package exception;

public class NoPermissionException extends MyException{
    @Override
    public String getMessage() {
        return "권한이 없습니다.";
    }
}
