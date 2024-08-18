package frontend;

import service.WMServiceGM;
import service.impl.WarehouseManagementImpl;
import vo.WarehouseVO;

import java.util.Scanner;

/*일단 예시로 대충 UI 짠거야 다 바꿀예정 구조가 어떤지만 봐봐.=>이 클래스를 싱글턴으로 구현해도 좋겠네 */
public class PrintImpl {
    //창고관리 총관리자 메서드 예시
    public void printMainMenuGM(){

        WMServiceGM service = new WarehouseManagementImpl();

        Scanner sc = new Scanner(System.in);
        System.out.println("1. 창고등록  2. 창고삭제  3. 창고조회\n");
        System.out.println("번호를 입력하세요 : ");
        int tmp = sc.nextInt();
        switch (tmp){
            case 1->printInsertWarehouse(service,sc);
            case 2->printDeleteWarehouse(service,sc);
            case 3->printInquiryWarehouse(service,sc);
        }
    }
    private void printInsertWarehouse(WMServiceGM service,Scanner sc){
        WarehouseVO data = new WarehouseVO();
        System.out.println("창고명을 입력하세요 : ");
        String storageName = sc.next();
        /*요런식으로 입력을 받고 서비스객체로 넘겨줘서 처리하자.=>서버에 넘기자.*/
        service.insertWarehouse(data);//이것도 void 말고 잘 처리여부를 bool값으로 줘서 그에따라 화면에 보여주면 좋겟네
    }
    private void printDeleteWarehouse(WMServiceGM service,Scanner sc){

    }
    private void printInquiryWarehouse(WMServiceGM service,Scanner sc){

    }
}
