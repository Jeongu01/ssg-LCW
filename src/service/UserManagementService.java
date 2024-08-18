package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import vo.UserVO;

public interface UserManagementService {

  public ArrayList<UserVO> printAllUsers() throws SQLException, InterruptedException;

  public UserVO printOneUserDetailsById(String userId) throws SQLException, InterruptedException;

  public UserVO printOneUserDetailsByBusinessNumber(String businessNumber)
      throws SQLException, InterruptedException;

  public ArrayList<UserVO> printWaitingUsers() throws SQLException, InterruptedException;

  public boolean approveRegistrationRequest(String userId, boolean approve)
      throws SQLException, InterruptedException;

  public boolean updateUserDetails(String userId)
      throws SQLException, InterruptedException, IOException;

  public boolean deleteUser(String userId) throws SQLException, InterruptedException, IOException;
}
