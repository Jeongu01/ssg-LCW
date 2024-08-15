package service;

import vo.ExpenditureDetailsVO;
import vo.RevenueVO;
import vo.SalesDetailsVO;

import java.util.ArrayList;
import java.sql.Date;

public interface FMserviceGM extends FMserviceWM{
    public ArrayList<ExpenditureDetailsVO> inquiryAllExpDet();
    public ArrayList<SalesDetailsVO> inquiryAllSalDet();
    public RevenueVO inquiryTotalRevenue(Date date);

}
