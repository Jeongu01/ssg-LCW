package vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ReleaseVO {

    private int requestId;
    private String userId;
    private String userAddress;
    private int productId;
    private String productName;
    private Date requestDate;
    private Date approvedDate;
    private Date completeDate;
    private String deliveryAddress;
    private String deliveryAddressDetail;
    private int zipcode;
    private int requestQuantity;
    private String requestComment;

    public ReleaseVO(int requestId, String userId, Date requestDate, int productId, String productName, int requestQuantity, String deliveryAddress, String deliveryAddressDetail, String userAddress) {
        this.requestId = requestId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.productId = productId;
        this.productName = productName;
        this.requestQuantity = requestQuantity;
        this.deliveryAddress = deliveryAddress;
        this.deliveryAddressDetail = deliveryAddressDetail;
        this.userAddress = userAddress;
    }
}
