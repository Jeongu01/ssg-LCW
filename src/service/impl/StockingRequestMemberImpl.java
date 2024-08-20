package service.impl;

import common.dao.StockingRequestDAO;
import frontend.PrintFunctionName;
import vo.StockingRequestVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class StockingRequestMemberImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StockingRequestDAO dao = new StockingRequestDAO();

    /*
     * 회원 메뉴
     */

    public static PrintFunctionName stockingRequestMemberMenu() throws IOException, SQLException { //회원 메뉴

        System.out.println("[입고 메뉴]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.입고요청\t\t2.내 입고요청서 조회\t\t3.요청취소\t\t4.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            requestStockingRequest();
        } else if (selNo == 2) {
            selectMyRequestList();
        } else if (selNo == 3) {
            cancleRequest();
        }

        return PrintFunctionName.EXIT;

    } //회원 메뉴

    public static void requestStockingRequest() throws IOException, SQLException {
        System.out.println("[입고 요청]");
        StockingRequestVO data = new StockingRequestVO();

        System.out.print("사용자ID : ");
        data.setUserId(br.readLine());
        System.out.print("상품번호 : ");
        data.setProductId(Integer.parseInt(br.readLine()));
        System.out.print("창고번호 : ");
        data.setStorageId(Integer.parseInt(br.readLine()));
        System.out.print("요청수량 : ");
        data.setRequestQuantity(Integer.parseInt(br.readLine()));
        System.out.print("요청 코멘트 : ");
        data.setRequestComment(br.readLine());

        dao.insertStockingRequest(data);

        System.out.println("입고요청이 완료되었습니다.");

        stockingRequestMemberMenu();
    } //입고 요청

    public static void selectMyRequestList() throws IOException, SQLException { //내 입고요청서 조회
        System.out.println("[입고요청서 리스트]");
        System.out.print("사용자ID : ");
        String userId = br.readLine();

        List<StockingRequestVO> ret4 = dao.selectInquiryWarehouseRequests(userId);

        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "승인날짜", "처리날짜", "요청수량", "요청 코멘트");

        for (StockingRequestVO vo : ret4) {
            System.out.printf("%-20s\t\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                    vo.getStockingRequestId(),
                    vo.getUserId(),
                    vo.getProductId(),
                    vo.getStorageId(),
                    vo.getRequestId(),
                    vo.getApprovedDate(),
                    vo.getCompleteDate(),
                    vo.getRequestQuantity(),
                    vo.getRequestComment());
        }
        stockingRequestMemberMenu();

    } //내 입고요청서 조회

    public static void cancleRequest() throws IOException, SQLException {
        StockingRequestVO data = new StockingRequestVO();

        System.out.println("[입고 요청 취소]");
        System.out.println("**주의**");
        System.out.println("**승인되면 요청 취소할 수 없습니다**");
        System.out.print("취소할 입고 요청 번호를 입력해주세요 : ");
        data.setStockingRequestId(Integer.parseInt(br.readLine()));
        dao.cancleStockingRequestMember(data);
        System.out.println("취소가 완료되었습니다.");
        stockingRequestMemberMenu();
    }





    public static void main(String[] args) throws SQLException, IOException {
        stockingRequestMemberMenu();
    }
}