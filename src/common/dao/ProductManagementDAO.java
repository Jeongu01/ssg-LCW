package common.dao;

import lib.ConnectionPool;
import vo.ProductVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductManagementDAO {

    private ConnectionPool conncp = null;
    private Connection connection = null;
    private ResultSet rs = null;
    public ProductManagementDAO(){
        init();
    }
    private void init(){
        conncp = ConnectionPool.getInstance();
    }
    public ArrayList<ProductVO> selectAllProducts(){
        ArrayList<ProductVO> ret = new ArrayList<>();
        String query = "select * from product";
        this.connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){
                ProductVO vo = new ProductVO(rs.getInt("product_id"),rs.getString("product_brand"),
                        rs.getString("product_name"),rs.getInt("area_per_product"),rs.getInt("small_category_id"));
                ret.add(vo);
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

        return ret;
    }
    public ArrayList<ProductVO> selectProductByName(String name){
        ArrayList<ProductVO> ret = new ArrayList<>();
        String query = "select * from product where product_name = ?";

        this.connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,name);
            rs = pstmt.executeQuery();

            while(rs.next()){
                ProductVO vo = new ProductVO(rs.getInt("product_id"),rs.getString("product_brand"),
                        rs.getString("product_name"),rs.getInt("area_per_product"),rs.getInt("small_category_id"));
                ret.add(vo);
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

        return ret;
    }
    public void insertProduct(ProductVO data){
        String query = "insert into product(product_id,product_brand,product_name,area_per_product,small_category_id)"+
                "values(?,?,?,?,?)";
        this.connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getProductId());
            pstmt.setString(2,data.getProductBrand());
            pstmt.setString(3,data.getProductName());
            pstmt.setInt(4,data.getAreaPerProduct());
            pstmt.setInt(5,data.getCategoryId());

            pstmt.executeUpdate();
            pstmt.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
    public void updateProduct(ProductVO data){
        String query = "update product set "
                + "product_brand = ?, "
                + "product_name = ?, "
                + "area_per_product = ?, "
                + "small_category_id = ? "
                + "where product_id = ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,data.getProductBrand());
            pstmt.setString(2,data.getProductName());
            pstmt.setInt(3,data.getAreaPerProduct());
            pstmt.setInt(4,data.getCategoryId());
            pstmt.setInt(5,data.getProductId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

    }
    public void deleteProduct(ProductVO data){
        String query = "delete from product where product_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getProductId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
}
