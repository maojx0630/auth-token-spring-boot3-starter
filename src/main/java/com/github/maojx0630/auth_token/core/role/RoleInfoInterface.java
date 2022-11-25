package com.github.maojx0630.auth_token.core.role;

import com.github.maojx0630.auth_token.model.AuthTokenRes;

import java.util.List;

/**
 * 角色信息获取 需自己实现缓存
 *
 * @author 毛家兴
 * @since 2022/10/24 11:42
 */
public interface RoleInfoInterface {

  /**
   * 获取用户拥有的角色
   *
   * @param res 用户信息
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/24 11:49
   */
  List<String> getUserRoleInfo(AuthTokenRes res);

  /**
   * 获取全部角色信息
   *
   * @return java.util.List
   * @author 毛家兴
   * @since 2022/10/24 11:49
   */
  List<RoleModel> getAllRoleList();
}
