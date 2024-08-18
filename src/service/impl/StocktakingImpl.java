package service.impl;

import java.util.List;
import common.dao.StocktakingDAO;
import service.StocktakingInterface;
import vo.StockCountVO;
import vo.StockVO;
import vo.UserVO;

public class StocktakingImpl implements StocktakingInterface {

  private StocktakingDAO dao = null;
  private UserVO userVO= null;

  public StocktakingImpl(){
    dao = new StocktakingDAO(userVO);
  }

  /*관리자급 재고 출력*/
  //전체 재고 목록 출력
  //카테고리별 재고 목록 출력
  //검색어로 재고 목록 출력
  //회원별 재고 목록 출력


  @Override
  public List<StockVO> wholeStockList() {
    return List.of();
  }

  @Override
  public List<StockVO> majorStockList(int categoryId) {
    return List.of();
  }

  @Override
  public List<StockVO> middleStockList(int categoryId) {
    return List.of();
  }

  @Override
  public List<StockVO> smallStockList(int categoryId) {
    return List.of();
  }

  @Override
  public List<StockVO> keywordStockList(String keyword) {
    return List.of();
  }

  @Override
  public List<StockVO> userStockList(int userId) {
    return List.of();
  }

  @Override
  public void editStockCount(int productId) {
  }

  @Override
  public void deleteStockCount(int productId) {

  }

  @Override
  public void uploadStockCount(String userId, int storageId, int productId, int quantity) {

  }

  @Override
  public void minusStock(boolean result) {

  }

  @Override
  public void plusStock(boolean result) {

  }

  @Override
  public void displayStorageStatus() {

  }

  @Override
  public void displayClientStorageUsage() {

  }

  @Override
  public void createStockLog() {


  }


}
