package service;

import vo.UserVO;
import vo.WarehouseVO;

import java.util.ArrayList;

public interface WMServiceWM {
    public ArrayList<WarehouseVO> warehouseInquiryById(UserVO user);
}
