package service;

import vo.ReleaseVO;
import vo.WaybillVO;

import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceMember {

    public boolean requestRelease(ReleaseVO data) throws SQLException, InterruptedException; // 출고요청
    public List<ReleaseVO> selectShippingReleaseAllList(); //출고지시서 리스트(전체 조회)
    public List<ReleaseVO> selectShippingReleaseYearList(); //출고지시서 리스트(년도별 검색)
    public List<ReleaseVO> selectShippingReleaseMonthList(); //출고지시서 리스트(월별 검색)
    public List<ReleaseVO> selectShippingReleaseDayList(); //출고지시서 리스트(일별 검색)
    public List<ReleaseVO> selectShippingReleaseDetail(); //출고지시서 상세보기(조회)
    public List<ReleaseVO> selectProductsReleaseNameList(); //출고 상품 리스트(이름으로 검색)
    public List<ReleaseVO> selectProductsReleaseYearList(); //출고 상품 리스트(년별로 검색)
    public List<ReleaseVO> selectProductsReleaseMonthList(); //출고 상품 리스트(월별로 검색)
    public List<ReleaseVO> selectProductsReleaseDayList(); //출고 상품 리스트(일별로 검색)
    public List<WaybillVO> selectWaybillAllList(); //운송장 리스트(전체 조회)
    public List<WaybillVO> selectWaybillYearList(); //운송장 리스트(년도별 조회)
    public List<WaybillVO> selectWaybillMonthList(); //운송장 리스트(월별 조회)
    public List<WaybillVO> selectWaybillDayList(); //운송장 리스트(일별 조회)
    public List<WaybillVO> selectWaybillDetail(); //운송장 상세보기(조회)



}
