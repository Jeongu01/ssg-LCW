package exception;

public class WrongEmailInputException extends MyException{
    @Override
    public String getMessage() {
        return "[이메일 잘못 입력]";
    }
}
