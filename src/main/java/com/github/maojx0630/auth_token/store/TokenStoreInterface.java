package com.github.maojx0630.auth_token.store;

import com.github.maojx0630.auth_token.model.AuthTokenRes;

import java.util.Collection;
import java.util.Collections;

/**
 * token存储接口
 *
 * @author 毛家兴
 * @since 2022/10/19 10:19
 */
public interface TokenStoreInterface {

  /**
   * 存储token信息
   *
   * @param userKey 用户key
   * @param tokenKey token key
   * @param res token信息
   * @author 毛家兴
   * @since 2022/10/19 10:27
   */
  void put(String userKey, String tokenKey, AuthTokenRes res);

  /**
   * 根据key获取token信息
   *
   * @param userKey 用户key
   * @param tokenKey token key
   * @return com.github.maojx0630.auth_token.model.AuthTokenRes
   * @author 毛家兴
   * @since 2022/10/19 10:26
   */
  AuthTokenRes get(String userKey, String tokenKey);

  /**
   * 获取当前缓存的全部用户key
   *
   * @return java.util.Collection
   * @author 毛家兴
   * @since 2022/10/24 10:07
   */
  Collection<String> getAllUserKey();

  /**
   * 获取一个用户所有的登录信息
   *
   * @param userKey 用户key
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/19 10:27
   */
  Collection<AuthTokenRes> getUserAll(String userKey);

  /**
   * 获取一个用户所有的token key
   *
   * @param userKey 用户key
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/19 13:41
   */
  Collection<String> getUserAllTokenKey(String userKey);

  /**
   * 移除一个用户所有的token
   *
   * @param userKey user key
   * @author 毛家兴
   * @since 2022/10/19 13:21
   */
  default void removeUser(String userKey) {
    removeUser(Collections.singletonList(userKey));
  }

  void removeUser(Collection<String> userKey);

  /**
   * 移除一个用户的一个token
   *
   * @param userKey user key
   * @param tokenKey token key
   * @author 毛家兴
   * @since 2022/10/19 13:22
   */
  default void removeToken(String userKey, String tokenKey) {
    removeToken(userKey, Collections.singletonList(tokenKey));
  }

  void removeToken(String userKey, Collection<String> tokenKey);

  /**
   * 清理所有用户
   *
   * @author 毛家兴
   * @since 2022/10/19 13:42
   */
  void clearAllUser();
}
