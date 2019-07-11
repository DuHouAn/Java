## Cookie
### Cookie简介
Cookie意为“甜饼”，是由W3C组织提出，最早由Netscape社区发展的一种机制。
目前Cookie已经成为标准，所有的主流浏览器如IE、Netscape、Firefox、Opera等都支持Cookie。

由于HTTP是一种无状态的协议，服务器单从网络连接上无从知道客户身份。
怎么办呢？就给客户端们颁发一个通行证吧，每人一个，无论谁访问都必须携带自己通行证。
这样服务器就能从通行证上确认客户身份了。这就是Cookie的工作原理。

Cookie实际上是一小段的文本信息。
客户端请求服务器，如果服务器需要记录该用户状态，就使用response向客户端浏览器颁发一个Cookie。
**客户端浏览器会把Cookie保存起来**。
当浏览器再请求该网站时，浏览器把请求的网址连同该Cookie一同提交给服务器。
服务器检查该Cookie，以此来辨认用户状态。
服务器还可以根据需要修改Cookie的内容。

### Cookie机制

Cookie技术是**客户端的解决方案**，Cookie就是由**服务器发给客户端**的特殊信息，而这些信息以文本文件的方式**存放在客户端**，
然后客户端每次向服务器发送请求的时候都会带上这些特殊的信息。

具体过程如下：

1. 用户使用浏览器访问一个支持Cookie的网站的时候，用户会提供包括用户名在内的个人信息并且提交至服务器；

2. 服务器在向客户端回传相应的超文本的同时也会发回这些个人信息，当然这些信息并不是存放在HTTP响应体
（Response Body)中的，而是存放于HTTP响应头(Response Header)

3. 客户端浏览器接收到来自服务器的响应之后，浏览器会将这些信息存放在一个统一的位置。
对于Windows操作系统而言，我们可以从： [系统盘]:\Documents and Settings[用户名]\Cookies目录中找到存储的Cookie；

4. 客户端再次向服务器发送请求的时候，都会把相应的Cookie再次发回至服务器。
而这次，Cookie信息则存放在HTTP请求头(equest Header)了。

### HTTP的Cookie机制

Web应用程序是使用HTTP协议传输数据的。HTTP协议是无状态的协议。
一旦数据交换完毕，客户端与服务器端的连接就会关闭，再次交换数据需要建立新的连接。
这就意味着服务器无法从连接上**跟踪会话**。
举个例子，用户A购买了一件商品放入购物车内，
当再次购买商品时服务器已经无法判断该购买行为是属于用户A的会话还是用户B的会话了。
要跟踪该会话，必须引入一种机制。
​    
Cookie就是这样的一种机制。它可以弥补HTTP协议无状态的不足。
在Session出现之前，基本上所有的网站都**采用Cookie来跟踪会话**。


### Set-Cookie和Cookie
两个Http头部和Cookie有关 : Set-Cookie和Cookie

当服务器返回给客户端一个Http响应信息时，其中如果包含Set-Cookie这个头部，说明：

1. 指示客户端建立一个cookie

2. 在后续的Http请求中自动发送这个cookie到服务器端，直到这个cookie过期。

3. 如果cookie的生存时间是整个会话期间的话，那么浏览器会将 cookie 保存在内存中，
浏览器关闭时就会自动清除这个cookie。

4. 如果将 cookie 保存在客户端的硬盘中，浏览器关闭的话，该 cookie 也不会被清除，
下次打开浏览器访问对应网站时，这个cookie就会自动再次发送到服务器端。

一个cookie的设置以及发送过程分为以下四步：

- 1. **客户端发送一个http请求到服务器端**
- 2. **服务器端发送一个http响应到客户端，其中包含Set-Cookie头部**
- 3. **客户端发送一个http请求到服务器端，其中包含Cookie头部**
- 4. **服务器端发送一个http响应到客户端**

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/java/01_2.png" width="400"/></div>

在客户端的第二次请求中包含Cookie头部，提供给了服务器端可以用来唯一标识客户端身份的信息。
这时，服务器端也就可以判断客户端是否启用了cookie。
尽管，用户可能在和应用程序交互的过程中突然禁用cookie的使用，
但是，这个情况基本是不太可能发生的，所以可以不加以考虑，这在实践中也被证明是对的。

