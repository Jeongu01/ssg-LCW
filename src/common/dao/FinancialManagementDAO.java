package common.dao;

import lib.ConnectionPool;
import vo.ContractVO;
import vo.ExpenditureDetailsVO;
import vo.SalesDetailsVO;

import java.sql.Connection;
import java.util.ArrayList;

public class FinancialManagementDAO {
    private ConnectionPool conncp = null;
    private Connection connection = null;

    public FinancialManagementDAO(){
        init();
    }

    private void init(){
        conncp = ConnectionPool.getInstance();
    }

    public ArrayList<ContractVO> selectContracts(String where){
        return null;
    }
    public void insertContract(ContractVO data){

    }
    public void updateContract(ContractVO data){

    }
    public void deleteContract(ContractVO data){

    }
    public ArrayList<ExpenditureDetailsVO> selectAllExpDet(){
        return null;
    }
    public ArrayList<SalesDetailsVO> selectAllSalDet(){
        return null;
    }
    public ArrayList<ExpenditureDetailsVO> selectExpDet(String where){
        return null;
    }
    public ArrayList<SalesDetailsVO> selectSalDet(String where){
        return null;
    }
    public void insertExpDet(ExpenditureDetailsVO data){}
    public void updateExpDet(ExpenditureDetailsVO data){}
    public void deleteExpDet(ExpenditureDetailsVO data){}
}
