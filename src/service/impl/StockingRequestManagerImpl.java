package service.impl;

import common.dao.StockingRequestDAO;

import frontend.PrintFunctionName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import vo.StockingRequestVO;

public class StockingRequestManagerImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StockingRequestDAO dao = new StockingRequestDAO();

    /*
     * 관리자 메뉴
     */

    public static PrintFunctionName stockingRequestManagerMenu() throws IOException, SQLException { //관리자 메뉴

        System.out.println("[입고 메뉴]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.입고요청서 조회\t\t2.입고 요청 승인\t\t3.입고처리\t\t4.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            selectStockingRequest();
        } else if (selNo == 2) {
            approveStockingRequest();
        } else if (selNo == 3) {
            completeStockingRequest();
        }

        return PrintFunctionName.EXIT;

    } //관리자 메뉴

    public static void selectStockingRequest() throws IOException, SQLException {
        System.out.println("[입고 요청서 조회]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.전체 입고 현황\t\t2.기간별 입고 현황\t\t3.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                    "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "승인날짜", "처리날짜", "요청수량", "요청 코멘트");

            List<StockingRequestVO> ret1 = dao.selectAllStockingRequestList();

            for (StockingRequestVO vo : ret1) {
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
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1.삭제\t\t2.나가기");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if (selNo == 1) {
                System.out.print("입고 요청 번호를 입력해주세요 : ");
                int requestId = Integer.parseInt(br.readLine());
                dao.deleteStockingRequest(requestId);
                System.out.println("삭제가 완료되었습니다.");
                stockingRequestManagerMenu();
            } else if (selNo == 2) {
                stockingRequestManagerMenu();
            }

        } else if (selNo == 2) {
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1.연도별 입고 현황\t\t2.월별 입고 현황\t\t3.일별 입고 현황\t\t4.나가기");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());


            if (selNo == 1) {
                System.out.print("시작 연도 : ");
                String startYear = br.readLine();
                System.out.print("끝 연도 : ");
                String endYear = br.readLine();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "요청수량", "요청 코멘트");

                List<StockingRequestVO> yearStockingRequest = dao.selectYearStockingRequest(startYear, endYear);

                for (StockingRequestVO vo : yearStockingRequest) {
                    System.out.printf("%-20s\t\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            vo.getStockingRequestId(),
                            vo.getUserId(),
                            vo.getProductId(),
                            vo.getStorageId(),
                            vo.getRequestId(),
                            vo.getRequestQuantity(),
                            vo.getRequestComment()
                    );
                }
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("1.삭제\t\t2.나가기");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    System.out.print("입고 요청 번호를 입력해주세요 : ");
                    int requestId = Integer.parseInt(br.readLine());
                    dao.deleteStockingRequest(requestId);
                    System.out.println("삭제가 완료되었습니다.");
                    stockingRequestManagerMenu();
                } else if (selNo == 2) {
                    stockingRequestManagerMenu();
                }


            } else if (selNo == 2) {
                System.out.print("시작 년-월('-'를 사용해서 입력해주세요) : ");
                String startYM = br.readLine();
                System.out.print("끝 년-월('-'를 사용해서 입력해주세요) : ");
                String endYM = br.readLine();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "요청수량", "요청 코멘트");

                List<StockingRequestVO> monthStockingRequest = dao.selectMonthStockingRequest(startYM, endYM);

                for (StockingRequestVO vo : monthStockingRequest) {
                    System.out.printf("%-20s\t\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            vo.getStockingRequestId(),
                            vo.getUserId(),
                            vo.getProductId(),
                            vo.getStorageId(),
                            vo.getRequestId(),
                            vo.getRequestQuantity(),
                            vo.getRequestComment()
                    );
                }
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("1.삭제\t\t2.나가기");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    System.out.print("입고 요청 번호를 입력해주세요 : ");
                    int requestId = Integer.parseInt(br.readLine());
                    dao.deleteStockingRequest(requestId);
                    System.out.println("삭제가 완료되었습니다.");
                    stockingRequestManagerMenu();
                } else if (selNo == 2) {
                    stockingRequestManagerMenu();
                }




            } else if (selNo == 3) {
                System.out.print("시작 년-월-일('-'를 사용해서 입력해주세요) : ");
                String startYMD = br.readLine();
                System.out.print("끝 년-월-일('-'를 사용해서 입력해주세요) : ");
                String endYMD = br.readLine();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "요청수량", "요청 코멘트");

                List<StockingRequestVO> dayStockingRequest = dao.selectDayStockingRequest(startYMD, endYMD);

                for (StockingRequestVO vo : dayStockingRequest) {
                    System.out.printf("%-20s\t\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            vo.getStockingRequestId(),
                            vo.getUserId(),
                            vo.getProductId(),
                            vo.getStorageId(),
                            vo.getRequestId(),
                            vo.getRequestQuantity(),
                            vo.getRequestComment()
                    );
                }
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("1.삭제\t\t2.나가기");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    System.out.print("입고 요청 번호를 입력해주세요 : ");
                    int requestId = Integer.parseInt(br.readLine());
                    dao.deleteStockingRequest(requestId);
                    System.out.println("삭제가 완료되었습니다.");
                    stockingRequestManagerMenu();
                } else if (selNo == 2) {
                    stockingRequestManagerMenu();
                }





            } else if (selNo == 4) {
                stockingRequestManagerMenu();
            }

        } else if (selNo == 3) {
            stockingRequestManagerMenu();
        }
    } //입고요청서 조회

    public static void approveStockingRequest() throws SQLException, IOException {
        System.out.println("[미승인 입고 리스트]");
        List<StockingRequestVO> ret2 = dao.selectUnApprovedStockingRequestList();

        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "승인날짜", "처리날짜", "요청수량", "요청 코멘트");

        for (StockingRequestVO vo : ret2) {
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
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.승인\t\t2.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if(selNo == 1) {
            System.out.print("입고 요청 번호를 입력해주세요 : ");
            int requestId = Integer.parseInt(br.readLine());
            dao.approveStockingRequest(requestId);
            System.out.println("입고 승인이 완료되었습니다.");
            stockingRequestManagerMenu();
        } else if (selNo == 2) {
            stockingRequestManagerMenu();
        }
        
    } //입고 요청 승인

    public static void completeStockingRequest() throws SQLException, IOException { //입고 처리 완료
        System.out.println("[승인 완료된 입고 리스트]");
        List<StockingRequestVO> ret3 = dao.selectApprovedStockingRequest();

        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                "입고 요청 번호", "사용자ID", "상품번호", "창고번호", "요청날짜", "승인날짜", "처리날짜", "요청수량", "요청 코멘트");

        for (StockingRequestVO vo : ret3) {
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
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.입고 처리\t\t2.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if(selNo == 1) {
            System.out.print("입고 요청 번호를 입력해주세요 : ");
            int requestId = Integer.parseInt(br.readLine());
            dao.CompleteStockingRequest(requestId);
            System.out.println("입고 처리가 완료되었습니다.");
            stockingRequestManagerMenu();
        } else if (selNo == 2) {
            stockingRequestManagerMenu();
        }


        
    } //입고 처리 완료



    /*
     * 회원 메뉴
     */


    public static void main(String[] args) throws SQLException, IOException {
        stockingRequestManagerMenu();
    }
}