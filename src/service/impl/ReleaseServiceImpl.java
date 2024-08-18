package service.impl;

import com.sun.tools.javac.Main;
import common.dao.ReleaseDAO;
import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.WaybillVO;

public class ReleaseServiceImpl {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ReleaseDAO releaseDAO = new ReleaseDAO();


    /*
     *  관리자 기능 구현
     */
    public static void releaseManagerMenu() throws IOException, SQLException, InterruptedException {

        System.out.println("[출고 메뉴]");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1.출고요청승인\t\t2.출고관리\t\t3.운송장관리\t\t4.배차관리\t\t5.차량관리\t\t6.나가기");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            releaseApprove();
        } else if (selNo == 2) {
            releaseManage();
        } else if (selNo == 3) {
            waybillManage();
        } else if (selNo == 4) {
            dispatchManage();
        } else if (selNo == 5) {
            carManage();
        } else {
            //메인메뉴 탭으로 이동
        }

    } //관리자 메인메뉴

    public static void releaseApprove() throws IOException, SQLException, InterruptedException { //출고요청승인 메뉴

        List<ReleaseVO> notApprovedReleaseList = releaseDAO.selectNotApprovedReleaseList();

        System.out.printf("%s %20s %20s %20s %20s %20s %20s %20s %20s\n", "출고번호", "사용자ID", "요청날짜",
                "상품번호", "상품이름", "요청수량", "발송지 주소", "수령지 주소", "수령지 상세주소");

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

        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.println("1.승인하기\t\t2.취소");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) { //승인하기
            releaseDAO.approveReleaseRequest();
            releaseDAO.registerWaybill();
            System.out.println("운송장이 등록되었습니다.");
            List<WaybillVO> waybilDetail = releaseDAO.selectWaybillDetailFromReleaseNo();
            System.out.println("[운송장 조회]");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                    "운송장번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송수량", "발송자명", "발송자 번호",
                    "발송지 주소", "수령지 주소", "수령지 상세주소", "요청 코멘트");
            for (WaybillVO waybillVO : waybilDetail) {
                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        waybillVO.getWaybillId(),
                        waybillVO.getDeliveryRequestId(),
                        waybillVO.getDepartDate(),
                        waybillVO.getProductId(),
                        waybillVO.getProductName(),
                        waybillVO.getDeliveryQuantity(),
                        waybillVO.getBusinessName(),
                        waybillVO.getBusinessTel(),
                        waybillVO.getStartAddress(),
                        waybillVO.getArriveAddress(),
                        waybillVO.getArriveAddressDetail(),
                        waybillVO.getRequestComment());
            }

            System.out.println("[차량 목록]");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            List<CarVO> carAllList = releaseDAO.selectCarAllList();

            System.out.printf("%s %10s %10s\n", "차량번호", "차종", "최대 적재량");

            for (CarVO carVO : carAllList) {
                System.out.printf("%s %10s %10s\n",
                        carVO.getCarNumber(),
                        carVO.getCarType(),
                        carVO.getMaxLoad());
            }
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.println("배차를 등록해주세요.");
            releaseDAO.registerDispatch(); // 배차 등록
            System.out.println("배차 등록이 완료되었습니다.");
            System.out.println("요청 승인이 완료되었습니다. 감사합니다.\n\n");

            releaseManagerMenu();

        } else if (selNo == 2) { //취소
            releaseManagerMenu();
        }
    } //출고요청승인 메뉴

    public static void releaseManage() throws IOException, SQLException, InterruptedException { //출고관리 메뉴
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

            System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

            for (ReleaseVO releaseVO : shippingReleaseList) {
                System.out.printf("%s %10s %10s %10s\n",
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                        "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                for (ReleaseVO releaseVO : shippingReleaseDetail) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            releaseVO.getRequestId(),
                            releaseVO.getUserId(),
                            releaseVO.getRequestDate(),
                            releaseVO.getApprovedDate(),
                            releaseVO.getProductId(),
                            releaseVO.getProductName(),
                            releaseVO.getRequestQuantity(),
                            releaseVO.getDeliveryAddress(),
                            releaseVO.getDeliveryAddressDetail(),
                            releaseVO.getCarNumber(),
                            releaseVO.getCarType()
                    );

                    releaseManagerMenu();
                }

            } else if (selNo == 2) { // 출고지시서 기간별 조회
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.연도별 조회\t\t2.연/월별 조회\t\t3.연/월/일별 조회");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) { //출고지시서 연도별 조회
                    List<ReleaseVO> shippingReleaseYearList = releaseDAO.selectShippingReleaseYearList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseYearList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
                            );

                            releaseManagerMenu();
                        }
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }

                } else if (selNo == 2) { //출고지시서 월별 조회
                    List<ReleaseVO> shippingReleaseMonthList = releaseDAO.selectShippingReleaseMonthList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseMonthList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
                            );

                            releaseManagerMenu();
                        }
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }


                } else if (selNo == 3) { // 출고지시서 일별 조회
                    List<ReleaseVO> shippingReleaseDayList = releaseDAO.selectShippingReleaseDayList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseDayList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
                            );

                            releaseManagerMenu();
                        }
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }

                }
            } else if (selNo == 3) {
                releaseManagerMenu();
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                        "요청수량", "요청날짜", "승인날짜");

                for (ReleaseVO releaseVO : productsReleaseList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                            releaseVO.getRequestId(),
                            releaseVO.getProductId(),
                            releaseVO.getProductName(),
                            releaseVO.getProductBrand(),
                            releaseVO.getRequestQuantity(),
                            releaseVO.getRequestDate(),
                            releaseVO.getApprovedDate()
                    );
                }

                releaseManagerMenu();
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

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseYearList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }
                    releaseManagerMenu();

                } else if (selNo == 2) {
                    List<ReleaseVO> productsReleaseMonthList = releaseDAO.selectProductsReleaseMonthList();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseMonthList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }

                    releaseManagerMenu();

                } else if (selNo == 3) {
                    List<ReleaseVO> productsReleaseDayList = releaseDAO.selectProductsReleaseDayList();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseDayList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                                releaseVO.getRequestId(),
                                releaseVO.getProductId(),
                                releaseVO.getProductName(),
                                releaseVO.getProductBrand(),
                                releaseVO.getRequestQuantity(),
                                releaseVO.getRequestDate(),
                                releaseVO.getApprovedDate()
                        );
                    }

                    releaseManagerMenu();

                } else if (selNo == 4) {
                    releaseManagerMenu();
                }
            } else if (selNo == 3) {
                releaseManagerMenu();
            }

        } else if (selNo == 3) {
            releaseManagerMenu();
        }

    } //출고관리 메뉴(관리자)

    public static void waybillManage() throws SQLException, IOException, InterruptedException { //운송장 관리
        System.out.println("[운송장 내역]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        List<WaybillVO> waybillAllList = releaseDAO.selectWaybillAllList();

        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                "운송장번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");


        for (WaybillVO waybillVO : waybillAllList) {
            System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                    waybillVO.getWaybillId(),
                    waybillVO.getDepartDate(),
                    waybillVO.getProductId(),
                    waybillVO.getProductName(),
                    waybillVO.getStartAddress(),
                    waybillVO.getArriveAddress(),
                    waybillVO.getArriveAddressDetail());
        }

        System.out.println("[운송장 관리 메뉴]");
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillYearList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.운송장 수정\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        releaseDAO.updateWaybill();
                        System.out.println("운송장 수정이 완료되었습니다.");
                        releaseManagerMenu();
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }


                } else if (selNo == 2) {
                    releaseManagerMenu();
                }


            } else if (selNo == 2) { //월별 조회
                List<WaybillVO> waybillMonthList = releaseDAO.selectWaybillMonthList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillMonthList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.운송장 수정\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        releaseDAO.updateWaybill();
                        System.out.println("운송장 수정이 완료되었습니다.");
                        releaseManagerMenu();
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }


                } else if (selNo == 2) {
                    releaseManagerMenu();
                }

            } else if (selNo == 3) { //일별 조회
                List<WaybillVO> waybillDayList = releaseDAO.selectWaybillDayList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillDayList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.println("1.운송장 수정\t\t2.나가기");
                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.print("메뉴 선택 : ");
                    selNo = Integer.parseInt(br.readLine());

                    if (selNo == 1) {
                        releaseDAO.updateWaybill();
                        System.out.println("운송장 수정이 완료되었습니다.");
                        releaseManagerMenu();
                    } else if (selNo == 2) {
                        releaseManagerMenu();
                    }


                } else if (selNo == 2) {
                    releaseManagerMenu();
                }

            }


        } else if (selNo == 2) {
            List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                    "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                    "배송지 주소", "배송지 상세주소", "요청 코멘트");

            for (WaybillVO waybillVO : waybillDetail) {
                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        waybillVO.getWaybillId(),
                        waybillVO.getDeliveryRequestId(),
                        waybillVO.getDepartDate(),
                        waybillVO.getProductId(),
                        waybillVO.getProductName(),
                        waybillVO.getDeliveryQuantity(),
                        waybillVO.getBusinessName(),
                        waybillVO.getStartAddress(),
                        waybillVO.getBusinessTel(),
                        waybillVO.getArriveAddress(),
                        waybillVO.getArriveAddressDetail(),
                        waybillVO.getRequestComment());
            }

            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.println("1.운송장 수정\t\t2.나가기");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            System.out.print("메뉴 선택 : ");
            selNo = Integer.parseInt(br.readLine());

            if (selNo == 1) {
                releaseDAO.updateWaybill();
                System.out.println("운송장 수정이 완료되었습니다.");
                releaseManagerMenu();
            } else if (selNo == 2) {
                releaseManagerMenu();
            }

        } else if (selNo == 3) {
            releaseManagerMenu();
        }

    } //운송장 관리

    public static void dispatchManage() throws SQLException, IOException, InterruptedException { //배차 관리
        List<DispatchVO> dispatchList = releaseDAO.selectDispatchList();
        System.out.println("[배차 목록]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.printf("%s %10s %10s\n", "운송장 번호", "차량번호", "차종");

        for(DispatchVO dispatchVO : dispatchList) {
            System.out.printf("%s %10s %10s\n",
                    dispatchVO.getWaybillId(),
                    dispatchVO.getCarNumber(),
                    dispatchVO.getCarType());
        }

        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.println("1.배차정보 수정\t\t2.배차취소\t\t3.나가기");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        int selNo = Integer.parseInt(br.readLine());

        if (selNo == 1) {
            System.out.println("[차량 목록]");
            System.out.println(
                    "--------------------------------------------------------------------------------------");
            List<CarVO> carAllList = releaseDAO.selectCarAllList();

            System.out.printf("%s %10s %10s\n", "차량번호", "차종", "최대 적재량");

            for(CarVO carVO : carAllList) {
                System.out.printf("%s %10s %10s\n",
                        carVO.getCarNumber(),
                        carVO.getCarType(),
                        carVO.getMaxLoad());
            };


            releaseDAO.updateDispatch();
            System.out.println("배차 수정이 완료되었습니다.");
            releaseManagerMenu();
        } else if (selNo == 2) {
            releaseDAO.cancleDispatch();
            System.out.println("배차 취소가 완료되었습니다.");
            releaseManagerMenu();
        } else if (selNo == 3) {
            releaseManagerMenu();
        }

        
    } //배차 관리

    public static void carManage() throws SQLException, IOException, InterruptedException { //차량 관리
        System.out.println("[차량 목록]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        List<CarVO> carAllList = releaseDAO.selectCarAllList();

        System.out.printf("%s %10s %10s\n", "차량번호", "차종", "최대 적재량");

        for(CarVO carVO : carAllList) {
            System.out.printf("%s %10s %10s\n",
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
            releaseManagerMenu();
        } else if (selNo == 2) {
            releaseDAO.updateCar();
            System.out.println("차량 수정이 완료되었습니다.");
            releaseManagerMenu();
        } else if (selNo == 3) {
            releaseManagerMenu();
        }

    } //차량관리



    /*
     *  회원 기능 구현
     */

    public static void releaseMemberMenu() throws IOException, SQLException, InterruptedException {

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
        } else {
            //메인메뉴 탭으로 이동
        }

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

            System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

            for (ReleaseVO releaseVO : shippingReleaseList) {
                System.out.printf("%s %10s %10s %10s\n",
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                        "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                for (ReleaseVO releaseVO : shippingReleaseDetail) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            releaseVO.getRequestId(),
                            releaseVO.getUserId(),
                            releaseVO.getRequestDate(),
                            releaseVO.getApprovedDate(),
                            releaseVO.getProductId(),
                            releaseVO.getProductName(),
                            releaseVO.getRequestQuantity(),
                            releaseVO.getDeliveryAddress(),
                            releaseVO.getDeliveryAddressDetail(),
                            releaseVO.getCarNumber(),
                            releaseVO.getCarType()
                    );

                    releaseMemberMenu();
                }

            } else if (selNo == 2) { // 출고지시서 기간별 조회
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.println("1.연도별 조회\t\t2.연/월별 조회\t\t3.연/월/일별 조회");
                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                selNo = Integer.parseInt(br.readLine());

                if (selNo == 1) { //출고지시서 연도별 조회
                    List<ReleaseVO> shippingReleaseYearList = releaseDAO.selectShippingReleaseYearList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseYearList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
                            );

                            releaseMemberMenu();
                        }
                    } else if (selNo == 2) {
                        releaseMemberMenu();
                    }

                } else if (selNo == 2) { //출고지시서 월별 조회
                    List<ReleaseVO> shippingReleaseMonthList = releaseDAO.selectShippingReleaseMonthList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseMonthList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
                            );

                            releaseMemberMenu();
                        }
                    } else if (selNo == 2) {
                        releaseMemberMenu();
                    }


                } else if (selNo == 3) { // 출고지시서 일별 조회
                    List<ReleaseVO> shippingReleaseDayList = releaseDAO.selectShippingReleaseDayList();

                    System.out.printf("%s %10s %10s %10s\n", "출고번호", "사용자ID", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : shippingReleaseDayList) {
                        System.out.printf("%s %10s %10s %10s\n",
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

                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                "출고번호", "사용자ID", "요청날짜", "승인날짜", "상품번호", "상품이름", "요청수량",
                                "배송지 주소", "배송지 상세주소", "차량번호", "차종");

                        for (ReleaseVO releaseVO : shippingReleaseDetail) {
                            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                    releaseVO.getRequestId(),
                                    releaseVO.getUserId(),
                                    releaseVO.getRequestDate(),
                                    releaseVO.getApprovedDate(),
                                    releaseVO.getProductId(),
                                    releaseVO.getProductName(),
                                    releaseVO.getRequestQuantity(),
                                    releaseVO.getDeliveryAddress(),
                                    releaseVO.getDeliveryAddressDetail(),
                                    releaseVO.getCarNumber(),
                                    releaseVO.getCarType()
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                        "요청수량", "요청날짜", "승인날짜");

                for (ReleaseVO releaseVO : productsReleaseList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseYearList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseMonthList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n", "출고번호", "상품번호", "상품이름", "상품브랜드",
                            "요청수량", "요청날짜", "승인날짜");

                    for (ReleaseVO releaseVO : productsReleaseDayList) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                "운송장번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");


        for (WaybillVO waybillVO : waybillAllList) {
            System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillYearList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseMemberMenu();


                } else if (selNo == 2) {
                    releaseMemberMenu();
                }


            } else if (selNo == 2) { //월별 조회
                List<WaybillVO> waybillMonthList = releaseDAO.selectWaybillMonthList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillMonthList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseMemberMenu();



                } else if (selNo == 2) {
                    releaseMemberMenu();
                }

            } else if (selNo == 3) { //일별 조회
                List<WaybillVO> waybillDayList = releaseDAO.selectWaybillDayList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillDayList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseMemberMenu();


                } else if (selNo == 2) {
                    releaseMemberMenu();
                }

            }


        } else if (selNo == 2) {
            List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                    "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                    "배송지 주소", "배송지 상세주소", "요청 코멘트");

            for (WaybillVO waybillVO : waybillDetail) {
                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        waybillVO.getWaybillId(),
                        waybillVO.getDeliveryRequestId(),
                        waybillVO.getDepartDate(),
                        waybillVO.getProductId(),
                        waybillVO.getProductName(),
                        waybillVO.getDeliveryQuantity(),
                        waybillVO.getBusinessName(),
                        waybillVO.getStartAddress(),
                        waybillVO.getBusinessTel(),
                        waybillVO.getArriveAddress(),
                        waybillVO.getArriveAddressDetail(),
                        waybillVO.getRequestComment());
            }
            releaseMemberMenu();

        } else if (selNo == 3) {
            releaseMemberMenu();
        }

    } //운송장 조회



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

        System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                "운송장번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");


        for (WaybillVO waybillVO : waybillAllList) {
            System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillYearList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseDriverMenu();


                } else if (selNo == 2) {
                    releaseDriverMenu();
                }


            } else if (selNo == 2) { //월별 조회
                List<WaybillVO> waybillMonthList = releaseDAO.selectWaybillMonthList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillMonthList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseDriverMenu();



                } else if (selNo == 2) {
                    releaseDriverMenu();
                }

            } else if (selNo == 3) { //일별 조회
                List<WaybillVO> waybillDayList = releaseDAO.selectWaybillDayList();

                System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
                        "운송장 번호", "출발날짜", "상품번호", "상품이름", "발송지 주소", "배송지 주소", "배송지 상세주소");

                for (WaybillVO waybillVO : waybillDayList) {
                    System.out.printf("%s %10s %10s %10s %10s %10s %10s\n",
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
                    List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

                    System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                            "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                            "배송지 주소", "배송지 상세주소", "요청 코멘트");

                    for (WaybillVO waybillVO : waybillDetail) {
                        System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                                waybillVO.getWaybillId(),
                                waybillVO.getDeliveryRequestId(),
                                waybillVO.getDepartDate(),
                                waybillVO.getProductId(),
                                waybillVO.getProductName(),
                                waybillVO.getDeliveryQuantity(),
                                waybillVO.getBusinessName(),
                                waybillVO.getStartAddress(),
                                waybillVO.getBusinessTel(),
                                waybillVO.getArriveAddress(),
                                waybillVO.getArriveAddressDetail(),
                                waybillVO.getRequestComment());
                    }
                    releaseDriverMenu();


                } else if (selNo == 2) {
                    releaseDriverMenu();
                }

            }


        } else if (selNo == 2) {
            List<WaybillVO> waybillDetail = releaseDAO.selectWaybillDetail();

            System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                    "운송장 번호", "출고번호", "출발날짜", "상품번호", "상품이름", "배송 수량", "사업자명", "발송지 주소", "사업자 전화번호",
                    "배송지 주소", "배송지 상세주소", "요청 코멘트");

            for (WaybillVO waybillVO : waybillDetail) {
                System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s\n",
                        waybillVO.getWaybillId(),
                        waybillVO.getDeliveryRequestId(),
                        waybillVO.getDepartDate(),
                        waybillVO.getProductId(),
                        waybillVO.getProductName(),
                        waybillVO.getDeliveryQuantity(),
                        waybillVO.getBusinessName(),
                        waybillVO.getStartAddress(),
                        waybillVO.getBusinessTel(),
                        waybillVO.getArriveAddress(),
                        waybillVO.getArriveAddressDetail(),
                        waybillVO.getRequestComment());
            }
            releaseDriverMenu();

        } else if (selNo == 3) {
            releaseDriverMenu();
        }

    } //운송장 조회

    public static void carManageDriver() throws SQLException, IOException, InterruptedException { //차량 관리
        System.out.println("[차량 목록]");
        System.out.println(
                "--------------------------------------------------------------------------------------");
        List<CarVO> carAllList = releaseDAO.selectCarAllList();

        System.out.printf("%s %10s %10s\n", "차량번호", "차종", "최대 적재량");

        for(CarVO carVO : carAllList) {
            System.out.printf("%s %10s %10s\n",
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
        releaseManagerMenu();
    }

}