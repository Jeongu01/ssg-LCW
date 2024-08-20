package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import service.UserManagementServiceForUser;
import vo.UserVO;

public class UserManagementServiceForUserImpl implements UserManagementServiceForUser {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private UserManagementDAO dao = null;

  public UserManagementServiceForUserImpl() {
    dao = new UserManagementDAO();
  }

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
  public UserVO updateUserDetails(UserVO userVO, UserVO updatedUserVO) throws IOException {
    updatedUserVO.setUserId(userVO.getUserId());
    updatedUserVO.setPassword(userVO.getPassword());
    updatedUserVO.setRole(userVO.getRole());
    updatedUserVO.setStatus(userVO.getStatus());
    if (dao.updateUser(updatedUserVO)) {
      return updatedUserVO;
    }
    return userVO;
  }

  /*
   * 비밀번호 변경
   * */
  @Override
  public boolean updatePassword(UserVO userVO, String nextPwd) throws IOException {
    if (dao.updatePassword(userVO.getUserId(), nextPwd)) {
      userVO.setPassword(nextPwd);
      return true;
    }
    return false;
  }

  /*
   * 회원 탈퇴
   * */
  @Override
  public boolean cancelAccount(UserVO userVO) throws IOException {
    return dao.deleteUser(userVO.getUserId());
  }
}