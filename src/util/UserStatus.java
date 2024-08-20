package util;

public enum UserStatus {
  ACTIVE("ACTIVE"),
  VIP("VIP"),
  WAITING("WAITING"),
  BANNED("BANNED"),
  DORMANT("DORMANT"),
  DENIED("DENIED");

  private String userStatus;

  private UserStatus(String userStatus) {
    this.userStatus = userStatus;
  }

  public String getUserStatus(){
    return userStatus;
  }
}
