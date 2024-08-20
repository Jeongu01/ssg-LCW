package util;

public enum Role {
  ADMIN("ADMIN"),
  EMPLOYEE("EMPLOYEE"),
  WH_MANAGER("WH_MANAGER"),
  DELIVERY_DRIVER("DELIVERY_DRIVER"),
  SUPPLIER("SUPPLIER"),
  STORE_OPERATOR("STORE_OPERATOR"),
  GUEST("GUEST");

  private String role;

  private Role(String role) {
    this.role = role;
  }

  public String getRole(){
    return role;
  }
}