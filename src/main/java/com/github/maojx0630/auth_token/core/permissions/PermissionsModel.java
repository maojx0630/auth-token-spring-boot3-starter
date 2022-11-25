package com.github.maojx0630.auth_token.core.permissions;

/**
 * @author 毛家兴
 * @since 2022/10/24 11:44
 */
public class PermissionsModel {

  /** 权限标识 */
  private String permissions;

  /** 请求类型 不限制可为空 */
  private String httpMethod;

  /** 权限路径 */
  private String pathPatterns;

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getPathPatterns() {
    return pathPatterns;
  }

  public void setPathPatterns(String pathPatterns) {
    this.pathPatterns = pathPatterns;
  }
}
