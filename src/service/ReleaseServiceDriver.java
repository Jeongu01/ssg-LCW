package service;

import java.io.IOException;
import vo.CarVO;
import vo.UserVO;
import vo.WaybillVO;

import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceDriver {

    public List<WaybillVO> selectWaybillAllList() throws SQLException; //운송장 리스트(전체 조회)
    public List<WaybillVO> selectWaybillYearList() throws SQLException, IOException; //운송장 리스트(년도별 조회)
    public List<WaybillVO> selectWaybillMonthList() throws IOException, SQLException; //운송장 리스트(월별 조회)
    public List<WaybillVO> selectWaybillDayList() throws IOException, SQLException; //운송장 리스트(일별 조회)
    public List<WaybillVO> selectWaybillDetail(int waybillId) throws IOException, SQLException; //운송장 상세보기(조회)
    public List<CarVO> selectCarAllList() throws SQLException; //차량 조회(전체 조회)
    public boolean registerCar() throws SQLException, InterruptedException, IOException; //차량 등록
    public boolean updateCar() throws SQLException, InterruptedException, IOException; // 차량 수정
    public boolean completeDelivery(UserVO user, int waybillId) throws SQLException, InterruptedException; //배송 완료


}
