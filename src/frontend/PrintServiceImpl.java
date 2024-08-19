package frontend;

import exception.ExitException;
import exception.NoPermissionException;
import exception.WrongInputNumberException;
import util.Role;
import vo.InputVO;
import vo.MainVO;
import vo.ProductVO;
import vo.UserVO;

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
                case 1:break;
                case 2:break;
                case 3:funName = PrintFunctionName.PRINT_PRODUCT_MANAGEMENT;break;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_PRODUCTS;
        }
        return funName;
    }
    public PrintFunctionName printUpdateProduct(MainVO data,InputVO inputData,BufferedReader br){
        return null;
    }
    /////////////////////////////////////////////////////창고관리 기능 관련
    public PrintFunctionName printWarehouseManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    public PrintFunctionName printFinancialManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    public PrintFunctionName printStockManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    public PrintFunctionName printStockingRequestManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    public PrintFunctionName printReleaseManagement(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }
    public PrintFunctionName printBoardMenu(MainVO data,BufferedReader br){
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        return funName;
    }

}
