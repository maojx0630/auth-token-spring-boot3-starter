package com.github.maojx0630.auth_token.store;

import com.github.maojx0630.auth_token.config.AuthTokenConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 毛家兴
 * @since 2022/10/19 16:42
 */
@Configuration
@ConditionalOnMissingBean(TokenStoreInterface.class)
@ConditionalOnProperty(prefix = "auth-token.core", name = "redis-cache", havingValue = "true")
public class RedisTokenStoreConfiguration {

  @Bean
  public TokenStoreInterface redisTokenStoreInterface(
      AuthTokenConfig config, RedisTemplate<String, String> redisTemplate) {
    return new RedisTokenStoreImpl(config.getRedisHead(), redisTemplate);
  }
}
