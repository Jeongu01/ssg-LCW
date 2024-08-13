package service.impl;

import common.dao.StockingRequestDAO;
import java.util.ArrayList;
import service.StockingRequestServiceManager;
import service.StockingRequestServiceSupplier;
import vo.StockingRequestVO;

public class StockingRequestImpl implements StockingRequestServiceManager,
    StockingRequestServiceSupplier {

  StockingRequestDAO dao = null;

  public StockingRequestImpl(){
    dao = new StockingRequestDAO();
  }

  @Override
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList() {
    return dao.selectApprovedStockingRequest();
  }

  @Override
  public ArrayList<StockingRequestVO> inquiryAllWarehouseRequests() {
    return dao.selectAllStockingRequest();
  }

  @Override
  public ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList() {
    return dao.selectUnApprovedStockingRequest();
  }

  @Override
  public void RequestWarehousing(StockingRequestVO data) {

  }

  @Override
  public ArrayList<StockingRequestVO> inquiryWarehouseRequests() {
    return dao.selectinquiryWarehouseRequests();
  }

  /*
  1. 관리자 입고 메뉴
  1) 입고 요청서 조회
  2) 입고 요청 승인
  3) 입고 처리

  2. 회원 입고 메뉴
  1) 입고 요청
  2) 입고 요청서 조회
  */
}
