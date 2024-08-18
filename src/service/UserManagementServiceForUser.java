package service;

import java.io.IOException;
import java.sql.SQLException;
import vo.UserVO;

public interface UserManagementServiceForUser {
  public UserVO printUserDetails(UserVO userVO);  // TODO : 흠.. 필요한가

  public UserVO updateUserDetails(UserVO userVO)
      throws SQLException, InterruptedException, IOException;

  public boolean updatePassword(UserVO userVO) throws SQLException, IOException;

  public boolean cancelAccount(UserVO userVO) throws IOException, SQLException;
}
