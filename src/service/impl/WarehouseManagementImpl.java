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
    public void insertWarehouse(WarehouseVO data) { //창고 등록
        dao.insertWarehouse(data);
    }

    @Override
    public void deleteWarehouse(WarehouseVO data) { //창고 삭제
        dao.deleteWarehouse(data);
    }

    @Override
    public ArrayList<WarehouseVO> inquiryAllWarehouse() {  // 모든 창고 조회
        return dao.selectAllWarehouse();
    }

    @Override
    public ArrayList<WarehouseVO> warehouseInquiryById() { // 자기의 창고 조회->싱글턴 유저가 있겟다.
        return dao.selectWarehouse("");
    }

    @Override
    public ArrayList<WarehouseVO> warehouseInquiryByLocation(String location){
        return dao.selectWarehouse("");
    }

    @Override
    public ArrayList<WarehouseVO> warehouseInquiryByName(String name){
        return dao.selectWarehouse("");
    }

}
