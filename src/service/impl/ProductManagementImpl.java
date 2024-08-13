package service.impl;

import common.dao.ProductManagementDAO;

public class ProductManagementImpl {
    private ProductManagementDAO dao = null;

    public ProductManagementImpl(){
        dao = new ProductManagementDAO();
    }

}
