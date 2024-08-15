package service;

import vo.ContractVO;
import vo.ExpenditureDetailsVO;
import vo.RevenueVO;
import vo.SalesDetailsVO;

import java.sql.Date;
import java.util.ArrayList;

public interface FMserviceWM {
    public ArrayList<ContractVO> inquiryAllContract();

    public ArrayList<ExpenditureDetailsVO> inquiryExpDet(String userId,Date date);
    public void insertExpDet(ExpenditureDetailsVO data);
    public void updateExpDet(ExpenditureDetailsVO data);
    public void deleteExpDet(ExpenditureDetailsVO data);

    public ArrayList<SalesDetailsVO> inquirySalDet(String userId, Date date);

    public RevenueVO inquiryRevenueByWarehouse(int warehouseId ,Date date);
}
