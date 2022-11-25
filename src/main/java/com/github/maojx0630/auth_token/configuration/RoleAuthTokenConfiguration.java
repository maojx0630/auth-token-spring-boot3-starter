package com.github.maojx0630.auth_token.configuration;

import com.github.maojx0630.auth_token.config.role.RoleAuthTokenConfig;
import com.github.maojx0630.auth_token.core.role.RoleInfoInterface;
import com.github.maojx0630.auth_token.core.role.RoleInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 角色拦截器配置器
 *
 * @author 毛家兴
 * @since 2022/10/19 15:19
 */
@Configuration
@ConditionalOnProperty(prefix = "auth-token.role", name = "enable", havingValue = "true")
public class RoleAuthTokenConfiguration implements WebMvcConfigurer {

  private final RoleAuthTokenConfig roleConfig;

  private final RoleInfoInterface roleInfoInterface;

  public RoleAuthTokenConfiguration(
      RoleAuthTokenConfig roleConfig, RoleInfoInterface roleInfoInterface) {
    this.roleConfig = roleConfig;
    this.roleInfoInterface = roleInfoInterface;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new RoleInterceptor(roleConfig, roleInfoInterface))
        .order(roleConfig.getOrder())
        .addPathPatterns("/**");
  }
}
