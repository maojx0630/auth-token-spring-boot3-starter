package com.github.maojx0630.auth_token.core.role;

import com.github.maojx0630.auth_token.config.role.RoleAuthTokenConfig;
import com.github.maojx0630.auth_token.core.BasicCoreUtil;
import com.github.maojx0630.auth_token.util.CollOrElseUtil;
import com.github.maojx0630.auth_token.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色拦截器
 *
 * @author 毛家兴
 * @since 2022/10/18 13:49
 */
public class RoleInterceptor implements HandlerInterceptor {

  private final RoleAuthTokenConfig roleConfig;

  private final RoleInfoInterface roleInfoInterface;

  public RoleInterceptor(RoleAuthTokenConfig roleConfig, RoleInfoInterface roleInfoInterface) {
    this.roleConfig = roleConfig;
    this.roleInfoInterface = roleInfoInterface;
  }

  @Override
  public boolean preHandle(
          HttpServletRequest request, HttpServletResponse response, Object handler) {
    boolean isCheck = true;
    String uri = request.getRequestURI();
    AntPathMatcher matcher = new AntPathMatcher();
    List<String> userRoleInfo = new ArrayList<>();
    BasicCoreUtil.getOptUser()
        .ifPresent(
            user ->
                userRoleInfo.addAll(CollOrElseUtil.get(roleInfoInterface.getUserRoleInfo(user))));
    ag:
    for (RoleModel role : CollOrElseUtil.get(roleInfoInterface.getAllRoleList())) {
      for (String pattern : CollOrElseUtil.get(role.getExcludePathPatterns())) {
        // 如果是被排除路径命中 这个角色就不通过
        if (matcher.match(pattern, uri)) {
          continue ag;
        }
      }
      // 如果拥有权限命中 就拥有权限
      for (String pattern : CollOrElseUtil.get(role.getPathPatterns())) {
        if (matcher.match(pattern, uri)) {
          isCheck = false;
          if (userRoleInfo.contains(role.getRole())) {
            return true;
          }
        }
      }
    }
    if (isCheck) {
      return true;
    } else {
      ResponseUtils.writeStr(response, roleConfig.getHttpCode(), roleConfig.getMessage());
      return false;
    }
  }
}
