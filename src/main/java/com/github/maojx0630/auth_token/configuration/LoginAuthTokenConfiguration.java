package com.github.maojx0630.auth_token.configuration;

import com.github.maojx0630.auth_token.config.login.LoginAuthTokenConfig;
import com.github.maojx0630.auth_token.core.login.LoginInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截器配置器 默认注册
 *
 * @author 毛家兴
 * @since 2022/10/19 15:19
 */
@Configuration
@ConditionalOnProperty(
    prefix = "auth-token.login",
    name = "enable",
    havingValue = "true",
    matchIfMissing = true)
public class LoginAuthTokenConfiguration implements WebMvcConfigurer {

  private final LoginAuthTokenConfig loginConfig;

  public LoginAuthTokenConfiguration(LoginAuthTokenConfig loginConfig) {
    this.loginConfig = loginConfig;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LoginInterceptor(loginConfig))
        .order(loginConfig.getOrder())
        .addPathPatterns(loginConfig.getLoginPath())
        .excludePathPatterns(loginConfig.getLoginExcludePath());
  }
}
