package service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import lib.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import lib.ObjectDBIO;
import vo.ProductVO;
import vo.StockVO;

public class StocktakingDAO {

  private ConnectionPool conncp = null;
  private Connection connection = null;
  private ResultSet rs = null;
  private String sql;
  private PreparedStatement pstmt;
  StockVO stock = null;

  public StocktakingDAO(){
    init();
  }

  private void init(){
    conncp = ConnectionPool.getInstance();
  }


  /*관리자급 재고 출력*/

  //회원별 재고 목록 출력


  //대분류 재고 목록 출력
  public List<StockVO> majorStockList(int categoryId) throws SQLException, InterruptedException {

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select *, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where productid IN "
          + " ( select p.productid, p.productName from product p "
          + " join (select * from small_category sm1 join "
          + " (select * from middle_category mi join major_category ma "
          + " on mi.majorcategoryid = ma.majorcategoryid "
          + " where ma.majorcategoryid = ? ) AS a "
          + " ON sm1.middle_categoryId = a.middle_categoryId) AS b "
          + " on b.small_categoryid = p.small_categoryid )";

      pstmt = connection.prepareStatement(sql);
      pstmt.setInt(1, categoryId);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

        stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;
  }

  //중분류 재고 목록 출력
  public List<StockVO> middleStockList(int categoryId) throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);

    try {
      sql = " select *, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where productid IN "
          + " ( select p.productid from product p "
          + " JOIN (select * from small_category sm1 "
          + " JOIN middle_category mi "
          + " ON sm1.small_categoryId = mi.small_categoryId "
          + " WHERE mi.middle_categoryId = ?) a"
          + " ON p.small_categoryId = a.small_categoryId) AS C ";

      pstmt = connection.prepareStatement(sql);
      pstmt.setInt(1, categoryId);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

      stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;
  }


  //소분류 재고 목록 출력
  public List<StockVO> smallStockList(int categoryId) throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select *, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where small_categoryId = ?";

      pstmt = connection.prepareStatement(sql);
      pstmt.setInt(1, categoryId);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

      stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;
  }

  //전체 재고 목록 출력
  public List<StockVO> wholeStockList() throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select *, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where small_categoryId = ?";

      pstmt = connection.prepareStatement(sql);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

      stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;

  }

  //검색어로 재고 목록 출력
  public List<StockVO> keywordStockList(String input) throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select *, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where s.productId = ? OR p.productName like =?";

      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, input);
      pstmt.setString(2, input);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

      stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;
  }


  //창고 현황 조회
  public List<StockVO> warehouseStatus()  throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select *, "
          + " (select ) "
          + " from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where s.productId = ? OR p.productName like =?";

      pstmt = connection.prepareStatement(sql);
      pstmt.setString(1, input);
      pstmt.setString(2, input);
      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next())

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

      stockList.add(stock);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return stockList;
  }






}
