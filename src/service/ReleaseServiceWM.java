package service;

import java.io.IOException;
import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceWM extends ReleaseServiceDriver{

    public boolean approveReleaseRequest(int requestId) throws SQLException, InterruptedException, IOException; //출고승인
    public List<ReleaseVO> selectNotApprovedReleaseList() throws SQLException, InterruptedException; //미승인 리스트
    public List<ReleaseVO> selectShippingReleaseAllList() throws SQLException; //출고지시서 리스트(전체 조회)
    public List<ReleaseVO> selectShippingReleaseYearList() throws SQLException, IOException; //출고지시서 리스트(년도별 검색)
    public List<ReleaseVO> selectShippingReleaseMonthList() throws IOException, SQLException; //출고지시서 리스트(월별 검색)
    public List<ReleaseVO> selectShippingReleaseDayList() throws IOException, SQLException; //출고지시서 리스트(일별 검색)
    public List<ReleaseVO> selectShippingReleaseDetail() throws SQLException, IOException; //출고지시서 상세보기(조회)
    public List<ReleaseVO> selectProductsReleaseNameList() throws SQLException, IOException; //출고 상품 리스트(이름으로 검색)
    public List<ReleaseVO> selectProductsReleaseYearList() throws SQLException, IOException; //출고 상품 리스트(년별로 검색)
    public List<ReleaseVO> selectProductsReleaseMonthList() throws IOException, SQLException; //출고 상품 리스트(월별로 검색)
    public List<ReleaseVO> selectProductsReleaseDayList() throws IOException, SQLException; //출고 상품 리스트(일별로 검색)
    public boolean updateWaybill(String businessName, String address, String businessTel, String arriveAddress, String arriveAddressDetail, int waybillId) throws IOException; //운송장 수정
    public boolean registerDispatch() throws SQLException, InterruptedException, IOException; //배차 등록
    public boolean updateDispatch() throws IOException, SQLException; //배차 수정
    public boolean registerWaybill() throws SQLException, InterruptedException, IOException; //운송장 등록
    public List<DispatchVO> selectDispatchList() throws SQLException; //배차 리스트 조회
    public boolean cancleDispatch() throws SQLException, InterruptedException, IOException; //배차 취소(삭제)
}