### Cookie的不可跨域名性

很多网站都会使用Cookie。例如，Google会向客户端颁发Cookie，Baidu也会向客户端颁发Cookie。
那浏览器访问Google会不会也携带上Baidu颁发的Cookie呢？或者Google能不能修改Baidu颁发的Cookie呢？

答案是否定的。Cookie具有**不可跨域名性**。
根据Cookie规范，浏览器访问Google只会携带Google的Cookie，而不会携带Baidu的Cookie。
Google也只能操作Google的Cookie，而不能操作Baidu的Cookie。

Cookie在客户端是由**浏览器来管理**的。
浏览器能够保证Google只会操作Google的Cookie而不会操作Baidu的Cookie，从而保证用户的隐私安全。
浏览器判断一个网站是否能操作另一个网站Cookie的依据是**域名**。
Google与Baidu的域名不一样，因此Google不能操作Baidu的Cookie。

- 注意：

虽然网站images.google.com与网站www.google.com同属于Google，
但是域名不一样，二者同样不能互相操作彼此的Cookie。

用户登录网站www.google.com之后会发现访问images.google.com时登录信息仍然有效，而普通的Cookie是做不到的。
这是因为Google做了特殊处理。


### 简单案例 : 记录上次访问时间
> cookie的API

```java
new Cookie(String key,String value);
String getName();//获取cookie的key(名称)
String getValue();//获取cookie的值
void setMaxAge(int);//设置cookie在浏览器存活时间，单位：秒
//如果设置成0：表示删除高cookie(前提：路径必须一致)
void setPath(String path);//设置cookie的路径
//当我们访问的路径中包含次cookie的path,才会携带cookie
//默认访问路径：访问Servlet的路径，从"/项目名称"开始，到最后一个"/"结束。比如：/demo/a/b，默认路径为/demo/a
//手动设置路径：以"/项目名称"开始，以"/"结尾
```

> 写回浏览器

```java
response.addCookie(Cookie);
```

> 获取cookie

```java
Cookie[] request.getCookies();
```

- 核心代码：

```java
/**
 * 根据 cookie名称获取Cookie 的工具类
 */
public class CookieUtils {
    public static Cookie getCookieByName(String name,Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
```

