package service.impl;

import common.dao.ProductManagementDAO;
import service.PMserviceGM;
import vo.ProductVO;

import java.util.ArrayList;

public class ProductManagementImpl implements PMserviceGM {
    private ProductManagementDAO dao = null;

    public ProductManagementImpl(){
        dao = new ProductManagementDAO();
    }

    @Override
    public void insertProduct(ProductVO data) {
        dao.insertProduct(data);
    }

    @Override
    public void updateProduct(ProductVO data){
        dao.updateProduct(data);
    }

    @Override
    public void deleteProduct(ProductVO data) {
        dao.deleteProduct(data);
    }

    @Override
    public ArrayList<ProductVO> inquiryAllProduct() {
        return dao.selectAllProducts();
    }

    @Override
    public ArrayList<ProductVO> inquiryProductByName(String name) {
        return dao.selectProductByName(name);
    }
}
