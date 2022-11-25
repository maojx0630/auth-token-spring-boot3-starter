package com.github.maojx0630.auth_token.model;

/**
 * @author 毛家兴
 * @since 2022/11/11 15:44
 */
public class LoginParam {

  private Long timeout;

  private Long loginTime;

  private String userType;

  private String deviceType;

  private String deviceName;

  public Long getTimeout() {
    return timeout;
  }

  public Long getLoginTime() {
    return loginTime;
  }

  public String getUserType() {
    return userType;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public LoginParam setTimeout(Long timeout) {
    this.timeout = timeout;
    return this;
  }

  public LoginParam setLoginTime(Long loginTime) {
    this.loginTime = loginTime;
    return this;
  }

  public LoginParam setUserType(String userType) {
    this.userType = userType;
    return this;
  }

  public LoginParam setDeviceType(String deviceType) {
    this.deviceType = deviceType;
    return this;
  }

  public LoginParam setDeviceName(String deviceName) {
    this.deviceName = deviceName;
    return this;
  }
}
