package com.github.maojx0630.auth_token.core;

import com.github.maojx0630.auth_token.config.AuthTokenConfig;
import com.github.maojx0630.auth_token.exception.AuthTokenException;
import com.github.maojx0630.auth_token.model.AuthTokenRes;
import com.github.maojx0630.auth_token.model.LoginParam;
import com.github.maojx0630.auth_token.store.TokenStoreInterface;
import com.github.maojx0630.auth_token.util.Base62;
import com.github.maojx0630.auth_token.util.UuidUtil;
import com.github.maojx0630.auth_token.util.rsa.RsaUtils;
import org.springframework.context.ApplicationContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author 毛家兴
 * @since 2022/10/18 14:27
 */
public class BasicCoreUtil {

  private static final Charset UTF8 = StandardCharsets.UTF_8;

  /** THREAD_LOCAL */
  private static final ThreadLocal<AuthTokenRes> THREAD_LOCAL = new ThreadLocal<>();

  private static AuthTokenConfig config;
  /** token存储 */
  private static TokenStoreInterface tokenStore;

  /**
   * 通过用户id获取user key(使用默认类型)
   *
   * @param userId 用户id
   * @return java.lang.String
   * @author 毛家兴
   * @since 2022/10/25 15:41
   */
  public static String getUserKey(String userId) {
    return getUserKey(userId, StaticProperty.USER_TYPE);
  }

  /**
   * 通过用户id获取 user key
   *
   * @param userId 用户id
   * @param userType 用户类型
   * @return java.lang.String
   * @author 毛家兴
   * @since 2022/10/25 15:41
   */
  public static String getUserKey(String userId, String userType) {
    return config.getRedisHead() + "_" + userType + "_" + userId;
  }

  /**
   * 获取登录用户 如果未登录会抛出异常
   *
   * @return com.github.maojx0630.auth_token.model.AuthTokenRes
   * @author 毛家兴
   * @since 2022/10/19 15:06
   */
  public static AuthTokenRes getUser() {
    return getOptUser().orElseThrow(() -> AuthTokenException.of("用户未登录"));
  }

  /**
   * 获取登录用户 可能为空
   *
   * @return java.util.Optional
   * @author 毛家兴
   * @since 2022/10/19 15:06
   */
  public static Optional<AuthTokenRes> getOptUser() {
    return Optional.ofNullable(THREAD_LOCAL.get());
  }

  /**
   * 获取全部用户key
   *
   * @return java.util.Collection
   * @author 毛家兴
   * @since 2022/10/25 10:58
   */
  public static Collection<String> getAllUserKey() {
    clearOverdueToken();
    return tokenStore.getAllUserKey();
  }

  /**
   * 获取当前登录用户的全部登录信息
   *
   * @return java.util.Collection
   * @author 毛家兴
   * @since 2022/10/26 09:15
   */
  public static Collection<AuthTokenRes> getUserAllDevice() {
    return getUserAllDevice(getUser().getUserKey());
  }

  /** 获取所有用户的登录信息 */
  private static Collection<AuthTokenRes> getUserAllDevice(String userKey) {
    clearOverdueUser(userKey);
    return tokenStore.getUserAll(userKey);
  }

  /**
   * 用户登录功能
   *
   * @param id 用户id
   * @return com.github.maojx0630.auth_token.model.AuthTokenRes
   * @author 毛家兴
   * @since 2022/10/26 09:15
   */
  public static AuthTokenRes login(String id) {
    return login(id, null);
  }

  /**
   * 用户登录功能
   *
   * @param id 用户id
   * @param loginParam 补充参数
   * @return com.github.maojx0630.auth_token.model.AuthTokenRes
   * @author 毛家兴
   * @since 2022/10/19 14:08
   */
  public static AuthTokenRes login(String id, LoginParam loginParam) {
    // 完善参数信息
    LoginParamReal param = new LoginParamReal(loginParam).completion(config);
    // 构建登录信息
    AuthTokenRes res = new AuthTokenRes();
    res.setId(id);
    res.setTimeout(param.timeout);
    res.setUserType(param.userType);
    res.setLoginTime(param.loginTime);
    res.setLastAccessTime(param.loginTime);
    res.setDeviceType(param.deviceType);
    res.setDeviceName(param.deviceName);
    res.setTokenKey(UuidUtil.uuid());
    res.setUserKey(getUserKey(res.getId(), res.getUserType()));
    res.setToken(generateToken(res.getUserKey(), res.getTokenKey()));
    // 并发登录移除
    if (!config.isConcurrentLogin()) {
      tokenStore.removeUser(res.getUserKey());
    }
    // 同端互斥移除登录
    if (config.isDeviceReject()) {
      Set<String> keySet = new HashSet<>();
      for (AuthTokenRes tokenRes : tokenStore.getUserAll(res.getUserKey())) {
        if (res.getDeviceType().equals(tokenRes.getDeviceType())) {
          keySet.add(tokenRes.getTokenKey());
        }
      }
      if (!keySet.isEmpty()) {
        tokenStore.removeToken(res.getUserKey(), keySet);
      }
    }
    // 设置登录信息 缓存token
    setThreadLocal(res);
    clearOverdueUser(res.getUserKey());
    tokenStore.put(res.getUserKey(), res.getTokenKey(), res);
    return res;
  }

