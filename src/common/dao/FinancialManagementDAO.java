package common.dao;

import lib.ConnectionPool;
import vo.ContractVO;
import vo.ExpenditureDetailsVO;
import vo.SalesDetailsVO;
import vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinancialManagementDAO {
    private ConnectionPool conncp = null;
    private Connection connection = null;
    private ResultSet rs = null;

    public FinancialManagementDAO(){
        init();
    }

    private void init(){
        conncp = ConnectionPool.getInstance();
    }

    public ArrayList<ContractVO> selectAllContracts(){
        ArrayList<ContractVO> ret = new ArrayList<>();
        String query = "select * from contract";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ContractVO vo = new ContractVO(rs.getInt("contract_id"),rs.getString("user_id"),
                        rs.getInt("contract_area"),rs.getInt("contract_cost"),rs.getDate("contract_date"),
                        rs.getDate("expiration_date"));
                ret.add(vo);
            }
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }

    public ArrayList<ContractVO> selectContracts(UserVO user){
        ArrayList<ContractVO> ret = new ArrayList<>();
        String query = "select * from contract where user_id = ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,user.getUserId());
            rs = pstmt.executeQuery();
            while(rs.next()){
                ContractVO vo = new ContractVO(rs.getInt("contract_id"),rs.getString("user_id"),
                        rs.getInt("contract_area"),rs.getInt("contract_cost"),rs.getDate("contract_date"),
                        rs.getDate("expiration_date"));
                ret.add(vo);
            }
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }
    public void insertContract(ContractVO data){
        String query = "insert into contract(contract_id,user_id,contract_area,contract_cost,contract_date,expiration_date)"+
                "values(?,?,?,?,?,?)";
        this.connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getContractId());
            pstmt.setString(2,data.getUserId());
            pstmt.setInt(3,data.getContractArea());
            pstmt.setInt(4,data.getContractCost());
            pstmt.setDate(5,data.getContractDate());
            pstmt.setDate(6,data.getExpirationDate());

            pstmt.executeUpdate();
            pstmt.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
    public void updateContract(ContractVO data){
        String query = "update contract set "
                + "contract_area = ?, "
                + "contract_cost = ?, "
                + "expiration_date = ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getContractArea());
            pstmt.setInt(2,data.getContractCost());
            pstmt.setDate(3,data.getExpirationDate());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

    }
    public void deleteContract(ContractVO data){
        String query = "delete from contract where contract_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getContractId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
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
