package com.github.maojx0630.auth_token.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 核心配置类
 *
 * @author 毛家兴
 * @since 2022/10/17 16:21
 */
@Configuration
@ConfigurationProperties(prefix = "auth-token.core")
public class AuthTokenConfig {

  /**
   * 设置为true会使用redis存储token信息 (设置为true 使用spring data redis操作redis)
   * (设置为false需引入com.github.ben-manes.caffeine)
   */
  private boolean redisCache = false;

  /** redis存储时的key头 */
  private String redisHead = "auth_token_login_cache_key";

  /** token名称 */
  private String tokenName = "authentication";

  /** 是否尝试从param里读取token */
  private boolean readParam = true;

  /** 是否尝试从header里读取token */
  private boolean readHeader = true;


  /** 是否尝试从session里读取token */
  private boolean readSession = true;

  /** 是否尝试从cookie里读取token */
  private boolean readCookie = true;

  /** 默认过期时间 */
  private long tokenTimeout = 1000 * 60 * 60 * 24;
  /** 是否访问后重置过期时间 */
  private boolean overdueReset = true;

  /** 是否同端互斥 */
  private boolean deviceReject = false;

  /** 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录) */
  private boolean concurrentLogin = true;

  /** token拦截器执行顺序 */
  private int authTokenHandlerInterceptorOrder = 0;

  /** 用户token信息签名校验使用 可以通过 RsaUtils.createKeyPair 重新生成 */
  private String signPublicKey =
      "wE4nw0gBJoihIZ49NEQABUAADEYjAATgJKQgBCAgwvdByHHuvPhpJFLFhDZtHxI9BByAJooemSnIFWhjxsrFIrzNfZn1lpDXcCzZEF5W5srSnanfwxuh5Svt0R7r35Hssi4Uba1X9DKKSJKXrd7f4cgMDDerk80vexCD8pHjTmYpLmeEM0A2PadBWpZD8xKglBlTLWisfyhexfNG6Y6CMQAAEA";

  /** 用户token信息签名校验使用 可以通过 RsaUtils.createKeyPair 重新生成 */
  private String signPrivateKey =
      "wIoA0JQAAATDGkgKGikh33QABEQBAQggC4FMCKgWCEAACEYgAAI8bXg8xh77TYaSxSR4QW7RMSfgAZgEUUvp0JShV4YM7aBy6czP7suMd4iTYOjoI3KndV6U78DH7GmL92SHtveOfBLrIO1mW9VfBFFpElr1u9POHIzwgfKJP9rPWYgP9YcyELdx0TwQDYfRrLwKNbgPWBsMocaxSk9LH6jfbM0x0FYgAAIABCEQZ8BFFIaGf1gYJz7ENB4qDeQpX3c0m0czwuJIvoeVfP9M3rTDxkvNXrk8rxKWK7uQnVnl1zIXGiuzHMJ7HgFWmnyVpYho9sis6oUoz1QS9sLQmnpetBTBmjmgaH8XIXEslwoGTWe7kJphhliKWmeDAl5QZj4z08CzKETUFgYhkfJQBCCgbjCWEZTNmxWeZjPfZncVelIuW0z5fbg1sLioTVA1D5KmXQmAG0jMbBr4L9AQ4ZObVQTzTKUjmBFFGjIcrdcrNKBCCAafQ8fNgBHwGsfpSD5iBe0f6nvhe8TpPeuzmhrAyG6qTS00gjkJJ4cXLDb2jq8OThp40Guqdo4VLJqQeiggGYdzQAgeW9TQgb6d1emw9WmUwaF3toNebaJ5NlCPYPOMVyyjygVCpp32J2MxyOlN4PKsKRznYzt3WRE0AQce56RR1EH5oFAocGxgLk4EeGpXT3MAc7UBNueO1uuxTaongPrUE3LWzefZV6whSVdsefNjsyh5rd6NzYt5HtddD4DtwJosBZZrmQF4nRYHU5T3pwcH59iepx3gSz3q3zoqjHbegNsP3fiWdbU7Ak1lKZKbgNrpdglm3eOWTERsa5BHQF7D3Eqi1YBkGE";

  public boolean isRedisCache() {
    return redisCache;
  }

  public void setRedisCache(boolean redisCache) {
    this.redisCache = redisCache;
  }

  public String getRedisHead() {
    return redisHead;
  }

  public void setRedisHead(String redisHead) {
    this.redisHead = redisHead;
  }

  public String getTokenName() {
    return tokenName;
  }

  public void setTokenName(String tokenName) {
    this.tokenName = tokenName;
  }

  public boolean isReadParam() {
    return readParam;
  }

  public void setReadParam(boolean readParam) {
    this.readParam = readParam;
  }

  public boolean isReadHeader() {
    return readHeader;
  }

  public void setReadHeader(boolean readHeader) {
    this.readHeader = readHeader;
  }

  public boolean isReadSession() {
    return readSession;
  }

  public void setReadSession(boolean readSession) {
    this.readSession = readSession;
  }

  public boolean isReadCookie() {
    return readCookie;
  }

  public void setReadCookie(boolean readCookie) {
    this.readCookie = readCookie;
  }

  public long getTokenTimeout() {
    return tokenTimeout;
  }

  public void setTokenTimeout(long tokenTimeout) {
    this.tokenTimeout = tokenTimeout;
  }

  public boolean isOverdueReset() {
    return overdueReset;
  }

  public void setOverdueReset(boolean overdueReset) {
    this.overdueReset = overdueReset;
  }

  public boolean isDeviceReject() {
    return deviceReject;
  }

  public void setDeviceReject(boolean deviceReject) {
    this.deviceReject = deviceReject;
  }

  public boolean isConcurrentLogin() {
    return concurrentLogin;
  }

  public void setConcurrentLogin(boolean concurrentLogin) {
    this.concurrentLogin = concurrentLogin;
  }

  public int getAuthTokenHandlerInterceptorOrder() {
    return authTokenHandlerInterceptorOrder;
  }

  public void setAuthTokenHandlerInterceptorOrder(int authTokenHandlerInterceptorOrder) {
    this.authTokenHandlerInterceptorOrder = authTokenHandlerInterceptorOrder;
  }

  public String getSignPublicKey() {
    return signPublicKey;
  }

  public void setSignPublicKey(String signPublicKey) {
    this.signPublicKey = signPublicKey;
  }

  public String getSignPrivateKey() {
    return signPrivateKey;
  }

  public void setSignPrivateKey(String signPrivateKey) {
    this.signPrivateKey = signPrivateKey;
  }
}
