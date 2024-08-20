package service;

import vo.ProductVO;

import java.util.ArrayList;

public interface PMserviceGM {
    public void insertProduct(ProductVO data);
    public void updateProduct(ProductVO data);
    public void deleteProduct(ProductVO data);
    public ArrayList<ProductVO> inquiryAllProduct();
    public ArrayList<ProductVO> inquiryProductByName(String name);
}
