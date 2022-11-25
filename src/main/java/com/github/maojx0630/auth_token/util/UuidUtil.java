package com.github.maojx0630.auth_token.util;

import java.util.UUID;

/**
 * @author 毛家兴
 * @since 2022/10/25 14:03
 */
public final class UuidUtil {

  private UuidUtil() {}

  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
