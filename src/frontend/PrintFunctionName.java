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
    PRINT_INQUIRY_REVENUE("printInquiryRevenue"),
    PRINT_INSERT_EXPDET("printInsertExpDet"),
    PRINT_UPDATE_EXPDET("printUpdateExpDet"),
    PRINT_DELETE_EXPDET("printDeleteExpDet"),
    PRINT_INQUIRY_CONTRACT("printInquiryContract"),
    PRINT_INQUIRY_CONTRACT_BY_ID("printInquiryContractByID"),
    PRINT_INSERT_CONTRACT("printInsertContract"),

    RESTART("restart"),
    PRINT_SELECT_MEMBER_MENU("printSelectMemberMenu"),
    PRINT_WAITING_MEMBER_LIST("printWaitingMemberList"),
    PRINT_MEMBERSHIP_MANAGEMENT_FOR_ADMIN("printMembershipManagementForAdmin"),
    PRINT_MEMBERSHIP_MANAGEMENT_FOR_MEMBER("printMembershipManagementForMember"),
    PRINT_MEMBERSHIP_MANAGEMENT_FOR_GUEST("printMembershipManagementForGuest"),
    PRINT_MEMBER_DETAILS("printMemberDetails"),
    PRINT_UPDATE_MEMBER_DETAILS("printUpdateMemberDetails"),
    PRINT_UPDATE_MEMBER_PASSWORD("printUpdateMemberPassword"),
    PRINT_CANCEL_ACCOUNT("printCancelAccount"),
    PRINT_MEMBER_LIST("printMemberList"),
    PRINT_MEMBER_BY_ID("printMemberById"),
    PRINT_MEMBER_BY_BUSINESS_NUMBER("printMemberByBusinessNumber"),
    PRINT_CHANGE_PASSWORD_RESULT("printChangePasswordResult"),
    PRINT_REQUEST_INPUT_ID("printRequestInputId"),
    PRINT_REQUEST_INPUT_BUSINESS_NUMBER("printRequestInputBusinessNumber"),
    PRINT_APPROVE_REGISTRATION_REQUEST("printApproveRegistrationRequest"),
    PRINT_LOGIN_FAIL("printLoginFail"),
    PRINT_INSERT_PRODUCT("printInsertProduct"),
    EXIT("EXIT");


    private String funName;
    private PrintFunctionName(String funName){ this.funName = funName;}
    public String getFunName(){return funName;}
}
