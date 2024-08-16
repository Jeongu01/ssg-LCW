package service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import lib.ConnectionPool;
import service.UserManagementService;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class UserManagementServiceImpl implements UserManagementService {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private ConnectionPool connectionPool = ConnectionPool.getInstance();
  private Connection connection = null;
  private ResultSet rs = null;
  private PreparedStatement pstmt;
  private String query;

  /*
   * 전체 회원 조회
   * */
  @Override
  public ArrayList<UserVO> printAllUsers() throws SQLException, InterruptedException {
    ArrayList<UserVO> userList = new ArrayList<>();
    connection = connectionPool.getConnection(100);
    query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user";
    pstmt = connection.prepareStatement(query);
    rs = pstmt.executeQuery();

    while (rs.next()) {
      UserVO user = new UserVO(
          rs.getString("user_id"),
          rs.getString("password"),
          rs.getString("name"),
          new Date(rs.getDate("birth").getTime()),
          rs.getString("email"),
          rs.getString("tel"),
          Role.valueOf(rs.getString("role")),
          UserStatus.valueOf(rs.getString("status")),
          rs.getString("address"),
          rs.getString("business_name"),
          rs.getString("business_number"));
      userList.add(user);
    }

    pstmt.close();
    connectionPool.releaseConnection(connection);
    return userList;
  }

  /*
   * 회원 상세보기 (아이디로 검색)
   * */
  @Override
  public UserVO printOneUserDetailsById(String userId) throws SQLException, InterruptedException {
    UserVO user = null;
    connection = connectionPool.getConnection(100);
    query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user"
            + " WHERE user_id = ?";
    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, userId);
    rs = pstmt.executeQuery();

    while (rs.next()) {
      user = new UserVO(
          rs.getString("user_id"),
          rs.getString("password"),
          rs.getString("name"),
          new Date(rs.getDate("birth").getTime()),
          rs.getString("email"),
          rs.getString("tel"),
          Role.valueOf(rs.getString("role")),
          UserStatus.valueOf(rs.getString("status")),
          rs.getString("address"),
          rs.getString("business_name"),
          rs.getString("business_number"));
    }

    pstmt.close();
    connectionPool.releaseConnection(connection);
    return user;
  }

  /*
   * 회원 상세보기 (사업자번호로 검색)
   * */
  @Override
  public UserVO printOneUserDetailsByBusinessNumber(String businessNumber)
      throws SQLException, InterruptedException {
    UserVO user = null;
    connection = connectionPool.getConnection(100);
    query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user"
            + " WHERE business_number = ?";
    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, businessNumber);
    rs = pstmt.executeQuery();

    while (rs.next()) {
      user = new UserVO(
          rs.getString("user_id"),
          rs.getString("password"),
          rs.getString("name"),
          new Date(rs.getDate("birth").getTime()),
          rs.getString("email"),
          rs.getString("tel"),
          Role.valueOf(rs.getString("role")),
          UserStatus.valueOf(rs.getString("status")),
          rs.getString("address"),
          rs.getString("business_name"),
          rs.getString("business_number"));
    }
    pstmt.close();
    connectionPool.releaseConnection(connection);
    return user;
  }

  /*
   * 승인 대기자 출력
   * */
  @Override
  public ArrayList<UserVO> printWaitingUsers() throws SQLException, InterruptedException {
    ArrayList<UserVO> userList = new ArrayList<>();
    connection = connectionPool.getConnection(100);
    query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user"
            + " WHERE status = ?";
    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, UserStatus.WAITING.toString());
    rs = pstmt.executeQuery();

    while (rs.next()) {
      UserVO userVO = new UserVO(
          rs.getString("user_id"),
          rs.getString("password"),
          rs.getString("name"),
          new Date(rs.getDate("birth").getTime()),
          rs.getString("email"),
          rs.getString("tel"),
          Role.valueOf(rs.getString("role")),
          UserStatus.valueOf(rs.getString("status")),
          rs.getString("address"),
          rs.getString("business_name"),
          rs.getString("business_number"));
      userList.add(userVO);
    }
    pstmt.close();
    connectionPool.releaseConnection(connection);
    return userList;
  }

  /*
   * 회원가입 요청 처리
   * */
  @Override
  public boolean approveRegistrationRequest(String userId, boolean approve)
      throws SQLException, InterruptedException {
    connection = connectionPool.getConnection(100);
    query = "UPDATE user"
        + " SET status = ?"
        + " WHERE status = ? AND user_id = ?";
    pstmt = connection.prepareStatement(query);
    if (approve) {   // 승인
      pstmt.setString(1, UserStatus.ACTIVE.toString());
    } else {        // 거부
      pstmt.setString(1,
          UserStatus.DENIED.toString()); // TODO : 로그인 했을 때 DENIED 라면 DELETE 되는 트리거 작성
    }
    pstmt.setString(2, UserStatus.WAITING.toString());
    pstmt.setString(3, userId);

    pstmt.close();
    connectionPool.releaseConnection(connection);

    int rows = pstmt.executeUpdate();
    return rows != 0;
  }

  /*
  * 회원 정보 수정
  * */
  @Override
  public boolean updateUserDetails(String userId)
      throws SQLException, InterruptedException, IOException {
    connection = connectionPool.getConnection(100);

    // TODO : Print 쪽으로 뺴기
    System.out.println("유저(" + userId + ")에 대한 변경 사항들을 입력해주십시오.");
    System.out.print("비밀번호: ");
    String password = br.readLine();
    System.out.print("이름: ");
    String name = br.readLine();
    System.out.print("생년월일: ");
    java.sql.Date birth = java.sql.Date.valueOf(br.readLine());
    System.out.print("이메일: ");
    String email = br.readLine();
    System.out.print("전화번호: ");
    String tel = br.readLine();

    System.out.println("직책: ");
    System.out.println("1.직원");
    System.out.println("2.창고관리자");
    System.out.println("3.운전기사");
    System.out.println("4.공급자");
    System.out.println("5.쇼핑몰 운영자");
    int sellectRole = Integer.parseInt(br.readLine());
    String role = switch (sellectRole) {
      case 1 -> Role.EMPLOYEE.toString();
      case 2 -> Role.WH_MANAGER.toString();
      case 3 -> Role.DELIVERY_DRIVER.toString();
      case 4 -> Role.SUPPLIER.toString();
      case 5 -> Role.STORE_OPERATOR.toString();
      default -> Role.GUEST.toString(); // TODO : 수정 필요
    };

    System.out.println("상태: ");
    System.out.println("1.활동 중");
    System.out.println("2.계약 중");
    System.out.println("3.대기 중");
    System.out.println("4.정지");
    System.out.println("5.휴면");
    int sellectStatus = Integer.parseInt(br.readLine());
    String status = switch (sellectStatus){
      case 1 -> UserStatus.ACTIVE.toString();
      case 2 -> UserStatus.VIP.toString();
      case 3 -> UserStatus.WAITING.toString();
      case 4 -> UserStatus.BANNED.toString();
      case 5 -> UserStatus.DORMANT.toString();
      default -> "여기엔 뭘 넣지";  // TODO : 진짜 뭐 넣지
    };

    System.out.print("주소: ");
    String address = br.readLine();
    System.out.print("사업자명: ");
    String businessName = br.readLine();
    System.out.print("사업자번호: ");
    String businessNumber = br.readLine();

    // TODO : 이 밑으론 DAO
    query = "UPDATE user"
        + " SET password = ?, name = ?, birth = ?, email = ?, tel = ?, role = ?, status = ?, address = ?, business_number = ?, business_name = ?"
        + " WHERE user_id = ?";

    pstmt = connection.prepareStatement(query);

    pstmt.setString(1, password);
    pstmt.setString(2, name);
    pstmt.setDate(3, birth);
    pstmt.setString(4, email);
    pstmt.setString(5, tel);
    pstmt.setString(6, role);
    pstmt.setString(7, status);
    pstmt.setString(8, address);
    pstmt.setString(9, businessName);
    pstmt.setString(10, businessNumber);
    pstmt.setString(11, userId);

    int rows = pstmt.executeUpdate();

    pstmt.close();
    connectionPool.releaseConnection(connection);

    return rows != 0;
  }

  /*
  * 회원 삭제
  * */
  @Override
  public boolean deleteUser(String userId) throws SQLException, InterruptedException, IOException {
    connection = connectionPool.getConnection(100);

    // TODO : Print 쪽으로 빼기
    System.out.println("비밀번호를 입력해주세요.");
    String checkPwd = br.readLine();

    System.out.println("진짜 삭제합니다? (Y/N)");
    Character lastConfirm = br.readLine().charAt(0);

    if (lastConfirm.equals('N')) return false;

    // TODO : 이 밑으론 DAO
    query = "DELETE FROM user"
        + " WHERE user_id = ? AND password = ?";

    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, userId);
    pstmt.setString(2, checkPwd);
    int rows = pstmt.executeUpdate();

    pstmt.close();
    connectionPool.releaseConnection(connection);

    return rows != 0;
  }


}
