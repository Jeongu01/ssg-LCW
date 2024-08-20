/*
package service.impl;

import common.dao.StockingRequestDAO_bak;
import java.sql.SQLException;
import java.util.ArrayList;
import service.StockingRequestServiceManager;
import service.StockingRequestServiceSupplier;
import vo.StockingRequestVO;
import vo.UserVO;

public class StockingRequestImpl implements StockingRequestServiceManager, StockingRequestServiceSupplier {

  */
/*
  1. 관리자 입고 메뉴
  1) 입고 요청서 조회
  2) 입고 요청 승인
  3) 입고 처리
  *//*




  public static StockingRequestDAO_bak dao = null;
  //관리자 입고 메뉴

  //1. 입고 요청서 조회(전체 입고 요청 목록) -> 기간별, 월별

  @Override
  public void inquiryAllWarehouseRequests() throws SQLException {
    dao.selectAllStockingRequest();
  }

  //2. 입고 요청 승인(미승인된 입고 요청 목록)
  @Override
  public static ArrayList<StockingRequestVO> getUnApprovedRecevingProcessingList()
      throws SQLException, InterruptedException {
    return dao.selectUnApprovedStockingRequest();
  }

  //3. 입고 처리 (승인된 입고 요청 목록)
  @Override
  public ArrayList<StockingRequestVO> getApprovedRecevingProcessingList()
      throws SQLException {
    return dao.selectApprovedStockingRequest();
  }

  //4. 입고 처리 완료
  @Override
  public void completeStockingRequest() {

  }







  //회원 입고 메뉴
  */
/*2. 회원 입고 메뉴
  1) 입고 요청
  2) 입고 요청서 조회
   *//*


  //1. 입고 요청
  @Override
  public void requestWarehousing(StockingRequestVO data) {
    dao.insertStockingRequest(data);
  }

  //2. 내 입고 요청서 조회 ->전체, 기간, 월
  @Override
  public ArrayList<StockingRequestVO> inquiryWarehouseRequests()
      throws SQLException {
    UserVO user = new UserVO();
    return dao.selectInquiryWarehouseRequests(user.getUserId());
  }


  public static void main(String[] args) throws SQLException, InterruptedException {
    getUnApprovedRecevingProcessingList();
  }
}
*/
