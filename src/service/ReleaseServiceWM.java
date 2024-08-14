package service;

import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceWM extends ReleaseServiceDriver{

    public ResultSet approveReleaseRequest() throws SQLException, InterruptedException; //출고승인
    public List<ReleaseVO> selectNotApprovedReleaseList() throws SQLException, InterruptedException; //미승인 리스트
    public List<ReleaseVO> selectShippingReleaseAllList(); //출고지시서 리스트(전체 조회)
    public List<ReleaseVO> selectShippingReleaseYearList(); //출고지시서 리스트(년도별 검색)
    public List<ReleaseVO> selectShippingReleaseMonthList(); //출고지시서 리스트(월별 검색)
    public List<ReleaseVO> selectShippingReleaseDayList(); //출고지시서 리스트(일별 검색)
    public List<ReleaseVO> selectShippingReleaseDetail(); //출고지시서 상세보기(조회)
    public List<ReleaseVO> selectProductsReleaseNameList(); //출고 상품 리스트(이름으로 검색)
    public List<ReleaseVO> selectProductsReleaseYearList(); //출고 상품 리스트(년별로 검색)
    public List<ReleaseVO> selectProductsReleaseMonthList(); //출고 상품 리스트(월별로 검색)
    public List<ReleaseVO> selectProductsReleaseDayList(); //출고 상품 리스트(일별로 검색)
    public boolean updateWaybill(); //운송장 수정
    public boolean registerDispatch(DispatchVO data) throws SQLException, InterruptedException; //배차 등록
    public void selectDispatchList(); //배차 리스트 조회
    public boolean cancleDispatch(DispatchVO data) throws SQLException, InterruptedException; //배차 취소(삭제)
}
