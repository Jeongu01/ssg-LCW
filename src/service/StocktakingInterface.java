package service;

import java.sql.SQLException;
import java.util.List;
import vo.StockVO;

public interface StocktakingInterface {

  /****재고 목록****/
  public List<StockVO> wholeStockList() throws SQLException, InterruptedException;//전체 재고 목록 출력
  public List<StockVO> majorStockList(String categoryName) throws SQLException, InterruptedException;//카테고리별 재고 목록 출력
  public List<StockVO> middleStockList(String categoryName)
      throws SQLException, InterruptedException;//카테고리별 재고 목록 출력
  public List<StockVO> smallStockList(String categoryName)
      throws SQLException, InterruptedException;//카테고리별 재고 목록 출력
  public List<StockVO> keywordStockList(String keyword) throws SQLException, InterruptedException;//검색어로 재고 목록 출력
  public List<StockVO> userStockList(int userId);//회원별 재고 목록 출력(관리자만)
public List<StockVO> keywordAndStorageIdStockList(int input, int storageNum)
    throws SQLException, InterruptedException;


  /****창고 사용 현황 조회****/
  public List<StockVO> displayStorageStatus() throws SQLException, InterruptedException;

  /****고객 창고 사용 현황 조회*****/
  public List<StockVO> displayClientStorageUsage() throws SQLException, InterruptedException;

  /****재고 실사****/
  public void editStockCount(int productId);//재고 실사 수정 -> 실사 테이블 수정 + 재고 테이블에 반영시키기(반영하고 commit 안 하기)
  public void deleteStockCount(int productId, int storageNum);//재고 실사 삭제 -> 실사 테이블에서 삭제 + 재고 테이블에 반영시키기(입력 전으로 되돌리기 - rollback)

  //재고 실사 등록(새제품만 등록)
  public void uploadStockCount(String userID, int storageId, int productId, int quantity);

  /****재고 로그****/
  //재고 로그 생성=> 입출고 승인시 프로시저 호출

  /****입출고 승인시 재고 수량 변경****/
  // 입출고 승인시 프로시저 호출
}
