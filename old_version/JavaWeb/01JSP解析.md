<!-- GFM-TOC -->
* [二、JSP解析](#JSP解析)
   * [JSP概述](#JSP概述) 
   * [JSP工作原理](#JSP工作原理)
   * [JSP九大内置对象、七大动作、三大指令](#JSP九大内置对象-七大动作-三大指令)
<!-- GFM-TOC -->

# JSP解析
## JSP概述

Servlet 是一个特殊的Java程序，它运行于服务器的JVM中，能够依靠服务器的支持向浏览器提供显示内容。

JSP本质上是**Servlet 的一种简易形式**，JSP会被服务器处理成一个类似于Servlet的Java程序，可以简化页面内容的生成。
Servlet 和 JSP 最主要的不同点在于:
- Servlet的应用逻辑是在Java文件中，并且完全从表示层中的HTML分离开来。
- JSP的情况是Java和HTML可以组合成一个扩展名为.jsp的文件。

JSP侧重于视图，Servlet更侧重于控制逻辑，
在MVC架构模式中，JSP适合充当视图(view)而Servlet适合充当控制器(controller)。

## JSP工作原理

JSP 是一种 Servlet ，但是与 HttpServlet 的工作方式不太一样。

HttpServlet是先由源代码编译为class文件后部署到服务器下，为**先编译后部署**。
而 JSP 则是先部署后编译。
JSP会在客户端第一次请求JSP文件时被编译为HttpJspPage类（实现 Servlet 接口）。
该类会被服务器**临时存放在服务器工作目录**里面。

下面通过实例给大家介绍。 
工程JspLoginDemo下有一个名为login.jsp的Jsp文件，
把工程第一次部署到服务器上后访问这个jsp文件，
我们发现会发现这个目录下多了2个文件:

- login_jsp.java 文件

- login_jsp.class 文件。该文件是 JSP 对应的 Servlet 。编译完毕后再运行 class 文件来响应客户端请求。

以后客户端访问 login.jsp 的时候，Tomcat 将不再重新编译JSP文件，而是直接调用class文件来响应客户端请求。 

由于Jsp只在客户端第一次请求的时候被编译 ，
因此第一次请求JSP时会感觉比较慢，之后就会感觉快很多。
如果把服务器保存的class文件删除，服务器也会重新编译JSP。

开发Web程序时经常需要修改JSP,Tomcat能够自动检测到JSP程序的改动。
如果检测到JSP源代码发生了改动，Tomcat会在下次客户端请求JSP时重新编译JSP，而不需要重启Tomcat。
这种自动检测功能是**默认开启**的，检测改动会消耗少量的时间，在部署Web应用的时候可以在web.xml中将它关掉。

## JSP九大内置对象、七大动作、三大指令
### 九大内置对象

<div align="center"><img src="pics\\01_1.png" width="600"/></div>


1. out 输出流对象

隐藏对象out是javax.servlet.jsp.JspWriter类的实例服务器向客户端**输出的字符内容可以通过out对象输出**。

获取：PrintWriter out = response.getWriter();

2. request 请求对象

隐藏对象request是javax.servlet.ServletRequest类的实例，代表**客户端的请求**。

request包含客户端的信息以及请求的信息，如请求那个文件，附带的地址参数等。

3. response 响应对象

隐藏对象response是javax.servlet.ServletResponse类的实例，代表**客户端的响应**。

服务器端的任何输出都通过response对象发送到客户端浏览器。
每次服务器端都会响应一个response实例。

4. config 配置对象

隐藏对象config是javax.servlet.ServletConfig类的实例，
ServletConfig封装了配置在web.xml中初始化JSP的参数。
JSP中通过config获取这些参数。
**每个JSP文件中共有一个config对象**。

5. session 会话对象
 
隐藏对象session是javax.servlet.http.HttpSession类的实例。

session与cookie是记录客户访问信息的两种机制:

- session是用于**服务器端保存用户信息**
- cookie用于在**客户端保存用户信息**

Servlet中通过request.getSession()来获取session对象，而JSP中可以直接使用。
如果JSP中配置
```html
<%@page session="false"%>
```
则隐藏对象session不可用。

**每个用户对应一个session对象**。

6. application 应用程序对象

隐藏对象application是javax.servlet.ServletContext类的对象。
application封装JSP所在Web应用程序的信息，
例如web.xml中配置的全局的初始化信息。

Servlet中application对象需要通过ServletConfig.getServletContext()来获取。

**整个Web应用程序对应一个application对象**。

7. page 页面对象

隐藏对象page是javax.servlet.jsp.HttpJspPage类的实例。
**page对象代表当前JSP页面**，是当前JSP编译后的Servlet类的对象。
page想当于Java类中的关键字this。

8. pageContext 页面上下文对象

隐藏对象pageContext为javax.servlet.jsp.PageContext类的实例。
pageContext对象代表**当前JSP页面编译后的内容**。
通过pageContext能够获取到JSP中的资源。

9. exception 异常对象

隐藏对象exception为java.lang.Exception类的对象。exception封装了JSP中抛出的异常信息。
要使用exception隐藏对象，需要设置
```html
<%@page isErrorPage="true"%>
```
隐藏对象exception通常被用来处理错误页面。

### 七大动作
| 动作 | 说明 |
| :--: | :--: |
| jsp:include | 在页面被请求的时候引入一个文件 |
| jsp:useBean | 寻找或者实例化一个 JavaBean |
| jsp:setProperty | 设置 JavaBean 的属性 |
| jsp:getProperty | 输出某个 JavaBean 的属性 | 
| jsp:forward | 把请求转到一个新的页面 |
| jsp:plugin | 根据浏览器类型为 Java 插件生成 OBJECT 或 EMBED 标记 |

### 三大指令
1. page指令

实例：

```html
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
```

- import : 等同于 import语句 

```html
<%@ page import="java.util.*"%> 
```

在一个JSP页面中可以给出多个page指令，而且import是可以重复出现的 

```html
<%@ page import="java.util.*" %> 
<%@ page import="java.net.*" %>
```

- pageEncoding : 指定当前页面的编码 

如果pageEncoding没有指定，那么默认为contentType的值
如果pageEncoding和contentType都没有指定，那么默认值为 "iso-8859-1"

- contentType : 等同与调用response.setContentType(“text/html;charset=xxx”); 

如果没有指定contentType属性，那么默认为pageEncoding的值；
如果contentType和pageEncoding都没有指定，那么默认值为 "iso-8859-1"

- errorPage : 如果当前页面出现异常，那么跳转到errorPage指定的jsp页面。 

例如:
```html
<%@ page errorPage="error.jsp" %> 
```

- isErrorPage : 上面示例中指定error.jsp为错误页面，但在error.jsp中不能使用内置对象exception，
只有error.jsp中使用

```html
<%@page isErrorPage="true"%>
```
才能在error.jsp中使用错误页面。 

- autoFlush : 当autoFlush为true时，表示out流缓冲区满时会自动刷新。默认为true。

- buffer : 指定out流的缓冲区大小，默认为8KB 

- isELIgnored : 当前JSP页面是否忽略EL表达式，默认为false，表示不忽略，即支持EL表达式

2. include指令

JSP可以通过include指令来包含其他文件。被包含的文件可以是JSP文件、HTML文件或文本文件。
包含的文件就好像是该JSP文件的一部分，会被同时编译执行。 
include指令的语法格式如下： 

```html
<%@ include file="文件相对 url 地址"%>
```

3. taglib指令

taglib指令是用来在当前jsp页面中**导入第三方的标签库**。

```html
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %> 
```

- prefix : 指定标签前缀，这个东西可以随意起名 
- uri : 指定第三方标签库的uri(唯一标识) 
当然，需要先把第三方标签库所需jar包放到类路径中。

注意 ：

i. **include指令与include行为**

- include指令 : JSP可以通过include指令来包含其他文件。
被包含的文件可以是JSP文件、HTML文件或文本文件。
包含的文件就好像是该**JSP文件的一部分**，会被同时编译执行。 
语法格式如下： 

```html
<%@ include file="文件相对 url 地址" %>
```

- include动作 : jsp:include动作元素用来包含静态和动态的文件。
该动作**把指定文件插入正在生成的页面**。
语法格式如下： 

```html
<jsp:include page="相对 URL 地址" flush="true" />
```

ii. **request.getAttribute()和 request.getParameter()**

> 获取方向:

getParameter()是获取 POST/GET 传递的参数值

getAttribute()是获取对象容器中的数据值

> 用途:

getParameter用于客户端重定向时，即**点击了链接或提交按扭时传值用**，
即用于在用表单或url重定向传值时接收数据用。

getAttribute用于服务器端重定向时，即在 sevlet 中使用了 forward 函数,
或 struts 中使用了 mapping.findForward。 
getAttribute 只能收到程序用 setAttribute 传过来的值。

> 数据类型:

可以用 setAttribute,getAttribute 发送接收对象。而 getParameter 显然只能传字符串。
setAttribute 是应用服务器把这个对象放在该页面所对应的一块内存中去，当你的页面服务器重定向到另一个页面时，
**应用服务器会把这块内存拷贝另一个页面所对应的内存中**。
这样getAttribute就能取得你所设下的值，当然这种方法可以传对象。

getParameter只是应用服务器在分析你送上来的 request页面的文本时，取得你设在表单或 url 重定向时的值。

iii. **JSP中的四种作用域**

JSP中的四种作用域包括**page**、**request**、**session**和**application**,具体来说：

| 作用域 | 说明 |
| :--: | :--: |
| page | 代表与一个页面相关的对象和属性 |
| request | 代表与客户端发出的一个请求相关的对象和属性。一个请求可能跨越多个页面，涉及多个Web组件；需要在页面显示的临时数据可以置于此作用域。 |
| session | 代表与某个用户与服务器建立的一次会话相关的对象和属性。跟某个用户相关的数据应该放在用户自己的session中。|
| application | 代表与整个Web应用程序相关的对象和属性，它实质上是跨越整个Web应用程序，包括多个页面、请求和会话的一个全局作用域。 |

