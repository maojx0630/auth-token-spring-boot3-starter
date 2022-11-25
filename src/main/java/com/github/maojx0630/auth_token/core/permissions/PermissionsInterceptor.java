package com.github.maojx0630.auth_token.core.permissions;

import com.github.maojx0630.auth_token.config.permissions.PermissionsAuthTokenConfig;
import com.github.maojx0630.auth_token.core.BasicCoreUtil;
import com.github.maojx0630.auth_token.util.CollOrElseUtil;
import com.github.maojx0630.auth_token.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author 毛家兴
 * @since 2022/10/18 13:49
 */
public class PermissionsInterceptor implements HandlerInterceptor {

  private final PermissionsInfoInterface permissionsInfo;

  private final PermissionsAuthTokenConfig permissionsConfig;

  public PermissionsInterceptor(
      PermissionsInfoInterface permissionsInfo, PermissionsAuthTokenConfig permissionsConfig) {
    this.permissionsInfo = permissionsInfo;
    this.permissionsConfig = permissionsConfig;
  }

  @Override
  public boolean preHandle(
          HttpServletRequest request, HttpServletResponse response, Object handler) {
    boolean isCheck = true;
    String uri = request.getRequestURI();
    AntPathMatcher matcher = new AntPathMatcher();
    List<String> userPermissionsList = new ArrayList<>();
    BasicCoreUtil.getOptUser()
        .ifPresent(
            user ->
                userPermissionsList.addAll(
                    CollOrElseUtil.get(permissionsInfo.getUserPermissionsInfo(user))));
    for (PermissionsModel model : CollOrElseUtil.get(permissionsInfo.getAllPermissionsList())) {
      // 如果拥有权限命中 就拥有权限
      if (checkHttpMethod(request.getMethod(), model)
          && matcher.match(model.getPathPatterns(), uri)) {
        isCheck = false;
        if (userPermissionsList.contains(model.getPermissions())) {
          return true;
        }
      }
    }
    if (isCheck) {
      return true;
    } else {
      ResponseUtils.writeStr(
          response, permissionsConfig.getHttpCode(), permissionsConfig.getMessage());
      return false;
    }
  }

  private boolean checkHttpMethod(String httpMethod, PermissionsModel model) {
    if (StringUtils.hasText(model.getHttpMethod())) {
      return httpMethod.equalsIgnoreCase(model.getHttpMethod());
    } else {
      return true;
    }
  }
}
