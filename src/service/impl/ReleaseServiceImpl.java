package service.impl;

import lib.ObjectDBIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReleaseServiceImpl extends ObjectDBIO {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /*
     *  관리자 기능 구현
     */

    /*
     *  회원 기능 구현
     */

    public boolean requestRelease() { //출고요청
        return false;
    }

    /*
     *  운송기사 기능 구현
     */


    /*
     *  기능별 쿼리문 작성
     */


    public List<ReleaseVO> selectNotApprovedReleaseList() { //미승인 리스트
        ArrayList<ReleaseVO> notApprovedReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT * FROM deliver_request WHERE approved_date is null";

        return notApprovedReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseAllList() { //출고지시서 리스트(전체 조회)
        ArrayList<ReleaseVO> shippingReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT * FROM "

        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseYearList() { //출고지시서 리스트(년도별 검색)
        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseMonthList() { //출고지시서 리스트(월별 검색)
        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseDayList() { //출고지시서 리스트(일별 검색)
        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseDetail() { //출고지시서 상세보기(조회)
        return shippingReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseNameList() { //출고 상품 리스트(이름으로 검색)
        return productsReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseYearList() { //출고 상품 리스트(년별로 검색)
        return productsReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseMonthList() { //출고 상품 리스트(월별로 검색)
        return productsReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseDayList() { //출고 상품 리스트(일별로 검색)
        return productsReleaseList;
    }

    public List<WaybillVO> selectWaybillAllList() { //운송장 리스트(전체 조회)
        return waybillList;
    }

    public List<WaybillVO> selectWaybillAllList() { //운송장 리스트(전체 조회)
        return waybillList;
    }

    public List<WaybillVO> selectWaybillYearList() { //운송장 리스트(년도별 조회)
        return waybillList;
    }

    public List<WaybillVO> selectWaybillMonthList() { //운송장 리스트(월별 조회)
        return waybillList;
    }

    public List<WaybillVO> selectWaybillDayList() { //운송장 리스트(일별 조회)
        return waybillList;
    }

    public List<WaybillVO> selectWaybillDetail() { //운송장 상세보기(조회)
        return waybillList;
    }

    public boolean updateWaybill() { //운송장 수정
        return false;
    }

    public boolean registerDispatch() { //배차 등록
        return false;
    }

    public boolean cancleDispatch() { //배차 취소(삭제)
        return false;
    }

}
