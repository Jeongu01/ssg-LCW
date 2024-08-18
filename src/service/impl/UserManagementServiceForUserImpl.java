package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import service.UserManagementServiceForUser;
import vo.UserVO;

public class UserManagementServiceForUserImpl implements UserManagementServiceForUser {

  private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private UserVO userVO = null;
  private UserManagementDAO dao = null;

  public UserManagementServiceForUserImpl(UserVO userVO) {
    dao = new UserManagementDAO();
    this.userVO = userVO;
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
  public UserVO updateUserDetails(UserVO userVO) throws IOException {

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


    if (dao.updateUser(updatedUser)) {
      userVO = updatedUser;
    }

    return userVO;
  }

  /*
  * 비밀번호 변경
  * */
  @Override
  public boolean updatePassword(UserVO userVO) throws IOException {
    System.out.println("현재 비밀번호를 입력하세요");
    String currentPwd = br.readLine();

    System.out.println("변경할 비밀번호를 입력하세요");
    String nextPwd = br.readLine();
    System.out.println("다시 한 번 입력하세요");
    String checkPwd = br.readLine();

    if (nextPwd.equals(checkPwd)) {
      return dao.updatePassword(userVO.getUserId(), nextPwd);
    }

    return false;

  }

  /*
  * 회원 탈퇴
  * */
  @Override
  public boolean cancelAccount(UserVO userVO) throws IOException {
    System.out.println("비밀번호를 입력하세요");
    String pwd = br.readLine();

    if (pwd.equals(userVO.getPassword())) {
      return dao.deleteUser(userVO.getUserId());
    }

    return false;
  }
}
