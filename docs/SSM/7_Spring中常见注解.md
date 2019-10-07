# Spring 中常见注解

## @Contoller

SpringMVC 中，控制器 Controller 负责处理 DispatcherServlet 分发的请求，它把用户请求的数据经过业务处理层处理之后封装成一个 Model，然后再把该 Model 返回给对应的 View 进行展示。

SpringMVC 提供了一个非常简便的定义 Controller 的方法，你无需继承特定的类或者接口，只需使用 @Controller 标记一个类是 Contoller。

## @RequestMapping

使用 @RequestMapping 来映射 URL 到控制器，或者到 Controller 控制器的处理方法上。method 的值一旦指定，则处理方法只对指定的 HTTP method 类型请求处理。

可以为多个方法映射相同的 URI，不同的 HTTP method 类型，Spring MVC 根据请求的 method 类型是可以区分开这些方法的。

## @RequestParam 和 @PathVariable

在 SpringMVC 中，两者的作用都是将 request 里的参数的值绑定到 Controller 里的方法参数中，区别在于 URL 的写法不同。

- 使用 @RequestParam 时，URL 是这样的：

```html
http://host:port/path?参数名=参数值
```

- 使用 @PathVariable 时，URL 是这样的：

```html
http://host:port/path/参数值
```

## @Autowired

@Autowired 可以对成员变量、成员方法和构造函数进行标注，来完成自动装配工作。

## @Service、 @Contrller、 @Repository 和 @Component

 @Service、 @Contrller、 @Repository 其实这 3 个注解和 @Component 是等效的，用在实现类上：

- @Service 用于标注业务层组件
- @Controller 用于标注控制层组件
- @Repository 用于编著数据访问组件
- @Component 泛指组件，当组件不好归类时，可以使用这个注解进行标注

## @Value

在 Spring 3.0 中，可以通过使用 @Value，对一些如 xxx.properties 文件中的文件，进行键值对的注入。

## @ResponseBody

该注解用于将 Controller 中方法返回的对象，通过适当的 HttpMessageConverter 转换为指定的格式后，写入到 Response 对象的 bodys 数据区。

 

> **@Autowired 和 @Resource 的区别**

@Autowired

- 是 Spring 提供的注解

- 采用的策略是**按照类型**注入的

  ```java
  public class UserService{
      @Autowired
      userDao; //在 Spring 容器中找到类型为 userDao 的类，将其注入
  }
  ```

  存在问题：同一类型有多个 Bean，可以使用 @Qualifier 具体去装配哪个对象。

  ```java
  public class UserService{
      @Autowired
      @Qualifier(name="userDao")
      userDao; //在 Spring 容器中找到类型为 userDao 的类，将其注入
  }
  ```

 @Resource 

- J2EE 提供的注解

- 默认是**按照名称**注入的

  ```java
  Service{
      @Resource
      userDao; //自动按名称进行装配
      
      @Resource(name="studentDao")
      studentDao; //按名称进行装配，找不到会抛出异常
      
      @Resource(type="TeacherDao")
      teacherDao; //按类型进行装配，找不到或者找到多个都会抛出异常
      
      @Resource(name="manDao",type="ManDao")
      manDao;//找唯一匹配的 Bean 进行装配，如果找不到则会抛出异常
  }
  ```
