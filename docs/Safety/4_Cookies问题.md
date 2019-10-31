# Cookies 问题

## Cookies 特点

- 前端数据存储
- 后端可通过 HTTP 头设置
- 请求时通过 HTTP 头传给后端
- 前端可读写
- 遵守同源（同协议、同域名、同端口）策略

## Cookies 属性

- 域名（Domain）
- 有效期（Expires）
- 路径（Path）
- http-only
- secure

## Cookies 作用

- 存储个性化设置
- 存储未登录时用户唯一标识
- 存储已登录用户的凭证
- 存储其他业务数据

## 登录用户凭证

### 1. 过程

- 前端提交用户名和密码
- 后端验证用户名和密码
- 后端通过 HTTP 头设置用户凭证
- 后续访问时后端先验证用户凭证

### 2. 用户凭证

- 用户 ID（容易被查看）
- 用户 ID + 签名 （签名不易被猜到，比较安全）
- sessionId （Cookie 中只维护 sessionId）

## Cookies 和 XSS 的关系

XSS 可能会偷取 Cookies。防御：将 Cookie 设置为 http-only。

## Cookies 和 CSRF 的关系

CSRF 利用了用户 Cookies，但攻击站点无法读写 Cookies。

防御：禁止第三方网站带 Cookies，但是这样适配性比较差，目前只有 Chrome 支持。

## Cookies 安全策略

- 签名防篡改
- 私有变换（加密）
- http-only
- same-site

