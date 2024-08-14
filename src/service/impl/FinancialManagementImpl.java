package service.impl;

import common.dao.FinancialManagementDAO;
import service.FMservice;
import service.FMserviceGM;
import vo.ContractVO;
import vo.ExpenditureDetailsVO;
import vo.RevenueVO;
import vo.SalesDetailsVO;

import java.sql.Date;
import java.util.ArrayList;

public class FinancialManagementImpl implements FMserviceGM, FMservice {

    private FinancialManagementDAO dao = null;

    public FinancialManagementImpl(){
        dao = new FinancialManagementDAO();
    }

    @Override
    public ArrayList<ContractVO> InquiryContract(Date date) {
        return dao.selectContracts("");
    }

    @Override
    public void requestContract(ContractVO data) {
        dao.insertContract(data);
    }

    @Override
    public ArrayList<ExpenditureDetailsVO> inquiryAllExpDet() {
        return dao.selectAllExpDet();
    }

    @Override
    public ArrayList<SalesDetailsVO> inquiryAllSalDet() {
        return dao.selectAllSalDet();
    }

    @Override
    public RevenueVO inquiryTotalRevenue(Date date) {
        ArrayList<ExpenditureDetailsVO> expDetList = inquiryExpDet(null,date);
        ArrayList<SalesDetailsVO> salDetList = inquirySalDet(null,date);
        //do something!!
        return null;
    }

    @Override
    public ArrayList<ExpenditureDetailsVO> inquiryExpDet(String userId, Date date) {
        return dao.selectExpDet("");
    }

    @Override
    public void insertExpDet(ExpenditureDetailsVO data) {
        dao.insertExpDet(data);
    }

    @Override
    public void updateExpDet(ExpenditureDetailsVO data) {
        dao.updateExpDet(data);
    }

    @Override
    public void deleteExpDet(ExpenditureDetailsVO data) {
        dao.deleteExpDet(data);
    }

    @Override
    public ArrayList<SalesDetailsVO> inquirySalDet(String userId, Date date) {
        return dao.selectSalDet("");
    }

    @Override
    public RevenueVO inquiryRevenueByWarehouse(int warehouseId, Date date) {
        ArrayList<ExpenditureDetailsVO> expDetList = inquiryExpDet("",date);
        ArrayList<SalesDetailsVO> salDetList = inquirySalDet("",date);
        //do something!!
        return null;
    }
}
