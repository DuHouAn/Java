# HTTP概述

## HTTP 协议的特点

- **支持客户端/服务器模式**。

- **简单快速。**

  客户端向服务端请求服务时，只需要**传输请求方法和路径**。请求的方法常用的有 GET、POST。

  HTTP 协议简单，是使得 **HTTP 服务程序规模小**，所以通信速度快。

- **灵活。**

  HTTP 允许传输任意类型的数据对象。

- **无连接。**

  这里的无连接是指**限制每次连接只处理一个请求**。服务器处理客户端的请求，并且收到客户端的应答后，即可断开连接，可节省传输时间。

- **无状态。**

  HTTP 协议是无状态协议。缺少状态意味着如果候选处理需要前面的的信息，则必须重传。另一方面，服务端不需要先前信息时，应答较快。

## URL

URI 包含 URL 和 URN，目前 WEB 只有 URL 比较流行，所以见到的基本都是 URL。

- URI（Uniform Resource Identifier，统一资源标识符）
- URL（Uniform Resource Locator，统一资源定位符）
- URN（Uniform Resource Name，统一资源名称）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/network/urlnuri.jpg" width="600"/> </div><br>

## 请求和响应报文

### 1. 请求报文

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/network/HTTP_RequestMessageExample.png" width=""/> </div>

### 2. 响应报文

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/network/HTTP_ResponseMessageExample.png" width=""/> </div>

### 3. 请求响应步骤

- 客户端连接到 Web 服务器
- 发送 HTTP 请求
- 服务器接受 HTTP 请求并且返回 HTTP 响应
- 释放 TCP 连接
- 客户端浏览器解析 HTML 内容

## 输入 URL 地址，显示主页

- DNS 解析获取相应的 IP 地址。

- TCP 连接。根据 IP 地址、端口号和服务器建立 TCP 连接。

- 发送 HTTP 请求。

  将请求发送给服务器，Cookie 也会随着请求发送给服务器。

- 服务器接收请求并处理，返回 HTTP 报文。

- 客户端收到 HTML 进行解析并渲染页面。

- 断开连接。

## HTTP 1.0 与 HTTP 1.1 的区别

- HTTP/1.1 默认是长连接。关闭长连接 `Connection : close`

  HTTP/1.0 默认是短连接。使用长连接 `Connection:Keep-Alive`

- HTTP/1.1 支持同时打开多个 TCP 连接

- HTTP/1.1 支持虚拟主机。

- HTTP/1.1 新增状态码。比如 409 Conflict 表请求的资源与**资源的当前状态**发生冲突。

- 带宽优化：

  HTTP/1.0 存在一些浪费带宽的现象，如客户端只需要某个对象的一部分，而服务器却将整个对象发送过过来。

  HTTP/1.1 在请求头中引入 range，它允许请求资源的某个部分，返回状态码为 206 Partial Content。