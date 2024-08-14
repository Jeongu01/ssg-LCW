package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ContractVO {
    private int contractId;
    private int userId;
    private int contractArea;
    private int contractCost;
    private Date contractDate;
    private Date expirationDate;
}
