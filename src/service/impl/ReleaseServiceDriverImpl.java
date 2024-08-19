package service.impl;

import common.dao.ReleaseDAO;
import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class ReleaseServiceDriverImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ReleaseDAO releaseDAO = new ReleaseDAO();

    /*
     *  운송기사 기능 구현
     */

    public static void releaseDriverMenu() throws IOException, SQLException, InterruptedException { //운송기사 메인메뉴

        System.out.println("[출고 메뉴]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.운송장조회\t\t2.차량관리\t\t3.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            searchWaybillDriver();
        } else if (selNo == 2) {
            carManageDriver();
        } else if (selNo == 3) {
            //메인메뉴로 나가기
        }

    } //운송기사 메인메뉴


    public static void searchWaybillDriver() throws SQLException, IOException, InterruptedException { //운송장 조회
        System.out.println("[운송장 내역]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        List<WaybillVO> waybillAllList = releaseDAO.selectWaybillAllList();

        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                "운송장번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");


        for (WaybillVO waybillVO : waybillAllList) {
            System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                    waybillVO.getWaybillId(),
                    waybillVO.getDepartDate(),
                    waybillVO.getProductId(),
                    waybillVO.getProductName(),
                    waybillVO.getStartAddress(),
                    waybillVO.getArriveAddress(),
                    waybillVO.getArriveAddressDetail());
        }

        System.out.println("[운송장 조회 메뉴]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.println("1.기간별 조회\t\t2.운송장 상세보기\t\t3.나가기");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            System.out.println("1.연도별 조회\t\t2.월별 조회\t\t3.일별 조회\t\t4.나가기");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if (selNo == 1) { //연도별 조회
                List<WaybillVO> waybillYearList = releaseDAO.selectWaybillYearList();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillYearList) {
                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            waybillVO.getWaybillId(),
                            waybillVO.getDepartDate(),
                            waybillVO.getProductId(),
                            waybillVO.getProductName(),
                            waybillVO.getStartAddress(),
                            waybillVO.getArriveAddress(),
                            waybillVO.getArriveAddressDetail());
                }

                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.상세보기\t\t2.나가기");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());




                if (selNo == 1) {
                    System.out.print("조회할 운송장 번호를 입력해주세요 : ");
                    int waybillId = Integer.parseInt(br.readLine());
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail(waybillId);

                    System.out.println("[운송장]");
                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                "운송장 번호 : " + waybillVO.getWaybillId(),
                                "출고번호 : " + waybillVO.getDeliveryRequestId(),
                                "출발날짜 : " + waybillVO.getDepartDate(),
                                "상품번호 : " + waybillVO.getProductId(),
                                "상품이름 : " + waybillVO.getProductName(),
                                "배송수량 : " + waybillVO.getDeliveryQuantity(),
                                "사업자명 : " + waybillVO.getBusinessName(),
                                "발송지 주소 : " + waybillVO.getStartAddress(),
                                "사업자 전화번호 : " + waybillVO.getBusinessTel(),
                                "배송지 주소 : " + waybillVO.getArriveAddress(),
                                "배송지 상세주소 : " + waybillVO.getArriveAddressDetail(),
                                "요청 코멘트 : " + waybillVO.getRequestComment());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.배송 완료\t\t2.나가기");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if(selNo == 1) {
                        releaseDAO.completeDelivery(waybillId);
                    } else if (selNo == 2){
                        releaseDriverMenu();
                    }


                } else if (selNo == 2) {
                    releaseDriverMenu();
                }


            } else if (selNo == 2) { //월별 조회
                List<WaybillVO> waybillMonthList = releaseDAO.selectWaybillMonthList();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillMonthList) {
                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            waybillVO.getWaybillId(),
                            waybillVO.getDepartDate(),
                            waybillVO.getProductId(),
                            waybillVO.getProductName(),
                            waybillVO.getStartAddress(),
                            waybillVO.getArriveAddress(),
                            waybillVO.getArriveAddressDetail());
                }

                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.상세보기\t\t2.나가기");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    System.out.print("조회할 운송장 번호를 입력해주세요 : ");
                    int waybillId = Integer.parseInt(br.readLine());
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail(waybillId);

                    System.out.println("[운송장]");
                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                "운송장 번호 : " + waybillVO.getWaybillId(),
                                "출고번호 : " + waybillVO.getDeliveryRequestId(),
                                "출발날짜 : " + waybillVO.getDepartDate(),
                                "상품번호 : " + waybillVO.getProductId(),
                                "상품이름 : " + waybillVO.getProductName(),
                                "배송수량 : " + waybillVO.getDeliveryQuantity(),
                                "사업자명 : " + waybillVO.getBusinessName(),
                                "발송지 주소 : " + waybillVO.getStartAddress(),
                                "사업자 전화번호 : " + waybillVO.getBusinessTel(),
                                "배송지 주소 : " + waybillVO.getArriveAddress(),
                                "배송지 상세주소 : " + waybillVO.getArriveAddressDetail(),
                                "요청 코멘트 : " + waybillVO.getRequestComment());
                    }
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.배송 완료\t\t2.나가기");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if(selNo == 1) {
                        releaseDAO.completeDelivery(waybillId);
                    } else if (selNo == 2){
                        releaseDriverMenu();
                    }



                } else if (selNo == 2) {
                    releaseDriverMenu();
                }

            } else if (selNo == 3) { //일별 조회
                List<WaybillVO> waybillDayList = releaseDAO.selectWaybillDayList();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillDayList) {
                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            waybillVO.getWaybillId(),
                            waybillVO.getDepartDate(),
                            waybillVO.getProductId(),
                            waybillVO.getProductName(),
                            waybillVO.getStartAddress(),
                            waybillVO.getArriveAddress(),
                            waybillVO.getArriveAddressDetail());
                }

                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.상세보기\t\t2.나가기");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    System.out.print("조회할 운송장 번호를 입력해주세요 : ");
                    int waybillId = Integer.parseInt(br.readLine());
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail(waybillId);

                    System.out.println("[운송장]");
                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                "운송장 번호 : " + waybillVO.getWaybillId(),
                                "출고번호 : " + waybillVO.getDeliveryRequestId(),
                                "출발날짜 : " + waybillVO.getDepartDate(),
                                "상품번호 : " + waybillVO.getProductId(),
                                "상품이름 : " + waybillVO.getProductName(),
                                "배송수량 : " + waybillVO.getDeliveryQuantity(),
                                "사업자명 : " + waybillVO.getBusinessName(),
                                "발송지 주소 : " + waybillVO.getStartAddress(),
                                "사업자 전화번호 : " + waybillVO.getBusinessTel(),
                                "배송지 주소 : " + waybillVO.getArriveAddress(),
                                "배송지 상세주소 : " + waybillVO.getArriveAddressDetail(),
                                "요청 코멘트 : " + waybillVO.getRequestComment());
                    }
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.배송 완료\t\t2.나가기");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if(selNo == 1) {
                        releaseDAO.completeDelivery(waybillId);
                    } else if (selNo == 2){
                        releaseDriverMenu();
                    }


                } else if (selNo == 2) {
                    releaseDriverMenu();
                }

            }


        } else if (selNo == 2) {
            System.out.print("조회할 운송장 번호를 입력해주세요 : ");
            int waybillId = Integer.parseInt(br.readLine());
            List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail(waybillId);

            System.out.println("[운송장]");
            for (WaybillVO waybillVO : waybillDetail) {
                System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                        "운송장 번호 : " + waybillVO.getWaybillId(),
                        "출고번호 : " + waybillVO.getDeliveryRequestId(),
                        "출발날짜 : " + waybillVO.getDepartDate(),
                        "상품번호 : " + waybillVO.getProductId(),
                        "상품이름 : " + waybillVO.getProductName(),
                        "배송수량 : " + waybillVO.getDeliveryQuantity(),
                        "사업자명 : " + waybillVO.getBusinessName(),
                        "발송지 주소 : " + waybillVO.getStartAddress(),
                        "사업자 전화번호 : " + waybillVO.getBusinessTel(),
                        "배송지 주소 : " + waybillVO.getArriveAddress(),
                        "배송지 상세주소 : " + waybillVO.getArriveAddressDetail(),
                        "요청 코멘트 : " + waybillVO.getRequestComment());
            }
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.println("1.배송 완료\t\t2.나가기");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if(selNo == 1) {
                releaseDAO.completeDelivery(waybillId);
            } else if (selNo == 2){
                releaseDriverMenu();
            }

        } else if (selNo == 3) {
            releaseDriverMenu();
        }

    } //운송장 조회

    public static void carManageDriver() throws SQLException, IOException, InterruptedException { //차량 관리
        System.out.println("[차량 목록]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        List<CarVO> carAllList = releaseDAO.selectCarAllList();

        System.out.printf("%-20s\t%-20s\t%-20s\n", "차량번호", "차종", "최대 적재량");

        for(CarVO carVO : carAllList) {
            System.out.printf("%-20s\t%-20s\t%-20s\n",
                    carVO.getCarNumber(),
                    carVO.getCarType(),
                    carVO.getMaxLoad());
        };

        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.println("1.차량등록\t\t2.차량수정\t\t3.나가기");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if(selNo == 1) {
            releaseDAO.registerCar();
            System.out.println("차량 등록이 완료되었습니다.");
            releaseDriverMenu();
        } else if (selNo == 2) {
            releaseDAO.updateCar();
            System.out.println("차량 수정이 완료되었습니다.");
            releaseDriverMenu();
        } else if (selNo == 3) {
            releaseDriverMenu();
        }

    } //차량관리



    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        releaseDriverMenu();
    }

}