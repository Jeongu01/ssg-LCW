package vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductVO {
    private int productId;
    private String productBrand;
    private String productName;
    private int areaPerProduct;
    private int categoryId;
}
