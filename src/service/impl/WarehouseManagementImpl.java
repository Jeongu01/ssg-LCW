package service.impl;

import common.dao.WarehouseManagementDAO;
import service.WMServiceGM;
import vo.WarehouseVO;

public class WarehouseManagementImpl implements WMServiceGM {
    private WarehouseManagementDAO dao = null;

    public WarehouseManagementImpl(){
        dao = new WarehouseManagementDAO();
    }

    @Override
    public void insertWarehouse(WarehouseVO data) {

    }

    @Override
    public void deleteWarehouse(WarehouseVO data) {

    }

    @Override
    public void inquiryAllWarehouse() {

    }

    @Override
    public void inquiryWarehouse(WarehouseVO data) {

    }
}