  /**
   * 退出
   *
   * @author 毛家兴
   * @since 2022/10/19 14:08
   */
  public static void logout() {
    AuthTokenRes authTokenRes = THREAD_LOCAL.get();
    kickOutToken(authTokenRes.getUserKey(), authTokenRes.getTokenKey());
  }

  /**
   * 踢出一个用户的所有登录
   *
   * @param userKey 用户key
   * @author 毛家兴
   * @since 2022/10/19 14:09
   */
  public static void kickOutByUserKey(String userKey) {
    tokenStore.removeUser(userKey);
  }

  /**
   * 踢出一个用户的所有登录
   *
   * @param userId 用户id
   * @author 毛家兴
   * @since 2022/10/25 15:43
   */
  public static void kickOutByUserId(String userId) {
    kickOutByUserId(userId, StaticProperty.USER_TYPE);
  }

  /**
   * 踢出一个用户的所有登录
   *
   * @param userId 用户id
   * @param userType 用户类型
   * @author 毛家兴
   * @since 2022/10/25 15:43
   */
  public static void kickOutByUserId(String userId, String userType) {
    tokenStore.removeUser(getUserKey(userId, userType));
  }

  /**
   * 踢出一个用户的指定token
   *
   * @param userKey 用户key
   * @param tokenKey token key
   * @author 毛家兴
   * @since 2022/10/19 14:09
   */
  public static void kickOutToken(String userKey, String tokenKey) {
    tokenStore.removeToken(userKey, tokenKey);
  }

  /**
   * 清理用户所有登录信息
   *
   * @author 毛家兴
   * @since 2022/10/19 14:10
   */
  public static void clearAllUser() {
    tokenStore.clearAllUser();
  }

  /* 非对外提供静态方法 */

  /**
   * 校验token是否正确
   *
   * @param token 前端传入token
   * @author 毛家兴
   * @since 2022/10/19 14:09
   */
  static boolean verifyToken(String token) {
    try {
      String str = new String(Base62.decode(token), UTF8);
      String[] split = str.split("&&");
      String data = split[0];
      String sign = split[1];
      // 校验签名
      if (!RsaUtils.verify(data, sign, config.getSignPublicKey())) {
        return false;
      }
      String[] list = data.split("@");
      AuthTokenRes authTokenRes = tokenStore.get(list[0], list[1]);
      if (authTokenRes == null) {
        return false;
      } else {
        // 判断是否过期
        if (authTokenRes.getTimeout()
            <= System.currentTimeMillis() - authTokenRes.getLastAccessTime()) {
          tokenStore.removeToken(authTokenRes.getUserKey(), authTokenRes.getTokenKey());
          return false;
        }
        if (config.isOverdueReset()) {
          authTokenRes.setLastAccessTime(System.currentTimeMillis());
          tokenStore.put(authTokenRes.getUserKey(), authTokenRes.getTokenKey(), authTokenRes);
        }
        setThreadLocal(authTokenRes);
        return true;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public static void clearOverdueUser(String userKey) {
    Collection<AuthTokenRes> userAll = tokenStore.getUserAll(userKey);
    if (null != userAll && !userAll.isEmpty()) {
      checkOverdue(userKey, userAll);
    } else {
      if (userAll != null) {
        tokenStore.removeUser(userKey);
      }
    }
  }

  /**
   * 清理过期token
   *
   * @author 毛家兴
   * @since 2022/10/24 10:10
   */
  public static void clearOverdueToken() {
    Collection<String> allUserKey = tokenStore.getAllUserKey();
    if (null != allUserKey && !allUserKey.isEmpty()) {
      for (String userKey : allUserKey) {
        Collection<AuthTokenRes> userAll = tokenStore.getUserAll(userKey);
        if (null != userAll && !userAll.isEmpty()) {
          checkOverdue(userKey, userAll);
        }
      }
    }
  }

  private static void checkOverdue(String userKey, Collection<AuthTokenRes> userAll) {
    int size = userAll.size();
    int count = 0;
    for (AuthTokenRes res : userAll) {
      if (res.getTimeout() <= System.currentTimeMillis() - res.getLastAccessTime()) {
        count++;
        tokenStore.removeToken(res.getUserKey(), res.getTokenKey());
      }
    }
    if (size == count) {
      tokenStore.removeUser(userKey);
    }
  }

  static void removeThreadLocal() {
    THREAD_LOCAL.remove();
  }

  private static void setThreadLocal(AuthTokenRes authTokenRes) {
    THREAD_LOCAL.set(authTokenRes);
  }

  /** 生成token */
  private static String generateToken(String userKey, String tokenKey) {
    String data = userKey + "@" + tokenKey + "@" + UuidUtil.uuid();
    try {
      String s = data + "&&" + RsaUtils.sign(data, config.getSignPrivateKey());
      return Base62.encode(s.getBytes(UTF8));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** 初始化配置 */
  static void initConfig(AuthTokenConfig authTokenConfig, ApplicationContext applicationContext) {
    config = authTokenConfig;
    tokenStore = applicationContext.getBean(TokenStoreInterface.class);
  }
}
