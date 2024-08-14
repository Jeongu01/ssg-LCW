package common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lib.ConnectionPool;
import vo.StockingRequestVO;

public class StockingRequestDAO {

  private ConnectionPool conncp = null;
  private Connection connection = null;
  private ResultSet rs = null;

  public StockingRequestDAO() {
    init();
  }

  private void init() {
    conncp = ConnectionPool.getInstance();
  }

  public ArrayList<StockingRequestVO> selectAllStockingRequest()
      throws SQLException, InterruptedException {

    ArrayList<StockingRequestVO> ret = new ArrayList<>();
    String query = "select * from stocking_request";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    this.rs = pstmt.executeQuery();

    conncp.releaseConnection(this.connection);
    this.connection = null;

    while (rs.next()) {
      StockingRequestVO vo = new StockingRequestVO();
      vo.setStockingRequestId(rs.getInt("stocking_request_id"));
      vo.setUserId(rs.getString("user_id"));
      vo.setProductId(rs.getInt("product_id"));
      vo.setStorageId(rs.getInt("storage_id"));
      vo.setRequestId(rs.getDate("request_id"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret.add(vo);
    }
    return ret;
  }

  public ArrayList<StockingRequestVO> selectApprovedStockingRequest()
      throws SQLException, InterruptedException {

    ArrayList<StockingRequestVO> ret1 = new ArrayList<>();

    String query = "select * from stocking_request where approved_date is not null";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    this.rs = pstmt.executeQuery();

    conncp.releaseConnection(this.connection);
    this.connection = null;

    while (rs.next()) {
      StockingRequestVO vo = new StockingRequestVO();
      vo.setStockingRequestId(rs.getInt("stocking_request_id"));
      vo.setUserId(rs.getString("user_id"));
      vo.setProductId(rs.getInt("product_id"));
      vo.setStorageId(rs.getInt("storage_id"));
      vo.setRequestId(rs.getDate("request_id"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret1.add(vo);
    }
    return ret1;

  }

  public ArrayList<StockingRequestVO> selectUnApprovedStockingRequest()
      throws SQLException, InterruptedException {

    ArrayList<StockingRequestVO> ret2 = new ArrayList<>();

    String query = "select * from stocking_request where approved_date is null";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    this.rs = pstmt.executeQuery();

    conncp.releaseConnection(this.connection);
    this.connection = null;

    while (rs.next()) {
      StockingRequestVO vo = new StockingRequestVO();
      vo.setStockingRequestId(rs.getInt("stocking_request_id"));
      vo.setUserId(rs.getString("user_id"));
      vo.setProductId(rs.getInt("product_id"));
      vo.setStorageId(rs.getInt("storage_id"));
      vo.setRequestId(rs.getDate("request_id"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret2.add(vo);
    }
    return ret2;
  }

  public ArrayList<StockingRequestVO> selectInquiryWarehouseRequests(String userId)
      throws SQLException, InterruptedException {
    ArrayList<StockingRequestVO> ret3 = new ArrayList<>();

    String query = "select * from stocking_request where user_id = ?";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    pstmt.setString(1,userId);
    this.rs = pstmt.executeQuery();

    conncp.releaseConnection(this.connection);
    this.connection = null;

    while (rs.next()) {
      StockingRequestVO vo = new StockingRequestVO();
      vo.setStockingRequestId(rs.getInt("stocking_request_id"));
      vo.setUserId(rs.getString("user_id"));
      vo.setProductId(rs.getInt("product_id"));
      vo.setStorageId(rs.getInt("storage_id"));
      vo.setRequestId(rs.getDate("request_id"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret3.add(vo);
    }
    return ret3;
  }
}