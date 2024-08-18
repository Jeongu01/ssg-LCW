package vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class WaybillVO {

    private int waybillId;
    private int deliveryRequestId;
    private Date departDate;
    private int productId;
    private String productName;
    private int deliveryQuantity;
    private String businessName;
    private String businessTel;
    private String startAddress;
    private String arriveAddress;
    private String arriveAddressDetail;
    private String requestComment;

    public WaybillVO(int waybillId, Date departDate, int productId, String productName, String startAddress, String arriveAddress, String arriveAddressDetail) {
        this.waybillId = waybillId;
        this.departDate = departDate;
        this.productId = productId;
        this.productName = productName;
        this.startAddress = startAddress;
        this.arriveAddress = arriveAddress;
        this.arriveAddressDetail = arriveAddressDetail;
    }
}
