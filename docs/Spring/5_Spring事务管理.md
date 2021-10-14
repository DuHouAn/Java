# Spring 事务管理

## 编程式事务 & 声明式事务

### 编程式事务

编程式事务指的是通过编码方式实现事务，类似 JDBC 编程实现事务管理，比如 TransactionalTemplate 或者 TransactionManager。

```java
@Autowired
private TransactionTemplate transactionTemplate;

public void testTransaction() {

    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

            try {
                // 业务代码
            } catch (Exception e){
                // 回滚
                transactionStatus.setRollbackOnly();
            }
        }
    });
}
```

```java
@Autowired
private PlatformTransactionManager transactionManager;

public void testTransaction() {

    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    try {
        // 业务代码
        transactionManager.commit(status);
    } catch (Exception e) {
        // 回滚
        transactionManager.rollback(status);
    }
}
```

### 声明式事务

声明式事务在 **XML 配置文件中配置**或者直接基于**注解**，其中基于`@Transactional` 的全注解方式使用最多。

|  事务方式  |        优点        |            缺点            |
| :--------: | :----------------: | :------------------------: |
| 编程式事务 | 显示调用，不易出错 |    侵入式代码，编码量大    |
| 声明式事务 | 简单，对代码侵入小 | 隐藏实现细节，出错不易定位 |



## Spring 事务管理接口

Spring 框架中，事务管理相关最重要的 3 个接口如下：

- PlatformTransactionManager：（平台）事务管理器
- TransactionDefinition：事务定义信息（隔离级别、传播行为、超时、只读、回滚）
- TransactionStatus：事务运行状态

**所谓事务管理，实质上就是按照给定的事务规则来执行提交或者回滚操作**。其中，“给定的事务规则”是用 TransactionDefinition 表示的，“按照……来执行提交或者回滚操作”是用 PlatformTransactionManager 表示的，而 TransactionStatus 可以看作代表事务本身。

### PlatformTransactionManager

**Spring 并不直接管理事务，而是提供了多种事务管理器** 。Spring 事务管理是通过 PlatformTransactionManager 接口体现的，该接口是 Spring事务策略的核心。通过该接口，Spring 为各个平台如  JDBC (DataSourceTransactionManager)、Hibernate (HibernateTransactionManager)、JPA (JpaTransactionManager) 等都提供了对应的事务管理器，但是具体的实现就是各个平台自己的事情了。

```java
public interface PlatformTransactionManager {

    //平台无关的获得事务的方法
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    //平台无关的事务提交方法
    void commit(TransactionStatus status) throws TransactionException;

    //平台无关的事务回滚方法
    void rollback(TransactionStatus status) throws TransactionException;
}
```

比如我们在使用 JDBC 进行数据持久化操作时，进行如下配置：

```xml
<!-- 事务管理器 -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <!-- 数据源 -->
    <property name="dataSource" ref="dataSource" />
</bean>
```

### TransactionDefinition

TransactionDefinition 接口用于定义事务属性，事务属性可以理解成事务的一些基本配置，描述了事务策略如何应用到方法上。事务属性包含了以下 5 个方面：

- 事务隔离级别
- 事务传播行为
- 回滚规则
- 是否只读
- 事务超时

### TransactionStatus

TransactionStatus 接口用来记录事务的状态，该接口定义了一组方法，用来获取或判断事务的相应状态信息。

```java
public interface TransactionStatus{
    boolean isNewTransaction(); // 是否是新的事务
    boolean hasSavepoint(); // 是否有恢复点
    void setRollbackOnly();  // 设置为只回滚
    boolean isRollbackOnly(); // 是否为只回滚
    boolean isCompleted; // 是否已完成
}
```



## Spring 事务属性

### 事务隔离级别

事务隔离级别是指多个事务之间的隔离程度。

TransactionDefinition 接口中定义了 5 个表示隔离级别的常量：

```java
public interface TransactionDefinition {
    ...
    int ISOLATION_DEFAULT = -1;
    int ISOLATION_READ_UNCOMMITTED = 1;
    int ISOLATION_READ_COMMITTED = 2;
    int ISOLATION_REPEATABLE_READ = 4;
    int ISOLATION_SERIALIZABLE = 8;
    ...
}
```

```java
// 事务隔离级别对应的枚举
public enum Isolation {
    DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),    READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
    READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
    REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
    SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);

    private final int value;

    Isolation(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
```

