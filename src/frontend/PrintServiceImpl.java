package frontend;

import exception.ExitException;
import exception.NoPermissionException;
import exception.WrongInputNumberException;
import util.Role;
import vo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

//싱글턴 : 출력 서비스 클래스
public class PrintServiceImpl {
    private static PrintServiceImpl instance = null;

    private PrintServiceImpl(){
    }

    public static PrintServiceImpl getInstance(){
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
    public PrintFunctionName printLoginMenu(InputVO data, BufferedReader br) throws IOException {
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

    public PrintFunctionName printLoginFail(){
        PrintFunctionName funName = null;
        System.out.println("로그인에 실패하였습니다.");
        return funName = PrintFunctionName.EXIT;
    }

    //회원가입정보 UserVo에 담아서 돌려줌=>이상한값넣었을때 예외발생시키는것도 여기서 처리하자.
    public PrintFunctionName printJoinMembership(InputVO data, BufferedReader br) throws IOException{
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
                case 6->printJoinMembershipWhManager(user,br);
            }
            funName = PrintFunctionName.EXIT;
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
        System.out.print("생년월일을 입력하세요 : ");
        user.setBirth(Date.valueOf(br.readLine()));
        System.out.print("이메일을 입력하세요 : ");
        user.setName(br.readLine());
        System.out.print("전화번호를 입력하세요 : ");
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
    private void printJoinMembershipWhManager(UserVO user,BufferedReader br)throws IOException{
//        UserVO user = data.getUser();
        user.setRole(Role.WH_MANAGER);
        printJoinMembershipCommon(user,br);
//        System.out.print("관리할 창고ID를 입력하세요 : ");
//        data.setWarehouseID(Integer.parseInt(br.readLine()));
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
                "3. 상품관리 \n"+
                "4. 재무관리 \n" +
                "5. 재고관리 \n"+
                "6. 입고관리 \n"+
                "7. 출고관리 \n"+
                "8. 게시판  \n"+
                "9. 로그아웃 \n");
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
                case 9->funName = PrintFunctionName.EXIT;
            }
        }catch (WrongInputNumberException e){
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_MAIN_MENU;
        }
        return funName;
    }
    /////////////////////////////////////////////회원관리 기능 관련
    public PrintFunctionName printMembershipManagementForAdmin(BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "1. 회원 조회 \n" +
                        "2. 승인 대기 조회 \n" +
                        "3. 돌아가기");
        try {
            int sel = this.printAskNumber(br, "메뉴를 입력하세요 :", 3);
            switch (sel) {
                case 1 -> funName = PrintFunctionName.PRINT_SELECT_MEMBER_MENU;
                case 2 -> funName = PrintFunctionName.PRINT_WAITING_MEMBER_LIST;
                case 3 -> funName = PrintFunctionName.EXIT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_MEMBERSHIP_MANAGEMENT;
        }
        return funName;
    }

    public PrintFunctionName printSelectMemberMenu(BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "1. 전체 회원 조회 \n" +
                        "2. ID로 회원 조회 \n" +
                        "3. 사업자번호로 회원 조회 \n"
                        + "4. 돌아가기");
        try {
            int sel = this.printAskNumber(br, "메뉴를 입력하세요 :", 4);
            switch (sel) {
                case 1 -> funName = PrintFunctionName.PRINT_MEMBER_LIST;
                case 2 -> funName = PrintFunctionName.PRINT_MEMBER_BY_ID;
                case 3 -> funName = PrintFunctionName.PRINT_MEMBER_BY_BUSINESS_NUMBER;
                case 4 -> funName = PrintFunctionName.EXIT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_SELECT_MEMBER_MENU;
        }
        return funName;
    }

    public PrintFunctionName printMemberList(ArrayList<UserVO> userList, String whatList) {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "--------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "사용자ID", "이름", "권한", "상태", "사업자명",
                "사업자 번호");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------");
        for (UserVO user : userList) {
            System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", user.getUserId(), user.getName(), user.getRole().toString(), user.getStatus().toString(), user.getBusinessName(),
                    user.getBusinessNumber());
        }

        if (whatList.equals("waitingMemberList")){
            return funName = PrintFunctionName.PRINT_APPROVE_REGISTRATION_REQUEST;
        } else {
            return funName = PrintFunctionName.EXIT;
        }
    }

    public PrintFunctionName printApproveRegistrationRequest(InputVO data, BufferedReader br) throws IOException{
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println("승인 처리를 진행할 아이디를 입력하세요");
        data.getUser().setUserId(br.readLine());
        System.out.println("승인하시겠습니까? (Y/N)");
        String yn = br.readLine();
        if (yn.equals("Y")) {
            data.setCheck(true);
        } else if (yn.equals("N")) {
            data.setCheck(false);
        }
        return funName = PrintFunctionName.EXIT;
    }

    public PrintFunctionName printRequestInputId(InputVO data, BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();

        System.out.println("조회하려는 회원의 아이디를 입력해주세요.");
        data.getUser().setUserId(br.readLine());

        return funName = PrintFunctionName.PRINT_MEMBER_BY_ID;
    }

    public PrintFunctionName printMemberById(UserVO user) {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();

        System.out.println("이름: " + user.getName());
        System.out.println("생년월일: " + user.getBirth());
        System.out.println("이메일: " + user.getEmail());
        System.out.println("전화번호: " + user.getTel());
        System.out.println("주소: " + user.getAddress());
        System.out.println("사업자명: " + user.getBusinessName());
        System.out.println("사업자번호: " + user.getBusinessNumber());

        return funName = PrintFunctionName.EXIT;
    }

    public PrintFunctionName printRequestInputBusinessNumber(InputVO data, BufferedReader br) throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();

        System.out.println("조회하려는 회원의 사업자 번호를 입력해주세요.");
        data.getUser().setBusinessNumber(br.readLine());

        return funName = PrintFunctionName.PRINT_MEMBER_BY_BUSINESS_NUMBER;
    }

    public PrintFunctionName printMemberByBusinessNumber(UserVO user) {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();

        System.out.println("이름: " + user.getName());
        System.out.println("생년월일: " + user.getBirth());
        System.out.println("이메일: " + user.getEmail());
        System.out.println("전화번호: " + user.getTel());
        System.out.println("주소: " + user.getAddress());
        System.out.println("사업자명: " + user.getBusinessName());
        System.out.println("사업자번호: " + user.getBusinessNumber());

        return funName = PrintFunctionName.EXIT;
    }

    public PrintFunctionName printMembershipManagementForMember(BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println(
                "1. 내정보 보기\n"
                        + "2. 내정보 수정\n"
                        + "3. 비밀번호 변경\n"
                        + "4. 회원 탈퇴\n"
                        + "5. 돌아가기"
        );
        try {
            int sel = this.printAskNumber(br, "메뉴를 입력하세요 :", 5);
            switch (sel) {
                case 1 -> funName = PrintFunctionName.PRINT_MEMBER_DETAILS;
                case 2 -> funName = PrintFunctionName.PRINT_UPDATE_MEMBER_DETAILS;
                case 3 -> funName = PrintFunctionName.PRINT_UPDATE_MEMBER_PASSWORD;
                case 4 -> funName = PrintFunctionName.PRINT_CANCEL_ACCOUNT;
                case 5 -> funName = PrintFunctionName.EXIT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_MEMBERSHIP_MANAGEMENT;
        }
        return funName;
    }

    public PrintFunctionName printMemberDetails(MainVO data, BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        UserVO user = data.getUser();
        System.out.flush();
        this.printLogo();
        System.out.println("이름: " + user.getName());
        System.out.println("생년월일: " + user.getBirth());
        System.out.println("이메일: " + user.getEmail());
        System.out.println("전화번호: " + user.getTel());
        System.out.println("주소: " + user.getAddress());
        System.out.println("사업자명: " + user.getBusinessName());
        System.out.println("사업자번호: " + user.getBusinessNumber());
        return funName = PrintFunctionName.EXIT;
    }

    public PrintFunctionName printUpdateMemberDetails(InputVO data, BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        UserVO user = data.getUser();
//    try {
        System.out.println("사용자(" + user.getUserId() + ")에 대한 변경 사항들을 입력해주십시오.");
        System.out.print("이름: ");
        user.setName(br.readLine());
        System.out.print("생년월일: ");
        user.setBirth(java.sql.Date.valueOf(br.readLine()));
        System.out.print("이메일: ");
        user.setEmail(br.readLine());
        System.out.print("전화번호: ");
        user.setTel(br.readLine());
        System.out.print("주소: ");
        user.setAddress(br.readLine());
        System.out.print("사업자명: ");
        user.setBusinessName(br.readLine());
        System.out.print("사업자번호: ");
        user.setBusinessNumber(br.readLine());
//    } catch (WrongInputNumberException e) {
//      System.err.println(e.getMessage());
//      funName = PrintFunctionName.PRINT_UPDATE_MEMBER_DETAILS;
//    }
        funName = PrintFunctionName.EXIT;
        return funName;
    }

    public PrintFunctionName printUpdateMemberPassword(String pwd, InputVO data, BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();

        System.out.println("현재 비밀번호를 입력하세요");
        String currentPwd = br.readLine();

        System.out.println("변경할 비밀번호를 입력하세요");
        String nextPwd = br.readLine();
        System.out.println("다시 한 번 입력하세요");
        String nextPwd2 = br.readLine();

        data.setCheck(pwd.equals(currentPwd) && nextPwd.equals(nextPwd2));

        if (data.getCheck()) {
            data.getUser().setPassword(nextPwd);
            System.out.println("비밀번호 변경에 성공하였습니다.");
        } else {
            System.out.println("비밀번호 변경에 실패하였습니다.");
        }

        funName = PrintFunctionName.PRINT_CHANGE_PASSWORD_RESULT;

        return funName;
    }

    public PrintFunctionName printCancelAccount(MainVO data, InputVO temp, BufferedReader br)
            throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println("비밀번호를 입력하세요");
        String pwd = br.readLine();

        temp.setCheck(data.getUser().getPassword().equals(pwd));

        if (temp.getCheck()) {
            System.out.println("회원 탈퇴가 완료되었습니다.");
        } else {
            System.out.println("회원 탈퇴에 실패하였습니다.");
        }

        return funName = PrintFunctionName.EXIT;
    }

    public PrintFunctionName printMembershipManagementForGuest() throws IOException {
        PrintFunctionName funName = null;
        System.out.flush();
        this.printLogo();
        System.out.println("비회원은 사용할 수 없는 기능입니다.");
        funName = PrintFunctionName.EXIT;
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
                case 3:funName = PrintFunctionName.EXIT;break;
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
                case 3->funName = PrintFunctionName.EXIT;
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

        return funName = PrintFunctionName.EXIT;
    }
    public PrintFunctionName printDeleteProduct(InputVO inputData,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        ProductVO input = inputData.getProduct();
        System.out.println("[상품 삭제]");
        System.out.print("삭제 할 상품 id를 입력하세요: ");
        input.setProductId(Integer.parseInt(br.readLine()));
        return funName = PrintFunctionName.EXIT;
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
                        case 5->funName = PrintFunctionName.EXIT;
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
                        case 2->funName = PrintFunctionName.EXIT;
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
                case 2->funName = PrintFunctionName.EXIT;
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

        return PrintFunctionName.EXIT;
    }
    public PrintFunctionName printInquiryWarehouseByID(ArrayList<WarehouseVO> arr,BufferedReader br)throws IOException{

        System.out.println("[창고 조회]");
        System.out.println("창고id | 창고 이름 | 창고 주소 | 창고 면적 | 관리자 ID");
        for(WarehouseVO vo :arr){
        System.out.println(
                vo.getStorageId()+" | "+
                        vo.getStorageName()+" | "+
                        vo.getAddress()+" | "+
                        vo.getStorageArea()+" | "+
                        vo.getManagerId()+" | "
        );
        }
        System.out.print("아무키나 입력하면 돌아갑니다. ");
        br.readLine();
        return PrintFunctionName.EXIT;
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
        return PrintFunctionName.EXIT;
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
                        case 5->funName = PrintFunctionName.EXIT;
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
                        case 1->funName = PrintFunctionName.PRINT_INQUIRY_EXPDET;
                        case 2->funName = PrintFunctionName.PRINT_INSERT_EXPDET;
                        case 3->funName = PrintFunctionName.PRINT_INQUIRY_SALDET;
                        case 4->funName = PrintFunctionName.PRINT_INQUIRY_REVENUE;
                        case 5->funName = PrintFunctionName.EXIT;
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
                        case 3->funName = PrintFunctionName.EXIT;
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
                case 3->funName = PrintFunctionName.EXIT;
            }
        } catch (WrongInputNumberException e) {
            System.err.println(e.getMessage());
            funName = PrintFunctionName.PRINT_INQUIRY_EXPDET;
        }
        return funName;
    }
    public PrintFunctionName printInquirySalDet(ArrayList<SalesDetailsVO> arr,BufferedReader br) throws IOException {
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
        System.out.print("아무키나 입력하면 돌아갑니다. ");
        br.readLine();
        return PrintFunctionName.EXIT;
    }
    public PrintFunctionName printInquiryRevenue(int revenue, BufferedReader br)throws IOException{
        System.out.println("[정산 내역 조회]");
        System.out.printf("총 순이익은 %d\n",revenue);
        System.out.println("\n1. 돌아가기");
        System.out.print("아무키나 입력하면 돌아갑니다. ");
        br.readLine();
        return PrintFunctionName.EXIT;
    }
    public PrintFunctionName printInsertExpDet(MainVO data,InputVO inputData, BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        String date = "";
        ExpenditureDetailsVO expDet = inputData.getExpDet();
        System.out.println("[지출 등록]");
        expDet.setExpenditureDetailsDate(Date.valueOf(LocalDate.now()));
        System.out.print("비용 : ");
        expDet.setCost(Integer.parseInt(br.readLine()));
        expDet.setWarehouseId(data.getWarehouseID());
        System.out.print("카테고리 ID : ");
        expDet.setCategoryId(Integer.parseInt(br.readLine()));
        return PrintFunctionName.EXIT;
    }
    public PrintFunctionName printInquiryContract(ArrayList<ContractVO> arr,BufferedReader br)throws IOException{
        System.out.println("[계약 내역 조회]");
        System.out.println("user id | 계약날짜 | 만료날짜 | 면적 | 임대료");
        for(ContractVO vo:arr){
            System.out.println(
                    vo.getUserId()+" | "+
                            vo.getContractDate()+" | "+
                            vo.getExpirationDate()+" | "+
                            vo.getContractArea()+" | "+
                            vo.getContractCost()
            );
        }
        System.out.print("아무키나 입력하면 돌아갑니다. ");
        br.readLine();
        return PrintFunctionName.EXIT;
    }
    public PrintFunctionName printInsertContract(MainVO data,InputVO inputData,BufferedReader br)throws IOException{
        PrintFunctionName funName = null;
        String date = "";
        ContractVO contract = inputData.getContract();
        System.out.println("[계약 등록]");
        contract.setUserId(data.getUser().getUserId());
        System.out.print("임대면적 : ");
        contract.setContractArea(Integer.parseInt(br.readLine()));
        System.out.print("임대료 : ");
        contract.setContractCost(Integer.parseInt(br.readLine()));
        contract.setContractDate(Date.valueOf(LocalDate.now()));
        System.out.print("만료 날짜 (yyyy-mm-dd) : ");
        contract.setContractDate(Date.valueOf(br.readLine()));

        //매출내역 바로 여기서 찍자=>창고 셋팅은 어케하지...스케줄러 없음ㅋ
        SalesDetailsVO salDet = inputData.getSalDet();
        salDet.setCost(contract.getContractCost());
        return PrintFunctionName.EXIT;
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
