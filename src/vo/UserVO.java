package vo;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import util.Role;
import util.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
  private String userId;
  private String password;
  private String name;
  private Date birth;
  private String email;
  private String tel;
  private Role role;
  private UserStatus status;
  private String address;
  private String businessName;
  private String businessNumber;
}
