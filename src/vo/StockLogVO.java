package vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class StockLogVO {

  private int stockHistoryId;
    private String userId;
    private int storageId;
    private int productId;
  private Date stockUpdateTime;
    private int quantity;
  private int variance;
  private String managerId;

}
