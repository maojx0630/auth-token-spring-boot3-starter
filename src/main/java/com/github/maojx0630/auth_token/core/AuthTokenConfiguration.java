package com.github.maojx0630.auth_token.core;

import com.github.maojx0630.auth_token.config.AuthTokenConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 毛家兴
 * @since 2022/10/17 16:13
 */
@Configuration
public class AuthTokenConfiguration implements WebMvcConfigurer, InitializingBean {

  private final AuthTokenConfig config;

  private final ApplicationContext applicationContext;

  public AuthTokenConfiguration(AuthTokenConfig config, ApplicationContext applicationContext) {
    this.config = config;
    this.applicationContext = applicationContext;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new AuthTokenInterceptor(config))
        .order(config.getAuthTokenHandlerInterceptorOrder())
        .addPathPatterns("/**");
  }

  @Override
  public void afterPropertiesSet() {
    BasicCoreUtil.initConfig(config, applicationContext);
  }
}
