package service.impl;

import common.dao.UserManagementDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lib.ConnectionPool;
import service.UserManagementService;
import util.Role;
import util.UserStatus;
import vo.UserVO;

public class UserManagementServiceImpl implements UserManagementService {

  private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private UserManagementDAO dao = null;
  private UserVO userVO = null;

  public UserManagementServiceImpl() {
    dao = new UserManagementDAO();
  }

  /*
   * 전체 회원 조회
   * */
  @Override
  public ArrayList<UserVO> printAllUsers() throws SQLException, InterruptedException {
    return dao.selectAllUsers();
  }

  /*
   * 회원 상세보기 (아이디로 검색)
   * */
  @Override
  public UserVO printOneUserDetailsById(String userId) throws SQLException, InterruptedException {
    return dao.selectUser(userId);
  }

  /*
   * 회원 상세보기 (사업자번호로 검색)
   * */
  @Override
  public UserVO printOneUserDetailsByBusinessNumber(String businessNumber) {
    return dao.selectUserByBusinessNumber(businessNumber);
  }

  /*
   * 승인 대기자 출력
   * */
  @Override
  public ArrayList<UserVO> printWaitingUsers() {
    return dao.selectWaitingUsers();
  }

  /*
   * 회원가입 요청 처리
   * */
  @Override
  public boolean approveRegistrationRequest(String userId, boolean approve) {
    return dao.approveRegistrationRequest(userId, approve);
  }

  /*
  * 회원 정보 수정
  * */
  @Override
  public boolean updateUserDetails(String userId)
      throws IOException {
    // TODO : Print 쪽으로 뺴기
    System.out.println("유저(" + userId + ")에 대한 변경 사항들을 입력해주십시오.");
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

    System.out.println("직책: ");
    System.out.println("1.직원");
    System.out.println("2.창고관리자");
    System.out.println("3.운전기사");
    System.out.println("4.공급자");
    System.out.println("5.쇼핑몰 운영자");
    int sellectRole = Integer.parseInt(br.readLine());
    Role role = switch (sellectRole) {
      case 1 -> Role.EMPLOYEE;
      case 2 -> Role.WH_MANAGER;
      case 3 -> Role.DELIVERY_DRIVER;
      case 4 -> Role.SUPPLIER;
      case 5 -> Role.STORE_OPERATOR;
      default -> Role.GUEST; // TODO : 수정 필요
    };

    System.out.println("상태: ");
    System.out.println("1.활동 중");
    System.out.println("2.계약 중");
    System.out.println("3.대기 중");
    System.out.println("4.정지");
    System.out.println("5.휴면");
    int sellectStatus = Integer.parseInt(br.readLine());
    UserStatus status = switch (sellectStatus){
      case 1 -> UserStatus.ACTIVE;
      case 2 -> UserStatus.VIP;
      case 3 -> UserStatus.WAITING;
      case 4 -> UserStatus.BANNED;
      case 5 -> UserStatus.DORMANT;
      default -> UserStatus.DENIED; // TODO : 다시 입력해달라고 수정해야됨
    };

    System.out.print("주소: ");
    String address = br.readLine();
    System.out.print("사업자명: ");
    String businessName = br.readLine();
    System.out.print("사업자번호: ");
    String businessNumber = br.readLine();

    // TODO : 이 밑으론 DAO
    UserVO userVO = new UserVO(userId, password, name, birth, email, tel, role, status, address, businessName, businessNumber);
    return dao.updateUser(userVO);
  }

  /*
  * 회원 삭제
  * */
  @Override
  public boolean deleteUser(String userId) throws IOException {
    // TODO : Print 쪽으로 빼기
    System.out.println("비밀번호를 입력해주세요.");
    String checkPwd = br.readLine();

    System.out.println("진짜 삭제합니다? (Y/N)");
    Character lastConfirm = br.readLine().charAt(0);

    if (lastConfirm.equals('N')) return false;

    // TODO : 이 밑으론 DAO
    return dao.deleteUser(userId);
  }


}
