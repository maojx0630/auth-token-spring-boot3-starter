package com.github.maojx0630.auth_token.store;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.maojx0630.auth_token.model.AuthTokenRes;

import java.util.Collection;
import java.util.Collections;

/**
 * @author 毛家兴
 * @since 2022/10/19 15:38
 */
public class LocalTokenStoreImpl implements TokenStoreInterface {

  private static final Cache<String, Cache<String, AuthTokenRes>> CACHE_CACHE =
      Caffeine.newBuilder().build();

  @Override
  public void put(String userKey, String tokenKey, AuthTokenRes res) {
    CACHE_CACHE.get(userKey, (k) -> Caffeine.newBuilder().build()).put(tokenKey, res);
  }

  @Override
  public AuthTokenRes get(String userKey, String tokenKey) {
    Cache<String, AuthTokenRes> cache = CACHE_CACHE.getIfPresent(userKey);
    if (cache != null) {
      return cache.getIfPresent(tokenKey);
    }
    return null;
  }

  @Override
  public Collection<String> getAllUserKey() {
    return CACHE_CACHE.asMap().keySet();
  }

  @Override
  public Collection<AuthTokenRes> getUserAll(String userKey) {
    Cache<String, AuthTokenRes> cache = CACHE_CACHE.getIfPresent(userKey);
    if (cache != null) {
      return cache.asMap().values();
    }
    return Collections.emptyList();
  }

  @Override
  public Collection<String> getUserAllTokenKey(String userKey) {
    Cache<String, AuthTokenRes> cache = CACHE_CACHE.getIfPresent(userKey);
    if (cache != null) {
      return cache.asMap().keySet();
    }
    return Collections.emptyList();
  }

  @Override
  public void removeUser(Collection<String> userKey) {
    CACHE_CACHE.invalidateAll(userKey);
  }

  @Override
  public void removeToken(String userKey, Collection<String> tokenKey) {
    Cache<String, AuthTokenRes> cache = CACHE_CACHE.getIfPresent(userKey);
    if (cache != null) {
      cache.invalidateAll(tokenKey);
    }
  }

  @Override
  public void clearAllUser() {
    CACHE_CACHE.invalidateAll();
  }
}
