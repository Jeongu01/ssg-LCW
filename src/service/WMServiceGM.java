package service;

import vo.WarehouseVO;

import java.util.ArrayList;

public interface WMServiceGM extends WMServiceWM{
    public void insertWarehouse(WarehouseVO data);
    public void deleteWarehouse(WarehouseVO data);
    public ArrayList<WarehouseVO> inquiryAllWarehouse();
    public ArrayList<WarehouseVO> warehouseInquiryByLocation(String location);
    public ArrayList<WarehouseVO> warehouseInquiryByName(String name);

}
