package com.github.maojx0630.auth_token.core.role;

import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/10/24 11:44
 */
public class RoleModel {

  /** 角色标识 */
  private String role;

  /** 角色拥有权限 * */
  private List<String> pathPatterns;

  /** 角色权限路径排除 * */
  private List<String> excludePathPatterns;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<String> getPathPatterns() {
    return pathPatterns;
  }

  public void setPathPatterns(List<String> pathPatterns) {
    this.pathPatterns = pathPatterns;
  }

  public List<String> getExcludePathPatterns() {
    return excludePathPatterns;
  }

  public void setExcludePathPatterns(List<String> excludePathPatterns) {
    this.excludePathPatterns = excludePathPatterns;
  }
}
