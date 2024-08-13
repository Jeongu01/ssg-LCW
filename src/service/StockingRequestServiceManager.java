package service;

import java.util.ArrayList;
import vo.StockingRequestVO;

public interface StockingRequestServiceManager {

  //승인된 입고처리 목록 조회(입고 처리)
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList();

  //모든 입고 요청서 조회
  public ArrayList<StockingRequestVO> inquiryAllWarehouseRequests();

  //미승인된 입고처리 목록 조회
  public ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList();
}
