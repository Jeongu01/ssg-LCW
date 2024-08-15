package common.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import lib.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
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

      while(rs.next()) {

        stock = new StockVO(
            rs.getString("user_id"),
            rs.getInt("storage_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

        stockList.add(stock);
      }

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
      sql = " s.user_id, s.storage_id, s.product_name, s.quantity, pd.productName from stock s "
          + " JOIN product pd ON s.productId = pd.productId  "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " where s.productId = %?% OR p.productName like =%?%";

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
  public void displayStorageStatus()  throws SQLException, InterruptedException{

    List<StockVO> stockList = new ArrayList<StockVO>();
    this.connection = conncp.getConnection(100);
    try {
      sql = " select s.storage_id as 창고번호, "
          + " st.storage_name as 창고이름 , "
          + " storage_area as 창고면적, "
          + " sum(s.storage_quantity)*area_per_product as 재고량 "
          + " , round((sum(s.storage_quantity)*area_per_product / storage_area)*100,1) as '창고사용량(%)' "
          + " from stock s "
          + " JOIN product pd ON s.product_Id = pd.product_Id "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " group by s.product_id, s.storage_id";

      pstmt = connection.prepareStatement(sql);

      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next()) {
        int storageId = rs.getInt("창고번호");
        String storageName = rs.getString("창고이름");
        int storageArea = rs.getInt("창고면적");
        String storageUsage = Integer.toString(rs.getInt("재고량"));
        String storageUsageRate = Integer.toString(rs.getInt("창고사용량(%)"))+"%";

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("  창고번호      |     창고이름      |     창고면적     |    재고량     |     창고사용량(%) ");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println( storageId+"    "+storageName+"    "+storageArea+"   "+storageUsage+"    "+storageUsageRate );
        System.out.println("-------------------------------------------------------------------------------------------");

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //거래처 현황 조회 displayClientStorageUsage
  public void displayClientStorageUsage()  throws SQLException, InterruptedException{


    this.connection = conncp.getConnection(100);
    try {
      sql = " select u.user_id as 고객번호, "
          + " s.storage_id as 창고번호, "
          + " st.storage_name as 창고이름 , "
          + " storage_area as 창고면적, "
          + " sum(s.storage_quantity)*area_per_product as 재고량 "
          + " , round((sum(s.storage_quantity)*area_per_product / storage_area)*100,1) as '창고사용량(%)' "
          + " from stock s "
          + " JOIN product pd ON s.product_Id = pd.product_Id "
          + " JOIN storage st ON s.storage_id = st.storage_id "
          + " JOIN user u ON u.user_id = s.user_id"
          + " group by u.user_id, s.product_id, s.storage_id";

      pstmt = connection.prepareStatement(sql);

      rs = pstmt.executeQuery();

      conncp.releaseConnection(this.connection);

      while(rs.next()) {
        String userId = rs.getString("고객번호");
        int storageId = rs.getInt("창고번호");
        String storageName = rs.getString("창고이름");
        int storageArea = rs.getInt("창고면적");
        String storageUsage = Integer.toString(rs.getInt("재고량"));
        String storageUsageRate = Integer.toString(rs.getInt("창고사용량(%)"))+"%";

        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("  고객번호      |     창고번호      |     창고이름      |     창고면적     |    재고량     |     창고사용량(%) ");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println( userId+"    "+storageId+"    "+storageName+"    "+storageArea+"   "+storageUsage+"    "+storageUsageRate );
        System.out.println("--------------------------------------------------------------------------------------------------------");

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }




}
