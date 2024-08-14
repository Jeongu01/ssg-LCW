package common.dao;

import lib.ConnectionPool;
import vo.ProductVO;

import java.sql.Connection;
import java.util.ArrayList;

public class ProductManagementDAO {

    private ConnectionPool conncp = null;
    private Connection connection = null;

    public ProductManagementDAO(){
        init();
    }
    private void init(){
        conncp = ConnectionPool.getInstance();
    }
    public ArrayList<ProductVO> selectAllProducts(){
        return null;
    }
    public ArrayList<ProductVO> selectProduct(String where){
        return null;
    }
    public void insertProduct(ProductVO data){

    }
    public void updateProduct(ProductVO data){

    }
    public void deleteProduct(ProductVO data){

    }
}
