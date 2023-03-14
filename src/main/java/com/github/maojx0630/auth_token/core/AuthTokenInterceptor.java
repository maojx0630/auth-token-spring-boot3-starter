package com.github.maojx0630.auth_token.core;

import com.github.maojx0630.auth_token.config.AuthTokenConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用于检查token填充登录用户
 *
 * @author 毛家兴
 * @since 2022/10/18 13:48
 */
public class AuthTokenInterceptor implements HandlerInterceptor {

  private final AuthTokenConfig authTokenConfig;

  public AuthTokenInterceptor(AuthTokenConfig authTokenConfig) {
    this.authTokenConfig = authTokenConfig;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (authTokenConfig.isReadHeader()) {
      String token = request.getHeader(authTokenConfig.getTokenName());
      if (StringUtils.hasText(token)) {
        if (BasicCoreUtil.verifyToken(token)) {
          return true;
        }
      }
    }
    if (authTokenConfig.isReadParam()) {
      String token = request.getParameter(authTokenConfig.getTokenName());
      if (StringUtils.hasText(token)) {
        if (BasicCoreUtil.verifyToken(token)) {
          return true;
        }
      }
    }
    if (authTokenConfig.isReadSession()) {
      Object attribute = request.getSession().getAttribute(authTokenConfig.getTokenName());
      if (attribute instanceof String token) {
        if (StringUtils.hasText(token)) {
          if (BasicCoreUtil.verifyToken(token)) {
            return true;
          }
        }
      }
    }
    if (authTokenConfig.isReadCookie()) {
      Cookie[] cookies = request.getCookies();
      if (null != cookies && cookies.length > 0) {
        for (Cookie cookie : cookies) {
          if (authTokenConfig.getTokenName().equals(cookie.getName())) {
            String token = cookie.getValue();
            if (StringUtils.hasText(token)) {
              if (BasicCoreUtil.verifyToken(token)) {
                return true;
              }
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    BasicCoreUtil.removeThreadLocal();
  }
}
