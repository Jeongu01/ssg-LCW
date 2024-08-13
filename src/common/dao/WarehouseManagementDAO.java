package common.dao;

import lib.ConnectionPool;
import vo.WarehouseVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

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
    public ArrayList<WarehouseVO> selectAllWarehouse(){
        return null;
    }
    public WarehouseVO selectWarehouse(){
        return null;
    }
    public void insertWarehouse(WarehouseVO data){}
    public void deleteWarehouse(WarehouseVO data){}
}
