package vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class DispatchVO {

    private int waybillId;
    private String carNumber;
    private int userId;
    private String carType;
    private int maxCarry;


    public DispatchVO(int waybillId, String carNumber, String carType) {
        this.waybillId = waybillId;
        this.carNumber = carNumber;
        this.carType = carType;
    }
}
