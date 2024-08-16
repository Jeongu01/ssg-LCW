package common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import lib.ConnectionPool;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class UserManagementDAO {

  private ConnectionPool connectionPool = null;
  private Connection connection = null;
  private ResultSet rs = null;

  public UserManagementDAO() {
    init();
  }

  private void init() {
    connectionPool = ConnectionPool.getInstance();
  }

  public ArrayList<UserVO> selectAllUsers() {
    ArrayList<UserVO> userList = new ArrayList<>();
    connection = connectionPool.getConnection(100);
    String query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user";
    try {
      PreparedStatement pstmt = connection.prepareStatement(query);
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

        pstmt.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      connectionPool.releaseConnection(connection);
    }
    return userList;
  }

  public UserVO selectUserByUserId(String userId) {
    UserVO user = null;
    connection = connectionPool.getConnection(100);
    String query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user"
            + " WHERE user_id = ?";
    try {
      PreparedStatement pstmt = connection.prepareStatement(query);
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
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      connectionPool.releaseConnection(connection);
    }

    return user;
  }

  public UserVO selectUserByBusinessNumber(String businessNumber) {
    UserVO user = null;
    connection = connectionPool.getConnection(100);
    String query =
        "SELECT user_id, password, name, birth, email, tel, role, status, address, business_name, business_number"
            + " FROM user"
            + " WHERE business_number = ?";
    try {
      PreparedStatement pstmt = connection.prepareStatement(query);
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
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      connectionPool.releaseConnection(connection);
    }
    return user;
  }

  public boolean insertUser(UserVO data) {
    boolean insertSuccess = false;
    connection = connectionPool.getConnection(100);

    try {
      String query =
          "INSERT INTO user(user_id, password, name, birth, email, tel, role, status, address, business_number, business_name)"
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

      PreparedStatement pstmt = connection.prepareStatement(query);
      pstmt.setString(1, data.getUserId());
      pstmt.setString(2, data.getPassword());
      pstmt.setString(3, data.getName());
      pstmt.setDate(4, java.sql.Date.valueOf(data.getBirth().toString()));
      pstmt.setString(5, data.getEmail());
      pstmt.setString(6, data.getTel());
      pstmt.setString(7, data.getRole().toString());
      pstmt.setString(8, data.getStatus().toString());
      pstmt.setString(9, data.getAddress());
      pstmt.setString(10, data.getBusinessName());
      pstmt.setString(11, data.getBusinessNumber());
      insertSuccess = pstmt.executeUpdate() != 0;
      pstmt.close();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      connectionPool.releaseConnection(connection);
    }

    return insertSuccess;
  }

  public boolean updateUser(UserVO data) {
    boolean updateSuccess = false;
    connection = connectionPool.getConnection(100);

    String query = "UPDATE user"
        + " SET password = ?, name = ?, birth = ?, email = ?, tel = ?, role = ?, status = ?, address = ?, business_number = ?, business_name = ?"
        + " WHERE user_id = ?";

    try {
      PreparedStatement pstmt = connection.prepareStatement(query);

      pstmt.setString(1, data.getPassword());
      pstmt.setString(2, data.getName());
      pstmt.setDate(3, java.sql.Date.valueOf(data.getBirth().toString()));
      pstmt.setString(4, data.getEmail());
      pstmt.setString(5, data.getTel());
      pstmt.setString(6, data.getRole().toString());
      pstmt.setString(7, data.getStatus().toString());
      pstmt.setString(8, data.getAddress());
      pstmt.setString(9, data.getBusinessName());
      pstmt.setString(10, data.getBusinessNumber());
      pstmt.setString(11, data.getUserId());

      int rows = pstmt.executeUpdate();
      updateSuccess = rows != 0;

      pstmt.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      connectionPool.releaseConnection(connection);
    }

    return updateSuccess;
  }

  public boolean deleteUser(UserVO data) {
    boolean deleteSuccess = false;
    connection = connectionPool.getConnection(100);

    String query = "DELETE FROM user"
        + " WHERE user_id = ?";

    try {
      PreparedStatement pstmt = connection.prepareStatement(query);
      pstmt.setString(1, data.getUserId());
      int rows = pstmt.executeUpdate();
      deleteSuccess = rows != 0;

      pstmt.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      connectionPool.releaseConnection(connection);
    }

    return deleteSuccess;
  }
}
