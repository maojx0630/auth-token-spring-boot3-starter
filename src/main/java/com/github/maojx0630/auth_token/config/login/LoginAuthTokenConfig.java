package com.github.maojx0630.auth_token.config.login;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * 登录拦截器配置项
 *
 * @author 毛家兴
 * @since 2022/10/19 15:19
 */
@Configuration
@ConfigurationProperties(prefix = "auth-token.login")
public class LoginAuthTokenConfig {

  /** 是否启用登录拦截器 */
  private boolean enable = true;

  /** 登录拦截器执行顺序 */
  private int order = 1;

  /** 登录拦截状态码 */
  private int httpCode = 401;

  /** 登录拦截提示语 */
  private String message = "{\"state\":401,\"message\":\"用户未登录\"}";

  /** 登录拦截路径 */
  private List<String> loginPath = Collections.singletonList("/**");

  /** 排除登录拦截路径 */
  private List<String> loginExcludePath = Collections.singletonList("/login/**");

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getHttpCode() {
    return httpCode;
  }

  public void setHttpCode(int httpCode) {
    this.httpCode = httpCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getLoginPath() {
    return loginPath;
  }

  public void setLoginPath(List<String> loginPath) {
    this.loginPath = loginPath;
  }

  public List<String> getLoginExcludePath() {
    return loginExcludePath;
  }

  public void setLoginExcludePath(List<String> loginExcludePath) {
    this.loginExcludePath = loginExcludePath;
  }
}
