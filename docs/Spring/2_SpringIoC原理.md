# Spring IoC 原理

## IoC (Inverse of Control）

IOC，即控制反转（Inverse of Control）是一种**设计思想**，并不是一个具体的技术实现。

- 控制：控制对象的创建及销毁(生命周期)。
- 反转：将对象的控制权交给 IoC 容器。

**所有类的创建、销毁都由 Spring 来控制，也就是说控制对象生命周期的不是引用它的对象，而是 Spring**。对于某个具体对象而言，以前是它控制其他对象，现在所有对象都被 Spring 控制。

## 依赖注入 (Dependency Injection)

依赖注入就是**将底层类作为参数传递给上层类，实现上层对下层的控制**，**依赖注入实现控制反转**。

举例说明依赖注入：以生产行李箱为例。

**传统设计思路**：先设计轮子，然后根据轮子 size 来设计底盘，再根据底盘来设计箱体，最后设计好行李箱。

可这样表示：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_1.png" width="700px"/></div>

相应代码代码：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_2.png" width="700px"/></div>

size 是固定值，可以进行相应的改进：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_3.png" width="700px"/></div>

**依赖注入设计思路**：

先设计行李箱的大概样子，再根据行李箱的样子设计箱体，根据箱体去设计底盘，然后去设计轮子。

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_4.png" width="700px"/></div>

相应代码如下：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_5.png" width="700px"/></div>

不难理解，依赖注入就是**将底层类作为参数传递给上层类，实现上层对下层的控制**。

Spring 支持 4 种依赖注入：setter 注入、构造器注入、注解注入和接口注入。

### setter 注入

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```

### 构造器注入

```xml
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }
}
```

### 注解注入

```java
public class ExampleBean {

    @Autowired
    @Qualifier("anotherExampleBean")
    private AnotherBean beanOne;
    
    @Autowired
    @Qualifier("yetAnotherBean")
    private YetAnotherBean beanTwo;

    @Value("1")
    private int i;
}
```

### 接口注入

接口注入模式因为历史较为悠久，在很多容器中都已经得到应用。但由于其在灵活性、易用性上不如其他注入模式，因而在 IOC 的专题世界内并不被看好。



## IoC 和 DI 的关系

依赖注入实现控制反转。

依赖倒置原则、IoC、DI 和 IoC 容器的关系：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_7.png" width="700px"/></div>

补充：[控制反转-知乎解答](https://www.zhihu.com/question/23277575/answer/169698662)



## IoC 容器

IoC 容器指具有依赖注入功能的容器。

IoC 容器负责创建对象，将对象连接在一起，配置这些对象，并从创建中处理这些对象的整个生命周期，直到它们被完全销毁。

Spring 通过**配置文件**描述 IoC 容器管理的对象。Spring IoC 容器通过读取配置文件中的配置元数据，通过元数据对应用中的各个对象进行实例化及装配。一般使用基于 xml 配置文件进行配置元数据，而且 Spring 与配置文件完全解耦的，可以使用其他任何可能的方式进行配置元数据，比如注解、基于 Java 文件的、基于属性文件的配置都可以。

Spring IoC 容器的代表就是 `org.springframework.beans` 包下的 **BeanFactory** 接口：

- IoC 容器要实现的最基础的接口
- 采用**延迟初始化策略**（容器初始化完成后并不会创建 Bean 对象，只有当收到初始化请求时才进行初始化）
- 由于是延迟初始化策略，因此启动速度较快，占用资源较少

`org.springframework.context` 包下的 **ApplicationContext** 接口扩展了 BeanFactory：

- 在 BeanFactory 基础上，增加了更为高级的特性：事件发布、国际化等。
- 在容器启动时，完成所有 Bean 的创建
- 启动时间较长，占用资源较多

### IoC 容器初始化过程

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/01_1.png" width="700px"/></div>

- Resource 定位：即 BeanDefinition 的资源定位，Resource 为各种形式的 BeanDefinition 的使用都提供了统一的接口
- BeanDefinition 的载入
- 向 IoC 容器中注册 BeanDefinition：实际上 IoC 容器内部维护一个 HashMap，注册过程就是将 BeanDefinition 添加至 HashMap 中。

### Spring Bean 的装配流程

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/ioc_11.png" width="700px"/></div>

IoC 容器其实就是一个大工厂，它用来管理我们所有的对象以及依赖关系：

- Spring 在启动时会从 xml 配置文件/注解中读取应用程序提供的 Bean 配置信息，并在 Spring 容器中生成一份相应的 Bean 定义注册表
- 根据注册表实例化 Bean，装配好 Bean 之间的依赖关系
- 将 Bean 实例放入 Spring IoC 容器中，等待应用程序调用

### getBean 方法

ApplicationContext 接口获取 Bean 的方法：

| 方法                                          | 说明                                                         |
| --------------------------------------------- | ------------------------------------------------------------ |
| Object getBean(String name)                   | 根据名称返回一个 Bean，**客户端需要自己进行类型转换**        |
| T getBean(String name, Class<T> requiredType) | 根据名称和指定的类型返回一个Bean，客户端无需自己进行类型转换，如果类型转换失败，容器抛出异常 |
| T getBean(Class<T> requiredType)              | 根据指定的类型返回一个Bean，客户端无需自己进行类型转换，如果没有或有多于一个Bean存在容器将抛出异常 |
| Map<String, T> getBeansOfType(Class<T> type)  | 根据指定的类型返回一个键值为名字和值为 Bean 对象的Map，如果没有Bean对象存在则返回空的 Map |

getBean(name) 代码逻辑：

- 获取参数 name 转化为 beanName
- 从缓存中加载实例
- 实例化 Bean
- 检测 parentBeanFactory（若无缓存数据，直接到 parentBeanFactory 中去加载）
- 初始化依赖的 Bean
- 返回 Bean

> **注意：BeanFactory 和 FactoryBean 的区别**

- BeanFactory 是 IoC 最基本的容器，负责生产和管理 Bean，为其他具体的 IoC 容器提供了最基本的规范。
- FactoryBean 是一个 Bean，是一个接口，当 IoC 容器中的 Bean 实现了 FactoryBean 后，通过 getBean(String beanName)  获取到的 Bean 对象并不是 FactoryBean 的实现类对象，而是这个实现类中的 getObject() 方法返回的对象。**要想获取 FactoryBean 的实现类对象，就是在 beanName 前面加上 "&"**。



## Spring 中 Bean 的作用域

### singleton

singleton 即单例模式。singleton 作用域是 Spring 中的**缺省作用域**。

当一个 Bean 的作用域为 singleton，那么 Spring IoC 容器中只会存在一个**共享的 Bean 实例**， 并且所有对 Bean 的请求，只要 id 与该 Bean 定义相匹配，则只会**返回 Bean 的同一实例**。

可以指定 Bean 节点的 lazy-init="true" 来延迟初始化 Bean， 此时只有在第一次获取 Bean 时才会初始化 Bean，即第一次请求该 Bean 时才初始化。 每次获取到的对象都是同一个对象。 

配置文件 XML 中将 Bean 定义成 singleton ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="singleton">
```

