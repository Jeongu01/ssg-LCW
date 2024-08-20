package service.impl;

import common.dao.ReleaseDAO;
import frontend.PrintFunctionName;
import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class ReleaseServiceMemberImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ReleaseDAO releaseDAO = new ReleaseDAO();


    /*
     *  회원 기능 구현
     */

    public static PrintFunctionName releaseMemberMenu() throws IOException, SQLException, InterruptedException {

        System.out.println("[출고 메뉴]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.출고요청\t\t2.출고관리\t\t3.운송장조회\t\t4.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            requestRelease();
        } else if (selNo == 2) {
            releaseManageMember();
        } else if (selNo == 3) {
            searchWaybillMember();
        }

        return PrintFunctionName.EXIT;

    } //회원 메인메뉴

    public static void requestRelease() throws SQLException, IOException, InterruptedException { //출고요청
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.출고요청\t\t2.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if(selNo == 1) {
            releaseDAO.requestRelease();
            System.out.println("출고요청이 완료되었습니다.");
            releaseMemberMenu();
        } else if (selNo == 2) {
            releaseMemberMenu();
        }
        
    } //출고요청

    public static void releaseManageMember() throws IOException, SQLException, InterruptedException { //출고관리 메뉴(회원)
        System.out.println("[출고 관리 메뉴]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.println("1.출고지시서 조회\t\t2.출고상품 검색\t\t3.나가기");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) { //출고지시서 조회
            List<ReleaseVO> shippingReleaseList = releaseDAO.selectShippingReleaseAllList();

            System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

            for (ReleaseVO releaseVO : shippingReleaseList) {
                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n",
                        releaseVO.getRequestId(),
                        releaseVO.getUserId(),
                        releaseVO.getRequestDate(),
                        releaseVO.getApprovedDate());
            }
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.println("1.상세보기\t\t2.기간별 조회\t\t3.나가기");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if (selNo == 1) { //출고지시서 상세보기
                List<ReleaseVO> shippingReleaseDetail = releaseDAO.selectShippingReleaseDetail();

                System.out.println("[출고지시서]");
                for (ReleaseVO releaseVO : shippingReleaseDetail) {
                    System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                            "출고번호 : " + releaseVO.getRequestId(),
                            "사용자ID : " + releaseVO.getUserId(),
                            "요청날짜 : " + releaseVO.getRequestDate(),
                            "승인날짜 : " + releaseVO.getApprovedDate(),
                            "상품번호 : " + releaseVO.getProductId(),
                            "상품이름 : " + releaseVO.getProductName(),
                            "요청수량 : " + releaseVO.getRequestQuantity(),
                            "배송지 주소 : " + releaseVO.getDeliveryAddress(),
                            "배송지 상세주소 : " + releaseVO.getDeliveryAddressDetail(),
                            "차량번호 : " + releaseVO.getCarNumber(),
                            "차종 : " + releaseVO.getCarType()
                    );


                    releaseMemberMenu();
                }

            } else if (selNo == 2) { // 출고지시서 기간별 조회
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.연도별 조회\t\t2.월별 조회\t\t3.일별 조회");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) { //출고지시서 연도별 조회
                    List<ReleaseVO> shippingReleaseYearList = releaseDAO.selectShippingReleaseYearList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseYearList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getUserId(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.상세보기\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        List<ReleaseVO> shippingReleaseDetail = releaseDAO.selectShippingReleaseDetail();

                        System.out.println("[출고지시서]");
                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                    "출고번호 : " + releaseVO.getRequestId(),
                                    "사용자ID : " + releaseVO.getUserId(),
                                    "요청날짜 : " + releaseVO.getRequestDate(),
                                    "승인날짜 : " + releaseVO.getApprovedDate(),
                                    "상품번호 : " + releaseVO.getProductId(),
                                    "상품이름 : " + releaseVO.getProductName(),
                                    "요청수량 : " + releaseVO.getRequestQuantity(),
                                    "배송지 주소 : " + releaseVO.getDeliveryAddress(),
                                    "배송지 상세주소 : " + releaseVO.getDeliveryAddressDetail(),
                                    "차량번호 : " + releaseVO.getCarNumber(),
                                    "차종 : " + releaseVO.getCarType()
                            );


                            releaseMemberMenu();
                        }
                    } else if (selNo == 2) {
                        releaseMemberMenu();
                    }

                } else if (selNo == 2) { //출고지시서 월별 조회
                    List<ReleaseVO> shippingReleaseMonthList = releaseDAO.selectShippingReleaseMonthList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseMonthList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getUserId(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.상세보기\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        List<ReleaseVO> shippingReleaseDetail = releaseDAO.selectShippingReleaseDetail();

                        System.out.println("[출고지시서]");
                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                    "출고번호 : " + releaseVO.getRequestId(),
                                    "사용자ID : " + releaseVO.getUserId(),
                                    "요청날짜 : " + releaseVO.getRequestDate(),
                                    "승인날짜 : " + releaseVO.getApprovedDate(),
                                    "상품번호 : " + releaseVO.getProductId(),
                                    "상품이름 : " + releaseVO.getProductName(),
                                    "요청수량 : " + releaseVO.getRequestQuantity(),
                                    "배송지 주소 : " + releaseVO.getDeliveryAddress(),
                                    "배송지 상세주소 : " + releaseVO.getDeliveryAddressDetail(),
                                    "차량번호 : " + releaseVO.getCarNumber(),
                                    "차종 : " + releaseVO.getCarType()
                            );


                            releaseMemberMenu();
                        }
                    } else if (selNo == 2) {
                        releaseMemberMenu();
                    }


                } else if (selNo == 3) { // 출고지시서 일별 조회
                    List<ReleaseVO> shippingReleaseDayList = releaseDAO.selectShippingReleaseDayList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseDayList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getUserId(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.상세보기\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        List<ReleaseVO> shippingReleaseDetail = releaseDAO.selectShippingReleaseDetail();

                        System.out.println("[출고지시서]");
                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                                    "출고번호 : " + releaseVO.getRequestId(),
                                    "사용자ID : " + releaseVO.getUserId(),
                                    "요청날짜 : " + releaseVO.getRequestDate(),
                                    "승인날짜 : " + releaseVO.getApprovedDate(),
                                    "상품번호 : " + releaseVO.getProductId(),
                                    "상품이름 : " + releaseVO.getProductName(),
                                    "요청수량 : " + releaseVO.getRequestQuantity(),
                                    "배송지 주소 : " + releaseVO.getDeliveryAddress(),
                                    "배송지 상세주소 : " + releaseVO.getDeliveryAddressDetail(),
                                    "차량번호 : " + releaseVO.getCarNumber(),
                                    "차종 : " + releaseVO.getCarType()
                            );


                            releaseMemberMenu();
                        }
                    } else if (selNo == 2) {
                        releaseMemberMenu();
                    }

                }
            } else if (selNo == 3) {
                releaseMemberMenu();
            }

        } else if (selNo == 2) { // 출고상품 검색

            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.println("1.이름으로 조회\t\t2.기간별 조회\t\t3.나가기");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if (selNo == 1) {
                List<ReleaseVO> productsReleaseList = releaseDAO.selectProductsReleaseNameList();

                System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                        "요청수량", "요청날짜", "승인날짜");

                for (ReleaseVO releaseVO : productsReleaseList) {
                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                            releaseVO.getRequestId(),
                            releaseVO.getProductId(),
                            releaseVO.getProductName(),
                            releaseVO.getProductBrand(),
                            releaseVO.getRequestQuantity(),
                            releaseVO.getRequestDate(),
                            releaseVO.getApprovedDate()
                    );
                }

                releaseMemberMenu();
            } else if (selNo == 2) {
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.연도별 조회\t\t2.월별 조회\t\t3.일별 조회\t\t4.나가기");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) {
                    List<ReleaseVO> productsReleaseYearList = releaseDAO.selectProductsReleaseYearList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseYearList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }
                    releaseMemberMenu();

                } else if (selNo == 2) {
                    List<ReleaseVO> productsReleaseMonthList = releaseDAO.selectProductsReleaseMonthList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseMonthList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }

                    releaseMemberMenu();

                } else if (selNo == 3) {
                    List<ReleaseVO> productsReleaseDayList = releaseDAO.selectProductsReleaseDayList();

                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseDayList) {
                        System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\t%-20s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }

                    releaseMemberMenu();

                } else if (selNo == 4) {
                    releaseMemberMenu();
                }
            } else if (selNo == 3) {
                releaseMemberMenu();
            }

        } else if (selNo == 3) {
            releaseMemberMenu();
        }

    } //출고관리 메뉴

    public static void searchWaybillMember() throws SQLException, IOException, InterruptedException { //운송장 조회
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
                    releaseMemberMenu();


                } else if (selNo == 2) {
                    releaseMemberMenu();
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
                    releaseMemberMenu();



                } else if (selNo == 2) {
                    releaseMemberMenu();
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
                    releaseMemberMenu();


                } else if (selNo == 2) {
                    releaseMemberMenu();
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
            releaseMemberMenu();

        } else if (selNo == 3) {
            releaseMemberMenu();
        }

    } //운송장 조회



//    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
//
//        releaseMemberMenu();
//
//    }

}