package vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockingRequestVO {
  private int stockingRequestId;
  private String userId;
  private int productId;
  private int storageId;
  private Date requestDate;
  private Date completeDate;
  private int requestQuantity;
  private String requestComment;

}