@Scope 注解的方式：

```java
@Service
@Scope("singleton")
public class ServiceImpl{
}
```

### prototype

prototype 即原型模式。当一个 Bean 的作用域为 prototype，表示一个 Bean 定义对应多个对象实例。 prototype 作用域的 Bean 会导致在每次对该 Bean 请求（将其注入到另一个 Bean 中，或者以程序的方式调用容器的 getBean() 方法）时都会创建一个新的 Bean 实例。 

在创建容器的时候并没有实例化， 而是当我们获取 Bean 的时候才会去创建一个对象，而且我们每次获取到的对象都不是同一个对象。

配置文件 XML 中将 Bean 定义成 prototype ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="prototype">
```

或者

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" singleton="false"/>
```

@Scope 注解的方式：

```java
@Service
@Scope("prototype")
public class ServiceImpl{
}
```

> **Spring  中线程安全问题**

有状态 Bean & 无状态 Bean：

- **有状态 Bean**

  对象中有实例变量（成员变量），可保存数据；

  **非线程安全**。

- **无状态 Bean**

  对象中无实例变量，不能保存数据，可在多线程环境下共享；

  **线程安全**。

Spring 采用两种方式保证线程安全：

- 采用 ThreadLocal 进行处理
- 采用原型模式，每次有 Bean 请求时，都会创建一个新的 Bean 实例

所以根据经验，**对有状态的 Bean 应该使用 prototype 作用域，而对无状态的 Bean 则应该使用 singleton 作用域。**

### request

request 只适用于**Web程序**，每一次 HTTP 请求都会产生一个新的 Bean ， 同时该 Bean 仅在当前HTTP request 内有效，当请求结束后，该对象的生命周期即告结束。

