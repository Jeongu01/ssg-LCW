package service;

import java.io.IOException;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceMember {

    public boolean requestRelease() throws SQLException, InterruptedException, IOException; // 출고요청
    public List<ReleaseVO> selectShippingReleaseAllList() throws SQLException; //출고지시서 리스트(전체 조회)
    public List<ReleaseVO> selectShippingReleaseYearList() throws SQLException, IOException; //출고지시서 리스트(년도별 검색)
    public List<ReleaseVO> selectShippingReleaseMonthList() throws IOException, SQLException; //출고지시서 리스트(월별 검색)
    public List<ReleaseVO> selectShippingReleaseDayList() throws IOException, SQLException; //출고지시서 리스트(일별 검색)
    public List<ReleaseVO> selectShippingReleaseDetail() throws SQLException, IOException; //출고지시서 상세보기(조회)
    public List<ReleaseVO> selectProductsReleaseNameList() throws SQLException, IOException; //출고 상품 리스트(이름으로 검색)
    public List<ReleaseVO> selectProductsReleaseYearList() throws SQLException, IOException; //출고 상품 리스트(년별로 검색)
    public List<ReleaseVO> selectProductsReleaseMonthList() throws IOException, SQLException; //출고 상품 리스트(월별로 검색)
    public List<ReleaseVO> selectProductsReleaseDayList() throws IOException, SQLException; //출고 상품 리스트(일별로 검색)
    public List<WaybillVO> selectWaybillAllList() throws SQLException; //운송장 리스트(전체 조회)
    public List<WaybillVO> selectWaybillYearList() throws SQLException, IOException; //운송장 리스트(년도별 조회)
    public List<WaybillVO> selectWaybillMonthList() throws IOException, SQLException; //운송장 리스트(월별 조회)
    public List<WaybillVO> selectWaybillDayList() throws IOException, SQLException; //운송장 리스트(일별 조회)
    public List<WaybillVO> selectWaybillDetail(int waybillId) throws IOException, SQLException; //운송장 상세보기(조회)



}