|          隔离级别          | 解释     |                             说明                             |
| :------------------------: | -------- | :----------------------------------------------------------: |
|     ISOLATION_DEFAULT      | 默认     |        这是默认值，表示使用底层数据库的默认隔离级别。        |
| ISOLATION_READ_UNCOMMITTED | 读未提交 | 该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据，该级别不能防止脏读和不可重复读，因此很少使用该隔离级别 |
|  ISOLATION_READ_COMMITTED  | 读可提交 | 该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下推荐的隔离级别 |
| ISOLATION_REPEATABLE_READ  | 可重复读 | 该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读，但幻读仍有可能发生 |
|   ISOLATION_SERIALIZABLE   | 可串行化 | 所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是，这将严重影响程序的性能，通常情况下也不会用到该级别 |

### 事务传播行为

**事务传播行为是为了解决业务层方法之间互相调用的事务问题**。

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行。

TransactionDefinition 接口中定义了 5t个表示传播行为的常量：

```java
public interface TransactionDefinition {
    int PROPAGATION_REQUIRED = 0;
    int PROPAGATION_SUPPORTS = 1;
    int PROPAGATION_MANDATORY = 2;
    int PROPAGATION_REQUIRES_NEW = 3;
    int PROPAGATION_NOT_SUPPORTED = 4;
    int PROPAGATION_NEVER = 5;
    int PROPAGATION_NESTED = 6;
    ...
}
```

```java
//  事务传播行为对应的枚举
public enum Propagation {
    REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),
    SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS),
    MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),
    REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),
    NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),
    NEVER(TransactionDefinition.PROPAGATION_NEVER),
    NESTED(TransactionDefinition.PROPAGATION_NESTED);

    private final int value;

    Propagation(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
```

|         传播行为          |     简写      |                             说明                             |
| :-----------------------: | :-----------: | :----------------------------------------------------------: |
|   PROPAGATION_REQUIRED    |   required    | 如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。 |
|   PROPAGATION_SUPPORTS    |   supports    |   表示支持当前事务，如果当前没有事务，就以无事务方式执行。   |
|   PROPAGATION_MANDATORY   |   mandatory   |      表示使用当前的事务，如果当前没有事务，就抛出异常。      |
| PROPAGATION_REQUIRES_NEW  | required_new  |       表示新建事务，如果当前存在事务，把当前事务挂起。       |
| PROPAGATION_NOT_SUPPORTED | not_supported | 表示以无事务方式执行操作，如果当前存在事务，就把当前事务挂起。 |
|     PROPAGATION_NEVER     |     never     |     表示以无事务方式执行，如果当前存在事务，则抛出异常。     |
|    PROPAGATION_NESTED     |    nested     | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |

举例说明事务传播类型：

```java
StudentServiceImplA implements StudentService{
    @Autowired
    studentDao;
    
    @Autowired
    studentService;
    
    @Transactional(required) // 事务传播特性为 requried: 如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。
    insertA(){
        // 对数据库操作，插入 A 数据
        studentService.insertB(); // 插入 B 数据
        // I/O 异常
    }
}
```

```java
StudentServiceImplB implements StudentService{
    @Autowired
    studentDao;
    
    @Transactional
    insertB(){
        // 对数据库操作，插入 B 数据
    }
}
```

分以下几种情况讨论：

- 若 StudentServiceImplB 中 insertB() 中传播类型是 required，数据库中既没有 A 数据，也没有 B 数据，则 insertA() 和 insertB() 属于同一个事务，发生异常，事务回滚即可。
- 若 StudentServiceImplB 中 insertB() 中传播类型是 required_new，数据库中没有 A 数据，但是有 B 数据，则 insertB() 创建了一个新事务，insertA() 中发生异常，该事务回滚。
- 若 StudentServiceImplB 中 insertB() 中传播类型是 not_supported，数据库中没有 A 数据，但是有 B 数据，则 insertB() 以非事务方式执行，执行 insertA() 的事务回滚，insertB() 中不会发生回滚

**注意：insertB() 为何要放入 StudentServiceImplB 中？**

Spring 的事务机制是基于 AOP 代理实现的。如果在 StudentServiceImplA 中使用 insertB() ，insertA() 中在调用 insertB() 是通过当前对象来调用 insertB() 的，而不是通过代理来调用  insertB() 的，此时  insertB() 上加**事务注解就失效了**。

### 事务超时属性

事务超时指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。在 TransactionDefinition 中以 int 的值来表示超时时间，其单位是秒，默认值为 -1。

### 事务只读属性

对于只有读取数据查询的事务，可以指定事务类型为 readonly，即只读事务。