在 XML 中将 Bean 定义成 request ，可以这样配置：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="request">
```

### session

session 只适用于**Web程序**， session 作用域表示该针对每一次 HTTP 请求都会产生一个新的 Bean， 同时**该 Bean 仅在当前 HTTP session 内有效**。 与 request 作用域一样，可以根据需要放心的更改所创建实例的内部状态， 而别的 HTTP session 中根据 userPreferences 创建的实例， 将不会看到这些特定于某个 HTTP session 的状态变化。 当HTTP session最终被废弃的时候，在该 HTTP session 作用域内的 Bean 也会被废弃掉。

```html
<bean id="userPreferences" class="com.foo.UserPreferences" scope="session"/>
```

### globalSession

globalSession 作用域**类似于标准的 HTTP session** 作用域， 不过仅仅在基于 portlet 的 Web 应用中才有意义。 Portlet 规范定义了全局 Session 的概念， 它被所有构成某个 portlet web 应用的各种不同的 portlet所共享。 在 globalSession 作用域中定义的 Bean 被限定于全局 portlet Session 的生命周期范围内。

```html
<bean id="user" class="com.foo.Preferences "scope="globalSession"/>
```

注意：五种作用域中，request、session 和 globalSession 三种作用域仅在基于 Web 的应用中使用（不必关心你所采用的是什么 Web 应用框架），只能用在基于 Web 的 Spring ApplicationContext 环境。



## Spring 中 Bean 的生命周期

Spring bean 的生命周期执行如下图：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/spring_1.png" width='450px'/></div>

1、Spring 对 Bean 进行实例化。

2、Spring 将值和 Bean 的引用注入到 Bean 对应的属性中。

3、如果 Bean 实现了 BeanNameAware 接口，则会调用其实现的 setBeanName() 方法，

Spring 会将 bean 的 id 传递给 setBeanName() 接口方法。

4、如果 Bean 实现了 BeanFactoryAware 接口，则会调用其实现的 setBeanFactory() 方法，将 BeanFactory 容器实例作为传入参数。

5、如果 Bean 实现了 ApplicationContextAware 接口，则会调用其实现的 setApplicationContext() 方法，将应用上下文的引用作为传入参数。

6、如果 Bean 实现了 BeanPostProcessor 接口，Spring 将调用 postProcessBeforeInitialization() 接口方法。

7、如果 Bean 实现了InitializingBean 接口，Spring 将调用 afterPropertiesSet() 接口方法。

8、如果Bean 实现了 init-method 声明了初始化方法，该方法也会被调用。

9、如果 Bean 实现了 BeanPostProcessor 接口，Spring 将调用 postProcessAfterInitialization() 接口方法。

10、此时 Bean 已经准备就绪，可以被应用程序使用了，他们将会一直驻留在应用上下文中，一直到该应用上下文被销毁。

11、如果 Bean 实现了 DisposableBean 接口，Spring 将调用它的 destroy() 接口方法。

12、如果 Bean 使用 destroy-method 声明了销毁方法，方法也会被调用。



## 循环依赖问题

循环依赖指的是若 A 中有 B 的属性，B 中有 A 的属性，则当进行依赖注入时，就会产生 A 还未创建完，因为对 B 的创建再次返回创建 A。

### 类的实例化 & 类的初始化

类的实例化是指创建一个类的实例（对象）的过程。

类的初始化是指为类中各个类成员（被 static 修饰的成员变量）赋初始值的过程，是**类生命周期中的一个阶段**。

### Spring 中类的实例化 & 类的初始化

Spring 中**所有 Bean 默认都是单例模式**，所以 Bean 的初始化和实例化都是在加载进入 Bean 容器时进行的。如果想使用时再初始化，那么可以把类定义为原型模式。

### 三级缓存

单例对象，在 Spring IoC 容器中，有且仅有一个对象，将对象放入缓存中。

Spring 中使用 “三级缓存”：

```java
// 一级缓存：单例对象的缓存（存储实例化完成的 Bean）
/** Cache of singleton objects: bean name --> bean instance */
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);
 
// 三级缓存：单例 ObjectFactory 的缓存
/** Cache of singleton factories: bean name --> ObjectFactory */
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>(16);

// 二级缓存：提前曝光的单例对象的缓存（存储正在实例化的 Bean）
/** Cache of early singleton objects: bean name --> bean instance */
private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);
```

举例说明解决循环依赖（A 中有B，B 中有 A）的具体过程：

Spring 中单例对象的初始化主要分为 3 步：

- 第一步：createBeanInstance 
- 第二步：populateBean 填充属性
- 第三步：intializeBean 初始化

在进行 createBeanInstance 后，该单例对象此时已被创建，Spring 将该对象**提前曝光到 singeltonFacoties 中**。

- A 完成 createBeanInstance ，并且**提前曝光到 singeltonFacoties 中**
- A 进行第二步，发现需要依赖 B，尝试获取 B
- B 开始创建，B 完成  createBeanInstance，发现需要依赖 A，尝试获取 A：先尝试从 singletonObjects 中获取，发现不存在，因为 A 未初始化完全；再尝试从 earlySingletonObjects 中获取；再去 singletonFactories 中获取，此时 B 获取 A，并将 A 放入 earlySingletonObjects 中，再删除 A 在singletonFactories 中对应的 ObjectFactory。
- B 获取 A，顺利完成第二、三步，将初始化完成的 B 放入 singletonObjects 中。
- 此时返回创建 A，A 可获取 B，顺利完成第二、三步，A 初始化完成， 将 A 放入 singletonObjects 中。

> **Spring 2 种循环依赖：构造器循环依赖 & setter 循环依赖**

- 构造器循环依赖：因为提前曝光到 singletonFactories 中的前提是需要执行构造方法，所以使用 “三级缓存” 无法解决该种循环依赖。
- setter 循环依赖

所以在使用 Spring 框架进行开发时：

- 尽量不要使用基于构造器的依赖注入方式，使用基于 setter 的依赖注入方式
- 使用 @Autowired 注解，让 Spring 决定合适的时机

# 补充

- [Spring 官网](https://docs.spring.io/spring-framework/docs/current/reference/html/index.html)
- [Spring 循环依赖及三级缓存](https://blog.csdn.net/u012098021/article/details/107352463?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link)
