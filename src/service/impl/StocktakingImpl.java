package service.impl;

import java.sql.SQLException;
import java.util.List;
import common.dao.StocktakingDAO;
import service.StocktakingInterface;
import vo.StockVO;
import vo.UserVO;

public class StocktakingImpl implements StocktakingInterface {

  private StocktakingDAO dao = null;
  private UserVO userVO= null;

  public StocktakingImpl(){
    dao = new StocktakingDAO(userVO);
  }


  @Override
  public List<StockVO> wholeStockList() throws SQLException, InterruptedException {
    return dao.wholeStockList();
  }

  @Override
  public List<StockVO> majorStockList(String categoryName) throws SQLException, InterruptedException {
    return dao.majorStockList(categoryName);
  }

  @Override
  public List<StockVO> middleStockList(String categoryName) throws SQLException, InterruptedException {
    return dao.middleStockList(categoryName);
  }

  @Override
  public List<StockVO> smallStockList(String categoryName) throws SQLException, InterruptedException {
    return dao.smallStockList(categoryName);
  }

  @Override
  public List<StockVO> keywordStockList(String keyword) throws SQLException, InterruptedException {
    return dao.keywordStockList(keyword);
  }

  @Override
  public List<StockVO> userStockList(int userId) {
    return List.of();
  }

  @Override
  public List<StockVO> keywordAndStorageIdStockList(int input, int storageNum) throws SQLException, InterruptedException {
    return dao.keywordAndStorageIdStockList(input, storageNum);
  }

  @Override
  public void editStockCount(int productId) {
    dao.editStockCount(productId);
  }

  @Override
  public void deleteStockCount(int productId, int storageNum) {
    dao.deleteStockCount(productId, storageNum);
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


}
