package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputVO {
    UserVO user;
    ProductVO product;
    WarehouseVO warehouse;
    ExpenditureDetailsVO expDet;
    SalesDetailsVO salDet;
    ContractVO contract;
    Boolean check;

    public InputVO(){
        user = new UserVO(null, null, null, null, null, null, null, null, null, null, null);
        product = new ProductVO(-1,null,null,-1,-1);
        warehouse = new WarehouseVO(-1,null,null,null,-1,-1,null);
        expDet = new ExpenditureDetailsVO(-1,null,-1,-1,-1);
        salDet = new SalesDetailsVO(-1,null,-1,-1,-1);
        contract = new ContractVO(-1,null,-1,-1,null,null);
    }
}
