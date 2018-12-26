<!-- GFM-TOC -->
* [一、Servlet工作原理解析](#Servlet工作原理解析)
   * [Servlet简介](#Servlet简介)
   * [Servlet生命周期](#Servlet生命周期)
   * [参考资料](#参考资料) 
   * [附录](#附录)
       * [CGI](#CGI)
<!-- GFM-TOC -->

# Servlet工作原理解析
## Servlet简介
Servlet是运行在服务端的Java小程序，是SUN公司提供**一套规范**，
用来**处理客户端请求、响应给浏览器的动态资源**。

Servlet的任务有：
- 1.获取请求数据
- 2.处理请求
- 3.完成响应


## Servlet生命周期
	生命周期:就是一个对象从创建到销毁的过程.
	Servlet生命周期:Servlet对象从创建到销毁的过程.
	何时创建:默认用户第一次访问Servlet创建Servlet的实例
	何时销毁:当项目从服务器中移除的时候，或者关闭服务器的时候.
	Servlet规范规定，所有的servlet必须实现 javax.servlet.Servlet接口。
	1.第一次调用时，将执行初始化方法：init(ServletConfig)
	2.每一次调用，都将执行service(ServletRequest，ServletResponse)方法
	3.服务器关闭，或项目移除：destroy()方法


### Servlet和CGI的区别
- [CGI (了解)](#CGI)

### CGI的不足之处
(1)需要为**每个请求启动一个操作CGI程序的系统进程**。如果请求频繁，这将会带来很大的开销。

(2)需要为每个请求加载和运行一个CGI程序，这将带来很大的开销

(3)需要**重复编写处理网络协议的代码以及编码**，这些工作都是非常耗时的。

### Servlet的优点
(1)只需要启动一个操作系统进程以及加载一个JVM，大大降低了系统的开销

(2)如果多个请求需要做同样处理的时候，这时候只需要加载一个类，这也大大降低了开销

(3)所有动态加载的类可以实现对网络协议以及请求解码的共享，大大降低了工作量。

(4)Servlet能**直接和Web服务器交互**，而普通的CGI程序不能。
Servlet还能在各个程序之间共享数据，使数据库连接池之类的功能很容易实现。



## 小结
- Servlet主要负责接收用户请求HttpServletRequest,在doGet(),doPost()中做相应的处理，并将回应HttpServletResponse反馈给用户。
- Servlet可以设置初始化参数，供Servlet内部使用。
- 一个Servlet类只会有一个实例，在它初始化时调用init()方法，销毁时调用destroy()方法。
- Servlet需要在web.xml中配置。
- 一个Servlet可以设置多个URL访问。
- Servlet**不是线程安全的**，因此要谨慎使用类变量。

## 参考资料
- 《深入分析Java Web 技术内幕(修订版)》
- [走进JavaWeb技术世界2：JSP与Servlet的曾经与现在](https://blog.csdn.net/a724888/article/details/77098958)
- [Servlet 工作原理详解](https://blog.csdn.net/a724888/article/details/78065232)

## 附录
### CGI
通用网关接口（Common Gateway Interface,CGI）是一个Web服务器主机提供信息服务的**标准接口**。
通过CGI接口，Web服务器就能够获取客户端提交的信息，转交给服务器端的CGI程序进行处理，最后返回结果给客户端。

- 组成CGI通信系统的是两部分：

一部分是html页面，就是在用户端浏览器上显示的页面。

一部分则是运行在服务器上的CGI程序。

它们之间的通讯方式如下图：

<div align="center"><img src="pics//00_1.png"/></div>

- CGI程序

CGI程序就是真正的被服务器（如Apache）调用的来处理用户发送过来的数据的程序。
所谓CGI程序就是按照CGI接口规范编写的能够处理用户通过浏览器发送到服务器的数据的一个程序。