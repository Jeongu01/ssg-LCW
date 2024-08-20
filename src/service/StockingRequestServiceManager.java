package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.StockingRequestVO;

public interface StockingRequestServiceManager {

  //모든 입고 요청서 조회
  public ArrayList<StockingRequestVO> selectAllStockingRequestList()
      throws SQLException, InterruptedException;

  //미승인된 입고처리 목록 조회
  public ArrayList<StockingRequestVO> selectUnApprovedStockingRequestList()
          throws SQLException;

  //입고 요청서 삭제
  public void deleteStockingRequest (int requestId) throws SQLException;

  //입고 승인
  public void approveStockingRequest(int requestId) throws SQLException;

  //처리 완료
  public void CompleteStockingRequest (int stockingRequestId) throws SQLException;

  //입고 요청서 조회(승인O/처리X)
  public ArrayList<StockingRequestVO> selectApprovedStockingRequest()
          throws SQLException;

  //입고 요청서 연도별 조회
  public List<StockingRequestVO> selectYearStockingRequest(String startYear, String endYear) throws SQLException;

  //입고 요청서 월별 조회
  public List<StockingRequestVO> selectMonthStockingRequest(String startYear, String endYear) throws SQLException;

  //입고 요청서 일별 조회
  public List<StockingRequestVO> selectDayStockingRequest(String startYear, String endYear) throws SQLException;


}
