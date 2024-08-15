package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockVO {

  StockVO(){}

  private String userId;
  private int storageId;
  private int productId;
  private int quantity;


}