只读事务不涉及数据的修改，数据库会提供一些优化手段，适合用在有多条数据库查询操作的方法中。

### 事务回滚规则

默认情况下，事务只有遇到运行期异常时才会回滚，Error 也会导致事务回滚，但遇到检查型异常时不会回滚。



## @Transactional 注解

@Transactional 注解可以使用在类上，表明该注解对该类中所有的 public 方法都生效。也可以使用在方法上，但是注解只能应用到 public 方法上，否则不生效。

### 源码解析

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

    @AliasFor("transactionManager")
    String value() default "";

    @AliasFor("value")
    String transactionManager() default "";

    // 事务的传播行为，默认值为 REQUIRED
    Propagation propagation() default Propagation.REQUIRED;

    // 事务隔离级别，默认值为 DEFAULT
    Isolation isolation() default Isolation.DEFAULT;

    // 事务的超时时间，默认值为-1，即不会超时
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    // 指定事务是否为只读事务，默认值为 false
    boolean readOnly() default false;

    // 用于指定能够触发事务回滚的异常类型
    // Class<? extends Throwable>[] 说明可以指定多个异常类型
    Class<? extends Throwable>[] rollbackFor() default {};

    String[] rollbackForClassName() default {};

    Class<? extends Throwable>[] noRollbackFor() default {};

    String[] noRollbackForClassName() default {};

}
```

### 工作机制

@Transactional 的工作机制是基于 Spring AOP 实现的。

如果一个类或者一个类中的 public 方法被标注 @Transactional 注解的话，Spring 容器就会在启动的时候为其创建一个代理类，在调用被 @Transactional 注解的 public 方法的时，实际调用的是 TransactionInterceptor  类中的 invoke() 方法。invoke() 方法会在目标方法之前开启事务，方法执行过程中如果遇到异常的时候回滚事务，方法调用完成后提交事务。

### 事务失效问题

#### 1. 同一个类中方法调用

在同一个类中的其他没有被 @Transactional 注解的方法内部调用 @Transactional 注解的方法，则 @Transactional 注解的方法的事务会失效。

这是因为Spring AOP 代理造成的，因为只有当 @Transactional`注解的方法在类以外被调用的时候，Spring 事务管理才生效。

```java
@Service
public class StudentService {

    private void insertA() {
        // insertA() 中调用 insertB() 是通过当前对象来调用 insertB() 的，而不是通过代理来调用 insertB() 的，此时insertB()上加事务注解就失效了。
        insertB();
        // ...
    }

    // 事务注解失效
    @Transactional
    public void insertB() {
        // ... 
    }
}Copy to clipboardErrorCopied
```

可以采用以下 2 种方式解决事务失效问题：

- 避免同一类中自调用
- 使用 AspectJ 取代 Spring AOP 代理

#### 2. try-catch 导致事务失效

```java
StudentServiceImplA implements StudentService{
    @Autowired
    studentDao;

    @Autowired
    studentService;

    @Transactional
    insertA(){
        try {
           // 对数据库操作，插入 A 数据
           studentService.insertB(); // 插入 B 数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

如果 insertB() 内部抛了异常，被 insertA() 方法 catch 了该异常，则该事务不能正常回滚。

这是因为当 ServiceB 中抛出异常后，ServiceB 标识当前事务需要 rollback 。但是ServiceA 中由于捕获这个异常并进行处理，ServiceA 认为当前事务应该正常commit ，会抛出UnexpectedRollbackException 异常。

Spring 的事务是在调用业务方法之前开始的，业务方法执行完毕之后才执行 commit 或者 rollback，事务是否执行取决于是否抛出运行时异常。如果抛出运行时异常并在业务方法中没有 catch 到的话，事务会回滚。

解决该事务失效问题：

在业务方法中一般不需要 catch 异常，如果非要 catch 则一定要抛出运行时异常，或者注解中指定抛异常类 @Transactional(rollbackFor=Exception.class)。

#### 3. 数据库引擎不支持事务

事务能否生效数据库引擎是否支持事务是关键。

常用的 MySQL 数据库默认使用支持事务的InnoDB 存储引擎。如果数据库引擎切换成不支持事务的 MyIsam，那么事务就从根本上失效了。

### 使用注意事项

- 正确的设置 @Transactional 的 rollbackFor 和 propagation 属性，否则事务可能会回滚失败
- @Transactional 注解只有作用到 public 方法上事务才生效，不推荐在接口上使用
- 避免同一个类中调用 @Transactional 注解的方法，这样会导致事务失效
