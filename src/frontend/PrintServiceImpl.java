package frontend;

import exception.ExitException;
import exception.NoPermissionException;
import exception.WrongInputNumberException;
import util.Role;
import vo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

//싱글턴 : 출력 서비스 클래스
public class PrintServiceImpl {
    private static PrintServiceImpl instance = null;

    private PrintServiceImpl(){
    }

    public PrintServiceImpl getInstance(){
        if(instance == null){
            instance = new PrintServiceImpl();
        }
        return instance;
    }


    public void printLogo(){
        System.out.println(
          " ▐█          █████▌    █▌    █    ▐█    ▐█▌     ▐█▌    ▐█████▌\n"
        + " ▐█         ███   █    ▐█   ▐█▌   █▌    ▐██     ██▌    ██   ▐█\n"
        + " ▐█        ▐██          █   █▐█  ▐█     ▐██▌   ▐██▌    █      \n"
        + " ▐█        ▐█           █▌ ▐█ █  █▌     ▐█ █   █ █▌    ██▌    \n"
        + " ▐█        ▐█           ▐█ █▌ █▌ █      ▐█ ▐▌ ▐█ █▌     ████▌ \n"
        + " ▐█        ▐█            █ █  ▐█▐█      ▐█  █ █▌ █▌         ██\n"
        + " ▐█        ▐██           ▐██   ██▌      ▐█  ▐██  █▌          █\n"
        + " ▐█         ███   █      ▐█▌   ▐█       ▐█   █   █▌    █    ██\n"
        + " ▐██████     █████▌       █     █       ▐█       █▌    ▐█████▌");
    }

