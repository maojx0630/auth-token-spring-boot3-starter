package com.github.maojx0630.auth_token.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maojx0630.auth_token.model.AuthTokenRes;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/10/19 16:28
 */
public class RedisTokenStoreImpl implements TokenStoreInterface {

  protected final String redisHead;

  protected final ObjectMapper json = new ObjectMapper();
  protected final HashOperations<String, String, String> hashRedis;

  public RedisTokenStoreImpl(String redisHead, RedisTemplate<String, String> redisTemplate) {
    this.redisHead = redisHead;
    this.hashRedis = redisTemplate.opsForHash();
  }

  @Override
  public void put(String userKey, String tokenKey, AuthTokenRes res) {
    hashRedis.put(userKey, tokenKey, toJson(res));
  }

  @Override
  public AuthTokenRes get(String userKey, String tokenKey) {
    return toRes(hashRedis.get(userKey, tokenKey));
  }

  @Override
  public Collection<String> getAllUserKey() {
    RedisOperations<String, ?> operations = hashRedis.getOperations();
    return operations.keys(redisHead + "*");
  }

  @Override
  public Collection<AuthTokenRes> getUserAll(String userKey) {
    List<String> values = hashRedis.values(userKey);
    List<AuthTokenRes> list = new ArrayList<>();
    if (!values.isEmpty()) {
      for (String value : values) {
        list.add(toRes(value));
      }
    }
    return list;
  }

  @Override
  public Collection<String> getUserAllTokenKey(String userKey) {
    return hashRedis.keys(userKey);
  }

  @Override
  public void removeUser(Collection<String> userKey) {
    hashRedis.getOperations().delete(userKey);
  }

  @Override
  public void removeToken(String userKey, Collection<String> tokenKey) {
    hashRedis.delete(userKey, tokenKey.toArray());
  }

  @Override
  public void clearAllUser() {
    Collection<String> keys = this.getAllUserKey();
    if (null != keys && !keys.isEmpty()) {
      hashRedis.getOperations().delete(keys);
    }
  }

  private String toJson(AuthTokenRes res) {
    try {
      return json.writeValueAsString(res);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private AuthTokenRes toRes(String str) {
    try {
      return json.readValue(str, AuthTokenRes.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
