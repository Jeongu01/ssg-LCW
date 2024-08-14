package common.dao;

import lib.ConnectionPool;
import service.ReleaseServiceGM;
import service.ReleaseServiceMember;
import vo.CarVO;
import vo.DispatchVO;
import vo.ReleaseVO;
import vo.WaybillVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReleaseDAO implements ReleaseServiceGM, ReleaseServiceMember {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ConnectionPool conncp = null;
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    ReleaseVO releaseVO = null;

    public ReleaseDAO(){
        this.conncp = ConnectionPool.getInstance();
    }

    public boolean requestRelease(ReleaseVO data) throws SQLException, InterruptedException { //출고요청
        String query = "INSERT INTO " +
                "delivery_request(delivery_request_id, product_id, request_date, delivery_address, " +
                "address_detail, request_quantity, request_comment) " +
                "VALUES(NULL, ?, NOW(), ?, ?, ?, ?)";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, data.getProductId());
            pstmt.setString(2, data.getDeliveryAddress());
            pstmt.setString(3, data.getDeliveryAddressDetail());
            pstmt.setInt(4, data.getRequestQuantity());
            pstmt.setString(5, data.getRequestComment());

            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        conncp.releaseConnection(this.connection);
        return result1;
    }

    public ResultSet approveReleaseRequest() throws SQLException, InterruptedException { //출고승인
/*DELIMITER $$ -- 승인 및 운송장 자동 등록
CREATE PROCEDURE releaseProc()
BEGIN
	INSERT INTO delivery_request(approved_date) VALUES(NOW())"; -- 승인(수정 필요)
	INSERT INTO waybill(product_id, product_name, delivery_quantity, -- 운송장 등록
						business_name, start_address, business_tel,
						arrive_address, arrive_address_detail, request_comment)
                SELECT  p.product_id, p.product_name, d.request_quantity,
                        u.business_name, u.address, u.tel,
                        d.delivery_address, d.address_detail, d.request_comment
                        FROM delivery_request AS d, user AS u, stock AS s, product AS p
                        WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id;
END
DELIMITER ;*/


        String query = "CALL releaseProc()";

        this.connection = conncp.getConnection(100);

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        conncp.releaseConnection(this.connection);
        return rs;
    }


    public List<ReleaseVO> selectNotApprovedReleaseList() throws SQLException, InterruptedException { //미승인 리스트
        ArrayList<ReleaseVO> notApprovedReleaseList = new ArrayList<ReleaseVO>();

        String query = "SELECT d.delivery_request_id, d.user_id, d.request_date, d.product_id, p.product_name, d.request_quantity, u.address, d.delivery_address, d.address_detail FROM delivery_request AS d, user AS u, product AS p WHERE d.user_id = u.user_id AND d.product_id = p.product_id AND d.approved_date is null ORDER BY d.request_date DESC;";

        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            releaseVO = new ReleaseVO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDate(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9));

            notApprovedReleaseList.add(releaseVO);
        }

        return notApprovedReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseAllList() { //출고지시서 리스트(전체 조회)
        ArrayList<ReleaseVO> shippingReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                "FROM delivery_request" +
                "WHERE approved_date IS NOT NULL";

        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseYearList() { //출고지시서 리스트(년도별 검색)
        ArrayList<ReleaseVO> shippingReleaseYearList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "user_id, DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                "FROM delivery_request" +
                "approved_date IS NOT NULL AND" +
                "WHERE approved_date BETWEEN '?-01-01' and '?-12-31'";

        return shippingReleaseYearList;
    }

    public List<ReleaseVO> selectShippingReleaseMonthList() { //출고지시서 리스트(월별 검색)
        ArrayList<ReleaseVO> shippingReleaseMonthList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id AS '요청 아이디', " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "user_id AS '승인자ID', DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                "FROM delivery_request" +
                "approved_date IS NOT NULL AND" +
                "WHERE approved_date BETWEEN '?-?-01' and '?-?-31'";

        return shippingReleaseMonthList;
    }

    public List<ReleaseVO> selectShippingReleaseDayList() { //출고지시서 리스트(일별 검색)
        ArrayList<ReleaseVO> shippingReleaseDayList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id AS '요청 아이디', " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                "FROM delivery_request" +
                "approved_date IS NOT NULL AND" +
                "WHERE approved_date BETWEEN '?-?-?' and '?-?-?'";

        return shippingReleaseDayList;
    }

    public List<ReleaseVO> selectShippingReleaseDetail() { //출고지시서 상세보기(조회)
        ArrayList<ReleaseVO> shippingReleaseDetail = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, user_id AS '요청 아이디', " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "DATE_FORMAT(approved_date, '%Y-%m-%d'), " +
                "p.product_id, p.product_name, d.request_quantity, " +
                "d.delivery_address, d.address_detail " +
                "c.car_number, c.car_type" +
                "FROM delivery_request AS d, user AS u, stock AS s, product AS p, dispatch AS dis, car AS c" +
                "WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id AND " +
                "d.delivery_request_id = dis.delivery_request_id AND dis.car_number = c.car_number AND " +
                "delivery_request_id = ?";

        return shippingReleaseDetail;
    }

    public List<ReleaseVO> selectProductsReleaseNameList() { //출고 상품 리스트(이름으로 검색)
        ArrayList<ReleaseVO> productsReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT p.product_id, p.product_name, p.product_brand," +
                "d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                "FROM delivery_request AS d, user AS u, stock AS s, product AS p" +
                "WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id" +
                "d.approved_date IS NOT NULL AND p.product_name like '%?%'";

        return productsReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseYearList() { //출고 상품 리스트(년별로 검색)
        ArrayList<ReleaseVO> productsReleaseYearList = new ArrayList<ReleaseVO>();
        String query = "SELECT p.product_id, p.product_name, p.product_brand," +
                "d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), DATE_FORMAT(approved_date, '%Y-%m-%d')" +
                "FROM delivery_request AS d, user AS u, stock AS s, product AS p" +
                "WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id" +
                "d.approved_date IS NOT NULL AND" +
                "d.approved_date BETWEEN '?-01-01' AND '?-12-31'";

        return productsReleaseYearList;
    }

    public List<ReleaseVO> selectProductsReleaseMonthList() { //출고 상품 리스트(월별로 검색)
        ArrayList<ReleaseVO> productsReleaseMonthList = new ArrayList<ReleaseVO>();
        String query = "SELECT p.product_id, p.product_name, p.product_brand," +
                "d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), DATE_FORMAT(approved_date, '%Y-%m-%d')" +
                "FROM delivery_request AS d, user AS u, stock AS s, product AS p" +
                "WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id" +
                "d.approved_date IS NOT NULL AND" +
                "d.approved_date BETWEEN '?-?-01' AND '?-?-31'";

        return productsReleaseMonthList;
    }

    public List<ReleaseVO> selectProductsReleaseDayList() { //출고 상품 리스트(일별로 검색)
        ArrayList<ReleaseVO> productsReleaseDayList = new ArrayList<ReleaseVO>();
        String query = "SELECT p.product_id, p.product_name, p.product_brand," +
                "d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), DATE_FORMAT(approved_date, '%Y-%m-%d')" +
                "FROM delivery_request AS d, user AS u, stock AS s, product AS p" +
                "WHERE d.user_id = u.user_id AND u.user_id = s.user_id AND s.product_id = p.product_id" +
                "d.approved_date IS NOT NULL AND" +
                "d.approved_date BETWEEN '?-?-?' AND '?-?-?'";

        return productsReleaseDayList;
    }


    public List<WaybillVO> selectWaybillAllList() { //운송장 리스트(전체 조회)
        ArrayList<WaybillVO> waybillAllList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                "product_id, product_name, " +
                "start_address, arrive_address, arrive_address_detail" +
                "FROM waybill";

        return waybillAllList;
    }

    public List<WaybillVO> selectWaybillYearList() { //운송장 리스트(년도별 조회)
        ArrayList<WaybillVO> waybillYearList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                "product_id, product_name, " +
                "start_address, arrive_address, arrive_address_detail" +
                "FROM waybill" +
                "WHERE depart_date BETWEEN '?-01-01' AND '?-12-31'";

        return waybillYearList;
    }

    public List<WaybillVO> selectWaybillMonthList() { //운송장 리스트(월별 조회)
        ArrayList<WaybillVO> waybillMonthList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                "product_id, product_name, " +
                "start_address, arrive_address, arrive_address_detail" +
                "FROM waybill" +
                "WHERE depart_date BETWEEN '?-?-01' AND '?-?-31'";

        return waybillMonthList;
    }

    public List<WaybillVO> selectWaybillDayList() { //운송장 리스트(일별 조회)
        ArrayList<WaybillVO> waybillDayList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                "product_id, product_name, " +
                "start_address, arrive_address, arrive_address_detail" +
                "FROM waybill" +
                "WHERE depart_date BETWEEN '?-?-?' AND '?-?-?'";

        return waybillDayList;
    }

    public List<WaybillVO> selectWaybillDetail() { //운송장 상세보기(조회)
        ArrayList<WaybillVO> waybillDetail = new ArrayList<WaybillVO>();
        String query = "SELECT * FROM waybill WHERE waybill_id = ?";

        return waybillDetail;
    }

    public boolean updateWaybill() { //운송장 수정
        String query = "UPDATE waybill" +
                "SET business_name = ?, address = ?, business_tel = ?" +
                "arrive_address = ?, arrive_address_detail = ?" +
                "WHERE waybill_id = ?";

        return false;
    }

    @Override
    public boolean registerDispatch(DispatchVO data) throws SQLException, InterruptedException { //배차 등록
        String query = "INSERT INTO VALUES(?,?)";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, data.getWaybillId());
            pstmt.setString(2, data.getCarNumber());

            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        conncp.releaseConnection(this.connection);

        return result1;
    }

    public void selectDispatchList() { //배차 리스트 조회
        String query = "SELECT dis.waybill_id, dis.car_number, c.car_type" +
                "FROM dispatch AS dis, waybill AS w, car AS c " +
                "WHERE w.waybill_id = dis.waybill_id AND dis.car_number = c.car_number" +
                "ORDER BY w.depart_date DESC";
    }

    public boolean cancleDispatch(DispatchVO data) throws SQLException, InterruptedException { //배차 취소(삭제)
        String query = "DELETE FROM dispatch WHERE waybill_id = ?";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, data.getWaybillId());


            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        conncp.releaseConnection(this.connection);
        return result1;
    }

    public boolean completeDelivery() throws SQLException, InterruptedException { //배송완료
        String query = "INSERT INTO delivery_request(complete_date) VALUES(NOW())";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);

            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        conncp.releaseConnection(this.connection);
        return result1;
    }

    public List<CarVO> selectCarAllList() { //차량 조회(전체 조회)
        ArrayList<CarVO> CarAllList = new ArrayList<>();
        String query = "SELECT * FROM car";

        return CarAllList;
    }

    public boolean registerCar(CarVO data) throws SQLException, InterruptedException { //차량 등록
        String query = "INSERT INTO car VALUES(?,?,?)";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, data.getCarNumber());
            pstmt.setString(2, data.getCarType());
            pstmt.setInt(3, data.getMaxLoad());

            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        conncp.releaseConnection(this.connection);
        return result1;
    }

    public boolean updateCar(CarVO data) throws SQLException, InterruptedException { // 차량 수정
        String query = "UPDATE car SET car_number = ?, car_type = ?, max_load = ?";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, data.getCarNumber());
            pstmt.setString(2, data.getCarType());
            pstmt.setInt(3, data.getMaxLoad());


            int result = pstmt.executeUpdate();
            pstmt.close();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        conncp.releaseConnection(this.connection);
        return result1;
    }

}
