package service;

import vo.ContractVO;

import java.sql.Date;
import java.util.ArrayList;

public interface FMservice {
    public ArrayList<ContractVO> InquiryContract(Date date);
    public void requestContract(ContractVO data);
}
