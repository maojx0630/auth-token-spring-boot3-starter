package com.github.maojx0630.auth_token.util;

import java.util.Collections;
import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/10/25 09:29
 */
public final class CollOrElseUtil {

  private CollOrElseUtil() {}

  public static <T> List<T> get(List<T> list) {
    if (null != list) {
      return list;
    } else {
      return Collections.emptyList();
    }
  }
}
