package com.github.maojx0630.auth_token.core.permissions;

import com.github.maojx0630.auth_token.model.AuthTokenRes;

import java.util.List;

/**
 * @author 毛家兴
 * @since 2022/10/24 13:38
 */
public interface PermissionsInfoInterface {

  /**
   * 获取用户拥有的权限
   *
   * @param res 用户信息
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/24 11:49
   */
  List<String> getUserPermissionsInfo(AuthTokenRes res);

  /**
   * 获取全部权限信息
   *
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/24 11:49
   */
  List<PermissionsModel> getAllPermissionsList();
}
