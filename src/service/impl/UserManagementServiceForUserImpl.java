package service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lib.ConnectionPool;
import service.UserManagementServiceForUser;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class UserManagementServiceForUserImpl implements UserManagementServiceForUser {

  private ConnectionPool connectionPool = ConnectionPool.getInstance();
  private Connection connection = null;
  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private String query;
  private PreparedStatement pstmt;

  /*
  * 내 정보 상세 보기
  * */
  @Override
  public UserVO printUserDetails(UserVO userVO) {
    return null;
  }

  /*
  * 내 정보 수정
  * */
  @Override
  public UserVO updateUserDetails(UserVO userVO)
      throws SQLException, InterruptedException, IOException {

    System.out.println(userVO);

    connection = connectionPool.getConnection(100);

    System.out.println("유저(" + userVO.getUserId() + ")에 대한 변경 사항들을 입력해주십시오.");
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

    query = "UPDATE user"
        + " SET name = ?, birth = ?, email = ?, tel = ?, address = ?, business_number = ?, business_name = ?"
        + " WHERE user_id = ?";

    pstmt = connection.prepareStatement(query);

    pstmt.setString(1, name);
    pstmt.setDate(2, birth);
    pstmt.setString(3, email);
    pstmt.setString(4, tel);
    pstmt.setString(5, address);
    pstmt.setString(6, businessName);
    pstmt.setString(7, businessNumber);
    pstmt.setString(8, userVO.getUserId());

    int rows = pstmt.executeUpdate();

    pstmt.close();
    connectionPool.releaseConnection(connection);

    if (rows != 0) {
      UserVO updatedUser = new UserVO(
          userVO.getUserId(),
          userVO.getPassword(),
          name,
          birth,
          email,
          tel,
          userVO.getRole(),
          userVO.getStatus(),
          address,
          businessName,
          businessNumber
      );
      userVO = updatedUser;
    }

    return userVO;
  }

  /*
  * 비밀번호 변경
  * */
  @Override
  public void updatePassword(UserVO userVO) throws SQLException, IOException {
    connection = connectionPool.getConnection(100);
    System.out.println("현재 비밀번호를 입력하세요");
    String currentPwd = br.readLine();

    System.out.println("변경할 비밀번호를 입력하세요");
    String nextPwd = br.readLine();
    System.out.println("다시 한 번 입력하세요");
    String checkPwd = br.readLine();

    if (nextPwd.equals(checkPwd)) {
      query = "UPDATE user"
          + " SET password = ?"
          + " WHERE user_id = ? and password = ?";
      pstmt = connection.prepareStatement(query);

      pstmt.setString(1, nextPwd);
      pstmt.setString(2, userVO.getUserId());
      pstmt.setString(3, currentPwd);
    }

    pstmt.close();
    connectionPool.releaseConnection(connection);

    return;

  }

  /*
  * 회원 탈퇴
  * */
  @Override
  public void cancelAccount(UserVO userVO) throws IOException, SQLException {
    connection = connectionPool.getConnection(100);
    System.out.println("비밀번호를 입력하세요");
    String pwd = br.readLine();

    query = "DELETE FROM user"
        + " WHERE user_id = ? AND password = ?";

    pstmt = connection.prepareStatement(query);
    pstmt.setString(1, userVO.getUserId());
    pstmt.setString(2, pwd);

    int rows = pstmt.executeUpdate();
  }
}
