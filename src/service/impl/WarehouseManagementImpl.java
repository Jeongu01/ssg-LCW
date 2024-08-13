package service.impl;

import common.dao.WarehouseManagementDAO;
import service.WMServiceGM;
import vo.WarehouseVO;

import java.util.ArrayList;

public class WarehouseManagementImpl implements WMServiceGM {
    private WarehouseManagementDAO dao = null;

    public WarehouseManagementImpl(){
        dao = new WarehouseManagementDAO();
    }

    @Override
    public void insertWarehouse(WarehouseVO data) {
        dao.insertWarehouse(data);
    }

    @Override
    public void deleteWarehouse(WarehouseVO data) {
        dao.deleteWarehouse(data);
    }

    @Override
    public ArrayList<WarehouseVO> inquiryAllWarehouse() {
        return null;
    }

    @Override
    public WarehouseVO inquiryWarehouse(WarehouseVO data) {
        return null;
    }
}
