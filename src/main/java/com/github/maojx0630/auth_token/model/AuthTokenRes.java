package com.github.maojx0630.auth_token.model;

/**
 * @author 毛家兴
 * @since 2022/10/18 14:33
 */
public class AuthTokenRes {

  /** id */
  private String id;

  /** 用户存储key */
  private String userKey;

  /** token存储key */
  private String tokenKey;

  /** 用户token */
  private String token;

  /** 过期时间 */
  private Long timeout;

  /** 登录时间 */
  private Long loginTime;

  /** 用户类型 */
  private String userType;

  /** 最后一次访问时间 如果访问不重置则为登录时间 */
  private Long lastAccessTime;

  /** 设备类型 */
  private String deviceType;

  /** 设备名称 */
  private String deviceName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public String getTokenKey() {
    return tokenKey;
  }

  public void setTokenKey(String tokenKey) {
    this.tokenKey = tokenKey;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getTimeout() {
    return timeout;
  }

  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }

  public Long getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Long loginTime) {
    this.loginTime = loginTime;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public Long getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(Long lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }
}
