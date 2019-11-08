# Spring 事务管理

## 编程式事务和声明式事务

### 编程式事务

编程式事务：指的是通过编码方式实现事务，类似 JDBC 编程实现事务管理，比如 jdbcTemplate、TransactionalTemplate。

### 声明式事务

声明式事务实现方式：

- XML 实现
- @Transactional 注解实现

### 比较

编程式事务：

- 优点：显示调用，不易出错
- 缺点：侵入式代码，编码量大

声明式事务：

- 优点：简介，对代码入侵小
- 缺点：隐藏实现细节，出错不易定位

## Spring 事务管理

Spring 事务的本质是**基于数据库对事务的支持**，没有数据库对事务的支持，Spring 本身无法提供事务管理功能。

Spring 事务通过 AOP 动态代理来是实现。

通常先在配置文件中**开启事务**，然后通过 xml 文件或者注解**配置执行方法**，在调用方法时，Spring 会自动生成代理，在调用前设置事务操作，调用方法后进行事务回滚或者提交操作。

## Spring 中事务隔离级别

事务隔离级别是指多个事务之间的隔离程度。

TransactionDefinition 接口中定义了五个表示隔离级别的常量：

|                隔离级别                |                             说明                             |
| :------------------------------------: | :----------------------------------------------------------: |
|       ISOLATION_DEFAULT（默认）        |        这是默认值，表示使用底层数据库的默认隔离级别。        |
| ISOLATION_READ_UNCOMMITTED（读未提交） | 该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据，该级别不能防止脏读和不可重复读，因此很少使用该隔离级别 |
|  ISOLATION_READ_COMMITTED（读可提交）  | 该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值 |
| ISOLATION_REPEATABLE_READ（可重复读）  | 该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读 |
|   ISOLATION_SERIALIZABLE（可串行化）   | 所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是，这将严重影响程序的性能，通常情况下也不会用到该级别 |



## Spring 七种事务传播类型

- **PROPAGATION_REQUIRED（required）**

  如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。

- PROPAGATION_SUPPORTS（support）

    表示支持当前事务，如果当前没有事务，就以无事务方式执行。

- PROPAGATION_MANDATORY（mandatory）

  表示使用当前的事务，如果当前没有事务，就抛出异常。

- **PROPAGATION_REQUIRES_NEW（required_new）**

  表示新建事务，如果当前存在事务，把当前事务挂起。

- **PROPAGATION_NOT_SUPPORTED（not_support）**

  表示以无事务方式执行操作，如果当前存在事务，就把当前事务挂起。

- PROPAGATION_NEVER（never）

  表示以无事务方式执行，如果当前存在事务，则抛出异常。

- PROPAGATION_NESTED（nested）

  如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。

举例说明事务传播类型：

- StudentServiceImplA

```java
StudentServiceImplA implements StudentService{
    @Autowired
    studentDao;
    
    @Autowired
    studentService;
    
    @Transactional(required) //事务传播特性为 requried
    insertA(){
        //...对数据库操作，插入 A 数据
        
        studentService.insertB();// 插入 B 数据
        
        //..i/o 异常
    }
}
```

- StudentServiceImplB

```java
StudentServiceImplB implements StudentService{
    @Autowired
    studentDao;
    
    @Transactional
    insertB(){
        
        //...对数据库操作，插入 B 数据
    
    }
}
```

StudentServiceImplB 中 insertB() 中传播类型如果是 required，数据库中既没有 A 数据，也没有 B 数据；

（insertA() 和 insertB() 属于同一个事务，发生异常，事务回滚）

StudentServiceImplB 中 insertB() 中传播类型如果是 required_new，数据库中没有 A 数据，但是有 B 数据；

（insertB() 创建了一个新事务，insertA() 中发生异常，事务回滚）

StudentServiceImplB 中 insertB() 中传播类型如果是 not_supported，数据库中没有 A 数据，但是有 B 数据

（insertB() 以非事务方式只想，执行 insertA() 的事务回滚，insertB()中不会发生回滚）



注意：insertB() 为何要放入 StudentServiceImplB 中？

Spring 的事务机制是使用 AOP  代理实现的。

如果在 StudentServiceImplA 中使用 insertB() ，insertA() 中在调用 insertB() 是通过当前对象来调用 doB() 的，而不是通过代理来调用 doB() 的，此时 doB() 上加事务注解就失效了。
