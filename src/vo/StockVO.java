package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StockVO {

  public StockVO(){}
  public StockVO(String userID, int storageId, int productId, int quantity){
    this.userId = userID;
    this.storageId = storageId;
    this.productId = productId;
    this.quantity = quantity;
  }

  private String userId;
  private int storageId;
  private int productId;
  private int quantity;

  private String storageName;
  private String productName;

  private String smallCategoryName;
  private String middleCategoryName;
  private String majorCategoryName;

  private int storageArea;
  private int storageUsage;
  private int usableStorageArea;
  private String storageUsageRate;



}
