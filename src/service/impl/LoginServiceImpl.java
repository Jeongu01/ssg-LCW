package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import service.LoginService;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class LoginServiceImpl implements LoginService {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private UserManagementDAO dao = null;

  public LoginServiceImpl() {
    dao = new UserManagementDAO();
  }

  /*
   * 로그인
   * */
  @Override
  public UserVO login(UserVO userVO) throws IOException, SQLException, InterruptedException {
    userVO = dao.selectUser(userVO.getUserId(), userVO.getPassword());
    return userVO;
  }

  /*
   * 회원가입
   * */
  @Override
  public boolean registerUser(UserVO userVO) throws IOException {
    userVO.setStatus(UserStatus.WAITING);
    return dao.insertUser(userVO);
  }

  /*
   * 아이디 찾기
   * */
  @Override
  public String findId(String email)
      throws IOException, SQLException, InterruptedException {
    return dao.selectUserId(email);
  }

  /*
   * 비밀번호 찾기
   * */
  @Override
  public String findPwd(String userId) throws IOException, SQLException, InterruptedException {
    return dao.selectUserPwd(userId);
  }
}