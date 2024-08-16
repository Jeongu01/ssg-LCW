package service;

import java.io.IOException;
import java.sql.SQLException;
import vo.UserVO;

public interface LoginService {
  public void showMenu() throws IOException, SQLException, InterruptedException;

  public UserVO login() throws IOException, SQLException, InterruptedException;

  public boolean registerUser() throws IOException;

  public String findId() throws IOException, SQLException, InterruptedException;

  public String findPwd() throws IOException, SQLException, InterruptedException;
}
