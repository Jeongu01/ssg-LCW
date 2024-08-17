package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ExpenditureDetailsVO {
    private int expenditureDetailsId;
    private Date expenditureDetailsDate;
    private int cost;
    private int warehouseId;
    private int categoryId;
}
