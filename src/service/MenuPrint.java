package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import service.impl.StocktakingImpl;
import vo.StockVO;

public class MenuPrint {

  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  StocktakingImpl service = new StocktakingImpl();
  List<StockVO> list = null;
  StockVO stockVO = null;

  public void mainMenu() throws Exception{
    System.out.println();
    System.out.println(
        "----------------------------------------------------------------------------------------------------");
    System.out.println("메인 메뉴: 1.회원관리 | 2.재무관리 | 3.창고관리 | 4.재고관리 | 5.입고관리 | 6.출고관리 | 7.로그인");
    System.out.print("메뉴 선택:");

    Scanner sc = new Scanner(System.in);
    int selectedMenu = Integer.parseInt(br.readLine());
    br.readLine();

    switch (selectedMenu) {
      case 4 -> stockMainMenu();
      default -> {
        System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
        mainMenu();
      }
    }
  }//end of mainMenu

  public void stockMainMenu() throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("1.재고조회 | 2.재고실사 | 3.메인메뉴");
    System.out.print("메뉴 선택: ");
    int selectedNum = Integer.parseInt(br.readLine());
    switch (selectedNum) {
      case 1 : list = service.wholeStockList();

      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
      System.out.println("  고객번호      |     창고번호      |     창고이름      |     제품번호    |    제품명     |     대분류     |     중분류     |     소분류     |     재고량 ");
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
      for(StockVO vo : list){
        System.out.println( vo.getUserId()+"    "+vo.getStorageId()+"    "+vo.getStorageName()+"    "+vo.getProductId()+"   "+vo.getProductId()+"    "+vo.getProductName()+"    "+vo.getMajorCategoryName()+"    "+vo.getMiddleCategoryName()+"    "+vo.getSmallCategoryName()+"    "+vo.getQuantity() );
      }
      System.out.println("--------------------------------------------------------------------------------------------------------");
      System.out.println("메인 메뉴: 1.전체조회 | 2.카테고리별조회 | 3.검색어조회 | 4.창고별조회 | 5.입고관리 | 6.출고관리 | 7.로그인");
      System.out.print("메뉴 선택:");
    }



    //return selectedNum;
  }//end of subMenu

  public void stockSubMenu() throws Exception {
    System.out.println("---------------------------------------------------");
    System.out.println("1.재고조회 | 2.재고실사 | 3.메인메뉴");
    System.out.print("메뉴 선택: ");
    String selectedNum = br.readLine();

  }//end of subMenu

  public void selectCategoryMenu() throws Exception {
    System.out.println("---------------------------------------------------");
    System.out.println("1.재고조회 | 2.재고실사 | 3.메인메뉴");
    System.out.print("메뉴 선택: ");
    String selectedNum = br.readLine();

  }//end of subMenu


}
