package threadClass;

import exception.ExitException;
import exception.NoPermissionException;
import frontend.PrintFunctionName;
import frontend.PrintServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import service.*;
import service.impl.*;
import util.Role;
import vo.InputVO;
import vo.MainVO;
import vo.UserVO;

/*이 클래스가 하나하나 클라이언트라고 생각하면 편해. main 에서 이 쓰레드 클래스 객체를 여러개 동시에 run 시킬거야.
 * 즉, 클라이언트가 연결요청을 하면 이 클래스에 담아서 돌릴 거라는 말이야.
 * */
public class ThreadEx implements Runnable {
    /*필드영역
     *
     * userVO 유저정보
     *
     *
     *
     *
     * */

    private MainVO mainVO;
    private InputVO tempInput = new InputVO();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private Stack<PrintFunctionName> functionStack = new Stack<>();
    private LoginService loginService = new LoginServiceImpl();
    private UserManagementServiceForUserImpl userManagementServiceForUser = new UserManagementServiceForUserImpl();
    private UserManagementServiceImpl userManagementService = new UserManagementServiceImpl();

    public ThreadEx() {
        mainVO = new MainVO(new UserVO(), -1);
        mainVO.getUser().setRole(Role.GUEST);
    }

    @Override
    public void run() {
        // 여기다가 다 박아넣기
        try {
            PrintServiceImpl printService = PrintServiceImpl.getInstance();
            functionStack.push(PrintFunctionName.PRINT_START_MENU);
            while (!functionStack.isEmpty()) {
                PrintFunctionName nextFunction = null;
                switch (functionStack.peek()) {
                    case PRINT_START_MENU -> {
                        nextFunction = printService.printStartMenu(mainVO, br);
                    } // 시작 메뉴
                    case PRINT_LOGIN_MENU -> {
                        nextFunction = printService.printLoginMenu(mainVO, br);
                        mainVO.setUser(loginService.login(mainVO.getUser()));
                    } // 로그인 메뉴
                    case PRINT_JOIN_MEMBERSHIP -> {
                        nextFunction = printService.printJoinMembership(mainVO, br);
                        loginService.registerUser(tempInput.getUser());
                    } // 회원가입
                    case PRINT_FIND_ID -> {
                        String email = "";
                        nextFunction = printService.printFindID(email, br);
                        loginService.findId(email);
                    } // 아이디 찾기
                    case PRINT_FIND_PW -> {
                        String pwd = "";
                        nextFunction = printService.printFindPW(pwd, br);
                        loginService.findPwd(pwd);
                    } // 비밀번호 찾기
                    case PRINT_MAIN_MENU -> {
                        nextFunction = printService.PrintMainMenu(br);
                    } // 로그인 후 메인 메뉴
                    case PRINT_MEMBERSHIP_MANAGEMENT -> {
                        if (mainVO.getUser().getRole().equals(Role.ADMIN)) {  // 관리자
                            nextFunction = printService.printMembershipManagementForAdmin(br);
                        } else if (mainVO.getUser().getRole().equals(Role.GUEST)){  // 비회원
                            nextFunction = printService.printMembershipManagementForGuest();
                        } else {  // 나머지 회원
                            nextFunction = printService.printMembershipManagementForMember(br);
                        }
                    } // 회원 관리
                    case PRINT_MEMBER_DETAILS -> {
                        nextFunction = printService.printMemberDetails(mainVO, br);
                    } // 내 정보 출력
                    case PRINT_UPDATE_MEMBER_DETAILS -> {
                        nextFunction = printService.printUpdateMemberDetails(tempInput, br);
                        userManagementServiceForUser.updateUserDetails(mainVO.getUser(), tempInput.getUser());
                    } // 내정보 수정
                    case PRINT_UPDATE_MEMBER_PASSWORD -> {
                        nextFunction = printService.printUpdateMemberPassword(mainVO.getUser().getPassword(), tempInput, br);
                        if (tempInput.getCheck()) userManagementServiceForUser.updatePassword(mainVO.getUser(), tempInput.getUser().getPassword());
                    } // 비밀번호 변경
                    case PRINT_CANCEL_ACCOUNT -> {
                        nextFunction = printService.printCancelAccount(mainVO, tempInput, br);
                        if (tempInput.getCheck()) userManagementServiceForUser.cancelAccount(mainVO.getUser());
                    } // 회원 탈퇴
                    case PRINT_SELECT_MEMBER_MENU -> {
                        nextFunction = printService.printSelectMemberMenu(br);
                    } // 회원 조회 메뉴
                    case PRINT_MEMBER_LIST -> {
                        ArrayList<UserVO> userList = userManagementService.printAllUsers();
                        nextFunction = printService.printMemberList(userList, "memberList");
                    } // 전체 회원 조회
                    case PRINT_REQUEST_INPUT_ID -> {
                        nextFunction = printService.printRequestInputId(tempInput, br);
                        tempInput.setUser(userManagementService.printOneUserDetailsById(tempInput.getUser().getUserId()));
                    } // 아이디로 회원 조회 입력
                    case PRINT_MEMBER_BY_ID -> {
                        nextFunction = printService.printMemberById(tempInput.getUser());
                    } // 아이디로 회원 조회 출력
                    case PRINT_REQUEST_INPUT_BUSINESS_NUMBER -> {
                        nextFunction = printService.printRequestInputBusinessNumber(tempInput, br);
                        tempInput.setUser(userManagementService.printOneUserDetailsByBusinessNumber(tempInput.getUser().getBusinessNumber()));
                    } // 사업자 번호로 회원 조회 입력
                    case PRINT_MEMBER_BY_BUSINESS_NUMBER -> {
                        nextFunction = printService.printMemberByBusinessNumber(tempInput.getUser());
                    } // 사업자 번호로 회원 조회 출력
                    case PRINT_WAITING_MEMBER_LIST -> {
                        ArrayList<UserVO> userList = userManagementService.printWaitingUsers();
                        nextFunction = printService.printMemberList(userList, "waitingMemberList");
                    } // 대기자 리스트 출력
                    case PRINT_APPROVE_REGISTRATION_REQUEST -> {
                        nextFunction = printService.printApproveRegistrationRequest(tempInput, br);
                        userManagementService.approveRegistrationRequest(tempInput.getUser().getUserId(), tempInput.getCheck());
                    } // 승인 처리
                    //창고
                    case PRINT_WAREHOUSE_MANAGEMENT ->{
                        nextFunction = printService.printWarehouseManagement(mainVO,br);
                    }
                    case PRINT_INQUIRY_WH->{
                        WMServiceGM service = new WarehouseManagementImpl();
                        nextFunction = printService.printInquiryWarehouse(service.inquiryAllWarehouse(),br);
                    }
                    case PRINT_INQUIRY_WH_BY_ID->{
                        WMServiceWM service = new WarehouseManagementImpl();
                        nextFunction = printService.printInquiryWarehouseByID(service.warehouseInquiryById(mainVO.getUser()),br);
                    }
                    /*case PRINT_INQUIRY_WH_BY_NAME->{
                        WMServiceGM service = new WarehouseManagementImpl();
                        String name = "";
                        nextFunction = printService.printInquiryWarehouseByName();
                    }
                    case PRINT_INQUIRY_WH_BY_LOCATION->{}구현안함*/
                    case PRINT_INSERT_WH->{
                        WMServiceGM service = new WarehouseManagementImpl();
                        nextFunction = printService.printInsertWarehouse(tempInput,br);
                        service.insertWarehouse(tempInput.getWarehouse());
                    }
                    case PRINT_DELETE_WH->{
                        WMServiceGM service = new WarehouseManagementImpl();
                        nextFunction = printService.printDeleteWarehouse(tempInput,br);
                        service.deleteWarehouse(tempInput.getWarehouse());
                    }
                    //재무
                    /*case PRINT_FINANCIAL_MANAGEMENT ->{

                    }
                    case PRINT_INQUIRY_REVENUE->{}
                    case PRINT_INQUIRY_EXPDET->{}
                    case PRINT_INQUIRY_SALDET->{}
                    case PRINT_INSERT_EXPDET ->{}
                    case PRINT_UPDATE_EXPDET->{}
                    case PRINT_DELETE_EXPDET->{}
                    case PRINT_INQUIRY_CONTRACT->{}
                    case PRINT_INQUIRY_CONTRACT_BY_ID->{}
                    case PRINT_INSERT_CONTRACT->{}*/
                    //상품
                    case PRINT_PRODUCT_MANAGEMENT ->{
                        nextFunction = printService.printProductManagement(mainVO,tempInput,br);
                    }
                    case PRINT_INQUIRY_PRODUCTS->{
                        PMserviceGM service = new ProductManagementImpl();
                        nextFunction = printService.printInquiryProducts(service.inquiryAllProduct(),br);
                    }
                    case PRINT_UPDATE_PRODUCT->{
                        PMserviceGM service = new ProductManagementImpl();
                        nextFunction = printService.printUpdateProduct(tempInput,br);
                        service.updateProduct(tempInput.getProduct());
                    }
                    case PRINT_DELETE_PRODUCT->{
                        PMserviceGM service = new ProductManagementImpl();
                        nextFunction = printService.printDeleteProduct(tempInput,br);
                        service.deleteProduct(tempInput.getProduct());
                    }

                    default -> nextFunction = PrintFunctionName.RESTART;
                }
                if (nextFunction.getFunName().equals("EXIT")){
                    functionStack.pop();
                }else if(!nextFunction.getFunName().equals(functionStack.peek().getFunName())){
                    functionStack.push(nextFunction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExitException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NoPermissionException e) {
            throw new RuntimeException(e);
        }
    }
}