package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueVO {
    private int totalRevenue;
    private Date beginPeriod;
    private Date endPeriod;
}
