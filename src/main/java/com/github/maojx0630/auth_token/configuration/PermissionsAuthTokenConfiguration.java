package com.github.maojx0630.auth_token.configuration;

import com.github.maojx0630.auth_token.config.permissions.PermissionsAuthTokenConfig;
import com.github.maojx0630.auth_token.core.permissions.PermissionsInfoInterface;
import com.github.maojx0630.auth_token.core.permissions.PermissionsInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限拦截器配置器
 *
 * @author 毛家兴
 * @since 2022/10/19 15:19
 */
@Configuration
@ConditionalOnProperty(prefix = "auth-token.permissions", name = "enable", havingValue = "true")
public class PermissionsAuthTokenConfiguration implements WebMvcConfigurer {

  private final PermissionsInfoInterface permissionsInfo;
  private final PermissionsAuthTokenConfig permissionsConfig;

  public PermissionsAuthTokenConfiguration(
      PermissionsInfoInterface permissionsInfo, PermissionsAuthTokenConfig permissionsConfig) {
    this.permissionsInfo = permissionsInfo;
    this.permissionsConfig = permissionsConfig;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new PermissionsInterceptor(permissionsInfo, permissionsConfig))
        .order(permissionsConfig.getOrder())
        .addPathPatterns("/**");
  }
}
