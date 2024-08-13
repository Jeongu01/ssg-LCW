package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarehouseVO {
    private int storageId;
    private String storageName;
    private String address;
    private String addressDetail;
    private int zipCode;
    private int storageArea;
    private int managerId;
}
