package common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lib.ConnectionPool;
import vo.StockingRequestVO;
import vo.UserVO;

public class StockingRequestDAO {

  private ConnectionPool conncp = null;
  private Connection connection = null;
  private ResultSet rs = null;
  private UserVO userVO = null;
  private PreparedStatement pstmt = null;
  private CallableStatement cstmt =  null;
  StockingRequestVO vo = null;

  public StockingRequestDAO() {
    this.userVO = uservo;
    init();
  }

  private void init() {
    conncp = ConnectionPool.getInstance();
  }

  //관리자 입고 메뉴
  //1. 입고 요청서 조회(전체 입고 요청 목록) -> [기간별, 삭제 , 나가기]
  public ArrayList<StockingRequestVO> selectAllStockingRequest()
      throws SQLException{

    ArrayList<StockingRequestVO> ret1 = new ArrayList<>();
    String query = "select "
        + " stocking_request_id, user_id, product_id, storage_id, request_date, approved_date, complete_date, request_quantity, request_comment "
        + "from stocking_request";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    rs = pstmt.executeQuery();

    conncp.releaseConnection(this.connection);

    while (rs.next()) {
      vo = new StockingRequestVO(
      rs.getInt(1),
      rs.getString(2),
      rs.getInt(3),
      rs.getInt(4),
      rs.getDate(5),
      rs.getDate(6),
      rs.getDate(7),
      rs.getInt(8),
      rs.getString(9));
      ret1.add(vo);
    }
    return ret1;
  }

  //2. 입고 요청 승인(미승인된 입고 요청 목록)[승인, 미승인]
  public ArrayList<StockingRequestVO> selectUnApprovedStockingRequest()
      throws SQLException{
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
      vo.setRequestDate(rs.getDate("request_date"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret2.add(vo);
    }
    return ret2;
  }

  //3. 입고 처리 (승인된 입고 요청 목록) [처리 완료, 미승인]
  public ArrayList<StockingRequestVO> selectApprovedStockingRequest()
      throws SQLException{
    ArrayList<StockingRequestVO> ret3 = new ArrayList<>();
    String query = "SELECT * FROM stocking_request WHERE approved_date IS NOT NULL";
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
      vo.setRequestDate(rs.getDate("request_date"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret3.add(vo);
    }
    return ret3;
  }

  //4. 처리 완료
  public void Complete (int stockingRequestId) throws SQLException {
    String query = "{CALL stocking_request_proc(?,?)}";
    this.connection = conncp.getConnection(100);

    cstmt = connection.prepareCall(query);
    cstmt.setString(1,userVO.getUserId());
    cstmt.setInt(2, stockingRequestId);

    conncp.releaseConnection(this.connection);
    cstmt.executeUpdate();
    cstmt.close();
  }

  //5. 삭제(관리자)
  public void delete (StockingRequestVO data) throws SQLException{
    String query = "DELETE FROM stocking_request WHERE stocking_request_id = ?";
    this.connection = conncp.getConnection(100);

    PreparedStatement pstmt = connection.prepareStatement(query);
    pstmt.setInt(1,data.getStockingRequestId());
    this.rs = pstmt.executeQuery();
    conncp.releaseConnection(this.connection);
    this.connection = null;


  }



  //회원 기능
  //1. 내 입고 요청서 조회
  public ArrayList<StockingRequestVO> selectInquiryWarehouseRequests(String userId)
      throws SQLException{
    ArrayList<StockingRequestVO> ret4 = new ArrayList<>();
    String query = "SELECT * FROM stocking_request WHERE user_id = ?";
    this.connection = conncp.getConnection(100);
    pstmt = connection.prepareStatement(query);
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
      vo.setRequestDate(rs.getDate("request_date"));
      vo.setApprovedDate(rs.getDate("approved_date"));
      vo.setCompleteDate(rs.getDate("complete_date"));
      vo.setRequestQuantity(rs.getInt("request_quantity"));
      vo.setRequestComment(rs.getString("request_comment"));
      ret4.add(vo);
    }
    return ret4;
    }

    //2. 입고 요청
    public void insertStockingRequest(StockingRequestVO data){
    String query = "insert into Stocking_request(user_id, product_id, storage_id, request_date, request_quantity, request_comment) "
        + "values(?,?,?,?,?,?)";
      this.connection = conncp.getConnection(100);

      try{
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,data.getUserId());
        pstmt.setInt(2,data.getProductId());
        pstmt.setInt(3,data.getStorageId());
        pstmt.setDate(4,data.getRequestDate());
        pstmt.setInt(5,data.getRequestQuantity());
        pstmt.setString(6,data.getRequestComment());
        pstmt.executeUpdate();
        pstmt.close();

      }catch (SQLException e){
        System.err.println(e.getMessage());
      }finally {
        conncp.releaseConnection(this.connection);
        this.connection = null;
      }
    }


  }

