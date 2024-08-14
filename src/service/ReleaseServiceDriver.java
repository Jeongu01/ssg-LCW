package service;

import vo.CarVO;
import vo.WaybillVO;

import java.sql.SQLException;
import java.util.List;

public interface ReleaseServiceDriver {

    public List<WaybillVO> selectWaybillAllList(); //운송장 리스트(전체 조회)
    public List<WaybillVO> selectWaybillYearList(); //운송장 리스트(년도별 조회)
    public List<WaybillVO> selectWaybillMonthList(); //운송장 리스트(월별 조회)
    public List<WaybillVO> selectWaybillDayList(); //운송장 리스트(일별 조회)
    public List<WaybillVO> selectWaybillDetail(); //운송장 상세보기(조회)
    public boolean completeDelivery() throws SQLException, InterruptedException; //배송완료
    public List<CarVO> selectCarAllList(); //차량 조회(전체 조회)
    public boolean registerCar(CarVO data) throws SQLException, InterruptedException; //차량 등록
    public boolean updateCar(CarVO data) throws SQLException, InterruptedException; // 차량 수정


}
