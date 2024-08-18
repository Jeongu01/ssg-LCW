package frontend;

public enum PrintFunctionName {
    PRINT_START_MENU("printStartMenu"),
    PRINT_LOGIN_MENU("printLoginMenu"),
    PRINT_JOIN_MEMBERSHIP("printJoinMembership"),
    PRINT_FIND_ID("printFindID"),
    PRINT_FIND_PW("printFindPW"),
    PRINT_MAIN_MENU("printMainMenu");

    private String funName;
    private PrintFunctionName(String funName){ this.funName = funName;}
    public String getFunName(){return funName;}
}
