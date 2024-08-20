package service;

import vo.*;

import java.sql.Date;
import java.util.ArrayList;

public interface FMserviceWM {
    public ArrayList<ContractVO> inquiryAllContract();

    public ArrayList<ExpenditureDetailsVO> inquiryExpDet(UserVO user, Date begin, Date end);
    public void insertExpDet(ExpenditureDetailsVO data);
    public void updateExpDet(ExpenditureDetailsVO data);
    public void deleteExpDet(ExpenditureDetailsVO data);

    public ArrayList<SalesDetailsVO> inquirySalDet(UserVO user, Date begin, Date end);

    public RevenueVO inquiryRevenueByWarehouse(UserVO user,Date begin,Date end);
}
