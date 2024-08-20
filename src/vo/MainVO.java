package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MainVO {
    UserVO user;
    int warehouseID;

    public MainVO() {
        user = new UserVO(null, null, null, null, null, null, null, null, null, null, null);
        warehouseID = -1;
    }
}
