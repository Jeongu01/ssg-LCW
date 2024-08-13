package common.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import lib.ConnectionPool;
import vo.StockingRequestVO;

public class StockingRequestDAO {
  private ConnectionPool conncp = null;
  private Connection connection = null;
  private ResultSet rs = null;

  public StockingRequestDAO(){
    init();

  }
  private void init(){
    conncp = ConnectionPool.getInstance();
  }
  public ArrayList<StockingRequestVO> selectAllStockingRequest(){
    return null;
  }
  public ArrayList<StockingRequestVO> selectApprovedStockingRequest(){
    return null;
  }
  public ArrayList<StockingRequestVO> selectUnApprovedStockingRequest(){
    return null;
  }
  public ArrayList<StockingRequestVO> selectinquiryWarehouseRequests(){
    return null;
  }
}
