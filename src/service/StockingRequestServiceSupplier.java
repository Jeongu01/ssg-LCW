package service;

import java.sql.SQLException;
import java.util.ArrayList;
import vo.StockingRequestVO;

//회원 기능

public interface StockingRequestServiceSupplier {


  //1. 내 입고 요청서 조회 -> userid를 필요로 함
  public ArrayList<StockingRequestVO> inquiryWarehouseRequests()
      throws SQLException, InterruptedException;

  //2. 입고 요청
  public void requestWarehousing(StockingRequestVO data);

}
