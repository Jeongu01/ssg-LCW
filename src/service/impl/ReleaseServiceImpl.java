package service.impl;

import common.dao.ReleaseDAO;
import vo.ReleaseVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class ReleaseServiceImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ReleaseDAO releaseDAO = new ReleaseDAO();


    /*
     *  관리자 기능 구현
     */
    public static void releaseManagerMenu() throws IOException, SQLException, InterruptedException {

        System.out.println("[출고 메뉴]");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("1.출고요청승인\t\t2.출고관리\t\t3.운송장관리\t\t4.배차관리");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            releaseApprove();
        } 

    }


    public static void releaseApprove() throws IOException, SQLException, InterruptedException {

        List<ReleaseVO> notApprovedReleaseList = releaseDAO.selectNotApprovedReleaseList();

        System.out.printf("%s %20s %20s %20s %20s %20s %20s %20s %20s\n", "출고요청서ID", "사용자ID", "요청날짜", "상품번호", "상품이름", "요청수량", "발송지 주소", "수령지 주소", "수령지 상세주소");

        for (ReleaseVO releaseVO : notApprovedReleaseList) {
            System.out.printf("%s %20s %20s %20s %20s %20s %20s %20s %20s\n",
                    releaseVO.getRequestId(),
                    releaseVO.getUserId(),
                    releaseVO.getRequestDate(),
                    releaseVO.getProductId(),
                    releaseVO.getProductName(),
                    releaseVO.getRequestQuantity(),
                    releaseVO.getUserAddress(),
                    releaseVO.getDeliveryAddress(),
                    releaseVO.getDeliveryAddressDetail()
            );
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("1.승인하기\t\t2.취소");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            System.out.println("출고번호를 입력해주세요 : ");
            System.out.println("해당번호의 요청을 승인하시겠습니까?");
            System.out.println("1.예 2.아니요");
            selNo = Integer.parseInt(br.readLine());

            if(selNo == 1) {
                releaseDAO.approveReleaseRequest();
                System.out.println("배차를 등록해주세요.");
            } else if (selNo == 2) {
                releaseManagerMenu();
            }

        } else if (selNo == 2) {
            releaseManagerMenu();
        }
    }
    
    


    /*
     *  회원 기능 구현
     */

    /*
     *  운송기사 기능 구현
     */


    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        releaseManagerMenu();
    }
}


