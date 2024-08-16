package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import lib.ConnectionPool;
import service.LoginService;
import service.MenuService;
import threadClass.ThreadPool;
import util.Role;
import util.UserStatus;
import vo.StockVO;
import vo.UserVO;

public class LoginServiceImpl implements LoginService {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private ConnectionPool connectionPool = ConnectionPool.getInstance();
  private Connection connection = null;
  private ResultSet rs = null;
  private String query;
  private PreparedStatement pstmt;
  private boolean quit = false;

  private UserManagementDAO dao = null;
  private ThreadPool threadPool = null;

  public LoginServiceImpl() {
    dao = new UserManagementDAO();
  }

  @Override
  public void showMenu() throws IOException, SQLException, InterruptedException {
    while (!quit) {
      System.out.println("1. 로그인");
      System.out.println("2. 회원가입");
      System.out.println("3. 아이디 찾기");
      System.out.println("4. 비밀번호 찾기");
      System.out.println("5. 종료");
      int sel = Integer.parseInt(br.readLine());
      switch (sel) {
        case 1:
          UserVO vo = login();
          System.out.println(vo);
          break;
        case 2:
          boolean b = registerUser();
          System.out.println(b);
          break;
        case 3:
          String id = findId();
          System.out.println(id);
          break;
        case 4:
          String pwd = findPwd();
          System.out.println(pwd);
          break;
        case 5:
          quit = true;
          ThreadPool threadPool = ThreadPool.getInstance();
          threadPool.shutdown();
          break;
        default:
          System.out.println("잘못된 선택입니다.");
          break;
      }
    }

  }

  /*
   * 로그인
   * */
  @Override
  public UserVO login() throws IOException, SQLException, InterruptedException {

    UserVO userVO = null;
    MenuService menuService = new MenuServiceImpl();

    System.out.println("1.회원");
    System.out.println("2.비회원");
    System.out.println("3.돌아가기");
    int sel = Integer.parseInt(br.readLine());

    switch (sel) {
      case 1:
        System.out.println("-".repeat(20));
        System.out.print("아이디: ");
        String id = br.readLine();
        System.out.print("비밀번호: ");
        String pwd = br.readLine();

        connection = connectionPool.getConnection(100);
        query =
            "SELECT user_id, password, name, birth, email, tel, role, status, address, business_number, business_name"
                + " FROM user"
                + " WHERE user_id = ? and password = ?";

        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, pwd);
        rs = pstmt.executeQuery();

        while (rs.next()) {
          userVO = new UserVO(
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
              rs.getString("business_number")
          );
        }

        menuService.showMenu(userVO);

        connectionPool.releaseConnection(connection);

        break;
      case 2:
        menuService.showMenu(userVO);
        break;
      case 3:
        break;
      default:
        break;
    }
    return userVO;
  }

  /*
   * 회원가입
   * */
  @Override
  public boolean registerUser() throws IOException {
    // TODO : Print 쪽으로 빼기
    System.out.println("-".repeat(20));
    System.out.println("가입하시려는 회원 유형을 선택해주세요");
    System.out.println("1.직원");
    System.out.println("2.창고관리자");
    System.out.println("3.운전기사");
    System.out.println("4.공급자");
    System.out.println("5.쇼핑몰 운영자");
    int sellectRole = Integer.parseInt(br.readLine());
    boolean registerSuccess = false;

    String role = switch (sellectRole) {
      case 1 -> Role.EMPLOYEE.toString();
      case 2 -> Role.WH_MANAGER.toString();
      case 3 -> Role.DELIVERY_DRIVER.toString();
      case 4 -> Role.SUPPLIER.toString();
      case 5 -> Role.STORE_OPERATOR.toString();
      default -> Role.GUEST.toString(); // TODO : 수정 필요
    };

    System.out.print("아이디: ");
    String userId = br.readLine();
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
    System.out.print("주소: ");
    String address = br.readLine();
    System.out.print("사업자명: ");
    String businessName = br.readLine();
    System.out.print("사업자번호: ");
    String businessNumber = br.readLine();

    // TODO : 여기부터 DAO
//    dao.insertUser()
    try {

      connection = connectionPool.getConnection(100);
      query =
          "INSERT INTO user(user_id, password, name, birth, email, tel, role, status, address, business_number, business_name)"
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

      pstmt = connection.prepareStatement(query);
      pstmt.setString(1, userId);
      pstmt.setString(2, password);
      pstmt.setString(3, name);
      pstmt.setDate(4, birth);
      pstmt.setString(5, email);
      pstmt.setString(6, tel);
      pstmt.setString(7, role);
      pstmt.setString(8, UserStatus.WAITING.toString());
      pstmt.setString(9, address);
      pstmt.setString(10, businessName);
      pstmt.setString(11, businessNumber);
      registerSuccess = pstmt.executeUpdate() != 0;
      pstmt.close();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      connectionPool.releaseConnection(connection);
    }

    return registerSuccess;
  }

  /*
   * 아이디 찾기
   * */
  @Override
  public String findId()
      throws IOException, SQLException, InterruptedException {  // TODO : email 유니크로 바꿔주기
    String userId = "";
    System.out.println("이메일 주소를 입력해주세요.");
    String email = br.readLine();

    connection = connectionPool.getConnection(100);
    query = "SELECT user_id"
        + " FROM user"
        + " WHERE email = ?";

    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, email);
    rs = pstmt.executeQuery();
    while (rs.next()) {
      userId = rs.getString("user_id"); // TODO : 중간에 몇글자는 *로 숨겨줄까..
    }
    pstmt.close();
    connectionPool.releaseConnection(connection);
    return userId;
  }

  /*
  * 비밀번호 찾기
  * */
  @Override
  public String findPwd() throws IOException, SQLException, InterruptedException {
    String password = "";
    System.out.println("아이디를 입력해주세요.");
    String userId = br.readLine();

    connection = connectionPool.getConnection(100);
    query = "SELECT password"
        + " FROM user"
        + " WHERE user_id = ?";

    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, userId);
    rs = pstmt.executeQuery();
    while (rs.next()) {
      password = rs.getString("password"); // TODO : 중간에 몇글자는 *로 숨겨줄까..
    }
    pstmt.close();
    connectionPool.releaseConnection(connection);
    return password;
  }
}
