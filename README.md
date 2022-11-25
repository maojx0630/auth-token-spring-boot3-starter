# auth-token-spring-boot-starter

#### 项目实现了一个简单的登录拦截提供了如下功能

* 登录
* 退出
* 踢出
* 多用户类型
* 多设备登录 单设备登录
* 多端登录 多端互斥
* 设置token过期时间
* 设置token过期策略
* 当前/所有 登录信息查询
* 根据角色权限拦截
* 根据uri权限拦截

### 使用方法如下

```xml
<dependency>
    <groupId>com.github.maojx0630</groupId>
    <artifactId>auth-token-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

```
AuthTokenUtil.login("123");
AuthTokenUtil.logout();
AuthTokenUtil.kickOutUser("123");
```

### 核心配置

```yaml
auth-token:
  core:
    #是否开启redis存储 如果设置为true需引入spring redis data
    #如果设置为false为本地缓存需引入caffeine 重启token会丢失
    redis-cache: true
    # 存储user key时使用的头
    redis-head: auth_token_login_cache_key
    # 前端请求时token参数名
    token-name: authentication
    # 是否从 url param中读取token
    read-param: true
    # 是否从 http header中读取token 优先读取header
    read-header: true
    # 过期时间 单位毫秒
    token-timeout: 86400000
    # 访问后是否重置过期时间
    # 设置为true计算过期时间使用最近一次访问时间
    # 设置为false则使用登录时间
    overdue-reset: true
    # 设备互斥 设置为true则相同deviceType不能同时登录
    device-reject: false
    # 是否允许并发登录 设置为false则一个用户只能存在一个登录设备
    concurrent-login: true
    # 设置用户登录信息填充拦截器order 
    # 仅用于填充登录信息 需在其他登录相关拦截器前面否则没有登录信息
    auth-token-handler-interceptor-order: 0
    # 签名公钥 用于token信息签名校验 可以RsaUtils.createKeyPair() 生成后贴入 建议替换
    sign-public-key:
    # 签名私钥 用于token信息签名生成 可以RsaUtils.createKeyPair() 生成后贴入 建议替换
    sign-private-key:
```

### 登录拦截器

用于对需要用户登录的uri予以拦截

```yaml
auth-token:
  login:
    # 开启登录拦截器 默认开启
    enable: true
    # 拦截器执行顺序 必须在填充信息拦截器之前
    order: 1
    # 若未登录返回的http状态码
    http-code: 401
    # 若未登录返回的body信息
    message: '{"state":401,"message":"用户未登录"}'
    # 需要拦截的路径
    login-path:
      - /**
    # 排除的路径
    login-exclude-path:
      - /login/**
```

### 角色拦截器

如果开启角色拦截器需要实现RoleInfoInterface并注册为spring bean

```
  获取用户拥有的角色
  List<String> getUserRoleInfo(AuthTokenRes res);
  
  获取全部角色信息
  List<RoleModel> getAllRoleList();
```

```yaml
auth-token:
  role:
    # 开启角色拦截器 默认关闭
    enable: false
    # 拦截器执行顺序 必须在填充信息拦截器之前
    order: 2
    # 若无权限返回的http状态码
    http-code: 401
    # 若无权限返回的body信息
    message: '{"state":401,"message":"用户无权限"}'
```

### 权限拦截器

如果开启权限拦截器需要实现PermissionsInfoInterface 并注册为spring bean

```
  获取用户拥有的权限
  List<String> getUserPermissionsInfo(AuthTokenRes res);

  获取全部权限信息
  List<PermissionsModel> getAllPermissionsList();
```

```yaml
auth-token:
  permissions:
    # 开启权限拦截器 默认关闭
    enable: false
    # 拦截器执行顺序 必须在填充信息拦截器之前
    order: 2
    # 若无权限返回的http状态码
    http-code: 401
    # 若无权限返回的body信息
    message: '{"state":401,"message":"用户无权限"}'
```

### token存储

默认提供了 local(caffeine) 和redis两种实现  
如果有需要也可自行实现TokenStoreInterface来使用其他存储方式

### 缓存

如果需要 用户信息 权限信息 角色信息缓存都需要自行实现