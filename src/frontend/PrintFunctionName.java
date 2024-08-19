package frontend;

public enum PrintFunctionName {
    PRINT_START_MENU("printStartMenu"),
    PRINT_LOGIN_MENU("printLoginMenu"),
    PRINT_JOIN_MEMBERSHIP("printJoinMembership"),
    PRINT_FIND_ID("printFindID"),
    PRINT_FIND_PW("printFindPW"),
    PRINT_MAIN_MENU("printMainMenu"),
    PRINT_MEMBERSHIP_MANAGEMENT("printMembershipManagement"),
    PRINT_PRODUCT_MANAGEMENT("printProductManagement"),
    PRINT_WAREHOUSE_MANAGEMENT("printWarehouseManagement"),
    PRINT_FINANCIAL_MANAGEMENT("printFinancialManagement"),
    PRINT_STOCK_MANAGEMENT("printStockManagement"),
    PRINT_STOCKING_REQUEST_MANAGEMENT("printStockingRequestManagement"),
    PRINT_RELEASE_MANAGEMENT("printReleaseManagement"),
    PRINT_BOARD_MENU("printBoardMenu"),
    //상품 관리 기능
    PRINT_INQUIRY_PRODUCTS("printInquiryProducts"),
    PRINT_UPDATE_PRODUCT("printUpdateProduct"),
    PRINT_DELETE_PRODUCT("printDeleteProduct"),
    //창고 관리 기능
    PRINT_INQUIRY_WH("printInquiryWarehouse"),
    PRINT_INQUIRY_WH_BY_ID("printInquiryWarehouseByID"),
    PRINT_INQUIRY_WH_BY_NAME("printInquiryWarehouseByName"),
    PRINT_INQUIRY_WH_BY_LOCATION("printInquiryWarehouseByLocation"),
    PRINT_INSERT_WH("printInsertWarehouse"),
    PRINT_DELETE_WH("printDeleteWarehouse"),
    //재무 관리 기능
    PRINT_INQUIRY_EXPDET("printInquiryExpDet"),
    PRINT_INQUIRY_SALDET("printInquirySalDet"),
    PRINT_INQUIRY_EXPDET_BY_WH("printInquiryExpDetByWH"),
    PRINT_INQUIRY_SALDET_BY_WH("printInquirySalDetByWH"),
    PRINT_INQUIRY_REVENUE("printInquiryRevenue"),
    PRINT_INQUIRY_REVENUE_BY_WH("printInquiryRevenueByWH"),
    PRINT_INSERT_EXPDET("printInsertExpDet"),
    PRINT_UPDATE_EXPDET("printUpdateExpDet"),
    PRINT_DELETE_EXPDET("printDeleteExpDet"),
    PRINT_INQUIRY_CONTRACT("printInquiryContract"),
    PRINT_INQUIRY_CONTRACT_BY_ID("printInquiryContractByID"),
    PRINT_INSERT_CONTRACT("printInsertContract"),




    private String funName;
    private PrintFunctionName(String funName){ this.funName = funName;}
    public String getFunName(){return funName;}
}
