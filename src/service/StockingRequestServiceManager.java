package service;

import java.sql.SQLException;
import java.util.ArrayList;
import vo.StockingRequestVO;

public interface StockingRequestServiceManager {

  //승인된 입고처리 목록 조회(입고 처리)
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList()
      throws SQLException, InterruptedException;

  //모든 입고 요청서 조회
  public ArrayList<StockingRequestVO> inquiryAllWarehouseRequests()
      throws SQLException, InterruptedException;

  //미승인된 입고처리 목록 조회
  public ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList()
      throws SQLException, InterruptedException;
}
