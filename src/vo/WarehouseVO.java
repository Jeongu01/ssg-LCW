package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseVO {
    private int storageId;
    private String storageName;
    private String address;
    private String addressDetail;
    private int zipCode;
    private int storageArea;
    private String managerId;
}
