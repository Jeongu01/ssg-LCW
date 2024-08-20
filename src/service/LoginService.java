package service;

import java.io.IOException;
import java.sql.SQLException;
import vo.UserVO;

public interface LoginService {
  public UserVO login(UserVO userVO) throws IOException, SQLException, InterruptedException;

  public boolean registerUser(UserVO userVO) throws IOException;

  public String findId(String email) throws IOException, SQLException, InterruptedException;

  public String findPwd(String pwd) throws IOException, SQLException, InterruptedException;
}