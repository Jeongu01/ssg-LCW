package service;

import vo.ExpenditureDetailsVO;
import vo.RevenueVO;
import vo.SalesDetailsVO;
import vo.UserVO;

import java.util.ArrayList;
import java.sql.Date;

public interface FMserviceGM extends FMserviceWM{
    public ArrayList<ExpenditureDetailsVO> inquiryAllExpDet();
    public ArrayList<SalesDetailsVO> inquiryAllSalDet();
    public RevenueVO inquiryTotalRevenue(UserVO user,Date begin, Date end);

}
