package service;

import java.sql.SQLException;
import java.util.ArrayList;
import vo.StockingRequestVO;

//관리자 기능

public interface StockingRequestServiceManager {

  //입고 처리 (승인된 입고처리 목록 조회)
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList()
      throws SQLException, InterruptedException;

  //입고 처리 완료
  public void completeStockingRequest();

  //모든 입고 요청서 조회
  public ArrayList<StockingRequestVO> inquiryAllWarehouseRequests()
      throws SQLException, InterruptedException;


  //입고 요청 승인(미승인된 입고 처리 목록 조회)
  public ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList()
      throws SQLException, InterruptedException;

  //삭제
  public void deleteRecevingProcessingList()
    throws SQLException, InterruptedException;


}
