package common.dao;

import lib.ConnectionPool;
import vo.WarehouseVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//겹치는 코드 개많음 리팩토링 해야댐...
public class WarehouseManagementDAO {
    private ConnectionPool conncp = null;
    private Connection connection = null;
    private ResultSet rs = null;

    public WarehouseManagementDAO(){
        init();
    }
    private void init(){
        conncp = ConnectionPool.getInstance();
    }
    public ArrayList<WarehouseVO> selectAllWarehouse() {
        ArrayList<WarehouseVO> ret = new ArrayList<>();
        String query = "select * from storage";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WarehouseVO vo = new WarehouseVO(rs.getInt("storage_id"),rs.getString("storage_name"),
                        rs.getString("address"),rs.getString("address_detail"),rs.getInt("zipcode"),
                        rs.getInt("storage_area"),rs.getString("manager_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
        }

        return ret;
    }
    public ArrayList<WarehouseVO> selectWarehouseById(String userId){
        ArrayList<WarehouseVO> ret = new ArrayList<>();
        String query = "select * from storage where manager_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,userId);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WarehouseVO vo = new WarehouseVO(rs.getInt("storage_id"),rs.getString("storage_name"),
                        rs.getString("address"),rs.getString("address_detail"),rs.getInt("zipcode"),
                        rs.getInt("storage_area"),rs.getString("manager_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
        }

        return ret;
    }
    public ArrayList<WarehouseVO> selectWarehouseByLocation(String address){
        ArrayList<WarehouseVO> ret = new ArrayList<>();
        String query = "select * from storage where address = %?%";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,address);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WarehouseVO vo = new WarehouseVO(rs.getInt("storage_id"),rs.getString("storage_name"),
                        rs.getString("address"),rs.getString("address_detail"),rs.getInt("zipcode"),
                        rs.getInt("storage_area"),rs.getString("manager_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
        }

        return ret;
    }
    public ArrayList<WarehouseVO> selectWarehouseByName(String name){
        ArrayList<WarehouseVO> ret = new ArrayList<>();
        String query = "select * from storage where storage_name = %?%";
        this.connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,name);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WarehouseVO vo = new WarehouseVO(rs.getInt("storage_id"),rs.getString("storage_name"),
                        rs.getString("address"),rs.getString("address_detail"),rs.getInt("zipcode"),
                        rs.getInt("storage_area"),rs.getString("manager_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
        }

        return ret;
    }
    public void insertWarehouse(WarehouseVO data){
        String query = "insert into storage(storage_id,storage_name,address,address_detail,zipcode,storage_area,manager_id)"+
                "values(?,?,?,?,?,?,?)";
        this.connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getStorageId());
            pstmt.setString(2,data.getStorageName());
            pstmt.setString(3,data.getAddress());
            pstmt.setString(4, data.getAddressDetail());
            pstmt.setInt(5,data.getZipCode());
            pstmt.setInt(6,data.getStorageArea());
            pstmt.setString(7, data.getManagerId());

            pstmt.executeUpdate();
            pstmt.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
        }
    }
    public void deleteWarehouse(WarehouseVO data){
        String query = "delete from storage where storage_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getStorageId());
            pstmt.executeUpdate();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