```java
public class RecordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        response.setContentType("text/html;charset=utf-8");
        PrintWriter w = response.getWriter();

        //2.获取指定名称的Cookie
        Cookie cookie = CookieUtils.getCookieByName("record",request.getCookies());

        //3.判断cookie是否为空；
        // 若为null,则说明是第一次访问；
        // 若不为 null,则根据cookie显示上一次的访问时间
        if(cookie == null){
            w.write("这是您第一次访问");
        }else{
            long lastTime= Long.parseLong(cookie.getValue());
            w.write("您上次访问的时间："+ new Date(lastTime).toLocaleString());
        }

        //4.记录当前访问时间，并且该信息存入cookie中
        Cookie c = new Cookie("record",System.currentTimeMillis()+"");
        //设置cookie的有效期是 1 小时
        c.setMaxAge(60*60);
        response.addCookie(c);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

- [该案例完整代码](https://github.com/DuHouAn/Java/tree/master/JavaWeb/Login)

### Cookie案例 : 浏览记录
- 核心代码1：记录商品浏览记录
```java
/**
 * 记录商品浏览记录，只展示3个商品
 */
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前访问商品的id
        String id = request.getParameter("id");

        Cookie c = CookieUtils.getCookieByName("ids",request.getCookies());

        //判断该 cookie 是否为空
        String ids="";
        if(c == null){
            //若为空,说明之前没有访问记录
            //将当前商品的id放入ids中
            ids = id;
        }else{
            //若不为空,获取值。也就是之前浏览的商品编号，使用 "-"进行连接
            ids = c.getValue();

            //将 ids 通过"-"进行分割，然后存入list中，方便后续的操作
            String[] categoryIds = ids.split("-");
            LinkedList<String> categories = new LinkedList<>();
            if(categories != null){
                for(String categoryId : categoryIds){
                    categories.add(categoryId);
                }
            }
            //判断之前记录中有无该商品
            if(categories.contains(id)){
                //若有，删除原来的id,将当前的id放入前面
                categories.remove(id);
            }else{
                // 若没有
                // 继续判断长度是否>=3
                // 若>=3,移除最后一个,将当前的id放入最前面
                // 若<3,直接将当前的id放入最前面.
                if(categories.size() >= 3){
                    categories.removeLast();
                }
            }
            //不管如何，id都是最新浏览的，直接加入到前面
            categories.addFirst(id);

            ids="";
            for(String categoryId : categories){
                ids += (categoryId + "-");
            }
            ids = ids.substring(0,ids.length()-1);
        }

        //创建cookie
        c=new  Cookie("ids",ids);
        //设置访问路径
        c.setPath(request.getContextPath()+"/");
        //设置存活时间
        c.setMaxAge(60);

        //写回浏览器
        response.addCookie(c);

        //跳转到指定的商品页面上
        response.sendRedirect(request.getContextPath()+"/category_info"+id+".htm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

- 显示浏览记录
```html
<ul style="list-style: none;">
    <%
        //获取指定名称的cookie ids
        Cookie c= CookieUtils.getCookieByName("ids", request.getCookies());

        //判断ids是否为空
        if(c==null){
    %>
    <h2>暂无浏览记录</h2>
    <%
    }else{//ids=3-2-1
        String[] arr=c.getValue().split("-");
        for(String id:arr){
    %>
    <li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="category/0<%=(Integer.parseInt(id)-1) %>.jpg" width="130px" height="130px" /></li>
    <%
            }
        }
    %>
</ul>
```

- 核心代码2：清空浏览记录

```java
/**
 * 清空浏览记录
 */
public class ClearServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie c = new Cookie("ids","");

        //cookie的路径与 CategoryServlet中的cookie中的路径要相同
        c.setPath(request.getContextPath()+"/");
        //直接将cookie设置成无效
        c.setMaxAge(0);
        response.addCookie(c);

        //重定向
        response.sendRedirect(request.getContextPath()+"/category_list.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

- 注意：

1. cookie是不能跨浏览器的

2. cookie不支持中文，需要编码


## Session

### Session简介

Session是一种记录客户状态的机制，不同于Cookie的是Cookie保存在客户端浏览器中，而Session保存在服务器上。
客户端浏览器访问服务器的时候，服务器把客户端信息以某种形式记录在服务器上。这就是Session。
客户端浏览器再次访问时只需要从该Session中查找该客户的状态就可以了。

如果说Cookie机制是通过检查客户身上的"通行证"来确定客户身份的话，
那么Session机制就是通过检查服务器上的"客户明细表"来确认客户身份。
Session相当于程序在服务器上建立的一份客户档案，
客户来访的时候只需要查询客户档案表就可以了。

### Session机制

一方面，我们可以把**客户端浏览器与服务器之间一系列交互的动作**称为一个 Session。
从这个语义出发，我们会提到Session持续的时间，会提到在Session过程中进行了什么操作等等。

另一方面，Session指的是**服务器端为客户端所开辟的存储空间**，该空间保存的信息就是用于保持状态。
从这个语义出发，我们则会提到往Session中存放什么内容，如何根据键值从Session中获取匹配的内容等。

- 要使用Session，当然是先要创建Session。那么Session在何时创建呢？

1. Session在服务器端程序运行的过程中创建的，不同语言实现的应用程序有不同创建Session的方法，
在Java中是通过调用HttpServletRequest的getSession方法(使用true作为参数)创建的。
创建Session的同时，**服务器会为该Session生成唯一的session id**，
这个session id在随后的请求中会被用来重新获得已经创建的Session

2. Session被创建之后，就可以调用Session相关的方法往Session中增加内容了，
而这些内容只会保存在服务器中，发到客户端的只有session id


3. 当客户端再次发送请求的时候，会将这个session id带上，
服务器接受到请求之后就会依据session id找到相应的Session，从而再次使用Session。


### Session的生命周期
Session保存在服务器端。为了获得更高的存取速度，服务器一般把Session放在**内存**中。
**每个用户都会有一个独立的Session**。
如果Session内容过于复杂，当大量客户访问服务器时可能会导致内存溢出。
因此，Session里的信息应该尽量精简。

**Session在用户第一次访问服务器的时候自动创建**。
需要注意只有访问JSP、Servlet等程序时才会创建Session，
只访问HTML、IMAGE等静态资源并不会创建Session。
如果尚未生成Session，也可以使用request.getSession(true)强制生成Session。

Session生成后，只要用户继续访问，服务器就会更新Session的最后访问时间，并维护该Session。
用户每访问服务器一次，无论是否读写Session，服务器都认为该用户的Session"活跃(active)"了一次。

### Session的有效期
由于会有越来越多的用户访问服务器，因此Session也会越来越多。
为防止内存溢出，服务器会把长时间内没有活跃的Session从内存删除。
这个时间就是Session的超时时间。如果超过了超时时间没访问过服务器，Session就自动失效了。

Session的超时时间为maxInactiveInterval属性，
可以通过对应的getMaxInactiveInterval()获取，通过setMaxInactiveInterval(longinterval)修改。

Session的超时时间也可以在web.xml中修改。
另外，通过调用Session的invalidate()方法可以使Session失效。

### Session案例 : 购物车
> 获取Session
```java
HttpSession getSession(); //request.getSession()
```
> 域对象
```java
xxxAttribute  //存放私有数据
```

> 域对象生命周期

- 创建：第一次调用request.getSession()
- 销毁：

1. 服务器非正常关闭;

2. session超时;

默认超时时间：30 min

手动设置超时：setMaxInactiveInterval(int) (单位：秒)

3. 手动设置;

Session接口中的invalidate()方法
```java
public void invalidate()
```

- 核心代码1：将商品添加到购物车
```java
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();

        //1.获取商品名称
        String name = request.getParameter("name");

        //2.获取购物车，实际上就是存入session的map
        HashMap<String,Integer> map = (HashMap<String, Integer>) request.getSession().getAttribute("cart");

        Integer num = null;

        //3.判断购物车是否为空
        if(map==null){
            //3.1 购物车为空，说明是第一次将商品放入购物车
            //先创建购物车,
            map = new HashMap<>();
            request.getSession().setAttribute("cart",map);
            num = 1;
        }else{
            //3.2 购物车不为空，判断该商品之前是否已经加入购物车
            num = map.get(name);
            if(num == null){
                //num==null,说明该商品之前未加入购物车
                num = 1;
            }else{
                num ++ ;
            }
        }
        map.put(name,num);

        //4.提示信息
        out.print("<center>已经将<b>"+name+"</b>添加到购物车中<hr></center>");
        out.print("<center><a href='"+request.getContextPath()+"/category_list.jsp'>继续购物</a></center><br/>");
        out.print("<center><a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a><center>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

- 显示购物车中信息
```java
<body>
    <div class="container">
        <a href="${pageContext.request.contextPath}/category_list.jsp">继续购物</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/clearCart">清空购物车</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <div class="row">
            <div style="margin:0 auto; margin-top:10px;width:950px;">
                <strong style="font-size:16px;margin:5px 0;">订单详情</strong>
                <table class="table table-bordered">
                    <tbody>
                    <tr class="warning" align="center">
                        <th>商品</th>
                        <th>数量</th>
                    </tr>
                    <%
                        HashMap<String,Integer> map = (HashMap<String,Integer>)request.getSession().getAttribute("cart");
                        if(map==null){
                            out.print("<tr><th colspan='2'>亲,购物车空空,先去逛逛~~</th></tr>");
                        }else{
                            for(String name : map.keySet()){
                                out.print("<tr class='active'>");

                                out.print("<td width='30%'>");
                                out.print(name);
                                out.print("</td>");
                                out.print("<td width='20%'>");
                                out.print(map.get(name));
                                out.print("</td>");

                                out.print("</tr>");
                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
```

- 核心代码2：清空购物车

```java
public class ClearCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

- [Coookie和Session案例的完整代码](https://github.com/DuHouAn/Java/tree/master/JavaWeb/Category)

## 实现会话跟踪的技术
- 1. **Cookie**

向客户端发送Cookie : 

```java
Cookie c =new Cookie("name","value"); //创建Cookie 
c.setMaxAge(60*60*24); //设置最大时效，此处设置的最大时效为一天
response.addCookie(c); //把Cookie放入到HTTP响应中
```

从客户端读取Cookie :

```java
String name ="name"; 
Cookie[]cookies =request.getCookies(); 
if(cookies !=null){ 
   for(int i= 0;i<cookies.length;i++){ 
        Cookie cookie =cookies[i]; 
        if(name.equals(cookis.getName())) 
        //something is here. 
        //you can get the value 
        cookie.getValue(); 
   }
}
```

优点: 数据可以持久保存，不需要服务器资源，简单，基于文本的Key-Value

缺点: 大小受到限制，用户可以禁用Cookie功能，由于保存在本地，有一定的安全风险。

- 2. **URL 重写**

在URL中添加用户会话的信息作为请求的参数，
或者将唯一的会话ID添加到URL结尾以标识一个会话。

优点： 在Cookie被禁用的时候依然可以使用

缺点： 必须对网站的URL进行编码，所有页面必须动态生成，不能用预先记录下来的URL进行访问。

- 3. **隐藏的表单域**

```html
<input type="hidden" name ="session" value="..."/>
```

优点： Cookie被禁时可以使用

缺点： 所有页面必须是表单提交之后的结果。

- 4. **session**

当一个用户第一次访问某个网站时会自动创建 HttpSession，**每个用户可以访问他自己的HttpSession**。

可以通过HttpServletRequest对象的getSession方法获得HttpSession。
通过HttpSession的setAttribute方法可以将一个值放在HttpSession中，
通过调用 HttpSession对象的getAttribute方法，同时传入属性名就可以获取保存在HttpSession中的对象。

与上面三种方式不同的是，**HttpSession放在服务器的内存中**，因此不要将过大的对象放在里面。
即使目前的Servlet容器可以在内存将满时将 HttpSession 中的对象移到其他存储设备中，但是这样势必影响性能。
添加到 HttpSession 中的值可以是任意Java对象，这个对象最好实现了 Serializable接口，
这样Servlet容器在必要的时候可以将其序列化到文件中，否则在序列化时就会出现异常。


## Cookie和Session的的区别

1. HTTP协议是**无状态的协议**，服务端需要记录用户的状态，就需要用某种机制来**识别具体的用户**，这个机制就是Session。
Session典型的应用场景就是购物车，当点击下单按钮时，由于HTTP协议无状态，所以并不知道是哪个用户操作的，
所以服务端要为特定的用户创建了特定的Session，用于**标识这个用户，并且跟踪用户**，这样才知道购物车里面的商品情况。
这个Session是保存在服务端的，有一个唯一标识。在服务端保存Session的方法很多，内存、数据库、文件都有。
集群的时候也要考虑Session的转移，在大型的网站，一般会有专门的Session服务器集群，
用来保存用户会话，这个时候 Session 信息都是放在内存的，此外，一些缓存服务比如Memcached之类的来放 Session。


2. 服务端使用Cookie来识别特定的客户。每次HTTP请求的时候，客户端都会发送相应的Cookie信息到服务端。
实际上大多数的应用都是用 Cookie 来实现Session跟踪的，
第一次创建Session的时候，服务端会在HTTP协议中告诉客户端，需要在 Cookie 里面记录一个session id，
以后每次请求把这个 session id发送到服务器，这样就可以使用对应的Seesion了。
如果客户端的浏览器禁用了 Cookie 怎么办？
一般这种情况下，会使用一种叫做**URL重写的技术**来进行**会话跟踪**，
即每次HTTP交互，URL后面都会被附加上一个诸如 sid=xxxxx 这样的参数，服务端据此来识别用户。


3. Cookie其实还可以用在一些方便用户的场景下，
设想你某次登陆过一个网站，下次登录的时候不想再次输入账号了，怎么办？
这个信息可以写到Cookie里面，访问网站的时候，
网站页面的脚本可以读取这个信息，就自动帮你把用户名给填了，
能够方便一下用户。这也是Cookie名称的由来，给用户的一点甜头。

总结：

- Session是在服务端保存的一个数据结构，用来跟踪用户的状态，这个数据可以保存在集群、数据库、文件中。
- Cookie是客户端保存用户信息的一种机制，用来记录用户的一些信息，也是实现Session的一种方式。