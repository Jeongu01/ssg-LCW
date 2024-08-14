package vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import util.Role;

@Data
@AllArgsConstructor
public class UserVO {

  private String userId;
  private String password;
  private String name;
  private Date birth;
  private String email;
  private String tel;
  private Role role;
  private int status;
  private String address;
  private int businessNumber;
  private int businessName;
}
