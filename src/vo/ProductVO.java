package vo;


import lombok.AllArgsConstructor;
import lombok.Data;
//집에갑시다

@Data
@AllArgsConstructor
public class ProductVO {
    private int productId;
    private String productBrand;
    private String productName;
    private int areaPerProduct;
    private int categoryId;
}
