package common.dao;

import java.io.IOException;
import lib.ConnectionPool;
import service.ReleaseServiceGM;
import service.ReleaseServiceMember;
import vo.*;

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
    WaybillVO waybillVO = null;
    DispatchVO dispatchVO = null;
    private UserVO userVO = null;
    CallableStatement cstmt = null;

    public ReleaseDAO(){
        this.conncp = ConnectionPool.getInstance();
        this.userVO = userVO;
    }

    public boolean requestRelease() throws SQLException, InterruptedException, IOException { //출고요청
        String query = "INSERT INTO " +
                " d.user_id, delivery_request(delivery_request_id, product_id, request_date, delivery_address, " +
                " address_detail, request_quantity, request_comment) " +
                " VALUES(?, NULL, ?, NOW(), ?, ?, ?, ?) ";

        boolean result1 = false;

        System.out.print("신청할 아이디 : ");
        String userId = br.readLine();
        System.out.print("요청할 상품번호 : ");
        int productId = Integer.parseInt(br.readLine());
        System.out.print("요청할 배송지 주소 : ");
        String deliveryAddress = br.readLine();
        System.out.print("요청할 배송지 상세주소 : ");
        String deliveryAddressDetail = br.readLine();
        System.out.print("요청할 배송수량 : ");
        int requestQuantity = Integer.parseInt(br.readLine());
        System.out.print("요청 코멘트 : ");
        String requestComment = br.readLine();

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setInt(2, productId);
            pstmt.setString(3, deliveryAddress);
            pstmt.setString(4, deliveryAddressDetail);
            pstmt.setInt(5, requestQuantity);
            pstmt.setString(6, requestComment);

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

    public boolean approveReleaseRequest(int requestId) throws SQLException, InterruptedException, IOException { //출고승인

        String query = "UPDATE delivery_request dr "
            + " JOIN stock s ON dr.product_id = s.product_id "
            + " AND dr.user_id = s.user_id "
            + " SET dr.approved_date = NOW() "
            + " WHERE dr.delivery_request_id = ? "
            + " AND dr.request_quantity <= s.storage_quantity ";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, requestId);

            int result = pstmt.executeUpdate();

            if(result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }

            pstmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        conncp.releaseConnection(this.connection);
        return result1;
    }

    public boolean registerWaybill() throws SQLException { //운송장 등록(승인시 사용)
        String query = "INSERT INTO waybill(product_id, product_name, delivery_quantity, "
            + " business_name, start_address, business_tel, " //business_name하고 number는 null값 허용하면 안됨
            + " arrive_address, arrive_address_detail, request_comment) "
            + " SELECT  p.product_id, p.product_name, d.request_quantity, "
            + " u.business_name, u.address, u.tel, "
            + " d.delivery_address, d.address_detail, d.request_comment "
            + " FROM delivery_request AS d, user AS u, product AS p "
            + " WHERE d.user_id = u.user_id AND d.product_id = p.product_id" +
                " AND u.business_name IS NOT NULL AND u.tel IS NOT NULL";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

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


    public List<ReleaseVO> selectNotApprovedReleaseList() throws SQLException, InterruptedException { //미승인 리스트
        ArrayList<ReleaseVO> notApprovedReleaseList = new ArrayList<ReleaseVO>();

        String query = "SELECT d.delivery_request_id, d.user_id, d.request_date, d.product_id, p.product_name, " +
                "d.request_quantity, u.address, d.delivery_address, d.address_detail FROM delivery_request AS d, user AS u, " +
                "product AS p WHERE d.user_id = u.user_id AND d.product_id = p.product_id AND d.approved_date IS NULL " +
                " AND u.business_name IS NOT NULL AND u.tel IS NOT NULL " +
                " ORDER BY d.request_date DESC";

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
        rs.close();
        pstmt.close();

        return notApprovedReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseAllList() throws SQLException { //출고지시서 리스트(전체 조회)
        ArrayList<ReleaseVO> shippingReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                "DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                "DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request " +
                " WHERE approved_date IS NOT NULL";

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4)
            );
            shippingReleaseList.add(releaseVO);
        }
        rs.close();
        pstmt.close();

        return shippingReleaseList;
    }

    public List<ReleaseVO> selectShippingReleaseYearList() throws SQLException, IOException { //출고지시서 리스트(년도별 검색)
        ArrayList<ReleaseVO> shippingReleaseYearList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                " DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request " +
                " WHERE approved_date IS NOT NULL AND " +
                " approved_date BETWEEN ? and ? ";

        System.out.print("시작 연도 : ");
        String startYear = br.readLine();
        System.out.print("끝 연도 : ");
        String endYear = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYear + "-01-01");
        pstmt.setString(2,endYear + "-12-31");

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4)

                );
            shippingReleaseYearList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return shippingReleaseYearList;
    }

    public List<ReleaseVO> selectShippingReleaseMonthList() throws IOException, SQLException { //출고지시서 리스트(월별 검색)
        ArrayList<ReleaseVO> shippingReleaseMonthList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                " DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request" +
                " WHERE approved_date IS NOT NULL AND" +
                " approved_date BETWEEN ? and ?";

        System.out.print("시작 년/월 ('-'를 사용해서 입력해주세요) : ");
        String startYM = br.readLine();
        System.out.print("끝 년/월 ('-'를 사용해서 입력해주세요) : ");
        String endYM = br.readLine();


        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYM + "-01");
        pstmt.setString(2,endYM + "-31");


        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4)

            );
            shippingReleaseMonthList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return shippingReleaseMonthList;
    }

    public List<ReleaseVO> selectShippingReleaseDayList() throws IOException, SQLException { //출고지시서 리스트(일별 검색)
        ArrayList<ReleaseVO> shippingReleaseDayList = new ArrayList<ReleaseVO>();
        String query = "SELECT delivery_request_id, user_id, " +
                " DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request" +
                " WHERE approved_date IS NOT NULL AND" +
                " approved_date BETWEEN ? and ?";

        System.out.print("시작 년-월-일 ('-'를 사용하여 입력해주세요) : ");
        String startYMD = br.readLine();
        System.out.print("끝 년-월-일 ('-'를 사용하여 입력해주세요) : ");
        String endYMD =  br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYMD);
        pstmt.setString(2,endYMD);


        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4)

            );
            shippingReleaseDayList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return shippingReleaseDayList;
    }

    public List<ReleaseVO> selectShippingReleaseDetail() throws SQLException, IOException { //출고지시서 상세보기(조회)
        ArrayList<ReleaseVO> shippingReleaseDetail = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, d.user_id, " +
                " DATE_FORMAT(d.request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(d.approved_date, '%Y-%m-%d'), " +
                " p.product_id, p.product_name, d.request_quantity, " +
                " d.delivery_address, d.address_detail, " +
                " c.car_number, c.car_type " +
                " FROM delivery_request AS d, user AS u, product AS p, dispatch AS dis, car AS c, waybill AS w " +
                " WHERE d.user_id = u.user_id AND d.product_id = p.product_id AND " +
                " d.delivery_request_id = w.delivery_request_id AND dis.car_number = c.car_number AND " +
                " d.delivery_request_id = ? AND d.approved_date IS NOT NULL";

        this.connection = conncp.getConnection(100);

        System.out.print("출고번호를 입력해주세요 : ");
        int releaseNo = Integer.parseInt(br.readLine());

        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,releaseNo);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4),
                rs.getInt(5),
                rs.getString(6),
                rs.getInt(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11)
                );
            shippingReleaseDetail.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return shippingReleaseDetail;
    }

    public List<ReleaseVO> selectProductsReleaseNameList() throws SQLException, IOException { //출고 상품 리스트(이름으로 검색)
        ArrayList<ReleaseVO> productsReleaseList = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, p.product_id, p.product_name, p.product_brand, " +
                " d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request AS d, product AS p" +
                " WHERE d.product_id = p.product_id AND " +
                " d.approved_date IS NOT NULL AND p.product_name like ?";

        System.out.print("상품이름 검색 : ");
        String productName = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,"%" + productName + "%");


        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getDate(6),
                rs.getDate(7)
            );
            productsReleaseList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return productsReleaseList;
    }

    public List<ReleaseVO> selectProductsReleaseYearList() throws SQLException, IOException { //출고 상품 리스트(년별로 검색)
        ArrayList<ReleaseVO> productsReleaseYearList = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, p.product_id, p.product_name, p.product_brand, " +
                " d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request AS d, product AS p" +
                " WHERE d.product_id = p.product_id AND " +
                " d.approved_date IS NOT NULL AND " +
                " d.approved_date BETWEEN ? AND ?";

        System.out.print("시작 연도 : ");
        String startYear = br.readLine();
        System.out.print("끝 연도 : ");
        String endYear = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYear + "-01-01");
        pstmt.setString(2,endYear + "-12-31");

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getDate(6),
                rs.getDate(7)
            );
            productsReleaseYearList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return productsReleaseYearList;
    }

    public List<ReleaseVO> selectProductsReleaseMonthList() throws IOException, SQLException { //출고 상품 리스트(월별로 검색)
        ArrayList<ReleaseVO> productsReleaseMonthList = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, p.product_id, p.product_name, p.product_brand, " +
                " d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request AS d, product AS p" +
                " WHERE d.product_id = p.product_id AND " +
                " d.approved_date IS NOT NULL AND " +
                " d.approved_date BETWEEN ? AND ?";

        System.out.print("시작 년-월 ('-'를 사용해서 입력해주세요) : ");
        String startYM = br.readLine();
        System.out.print("끝 년-월 ('-'를 사용해서 입력해주세요) : ");
        String endYM = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYM + "-01");
        pstmt.setString(2,endYM + "-31");

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getDate(6),
                    rs.getDate(7)
            );
            productsReleaseMonthList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return productsReleaseMonthList;
    }

    public List<ReleaseVO> selectProductsReleaseDayList() throws IOException, SQLException { //출고 상품 리스트(일별로 검색)
        ArrayList<ReleaseVO> productsReleaseDayList = new ArrayList<ReleaseVO>();
        String query = "SELECT d.delivery_request_id, p.product_id, p.product_name, p.product_brand, " +
                " d.request_quantity, DATE_FORMAT(request_date, '%Y-%m-%d'), " +
                " DATE_FORMAT(approved_date, '%Y-%m-%d') " +
                " FROM delivery_request AS d, product AS p" +
                " WHERE d.product_id = p.product_id AND " +
                " d.approved_date IS NOT NULL AND " +
                " d.approved_date BETWEEN ? AND ?";

        System.out.print("시작 년-월 ('-'를 사용해서 입력해주세요) : ");
        String startYMD = br.readLine();
        System.out.print("끝 년-월 ('-'를 사용해서 입력해주세요) : ");
        String endYMD = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startYMD);
        pstmt.setString(2,endYMD);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            ReleaseVO releaseVO = new ReleaseVO(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getDate(6),
                    rs.getDate(7)
            );
            productsReleaseDayList.add(releaseVO);
        }

        rs.close();
        pstmt.close();

        return productsReleaseDayList;
    }


    public List<WaybillVO> selectWaybillAllList() throws SQLException { //운송장 리스트(전체 조회)
        ArrayList<WaybillVO> waybillAllList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d'), " +
                "product_id, product_name, " +
                "start_address, arrive_address, arrive_address_detail " +
                "FROM waybill";

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            waybillVO = new WaybillVO(
                    rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            waybillAllList.add(waybillVO);
        }
        rs.close();
        pstmt.close();


        return waybillAllList;
    }

    public List<WaybillVO> selectWaybillYearList() throws SQLException, IOException { //운송장 리스트(년도별 조회)
        ArrayList<WaybillVO> waybillYearList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                " product_id, product_name, " +
                " start_address, arrive_address, arrive_address_detail" +
                " FROM waybill" +
                " WHERE depart_date BETWEEN ? AND ?";

        System.out.print("시작 연도 : ");
        String startDate = br.readLine();
        System.out.print("끝 연도 : ");
        String endDate = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startDate + "-01-01");
        pstmt.setString(2,endDate + "-12-31");

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            waybillVO = new WaybillVO(
                    rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            waybillYearList.add(waybillVO);
        }
        rs.close();
        pstmt.close();

        return waybillYearList;
    }

    public List<WaybillVO> selectWaybillMonthList() throws IOException, SQLException { //운송장 리스트(월별 조회)
        ArrayList<WaybillVO> waybillMonthList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                " product_id, product_name, " +
                " start_address, arrive_address, arrive_address_detail" +
                " FROM waybill" +
                " WHERE depart_date BETWEEN ? AND ?";

        System.out.print("시작 년-월 ('-'를 사용해서 입력해주세요) : ");
        String startDate = br.readLine();
        System.out.print("끝 년-월 ('-'를 사용해서 입력해주세요) : ");
        String endDate = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startDate + "-01");
        pstmt.setString(2,endDate + "-31");

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            waybillVO = new WaybillVO(
                    rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            waybillMonthList.add(waybillVO);
        }
        rs.close();
        pstmt.close();

        return waybillMonthList;
    }

    public List<WaybillVO> selectWaybillDayList() throws IOException, SQLException { //운송장 리스트(일별 조회)
        ArrayList<WaybillVO> waybillDayList = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, DATE_FORMAT(depart_date, '%Y-%m-%d')," +
                " product_id, product_name, " +
                " start_address, arrive_address, arrive_address_detail" +
                " FROM waybill" +
                " WHERE depart_date BETWEEN ? AND ?";

        System.out.print("시작 년-월-일 ('-'를 사용해서 입력해주세요) : ");
        String startDate = br.readLine();
        System.out.print("끝 년-월-일 ('-'를 사용해서 입력해주세요) : ");
        String endDate = br.readLine();

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1,startDate);
        pstmt.setString(2,endDate);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            waybillVO = new WaybillVO(
                    rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            waybillDayList.add(waybillVO);
        }
        rs.close();
        pstmt.close();

        return waybillDayList;
    }

    public List<WaybillVO> selectWaybillDetail(int waybillId) throws IOException, SQLException { //운송장 상세보기(조회)
        ArrayList<WaybillVO> waybillDetail = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, delivery_request_id, depart_date, "
            + " product_id, product_name, delivery_quantity, "
            + " business_name, start_address, business_tel, "
            + " arrive_address, arrive_address_detail, request_comment "
            + " FROM waybill "
            + " WHERE waybill_id = ? AND business_name IS NOT NULL AND business_tel IS NOT NULL";

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, waybillId);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            WaybillVO waybillVO = new WaybillVO(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDate(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getInt(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            );
            waybillDetail.add(waybillVO);
        }

        rs.close();
        pstmt.close();

        return waybillDetail;
    }


    public List<WaybillVO> selectWaybillDetailFromReleaseNo(int requestId) throws SQLException, IOException { //운송장 상세보기(출고번호로 조회)
        ArrayList<WaybillVO> waybillDetail = new ArrayList<WaybillVO>();
        String query = "SELECT waybill_id, delivery_request_id, depart_date, "
            + " product_id, product_name, delivery_quantity, "
            + " business_name, start_address, business_tel, "
            + " arrive_address, arrive_address_detail, request_comment "
            + " FROM waybill "
            + " WHERE delivery_request_id = ? ";

        this.connection = conncp.getConnection(100);

        pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, requestId);

        rs = pstmt.executeQuery();
        conncp.releaseConnection(this.connection);

        while(rs.next()){
            WaybillVO waybillVO = new WaybillVO(
            rs.getInt(1),
            rs.getInt(2),
            rs.getDate(3),
            rs.getInt(4),
            rs.getString(5),
            rs.getInt(6),
            rs.getString(7),
            rs.getString(8),
            rs.getString(9),
            rs.getString(10),
            rs.getString(11),
            rs.getString(12)
                );
            waybillDetail.add(waybillVO);
        }

        rs.close();
        pstmt.close();

        return waybillDetail;
    }


    public boolean updateWaybill(String businessName, String address, String businessTel, String arriveAddress, String arriveAddressDetail, int waybillId) throws IOException { //운송장 수정
        String query = "UPDATE waybill " +
                "SET business_name = ?, start_address = ?, business_tel = ?, " +
                "arrive_address = ?, arrive_address_detail = ? " +
                "WHERE waybill_id = ?";

        boolean result1 = false;


        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1,businessName);
            pstmt.setString(2,address);
            pstmt.setString(3, businessTel);
            pstmt.setString(4, arriveAddress);
            pstmt.setString(5, arriveAddressDetail);
            pstmt.setInt(6, waybillId);

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

    @Override
    public boolean registerDispatch() throws SQLException, InterruptedException, IOException { //배차 등록
        String query = "INSERT INTO dispatch VALUES(?,?)";

        System.out.print("운송장 번호 : ");
        int waybillId = Integer.parseInt(br.readLine());
        System.out.print("차량 번호 : ");
        String carNumber = br.readLine();

        boolean result1 = false;

        this.connection = conncp.getConnection(100);

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, waybillId);
            pstmt.setString(2, carNumber);

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

    public List<DispatchVO> selectDispatchList() throws SQLException { //배차 리스트 조회
        List<DispatchVO> dispatchList = new ArrayList<>();
        String query = "SELECT dis.waybill_id, dis.car_number, c.car_type " +
                "FROM dispatch AS dis, waybill AS w, car AS c " +
                "WHERE w.waybill_id = dis.waybill_id AND dis.car_number = c.car_number " +
                "ORDER BY w.depart_date DESC";


        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            dispatchVO = new DispatchVO(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3));

            dispatchList.add(dispatchVO);
        }
        rs.close();
        pstmt.close();

        return dispatchList;
    }

    public boolean updateDispatch () throws IOException, SQLException {
        String query = "UPDATE dispatch SET car_number = ? " +
                "WHERE waybill_id = ? ";

        boolean result1 = false;
        System.out.print("수정할 배차의 운송장 번호를 입력해주세요 : ");
        int waybillId = Integer.parseInt(br.readLine());
        System.out.print("수정할 차량번호를 입력해주세요 : ");
        String carNumber = br.readLine();

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, carNumber);
            pstmt.setInt(2, waybillId);

            int result = pstmt.executeUpdate();
            pstmt.close();

            if(result == 1) {
                result1 = true;
            } else if (result == 2) {
                result1 = false;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        conncp.releaseConnection(this.connection);
        return result1;
    }

    public boolean cancleDispatch() throws SQLException, InterruptedException, IOException { //배차 취소(삭제)
        String query = "DELETE FROM dispatch WHERE waybill_id = ?";

        System.out.print("취소할 배차의 운송장 번호 : ");
        int waybillId = Integer.parseInt(br.readLine());


        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, waybillId);


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

    @Override
    public boolean completeDelivery(UserVO user, int waybillId) throws SQLException, InterruptedException { //배송완료
        //String query = "INSERT INTO delivery_request(complete_date) VALUES(NOW())";
        String query = "{CALL delivery_request_proc(?,?)}";

        boolean result1 = false;

        this.connection = conncp.getConnection(100);
        try {
            cstmt = connection.prepareCall(query);
            cstmt.setString(1, user.getUserId());
            cstmt.setInt(2, waybillId);
            int result = cstmt.executeUpdate();
            cstmt.close();

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

    public List<CarVO> selectCarAllList() throws SQLException { //차량 조회(전체 조회)
        ArrayList<CarVO> carAllList = new ArrayList<CarVO>();
        String query = "SELECT car_number, car_type, max_load FROM car";

        this.connection = conncp.getConnection(100);
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery();

        conncp.releaseConnection(this.connection);

        while (rs.next()) {
            CarVO carVO = new CarVO(
                rs.getString(1),
                rs.getString(2),
                rs.getInt(3));

            carAllList.add(carVO);
        }
        rs.close();

        return carAllList;
    }

    public boolean registerCar() throws SQLException, InterruptedException, IOException { //차량 등록
        String query = "INSERT INTO car VALUES(?,?,?)";

        boolean result1 = false;

        System.out.print("등록할 차량번호 : ");
        String carNumber = br.readLine();
        System.out.print("등록할 차종");
        String carType = br.readLine();
        System.out.print("등록할 최대적재량 : ");
        int maxLoad = Integer.parseInt(br.readLine());


        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, carNumber);
            pstmt.setString(2, carType);
            pstmt.setInt(3, maxLoad);

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

    public boolean updateCar() throws SQLException, InterruptedException, IOException { // 차량 수정
        String query = "UPDATE car SET car_number = ?, car_type = ?, max_load = ? WHERE car_number = ?";

        boolean result1 = false;
        System.out.print("기존 차량번호 : ");
        String carNumber1 = br.readLine();
        System.out.print("수정할 차량번호 : ");
        String carNumber2 = br.readLine();
        System.out.print("수정할 차종");
        String carType = br.readLine();
        System.out.print("수정할 최대적재량 : ");
        int maxLoad = Integer.parseInt(br.readLine());


        this.connection = conncp.getConnection(100);
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, carNumber2);
            pstmt.setString(2, carType);
            pstmt.setInt(3, maxLoad);
            pstmt.setString(4, carNumber1);

            int result = pstmt.executeUpdate();

            if (result == 1) {
                result1 = true;
            } else if (result == 0) {
                result1 = false;
            }

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
