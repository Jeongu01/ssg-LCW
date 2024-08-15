package service.impl;

import common.dao.StockingRequestDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import service.StockingRequestServiceManager;
import service.StockingRequestServiceSupplier;
import vo.StockingRequestVO;
import vo.UserVO;

public class StockingRequestImpl implements StockingRequestServiceManager, StockingRequestServiceSupplier {

  /*
  1. 관리자 입고 메뉴
  1) 입고 요청서 조회
  2) 입고 요청 승인
  3) 입고 처리

  2. 회원 입고 메뉴
  1) 입고 요청
  2) 입고 요청서 조회
  */

  StockingRequestDAO dao = null;

  public StockingRequestImpl(){
    dao = new StockingRequestDAO();
  }

  @Override
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList()
      throws SQLException, InterruptedException {
    return dao.selectApprovedStockingRequest();
  }

  @Override
  public ArrayList<StockingRequestVO> inquiryAllWarehouseRequests()
      throws SQLException, InterruptedException {
    return dao.selectAllStockingRequest();
  }

  @Override
  public ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList()
      throws SQLException, InterruptedException {
    return dao.selectUnApprovedStockingRequest();
  }

  @Override
  public void requestWarehousing(StockingRequestVO data) {

  }

  @Override
  public ArrayList<StockingRequestVO> inquiryWarehouseRequests()
      throws SQLException, InterruptedException {
    UserVO user = new UserVO();
    return dao.selectInquiryWarehouseRequests(user.getUserId());
  }
}
