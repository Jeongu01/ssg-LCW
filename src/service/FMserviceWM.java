package service;

import vo.ExpenditureDetailsVO;
import vo.SalesDetailsVO;

import java.sql.Date;
import java.util.ArrayList;

public interface FMserviceWM {
    public ArrayList<ExpenditureDetailsVO> inquiryExpDet();
    public ArrayList<ExpenditureDetailsVO> inquiryExpDetByDate(Date date);
    public void insertExpDet(ExpenditureDetailsVO data);
    public void updateExpDet(ExpenditureDetailsVO data);
    public void deleteExpDet(ExpenditureDetailsVO data);

    public ArrayList<SalesDetailsVO> inquirySalDet();
    public ArrayList<SalesDetailsVO> inquirySalDetByDate(Date date);
}
