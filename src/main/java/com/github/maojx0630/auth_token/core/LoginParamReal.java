package com.github.maojx0630.auth_token.core;

import com.github.maojx0630.auth_token.config.AuthTokenConfig;
import com.github.maojx0630.auth_token.model.LoginParam;
import org.springframework.util.StringUtils;

/**
 * @author 毛家兴
 * @since 2022/10/18 14:37
 */
public class LoginParamReal {

  Long timeout;

  Long loginTime;

  String userType;

  String deviceType;

  String deviceName;

  LoginParamReal(LoginParam loginParam) {
    if (loginParam != null) {
      this.timeout = loginParam.getTimeout();
      this.loginTime = loginParam.getLoginTime();
      this.userType = loginParam.getUserType();
      this.deviceType = loginParam.getDeviceType();
      this.deviceName = loginParam.getDeviceName();
    }
  }

  LoginParamReal completion(AuthTokenConfig config) {
    if (null == timeout) {
      timeout = config.getTokenTimeout();
    }
    if (null == loginTime) {
      loginTime = System.currentTimeMillis();
    }
    if (!StringUtils.hasText(userType)) {
      userType = StaticProperty.USER_TYPE;
    }
    if (!StringUtils.hasText(deviceType)) {
      deviceType = StaticProperty.DEVICE_TYPE;
    }
    if (!StringUtils.hasText(deviceName)) {
      deviceName = StaticProperty.DEVICE_NAME;
    }
    return this;
  }
}
