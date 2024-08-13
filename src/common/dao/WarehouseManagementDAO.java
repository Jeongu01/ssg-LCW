package common.dao;

import lib.ConnectionPool;

import java.sql.Connection;

public class WarehouseManagementDAO {
    private ConnectionPool conncp = null;
    private Connection connection = null;

    public WarehouseManagementDAO(){
        init();
    }
    private void init(){
        conncp = ConnectionPool.getInstance();
    }
}
