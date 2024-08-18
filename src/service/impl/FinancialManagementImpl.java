package service.impl;

import common.dao.FinancialManagementDAO;
import service.FMservice;
import service.FMserviceGM;
import vo.*;

import java.sql.Date;
import java.util.ArrayList;

public class FinancialManagementImpl implements FMserviceGM, FMservice {

    private FinancialManagementDAO dao = null;

    public FinancialManagementImpl(){
        dao = new FinancialManagementDAO();
    }

    @Override
    public ArrayList<ContractVO> InquiryContract(UserVO user) {
        return dao.selectContracts(user);
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
    public RevenueVO inquiryTotalRevenue(UserVO user,Date begin,Date end) {
        int expDetCost = dao.sumExpDet(user,begin,end);
        int salDetCost = dao.sumSalDet(user,begin,end);
        return new RevenueVO(salDetCost - expDetCost,begin,end);
    }

    @Override
    public ArrayList<ContractVO> inquiryAllContract() {
        return dao.selectAllContracts();
    }

    @Override
    public ArrayList<ExpenditureDetailsVO> inquiryExpDet(UserVO user, Date begin,Date end) {
        return dao.selectExpDet(user,begin,end);
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
    public ArrayList<SalesDetailsVO> inquirySalDet(UserVO user, Date begin,Date end) {
        return dao.selectSalDet(user,begin,end);
    }

    @Override
    public RevenueVO inquiryRevenueByWarehouse(UserVO user, Date begin,Date end) {
        //여기서 관리자 총관리자를 판단해주자.
        int expDetCost = dao.sumExpDet(user,begin,end);
        int salDetCost = dao.sumSalDet(user,begin,end);
        return new RevenueVO(salDetCost - expDetCost,begin,end);
    }
}
