package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SalesDetailsVO {
    private int salesDetailsId;
    private Date salesDetailsDate;
    private int cost;
    private int warehouseId;
    private int categoryId;
}
