package vo;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockingRequestVO {



  private int stockingRequestId;
  private String userId;
  private int productId;
  private int storageId;
  private Date requestId;
  private Date approvedDate;
  private Date completeDate;
  private int requestQuantity;
  private String requestComment;



}