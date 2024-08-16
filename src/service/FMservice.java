package service;

import vo.ContractVO;
import vo.UserVO;

import java.util.ArrayList;

public interface FMservice {
    public ArrayList<ContractVO> InquiryContract(UserVO user);
    public void requestContract(ContractVO data);
}
