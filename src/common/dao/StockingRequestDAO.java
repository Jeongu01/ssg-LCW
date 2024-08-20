package common.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lib.ConnectionPool;
import service.StockingRequestServiceManager;
import service.StockingRequestServiceSupplier;
import vo.StockingRequestVO;
import vo.UserVO;

public class StockingRequestDAO implements StockingRequestServiceManager, StockingRequestServiceSupplier {

    private ConnectionPool conncp = null;
    private Connection connection = null;
    private ResultSet rs = null;
    private UserVO userVO = null;
    private PreparedStatement pstmt = null;
    private CallableStatement cstmt = null;
    StockingRequestVO vo = null;

    public StockingRequestDAO() {
        init();
    }

    private void init() {
        conncp = ConnectionPool.getInstance();
    }

    //관리자 입고 메뉴
    //1. 입고 요청서 조회(전체 입고 요청 목록) -> [기간별, 삭제 , 나가기]
    public ArrayList<StockingRequestVO> selectAllStockingRequestList()
            throws SQLException {

        ArrayList<StockingRequestVO> ret1 = new ArrayList<>();
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
            ret1.add(vo);
        }
        return ret1;
    }

    //입고 요청서 삭제
    public void deleteStockingRequest(int requestId) throws SQLException {
        String query = "DELETE FROM stocking_request WHERE stocking_request_id = ?";
        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, requestId);
        pstmt.executeUpdate();
        pstmt.close();
        conncp.releaseConnection(this.connection);

    }

    //2. 미승인된 입고 요청 목록(승인X/처리X)
    public ArrayList<StockingRequestVO> selectUnApprovedStockingRequestList()
            throws SQLException {
        ArrayList<StockingRequestVO> ret2 = new ArrayList<>();
        String query = "select * from stocking_request where approved_date is null AND complete_date IS NULL";

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

    //입고승인
    public void approveStockingRequest(int requestId) throws SQLException {
        String query = "UPDATE stocking_request SET approved_date = NOW() "
                + " WHERE stocking_request_id = ? ";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, requestId);
        pstmt.executeUpdate();
        pstmt.close();
        conncp.releaseConnection(this.connection);
    }


    //입고요청서 연도별 조회
    public List<StockingRequestVO> selectYearStockingRequest(String startYear, String endYear) throws SQLException {

        ArrayList<StockingRequestVO> yearStockingRequest = new ArrayList<>();
        String query = "SELECT stocking_request_id, s.user_id, s.product_id, storage_id, " +
                "request_id, request_quantity, request_comment " +
                "FROM stocking_request as s, user as u, product as p " +
                "WHERE s.user_id = u.user_id AND s.product_id = p.product_id AND " +
                "s.request_id BETWEEN ? AND ?";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, startYear + "-01-01");
        pstmt.setString(2, endYear + "-12-31");
        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            StockingRequestVO vo = new StockingRequestVO();
            vo.setStockingRequestId(rs.getInt(1));
            vo.setUserId(rs.getString(2));
            vo.setProductId(rs.getInt(3));
            vo.setStorageId(rs.getInt(4));
            vo.setRequestId(rs.getDate(5));
            vo.setRequestQuantity(rs.getInt(6));
            vo.setRequestComment(rs.getString(7));

            yearStockingRequest.add(vo);
        }
        rs.close();
        pstmt.close();
        return yearStockingRequest;
    } //입고 요청서 연도별 조회

    //입고 요청서 월별 조회
    public List<StockingRequestVO> selectMonthStockingRequest(String startYM, String endYM) throws SQLException {

        ArrayList<StockingRequestVO> yearStockingRequest = new ArrayList<>();
        String query = "SELECT stocking_request_id, s.user_id, s.product_id, storage_id, " +
                "request_id, request_quantity, request_comment " +
                "FROM stocking_request as s, user as u, product as p " +
                "WHERE s.user_id = u.user_id AND s.product_id = p.product_id AND " +
                "s.request_id BETWEEN ? AND ?";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, startYM + "-01");
        pstmt.setString(2, endYM + "-31");
        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            StockingRequestVO vo = new StockingRequestVO();
            vo.setStockingRequestId(rs.getInt(1));
            vo.setUserId(rs.getString(2));
            vo.setProductId(rs.getInt(3));
            vo.setStorageId(rs.getInt(4));
            vo.setRequestId(rs.getDate(5));
            vo.setRequestQuantity(rs.getInt(6));
            vo.setRequestComment(rs.getString(7));

            yearStockingRequest.add(vo);
        }
        rs.close();
        pstmt.close();
        return yearStockingRequest;
    } //입고 요청서 월별 조회

    //입고 요청서 일별 조회
    public List<StockingRequestVO> selectDayStockingRequest(String startYMD, String endYMD) throws SQLException {

        ArrayList<StockingRequestVO> yearStockingRequest = new ArrayList<>();
        String query = "SELECT stocking_request_id, s.user_id, s.product_id, storage_id, " +
                "request_id, request_quantity, request_comment " +
                "FROM stocking_request as s, user as u, product as p " +
                "WHERE s.user_id = u.user_id AND s.product_id = p.product_id AND " +
                "s.request_id BETWEEN ? AND ?";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, startYMD);
        pstmt.setString(2, endYMD);
        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            StockingRequestVO vo = new StockingRequestVO();
            vo.setStockingRequestId(rs.getInt(1));
            vo.setUserId(rs.getString(2));
            vo.setProductId(rs.getInt(3));
            vo.setStorageId(rs.getInt(4));
            vo.setRequestId(rs.getDate(5));
            vo.setRequestQuantity(rs.getInt(6));
            vo.setRequestComment(rs.getString(7));

            yearStockingRequest.add(vo);
        }
        rs.close();
        pstmt.close();
        return yearStockingRequest;
    } //입고 요청서 일별 조회
    
    
    

    //3. 입고 처리 (승인된 입고 요청 목록) [승인O/처리X]
    public ArrayList<StockingRequestVO> selectApprovedStockingRequest()
            throws SQLException {
        ArrayList<StockingRequestVO> ret3 = new ArrayList<>();
        String query = "SELECT * FROM stocking_request WHERE approved_date IS NOT NULL AND complete_date IS NULL";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
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

    //처리 완료 = 입고 완료
    public void CompleteStockingRequest(int stockingRequestId) throws SQLException {
        String query = "{CALL stocking_request_proc(?,?)}";
        this.connection = conncp.getConnection(100);

        cstmt = connection.prepareCall(query);
        cstmt.setString(1, "user013"); //여기 임시로 유저 설정해서 수정해야됨
        cstmt.setInt(2, stockingRequestId);

        conncp.releaseConnection(this.connection);
        cstmt.executeUpdate();
        cstmt.close();
    }


    //회원 기능
    //1. 내 입고 요청서 조회
    public ArrayList<StockingRequestVO> selectInquiryWarehouseRequests(String userId)
            throws SQLException {
        ArrayList<StockingRequestVO> ret4 = new ArrayList<>();
        String query = "SELECT * FROM stocking_request WHERE user_id = ?";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, userId);
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
            ret4.add(vo);
        }
        return ret4;
    }

    //2. 입고 요청
    public void insertStockingRequest(StockingRequestVO data) {
        String query = "insert into Stocking_request(user_id, product_id, storage_id, request_id, request_quantity, request_comment) "
                + "values(?,?,?,NOW(),?,?)";
        this.connection = conncp.getConnection(100);

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, data.getUserId());
            pstmt.setInt(2, data.getProductId());
            pstmt.setInt(3, data.getStorageId());
            pstmt.setInt(4, data.getRequestQuantity());
            pstmt.setString(5, data.getRequestComment());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            conncp.releaseConnection(this.connection);
            this.connection = null;
        }
    }

    //요청취소(회원)
    public void cancleStockingRequestMember(StockingRequestVO data) throws SQLException {
        String query = "DELETE FROM stocking_request " +
                "WHERE approved_date IS NULL AND " +
                "complete_date IS NULL AND " +
                "stocking_request_id = ?";
        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, data.getStockingRequestId());

        pstmt.executeUpdate();
        pstmt.close();
        conncp.releaseConnection(this.connection);
    }


}

