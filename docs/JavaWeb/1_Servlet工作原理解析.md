# Servlet简介

Servlet是运行在服务端的Java小程序，是SUN公司提供**一套规范**，
用来**处理客户端请求、响应给浏览器的动态资源**。

Servlet的任务有：
- 1.获取请求数据
- 2.处理请求
- 3.完成响应


在Java Web 程序中， Servlet 主要负责接收用户请求 HttpServletRequest ,
在 doGet() , doPost() 中做相应的处理，并将 HttpServletResponse 反馈给用户。

Servlet 可以设置初始化参数，供 Servlet 内部使用。
**一个 Servlet 类只会有一个实例**，在它初始化时调用 init() 方法，销毁时调用 destroy() 方法。
Servlet需要在web.xml中配置(MyEclipse中创建Servlet会自动配置) ，
一个Servlet可以设置多个URL访问。

Servlet **不是线程安全的**，因此要谨慎使用类变量。

## Servlet中的方法
Servlet 接口定义了 5 个方法：
```java
void init(ServletConfig config) throws ServletException
void service(ServletRequest req, ServletResponse resp) throws ServletException, java.io.IOException
void destory()
java.lang.String getServletInfo()
ServletConfig getServletConfig()
```

其中init()、service() 和 destroy()方法和 Servlet 生命周期有关。所谓Servlet生命周期，就是 **Servlet 对象从创建到销毁的过程**。

Servlet规范规定，所有的servlet必须实现 javax.servlet.Servlet接口。
Web容器加载Servlet并将其实例化后，Servlet生命周期开始：

- 1. 容器运行其init()方法进行Servlet的初始化

- 2. 请求到达时调用Servlet的service()方法，service()方法会
根据需要调用与请求对应的 doGet 或 doPost 等方法。
  
- 3. 当服务器关闭或项目被卸载时服务器会将Servlet实例销毁，此时会调用Servlet的destroy()方法。
     

注意：

- **init 方法和 destory 方法只会执行一次，而 service 方法当客户端每次请求Servlet都会执行**。

- Servlet中有时会用到一些需要初始化与销毁的资源，
因此可以**把初始化资源的代码放入init方法中，销毁资源的代码放入destroy方法中**，
这样就不需要每次处理客户端的请求都要初始化与销毁资源。

## Servlet与线程安全

**Servlet不是线程安全的，多线程并发的读写会导致数据不同步的问题**。
解决的办法是尽量不要定义name属性，而是要把name变量分别定义在doGet()和doPost()方法内。
虽然使用 synchronized 语句块可以解决问题，但是会造成线程的等待，不是很科学的办法。 

- 注意：

多线程的并发的读写Servlet类属性会导致数据不同步。
但是如果只是并发地读取属性而不写入，则不存在数据不同步的问题。
因此Servlet里的只读属性最好定义为final类型的。

## Get和Post请求的区别

|  | Get请求 | Post请求 |
| :--: | :--: | :--: |
| 用途  | 用来从服务器上获得资源  |  用来向服务器提交数据  |
| 传输数据格式 | 将表单中数据按照 name=value 的形式，添加到 action 所指向的URL 后面，并且两者使用"?"连接，而各个变量之间使用"&"连接  | 将表单中的数据放在HTTP协议的请求头或消息体中，传递到action所指向URL |
| 传输数据限制 | 受到URL长度限制(1024字节即256个字符) | 可以传输大量的数据，上传文件通常要使用post方式 |
| 参数 | 参数会显示在地址栏上，如果这些数据不是敏感数据，那么可以使用get | 对于敏感数据还是应用使用post |
| 典型应用 | GET方式提交表单的典型应用是**搜索引擎**，GET方式就是被设计为查询用的 | 上传文件 |


## 转发和重定向的区别
### 转发(Forward)

转发(Forword)通过 RequestDispatcher 对象的
forward（HttpServletRequest request,HttpServletResponse response）方法实现的。
RequestDispatcher可以通过HttpServletRequest 的getRequestDispatcher()方法获得。

### 重定向(Redirect)

重定向(Redirect)是利用服务器返回的状态码来实现的。
客户端浏览器请求服务器的时候，服务器会返回一个状态码。
服务器通过HttpServletRequestResponse的setStatus(int status)方法设置状态码。
如果服务器返回301或者302，则浏览器会到**新的网址重新请求该资源**。

| | 转发(Forward) | 重定向(Redirect) |
| :--:| :--: | :--: |
| 地址栏显示 | 服务器请求资源,服务器直接访问目标地址的URL,把那个URL的响应内容读取过来,然后把这些内容再发给浏览器。浏览器根本不知道服务器发送的内容从哪里来的,所以它的地址栏还是原来的地址 | 服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求那个地址。所以地址栏显示的是新的URL |
| 数据共享 | 转发页面和转发到的页面可以共享request里面的数据 | 不能共享数据 |
| 应用 | 一般用于用户登陆的时候,根据角色转发到相应的模块 | 一般用于用户注销登陆时返回主页面和跳转到其它的网站等 |
| 效率 | 高 | 低 |

## [Servlet 工作原理详解](https://blog.csdn.net/a724888/article/details/78065232)
