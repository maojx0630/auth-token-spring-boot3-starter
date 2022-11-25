package com.github.maojx0630.auth_token.core.login;

import com.github.maojx0630.auth_token.config.login.LoginAuthTokenConfig;
import com.github.maojx0630.auth_token.core.BasicCoreUtil;
import com.github.maojx0630.auth_token.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 登录拦截器
 *
 * @author 毛家兴
 * @since 2022/10/18 14:08
 */
public class LoginInterceptor implements HandlerInterceptor {

  private final LoginAuthTokenConfig loginConfig;

  public LoginInterceptor(LoginAuthTokenConfig loginConfig) {
    this.loginConfig = loginConfig;
  }

  @Override
  public boolean preHandle(
          HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (BasicCoreUtil.getOptUser().isPresent()) {
      return true;
    } else {
      ResponseUtils.writeStr(response, loginConfig.getHttpCode(), loginConfig.getMessage());
      return false;
    }
  }
}
