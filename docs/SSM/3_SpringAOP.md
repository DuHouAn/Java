# SpringAOP

## AOP

AOP，即面向切面编程（Aspect Oriented Programing）。实际上是将一些通用的功能横向抽取出来：一方面，可减少系统的代码；另一方面，降低模块间的耦合度，比较好的维护和扩展。



## AOP 相关术语

|        术语        |                             描述                             |
| :----------------: | :----------------------------------------------------------: |
| Joinpoint(连接点)  | 所谓连接点是指那些被拦截到的点。在Spring中,这些点指的是**方法**,因为Spring只支持**方法类型的连接点**。 |
|  Pointcut(切入点)  |   所谓切入点是指我们要**对哪些Joinpoint进行拦截**的定义。    |
| Advice(通知/增强)  | 所谓通知是指拦截到Joinpoint之后所要做的事情就是**通知**。通知分为前置通知,后置通知,异常通知,最终通知,环绕通知(切面要完成的功能) |
| Introduction(引介) | 引介是一种**特殊的通知**在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field |
|  Target(目标对象)  |                        代理的目标对象                        |
|   Weaving(织入)    | 是指把增强应用到目标对象来创建新的代理对象的过程。 有三种织入方式： Spring采用**动态代理织入**，而AspectJ采用**编译期织入**和**类装载期织入** |
|   Proxy（代理）    |         一个类被AOP织入增强后，就产生一个结果代理类          |
|    Aspect(切面)    |                是切入点和通知（/引介）的结合                 |

- 示例：在 IUserDao() 中

  ```java
  public interface IUserDao {
      void add();
      void delete();
      void update();
      void search();
  }
  // IUserDao 被增强的对象，就是 Target(目标对象)
  // add()、delete()、update() 和 search() 都是 JoinPoint(连接点) 
  // 这里要对 add() 和 update() JoinPoint 进行拦截，则 add() 和 update() 就是 Pointcut(切入点)
  // Advice 指的是要增强的代码，也就是代码的增强
  // Weaving：指的是把增强(Advice)应用到目标对象(Target)创建新的代理对象得人过程
  // Aspect：是切入点和通知的结合，在 add 或 delete 方法上应用增强
  ```



## AOP 底层原理

AOP 的底层原理是代理机制：

- 类实现了接口，JDK 动态代理
- 类未实现任何接口，Cglib 动态代理