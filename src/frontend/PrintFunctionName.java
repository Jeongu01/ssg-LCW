package frontend;

public enum PrintFunctionName {
    PRINT_START_MENU("printStartMenu"),
    PRINT_LOGIN_MENU("printLoginMenu"),
    PRINT_JOIN_MEMBERSHIP("printJoinMembership"),
    PRINT_FIND_ID("printFindID"),
    PRINT_FIND_PW("printFindPW"),
    PRINT_MAIN_MENU("printMainMenu"),
    PRINT_MEMBERSHIP_MANAGEMENT("printMembershipManagement"),
    PRINT_WAREHOUSE_MANAGEMENT("printWarehouseManagement"),
    PRINT_FINANCIAL_MANAGEMENT("printFinancialManagement"),
    PRINT_STOCK_MANAGEMENT("printStockManagement"),
    PRINT_STOCKING_REQUEST_MANAGEMENT("printStockingRequestManagement"),
    PRINT_RELEASE_MANAGEMENT("printReleaseManagement"),
    PRINT_BOARD_MENU("printBoardMenu"),


    private String funName;
    private PrintFunctionName(String funName){ this.funName = funName;}
    public String getFunName(){return funName;}
}
