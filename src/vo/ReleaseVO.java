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
    private String productBrand;
    private Date requestDate;
    private Date approvedDate;
    private Date completeDate;
    private String deliveryAddress;
    private String deliveryAddressDetail;
    private int zipcode;
    private int requestQuantity;
    private String requestComment;
    private String carNumber;
    private String carType;

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

    public ReleaseVO(int requestId, String userId, Date requestDate, Date approvedDate,
        int productId,
        String productName, int requestQuantity, String deliveryAddress,
        String deliveryAddressDetail,
        String carNumber, String carType) {
        this.requestId = requestId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
        this.productId = productId;
        this.productName = productName;
        this.requestQuantity = requestQuantity;
        this.deliveryAddress = deliveryAddress;
        this.deliveryAddressDetail = deliveryAddressDetail;
        this.carNumber = carNumber;
        this.carType = carType;
    }

    public ReleaseVO(int requestId, String userId, Date requestDate, Date approvedDate) {
        this.requestId = requestId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
    }

    public ReleaseVO(int requestId, int productId, String productName, String productBrand, int requestQuantity,
        Date requestDate, Date approvedDate) {
        this.requestId = requestId;
        this.productId = productId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.requestQuantity = requestQuantity;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
    }
}
