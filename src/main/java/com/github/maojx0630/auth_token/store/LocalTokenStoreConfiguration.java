package com.github.maojx0630.auth_token.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 毛家兴
 * @since 2022/10/19 16:42
 */
@Configuration
@ConditionalOnMissingBean(TokenStoreInterface.class)
@ConditionalOnProperty(
    prefix = "auth-token.core",
    name = "redis-cache",
    havingValue = "false",
    matchIfMissing = true)
public class LocalTokenStoreConfiguration {

  @Bean
  public TokenStoreInterface localTokenStoreInterface() {
    return new LocalTokenStoreImpl();
  }
}
