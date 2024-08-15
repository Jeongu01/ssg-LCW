package service;

import java.util.List;
import vo.StockCountVO;
import vo.StockVO;

public interface StocktakingInterface {

  /****재고 목록****/
  public List<StockVO> wholeStockList();//전체 재고 목록 출력
  public List<StockVO> majorStockList(int categoryId);//카테고리별 재고 목록 출력
  public List<StockVO> middleStockList(int categoryId);//카테고리별 재고 목록 출력
  public List<StockVO> smallStockList(int categoryId);//카테고리별 재고 목록 출력
  public List<StockVO> keywordStockList(String keyword);//검색어로 재고 목록 출력
  public List<StockVO> userStockList(int userId);//회원별 재고 목록 출력(관리자만)

  /****재고 실사****/
  //재고 실사 테이블 만들어야됨
  //전체 재고 실사 목록 출력
  //카테고리별(대,중,소 카테고리 오버라이딩) 실사 목록 출력
  //검색어로 재고 실사 목록 출력
  //회원별 재고 실사 목록 출력(관리자만)

  public StockCountVO editStockCount(int productId);//재고 실사 수정 -> 실사 테이블 수정 + 재고 테이블에 반영시키기(반영하고 commit 안 하기)
  public void deleteStockCount(int productId);//재고 실사 삭제 -> 실사 테이블에서 삭제 + 재고 테이블에 반영시키기(입력 전으로 되돌리기 - rollback)

  //재고 실사 등록(새제품만 등록)
  public void uploadStockCount(int productId, String productName, int quantity);

  /****재고 로그****/
  //재고 로그 생성
  public void createStockLog();

  /****입출고 승인시 재고 수량 변경****/
  public void minusStock(boolean result);
  public void plusStock(boolean result);

  /****창고 현황 조회****/
  public List<StockVO> warehouseStatus();

}
