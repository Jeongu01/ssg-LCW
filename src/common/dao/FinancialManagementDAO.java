package common.dao;

import lib.ConnectionPool;
import vo.ContractVO;
import vo.ExpenditureDetailsVO;
import vo.SalesDetailsVO;
import vo.UserVO;

import java.sql.*;
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
                + "expiration_date = ? "
                + "where contract_id = ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getContractArea());
            pstmt.setInt(2,data.getContractCost());
            pstmt.setDate(3,data.getExpirationDate());
            pstmt.setInt(4,data.getContractId());
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
        ArrayList<ExpenditureDetailsVO> ret = new ArrayList<>();
        String query = "select * from loss_history";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ExpenditureDetailsVO vo = new ExpenditureDetailsVO(rs.getInt("loss_history_id"),rs.getDate("loss_date"),
                        rs.getInt("loss_cost"),rs.getInt("storage_id"),rs.getInt("loss_category_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }
    public ArrayList<SalesDetailsVO> selectAllSalDet(){
        ArrayList<SalesDetailsVO> ret = new ArrayList<>();
        String query = "select * from profit_history";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                SalesDetailsVO vo = new SalesDetailsVO(rs.getInt("profit_history_id"),rs.getDate("profit_date"),
                        rs.getInt("profit_cost"),rs.getInt("storage_id"),rs.getInt("profit_category_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }
    public ArrayList<ExpenditureDetailsVO> selectExpDet(UserVO user, Date begin, Date end){
        //유저의 권한 별로 총관리자면 기간내 모두 출력, 창고관리자면 관할창고만 출력
        ArrayList<ExpenditureDetailsVO> ret = new ArrayList<>();
        String query = "select * from loss_history "+
                "where loss_date between ? and ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1,begin);
            pstmt.setDate(2,end);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ExpenditureDetailsVO vo = new ExpenditureDetailsVO(rs.getInt("loss_history_id"),rs.getDate("loss_date"),
                        rs.getInt("loss_cost"),rs.getInt("storage_id"),rs.getInt("loss_category_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

        return ret;
    }
    public int sumExpDet(UserVO user,Date begin,Date end){
        //유저의 권한 별로 총관리자면 기간내 모두 출력, 창고관리자면 관할창고만 출력
        String query = "select sum(loss_cost) as sum from loss_history";
        int ret=-1;
        //회원권한에 따라 where문을 붙여주자.
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            ret = rs.getInt("sum");//이렇게 불러와야 하나??
            rs.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }
    public ArrayList<SalesDetailsVO> selectSalDet(UserVO user, Date begin, Date end){
        //유저의 권한 별로 총관리자면 기간내 모두 출력, 창고관리자면 관할창고만 출력
        ArrayList<SalesDetailsVO> ret = new ArrayList<>();
        String query = "select * from profit_history "+
                "where profit_date between ? and ?";
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1,begin);
            pstmt.setDate(2,end);
            rs = pstmt.executeQuery();
            while(rs.next()){
                SalesDetailsVO vo = new SalesDetailsVO(rs.getInt("profit_history_id"),rs.getDate("profit_date"),
                        rs.getInt("profit_cost"),rs.getInt("storage_id"),rs.getInt("profit_category_id"));
                ret.add(vo);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }

        return ret;
    }
    public int sumSalDet(UserVO user, Date begin, Date end){
        //유저의 권한 별로 총관리자면 기간내 모두 출력, 창고관리자면 관할창고만 출력
        String query = "select sum(profit_cost) as sum from profit_history";
        int ret=-1;
        //회원권한에 따라 where문을 붙여주자.
        connection = conncp.getConnection(100);
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            ret = rs.getInt("sum");//이렇게 불러와야 하나??
            rs.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
        return ret;
    }
    public void insertExpDet(ExpenditureDetailsVO data){
        String query = "insert into loss_history(loss_history_id,loss_date,loss_cost,storage_id,loss_category_id) "+
                "values(?,?,?,?,?)";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getExpenditureDetailsId());
            pstmt.setDate(2,data.getExpenditureDetailsDate());
            pstmt.setInt(3,data.getCost());
            pstmt.setInt(4, data.getWarehouseId());
            pstmt.setInt(5,data.getCategoryId());

            pstmt.executeUpdate();
            pstmt.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
    public void updateExpDet(ExpenditureDetailsVO data){
        String query = "update loss_history set loss_date = ?,loss_cost = ?,storage_id = ?,loss_category_id = ? "+
                "where loss_history_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getExpenditureDetailsId());
            pstmt.setDate(2,data.getExpenditureDetailsDate());
            pstmt.setInt(3,data.getCost());
            pstmt.setInt(4, data.getWarehouseId());
            pstmt.setInt(5,data.getCategoryId());

            pstmt.executeUpdate();
            pstmt.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
    public void deleteExpDet(ExpenditureDetailsVO data){
        String query = "delete from loss_history where loss_history_id = ?";
        connection = conncp.getConnection(100);

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,data.getExpenditureDetailsId());
            pstmt.executeUpdate();
            pstmt.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            conncp.releaseConnection(this.connection);
            connection = null;
        }
    }
}