    private int printAskNumber(BufferedReader br, String message,int range) throws IOException, WrongInputNumberException {
        System.out.print(message);
        int sel = Integer.parseInt(br.readLine());
        if(sel<=0&&sel>range) throw new WrongInputNumberException();
        return sel;
    }
    public PrintFunctionName printStartMenu(MainVO data,BufferedReader br) throws IOException, ExitException{
        PrintFunctionName funName = null;
        data = new MainVO(new UserVO(),-1);
        System.out.println(
                        "1. 로그인\n"+
                        "2. 회원가입\n"+
                        "3. ID 찾기\n"+
                        "4. password 찾기\n"+
                        "5. 종료\n");

        try{
            int sel = this.printAskNumber(br,"메뉴번호를 입력하세요:",5);
            switch (sel){
                case 1-> funName = PrintFunctionName.PRINT_LOGIN_MENU;
                case 2-> funName = PrintFunctionName.PRINT_JOIN_MEMBERSHIP;
                case 3-> funName = PrintFunctionName.PRINT_FIND_ID;
                case 4-> funName = PrintFunctionName.PRINT_FIND_PW;
                case 5-> throw new ExitException();
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName =  PrintFunctionName.PRINT_START_MENU;
        }
        return funName;
    }
    public PrintFunctionName printLoginMenu(MainVO data, BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        UserVO user = data.getUser();
        System.out.println(
                        "1. 회원 로그인\n"+
                        "2. 비회원 로그인\n"+
                        "3. 뒤로가기\n");
        try{
            int sel = this.printAskNumber(br,"번호를 입력하세요.",3);
            switch (sel){
                case 1:
                    System.out.print("ID : ");
                    user.setUserId(br.readLine());
                    System.out.print("PassWord : ");
                    user.setPassword(br.readLine());
                    funName = PrintFunctionName.PRINT_MAIN_MENU;
                    break;
                case 2:
                    user.setRole(Role.GUEST);
                    funName = PrintFunctionName.PRINT_MAIN_MENU;
                    break;
                case 3:
                    funName = PrintFunctionName.PRINT_START_MENU;
                    break;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_LOGIN_MENU;
        }
        return funName;
    }
    //회원가입정보 UserVo에 담아서 돌려줌=>이상한값넣었을때 예외발생시키는것도 여기서 처리하자.
    public PrintFunctionName printJoinMembership(MainVO data, BufferedReader br) throws IOException{
        PrintFunctionName funName = null;
        UserVO user = data.getUser();
        System.out.println("회원유형");
        System.out.println(
                "1. 관리자 \n" +
                "2. 스토어 매니져 \n"+
                "3. 스토어 근무자 \n" +
                "4. 배송기사 \n"+
                "5. 공급자 \n"+
                "6. 창고 관리자\n"
                );
        try{
            int sel = this.printAskNumber(br,"회원유형을 입력하세요.",6);
            switch (sel){
                case 1->printJoinMembershipAdmin(user,br);
                case 2->printJoinMembershipStoreManager(user,br);
                case 3->printJoinMembershipStoreEmployee(user,br);
                case 4->printJoinMembershipDeliveryDriver(user,br);
                case 5->printJoinMembershipSupplier(user,br);
                case 6->printJoinMembershipWhManager(data,br);
            }
            funName = PrintFunctionName.PRINT_START_MENU;
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_JOIN_MEMBERSHIP;
        }
        return funName;
    }
    private void printJoinMembershipCommon(UserVO user,BufferedReader br)throws IOException{
        System.out.print("ID를 입력하세요 : ");
        user.setUserId(br.readLine());
        System.out.print("비밀번호를 입력하세요 : ");
        user.setPassword(br.readLine());
        System.out.print("이름을 입력하세요 : ");
        user.setName(br.readLine());
        System.out.print("주소를 입력하세요: ");
        user.setAddress(br.readLine());
        //어차피 번지 이거 2차때 api쓸때나 필요함...
    }
    private void printJoinMembershipAdmin(UserVO user,BufferedReader br)throws IOException{
        user.setRole(Role.ADMIN);
        printJoinMembershipCommon(user,br);
    }
    private void printJoinMembershipStoreManager(UserVO user,BufferedReader br)throws IOException{
        user.setRole(Role.STORE_OPERATOR);
        printJoinMembershipCommon(user,br);
        System.out.print("사업자 이름을 입력하세요: ");
        user.setBusinessName(br.readLine());
        System.out.print("사업자 번호를 입력하세요: ");
        user.setBusinessNumber(br.readLine());
    }
    private void printJoinMembershipStoreEmployee(UserVO user,BufferedReader br)throws IOException{
        user.setRole(Role.EMPLOYEE);
        printJoinMembershipCommon(user,br);
        System.out.print("사업자 이름을 입력하세요: ");
        user.setBusinessName(br.readLine());
        System.out.print("사업자 번호를 입력하세요: ");
        user.setBusinessNumber(br.readLine());
    }
    private void printJoinMembershipDeliveryDriver(UserVO user,BufferedReader br)throws IOException{
        user.setRole(Role.DELIVERY_DRIVER);
        printJoinMembershipCommon(user,br);
    }
    private void printJoinMembershipSupplier(UserVO user,BufferedReader br)throws IOException{
        user.setRole(Role.SUPPLIER);
        printJoinMembershipCommon(user,br);
    }
    private void printJoinMembershipWhManager(MainVO data,BufferedReader br)throws IOException{
        UserVO user = data.getUser();
        user.setRole(Role.WH_MANAGER);
        printJoinMembershipCommon(user,br);
        System.out.print("관리할 창고ID를 입력하세요 : ");
        data.setWarehouseID(Integer.parseInt(br.readLine()));
    }

    public PrintFunctionName printFindID(String email,BufferedReader br) throws IOException{
        PrintFunctionName funName = null;
        System.out.println("이메일 주소를 입력해주세요.");
        email = br.readLine();
        return funName = PrintFunctionName.PRINT_START_MENU;
    }
    public PrintFunctionName printFindPW(String userId, BufferedReader br) throws IOException{
        PrintFunctionName funName = null;
        System.out.println("아이디를 입력해주세요.");
        userId = br.readLine();
        return funName = PrintFunctionName.PRINT_START_MENU;
    }

    public PrintFunctionName PrintMainMenu(BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "1. 회원관리 \n" +
                "2. 창고관리 \n"+
                "3. 재무관리 \n" +
                "4. 재고관리 \n"+
                "5. 입고관리 \n"+
                "6. 출고관리 \n"+
                "7. 게시판  \n"+
                "8. 로그아웃 \n");
        try{
            int sel = this.printAskNumber(br,"찾으시는 서비스를 입력하세요 :",9);
            switch (sel){
                case 1->funName = PrintFunctionName.PRINT_MEMBERSHIP_MANAGEMENT;
                case 2->funName = PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
                case 3->funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;
                case 4->funName = PrintFunctionName.PRINT_FINANCIAL_MANAGEMENT;
                case 5->funName = PrintFunctionName.PRINT_STOCK_MANAGEMENT;
                case 6->funName = PrintFunctionName.PRINT_STOCKING_REQUEST_MANAGEMENT;
                case 7->funName = PrintFunctionName.PRINT_RELEASE_MANAGEMENT;
                case 8->funName = PrintFunctionName.PRINT_BOARD_MENU;
                case 9->funName = PrintFunctionName.PRINT_START_MENU;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_MAIN_MENU;
        }
        return funName;
    }
    /////////////////////////////////////////////회원관리 기능 관련
    public PrintFunctionName printMembershipManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    ///////////////////////////////////////////상품관리 기능 관련
    public PrintFunctionName printProductManagement(MainVO data, InputVO inputData, BufferedReader br) throws IOException, NoPermissionException {
        if(data.getUser().getRole()!=Role.ADMIN) throw new NoPermissionException();
        ProductVO productVO = inputData.getProduct();
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "1. 상품등록\n"+
                "2. 상품조회\n"+
                "3. 뒤로가기\n");
        try{
            int sel = this.printAskNumber(br,"메뉴를 선택하세요 ",3);
            switch (sel){
                case 1 :
                    System.out.println("[상품등록]");
                    System.out.print("상품명 : ");
                    productVO.setProductName(br.readLine());
                    System.out.println("상품브랜드 : ");
                    productVO.setProductBrand(br.readLine());
                    System.out.println("상품 면적 : ");
                    productVO.setAreaPerProduct(Integer.parseInt(br.readLine()));
                    System.out.println("카테고리 ID : ");
                    productVO.setCategoryId(Integer.parseInt(br.readLine()));
                    break;
                case 2:
                    System.out.println("[상품조회]");
                    System.out.println("상품ID|상품명|브랜드|상품면적|상품카테고리");

                    break;
                case 3:funName = PrintFunctionName.PRINT_MAIN_MENU;break;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;
        }
        return funName;
    }
    public PrintFunctionName printInquiryProducts(ArrayList<ProductVO> ret,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        System.out.println("[상품조회]");
        System.out.println("상품ID|상품명|브랜드|상품면적|상품카테고리");
        for(ProductVO vo:ret){
            System.out.println(
                            vo.getProductId()+"|"+
                            vo.getProductName()+"|"+
                            vo.getProductBrand()+"|"+
                            vo.getAreaPerProduct()+"|"+
                            vo.getCategoryId()+"|"
            );
        }
        System.out.println("\n1. 수정  2. 삭제  3. 뒤로가기");
        try{
            int sel = this.printAskNumber(br,"메뉴번호를 입력하세요: ",3);
            switch (sel){
                case 1->funName = PrintFunctionName.PRINT_UPDATE_PRODUCT;
                case 2->funName = PrintFunctionName.PRINT_DELETE_PRODUCT;
                case 3->funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_PRODUCTS;
        }
        return funName;
    }
    public PrintFunctionName printUpdateProduct(InputVO inputData,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        ProductVO input = inputData.getProduct();
        System.out.println("[상품 수정]");
        System.out.print("수정 할 상품 id를 입력하세요: ");
        input.setProductId(Integer.parseInt(br.readLine()));
        System.out.print("상품명 : ");
        input.setProductName(br.readLine());
        System.out.print("상품 브랜드 : ");
        input.setProductBrand(br.readLine());
        System.out.print("상품 면적 : ");
        input.setAreaPerProduct(Integer.parseInt(br.readLine()));
        System.out.println("상품 카테고리ID : ");
        input.setCategoryId(Integer.parseInt(br.readLine()));

        return funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;
    }
    public PrintFunctionName printDeleteProduct(InputVO inputData,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        ProductVO input = inputData.getProduct();
        System.out.println("[상품 삭제]");
        System.out.print("삭제 할 상품 id를 입력하세요: ");
        input.setProductId(Integer.parseInt(br.readLine()));
        return funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;
    }
    /////////////////////////////////////////////////////창고관리 기능 관련
    public PrintFunctionName printWarehouseManagement(MainVO data,BufferedReader br) throws NoPermissionException,IOException {
        Role r = data.getUser().getRole();
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println("[창고 관리]");
        try {
            int sel;
            switch (r) {
                case ADMIN:
                    System.out.println(
                            "1. 창고 등록\n" +
                            "2. 전체 창고 조회\n"+
                            "3. 소재지별 창고 조회\n" +
                            "4. 창고명별 창고 조희\n" +
                            "5. 돌아가기"
                    );
                    sel = this.printAskNumber(br, "메뉴를 선택하세요 : ", 5);
                    switch (sel){
                        case 1->funName = PrintFunctionName.PRINT_INSERT_WH;
                        case 2->funName = PrintFunctionName.PRINT_INQUIRY_WH;
                        case 3->funName = PrintFunctionName.PRINT_INQUIRY_WH_BY_LOCATION;
                        case 4->funName = PrintFunctionName.PRINT_INQUIRY_WH_BY_NAME;
                        case 5->funName = PrintFunctionName.PRINT_MAIN_MENU;
                    }
                    break;
                case WH_MANAGER:
                    System.out.println(
                            "1. 창고조희" +
                            "2. 돌아가기"
                    );
                    sel = this.printAskNumber(br, "메뉴를 선택하세요 : ", 2);
                    switch (sel){
                        case 1->funName = PrintFunctionName.PRINT_INQUIRY_WH_BY_ID;
                        case 2->funName = PrintFunctionName.PRINT_MAIN_MENU;
                    }
                    break;
                default : throw new NoPermissionException();
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
        }
        return funName;
    }
    public PrintFunctionName printInquiryWarehouse(ArrayList<WarehouseVO> arr,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        System.out.println("[전체 창고 조회]");
        System.out.println("창고id | 창고 이름 | 창고 주소 | 창고 면적 | 관리자 ID");
        for(WarehouseVO vo:arr){
            System.out.println(
                    vo.getStorageId()+" | "+
                    vo.getStorageName()+" | "+
                    vo.getAddress()+" | "+
                    vo.getStorageArea()+" | "+
                    vo.getManagerId()+" | "
            );
        }
        System.out.println("\n1. 삭제 2. 돌아가기");
        try{
            int sel = this.printAskNumber(br,"번호를 입력하세요 : ",2);
            switch (sel){
                case 1->funName = PrintFunctionName.PRINT_DELETE_WH;
                case 2->funName = PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_WH;
        }
        return funName;
    }
    public PrintFunctionName printDeleteWarehouse(InputVO inputData,BufferedReader br)throws IOException{
        WarehouseVO warehouseVO = inputData.getWarehouse();
        System.out.println("[창고 삭제]");
        System.out.print("삭제할 창고의 id를 입력하세요 : ");
        warehouseVO.setStorageId(Integer.parseInt(br.readLine()));

        return PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
    }
    public PrintFunctionName printInquiryWarehouseByID(WarehouseVO vo,BufferedReader br)throws IOException{

        System.out.println("[창고 조회]");
        System.out.println("창고id | 창고 이름 | 창고 주소 | 창고 면적 | 관리자 ID");
        System.out.println(
                vo.getStorageId()+" | "+
                        vo.getStorageName()+" | "+
                        vo.getAddress()+" | "+
                        vo.getStorageArea()+" | "+
                        vo.getManagerId()+" | "
        );
        System.out.print("아무키나 입력하면 돌아갑니다. ");
        br.readLine();
        return PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
    }
    public PrintFunctionName printInquiryWarehouseByName(){
        PrintFunctionName funName = null;
        //Do something!!
        return PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
    }
    public PrintFunctionName printInquiryWarehouseByLocation(){
        PrintFunctionName funName = null;
        //Do something!!
        return PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
    }
    public PrintFunctionName printInsertWarehouse(InputVO inputData,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        WarehouseVO warehouse = inputData.getWarehouse();
        System.out.println("[창고 등록]");
        System.out.print("창고 명 : ");
        warehouse.setStorageName(br.readLine());
        System.out.print("주소지 : ");
        warehouse.setAddress(br.readLine());
        System.out.print("창고 면적 : ");
        warehouse.setStorageArea(Integer.parseInt(br.readLine()));
        System.out.print("관리자 ID : ");
        warehouse.setManagerId(br.readLine());
        return PrintFunctionName.PRINT_WAREHOUSE_MANAGEMENT;
    }
    //////////////////////////////////////////////////////재무관리
    public PrintFunctionName printFinancialManagement(MainVO data,BufferedReader br) throws IOException, NoPermissionException {
        Role r = data.getUser().getRole();
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println("[재무 관리]");
        try {
            int sel;
            switch (r) {
                case ADMIN:
                    System.out.println(
                            "1. 지출 내역 조회\n" +
                                    "2. 매출 내역 조회\n"+
                                    "3. 총 정산 조회\n" +
                                    "4. 전체 계약 현황 조회\n"+
                                    "5. 돌아가기\n"
                    );
                    sel = this.printAskNumber(br, "메뉴를 선택하세요 : ", 4);
                    switch (sel){
                        case 1->funName = PrintFunctionName.PRINT_INQUIRY_EXPDET;
                        case 2->funName = PrintFunctionName.PRINT_INQUIRY_SALDET;
                        case 3->funName = PrintFunctionName.PRINT_INQUIRY_REVENUE;
                        case 4->funName = PrintFunctionName.PRINT_INQUIRY_CONTRACT;
                        case 5->funName = PrintFunctionName.PRINT_MAIN_MENU;
                    }
                    break;
                case WH_MANAGER:
                    System.out.println(
                            "1. 지출 내역 조회\n" +
                                    "2. 지출 내역 등록\n"+
                                    "3. 매출 내역 조회\n"+
                                    "4. 총 정산 조회\n" +
                                    "5. 돌아가기\n"
                    );
                    sel = this.printAskNumber(br, "메뉴를 선택하세요 : ", 5);
                    switch (sel){
                        case 1->funName = PrintFunctionName.PRINT_INQUIRY_EXPDET_BY_WH;
                        case 2->funName = PrintFunctionName.PRINT_INSERT_EXPDET;
                        case 3->funName = PrintFunctionName.PRINT_INQUIRY_SALDET_BY_WH;
                        case 4->funName = PrintFunctionName.PRINT_INQUIRY_REVENUE_BY_WH;
                        case 5->funName = PrintFunctionName.PRINT_MAIN_MENU;
                    }
                    break;
                case STORE_OPERATOR:
                case EMPLOYEE:
                    System.out.println(
                            "1. 임대요청\n" + "2. 임대료 및 만료기간 조회\n3. 돌아가기"
                    );
                    sel = this.printAskNumber(br, "메뉴를 선택하세요 : ", 3);
                    switch (sel){
                        case 1->funName = PrintFunctionName.PRINT_INSERT_CONTRACT;
                        case 2->funName = PrintFunctionName.PRINT_INQUIRY_CONTRACT_BY_ID;
                        case 3->funName = PrintFunctionName.PRINT_MAIN_MENU;
                    }
                    break;
                default : throw new NoPermissionException();
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_FINANCIAL_MANAGEMENT;
        }
        return funName;
    }
    public PrintFunctionName printInquiryExpDet(ArrayList<ExpenditureDetailsVO> arr,BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        System.out.println("[지출 내역 조회]");
        System.out.println("지출 내역 id | 날짜 | 비용 | 창고 ID | 카테고리 ID");
        for(ExpenditureDetailsVO vo:arr){
            System.out.println(
                    vo.getExpenditureDetailsId()+" | "+
                            vo.getExpenditureDetailsDate()+" | "+
                            vo.getCost()+" | "+
                            vo.getWarehouseId()+" | "+
                            vo.getCategoryId()
            );
        }
        System.out.println("\n1. 수정 2. 삭제 3. 돌아가기");
        try{
            int sel = this.printAskNumber(br,"번호를 입력하세요 : ",3);
            switch (sel){
                case 1->funName = PrintFunctionName.PRINT_UPDATE_EXPDET;
                case 2->funName = PrintFunctionName.PRINT_DELETE_EXPDET;
                case 3->funName = PrintFunctionName.PRINT_FINANCIAL_MANAGEMENT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_EXPDET;
        }
        return funName;
    }
    public PrintFunctionName printInquirySalDet(ArrayList<SalesDetailsVO> arr,BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        System.out.println("[매출 내역 조회]");
        System.out.println("매출 내역 id | 날짜 | 비용 | 창고 ID | 카테고리 ID");
        for(SalesDetailsVO vo:arr){
            System.out.println(
                    vo.getSalesDetailsId()+" | "+
                            vo.getSalesDetailsDate()+" | "+
                            vo.getCost()+" | "+
                            vo.getWarehouseId()+" | "+
                            vo.getCategoryId()
            );
        }
        System.out.println("\n1. 돌아가기");
        try{
            int sel = this.printAskNumber(br,"번호를 입력하세요 : ",3);
            switch (sel){
                case 1->funName = PrintFunctionName.PRINT_UPDATE_EXPDET;
                case 2->funName = PrintFunctionName.PRINT_DELETE_EXPDET;
                case 3->funName = PrintFunctionName.PRINT_FINANCIAL_MANAGEMENT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_EXPDET;
        }
        return funName;
    }
    public PrintFunctionName printInquiryExpDetByWH(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInquirySalDetByWH(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInquiryRevenue(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInquiryRevenueByWH(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInsertExpDet(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInquiryContract(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInquiryContractByID(){
        PrintFunctionName funName = null;
        return funName;
    }
    public PrintFunctionName printInsertContract(){
        PrintFunctionName funName = null;
        return funName;
    }
    /////////////////////////////////////////////////////재고관리
    public PrintFunctionName printStockManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    //////////////////////////////////////////////////입고관리
    public PrintFunctionName printStockingRequestManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    ////////////////////////////////////////////////출고관리
    public PrintFunctionName printReleaseManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    /////////////////////////////////////////////게시판메뉴관리
    public PrintFunctionName printBoardMenu(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }

}
