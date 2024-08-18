package frontend;

import exception.ExitException;
import exception.WrongInputNumberException;
import service.LoginService;
import util.Role;
import vo.MainVO;
import vo.UserVO;

import java.util.Scanner;

//싱글턴 : 출력 서비스 클래스
public class PrintServiceImp {
    private static PrintServiceImp instance = null;

    private PrintServiceImp(){
    }

    public PrintServiceImp getInstance(){
        if(instance == null){
            instance = new PrintServiceImp();
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

    private int printAskNumber(Scanner sc,String message){
        System.out.print(message);
        return sc.nextInt();
    }
    public int printStartMenu(Scanner sc){
        System.out.println(
                        "1. 로그인\n"+
                        "2. 회원가입\n"+
                        "3. ID 찾기\n"+
                        "4. password 찾기\n"+
                        "5. 종료\n");
        return this.printAskNumber(sc,"메뉴번호를 입력하세요:");
    }
    //회원로그인시 userVO에 id,password담아서 돌려줌...비회원시 Role = Guest돌려주기로
    public void printLoginMenu(MainVO data, Scanner sc) throws ExitException,WrongInputNumberException {
        UserVO user = data.getUser();
        System.out.println(
                        "1. 회원 로그인\n"+
                        "2. 비회원 로그인\n"+
                        "3. 종료\n");
        int sel = this.printAskNumber(sc,"번호를 입력하세요.");
        switch (sel){
            case 1:
                System.out.print("ID : ");
                user.setUserId(sc.next());
                System.out.print("PassWord : ");
                user.setPassword(sc.next());
                break;
            case 2:user.setRole(Role.GUEST);break;
            case 3:throw new ExitException();
            default:throw new WrongInputNumberException();
        }
    }
    //회원가입정보 UserVo에 담아서 돌려줌=>이상한값넣었을때 예외발생시키는것도 여기서 처리하자.
    public void printJoinMembership(MainVO data, Scanner sc) throws WrongInputNumberException{
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
        int sel = this.printAskNumber(sc,"회원유형을 입력하세요.");
        switch (sel){
            case 1->printJoinMembershipAdmin(user,sc);
            case 2->printJoinMembershipStoreManager(user,sc);
            case 3->printJoinMembershipStoreEmployee(user,sc);
            case 4->printJoinMembershipDeliveryDriver(user,sc);
            case 5->printJoinMembershipSupplier(user,sc);
            case 6->printJoinMembershipWhManager(data,sc);
            default -> throw new WrongInputNumberException();
        }
    }
    private void printJoinMembershipCommon(UserVO user,Scanner sc){
        System.out.print("ID를 입력하세요 : ");
        user.setUserId(sc.next());
        System.out.print("비밀번호를 입력하세요 : ");
        user.setPassword(sc.next());
        System.out.print("이름을 입력하세요 : ");
        user.setName(sc.next());
        System.out.print("주소를 입력하세요: ");
        user.setAddress(sc.next());
        //어차피 번지 이거 2차때 api쓸때나 필요함...
    }
    private void printJoinMembershipAdmin(UserVO user,Scanner sc){
        user.setRole(Role.ADMIN);
        printJoinMembershipCommon(user,sc);
    }
    private void printJoinMembershipStoreManager(UserVO user,Scanner sc){
        user.setRole(Role.STORE_OPERATOR);
        printJoinMembershipCommon(user,sc);
        System.out.print("사업자 이름을 입력하세요: ");
        user.setBusinessName(sc.next());
        System.out.print("사업자 번호를 입력하세요: ");
        user.setBusinessNumber(sc.next());
    }
    private void printJoinMembershipStoreEmployee(UserVO user,Scanner sc){
        user.setRole(Role.EMPLOYEE);
        printJoinMembershipCommon(user,sc);
        System.out.print("사업자 이름을 입력하세요: ");
        user.setBusinessName(sc.next());
        System.out.print("사업자 번호를 입력하세요: ");
        user.setBusinessNumber(sc.next());
    }
    private void printJoinMembershipDeliveryDriver(UserVO user,Scanner sc){
        user.setRole(Role.DELIVERY_DRIVER);
        printJoinMembershipCommon(user,sc);
    }
    private void printJoinMembershipSupplier(UserVO user,Scanner sc){
        user.setRole(Role.SUPPLIER);
        printJoinMembershipCommon(user,sc);
    }
    private void printJoinMembershipWhManager(MainVO data,Scanner sc){
        UserVO user = data.getUser();
        user.setRole(Role.WH_MANAGER);
        printJoinMembershipCommon(user,sc);
        System.out.print("관리할 창고ID를 입력하세요 : ");
        data.setWarehouseId(sc.nextInt());
    }

    public void printFindID(LoginService service){
        //service.findId();
    }
}
