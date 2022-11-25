package com.github.maojx0630.auth_token.exception;

/**
 * @author 毛家兴
 * @since 2022/10/19 15:01
 */
public class AuthTokenException extends RuntimeException {

  private AuthTokenException(String message) {
    super(message);
  }

  public static AuthTokenException of(String message) {
    return new AuthTokenException(message);
  }
}
