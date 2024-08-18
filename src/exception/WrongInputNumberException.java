package exception;

public class WrongInputNumberException extends MyException{
    @Override
    public String getMessage() {
        return "[잘못된 숫자를 입력하셨습니다]";
    }
}
