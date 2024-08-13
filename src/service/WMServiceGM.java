package service;

import vo.WarehouseVO;

public interface WMServiceGM extends WMServiceWM{
    public void insertWarehouse(WarehouseVO data);
    public void deleteWarehouse(WarehouseVO data);
    public void inquiryAllWarehouse();
}
