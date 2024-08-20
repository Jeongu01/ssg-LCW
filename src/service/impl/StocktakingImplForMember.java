package service.impl;

import common.dao.StocktakingDAO;
import frontend.PrintFunctionName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import service.StocktakingInterface;
import vo.StockVO;
import vo.UserVO;

public class StocktakingImplForMember implements StocktakingInterface {

  private StocktakingDAO dao = null;
  private UserVO userVO = null;

  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  List<StockVO> list = null;
  StockVO stockVO = null;

  public StocktakingImplForMember(UserVO userVO) {
    //dao = new StocktakingDAO(userVO);
    dao = new StocktakingDAO();
    this.userVO = userVO;
  }


  @Override
  public List<StockVO> wholeStockList() throws SQLException, InterruptedException {
    return dao.wholeStockList(userVO);
  }

  @Override
  public List<StockVO> majorStockList(String categoryName)
      throws SQLException, InterruptedException {
    return dao.majorStockList(userVO, categoryName);
  }

  @Override
  public List<StockVO> middleStockList(String categoryName)
      throws SQLException, InterruptedException {
    return dao.middleStockList(userVO, categoryName);
  }

  @Override
  public List<StockVO> smallStockList(String categoryName)
      throws SQLException, InterruptedException {
    return dao.smallStockList(userVO, categoryName);
  }

  @Override
  public List<StockVO> keywordStockList(String keyword) throws SQLException, InterruptedException {
    return dao.keywordStockList(userVO, keyword);
  }

  @Override
  public List<StockVO> userStockList(String userId) throws SQLException, InterruptedException {
    return dao.userdStockList(userId);
  }

  @Override
  public List<StockVO> keywordAndStorageIdStockList(int input, int storageNum)
      throws SQLException, InterruptedException {
    return dao.keywordAndStorageIdStockList(userVO, input, storageNum);
  }

  @Override
  public void editStockCount(int productId, int storageId, int quantity) {
    dao.editStockCount(userVO, productId, storageId, quantity);
  }

  @Override
  public void deleteStockCount(int productId, int storageNum) {
    dao.deleteStockCount(userVO, productId, storageNum);
  }

  @Override
  public void uploadStockCount(String userId, int storageId, int productId, int quantity) {
    dao.uploadStockCount(userId, storageId, productId, quantity);
  }


  @Override
  public List<StockVO> displayStorageStatus() throws SQLException, InterruptedException {
    return dao.displayStorageStatus();
  }

  @Override
  public List<StockVO> displayClientStorageUsage() throws SQLException, InterruptedException {
    return dao.displayClientStorageUsage();
  }

  private void printList(List<StockVO> list) {
    System.out.println(
        "----------------------------------------------------------------------------------------------------------------------------------------------------");
    System.out.println(
        "  고객번호      |     창고번호      |     창고이름      |     제품번호    |    제품명     |     대분류     |     중분류     |     소분류     |     재고량 ");
    System.out.println(
        "----------------------------------------------------------------------------------------------------------------------------------------------------");
    for (StockVO vo : list) {
      System.out.println(
          vo.getUserId() + "    " + vo.getStorageId() + "    " + vo.getStorageName() + "    "
              + vo.getProductId() + "   " + vo.getProductId() + "    " + vo.getProductName()
              + "    " + vo.getMajorCategoryName() + "    " + vo.getMiddleCategoryName() + "    "
              + vo.getSmallCategoryName() + "    " + vo.getQuantity());
    }
    System.out.println(
        "--------------------------------------------------------------------------------------------------------");
  }

  public PrintFunctionName stockMenu() throws IOException, SQLException, InterruptedException {

    boolean flag = true;
    while (flag) {
      System.out.println("---------------------------------------------------");
      System.out.println("1.재고조회 | 2.메인메뉴");
      System.out.print("메뉴 선택: ");
      int selectedNum = Integer.parseInt(br.readLine());
      switch (selectedNum) {
        case 1: //재고조회
          System.out.println(
              "1.전체 조회 | 2.카테고리별 조회 | 3.상품별 조회 | 4.제품번호+창고번호 키워드 조회 | 5.회원별 조회 | 6.돌아가기");
          System.out.print("메뉴 선택: ");
          int num = Integer.parseInt(br.readLine());
          switch (num) {
            case 1 -> printList(wholeStockList());
            case 2 -> selectCategoryMenu();
            case 3 -> {
              System.out.println("검색할 상품명 : ");
              printList(keywordStockList(br.readLine()));
            }
            case 4 -> {
              System.out.println("검색할 상품 번호 : ");
              int input = Integer.parseInt(br.readLine());
              System.out.println("검색할 창고 번호 : ");
              int storageNum = Integer.parseInt(br.readLine());
              printList(keywordAndStorageIdStockList(input, storageNum));
            }
            case 5 -> {
              System.out.println("검색할 회원 아이디 : ");
              printList(userStockList(br.readLine()));
            }
            case 6 -> stockMenu();
          }//case 1(재고조회)

        default:
          flag = false;
      }

    }

    return PrintFunctionName.EXIT;
  } //메인메뉴


  private void selectCategoryMenu() throws IOException, SQLException, InterruptedException {
    printList(wholeStockList());
    System.out.println("---------------------------------------------------");
    System.out.println("1.대분류 | 2.중분류 | 3.소분류 | 4.뒤로 가기");
    System.out.print("메뉴 선택: ");
    int selectedNum = Integer.parseInt(br.readLine());
    switch (selectedNum) {
      case 1 -> printList(majorStockList(br.readLine()));
      case 2 -> printList(middleStockList(br.readLine()));
      case 3 -> printList(smallStockList(br.readLine()));
      case 4 -> stockMenu();
    }

  }


}
