package vo;


import lombok.AllArgsConstructor;
import lombok.Data;
//집에갑시다2

@Data
@AllArgsConstructor
public class ProductVO {
    private int productId;
    private String productBrand;
    private String productName;
    private int areaPerProduct;
    private int categoryId;
}
