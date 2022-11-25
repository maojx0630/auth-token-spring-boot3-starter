package com.github.maojx0630.auth_token.config.permissions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限拦截器配置项
 *
 * @author 毛家兴
 * @since 2022/10/19 15:15
 */
@Configuration
@ConfigurationProperties(prefix = "auth-token.permissions")
public class PermissionsAuthTokenConfig {

  /** 是否启用权限拦截器 启用需实现PermissionsInfoInterface接口 */
  private boolean enable = false;

  /** 权限拦截器执行顺序 */
  private int order = 2;

  /** 权限拦截http状态码 */
  private int httpCode = 401;

  /** 权限拦截提示语 */
  private String message = "{\"state\":401,\"message\":\"用户无权限\"}";

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
}
