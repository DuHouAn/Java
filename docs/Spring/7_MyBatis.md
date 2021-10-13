# Mybatis 基本原理

MyBatis 是 Apache 的一个 Java 开源项目，是一款优秀的持久层框架。

支持定制化 SQL、存储过程以及高级映射。Mybatis 可将 SQL 语句配置在 XML 文件中，避免将 SQL 语句硬编码在 Java 类中。MyBatis 具有如下特点：

- 通过参数映射方式，可以将参数灵活的配置在 SQL 语句中的配置文件中，避免在 Java 类中配置参数（JDBC）
- 通过输出映射机制，将结果集的检索自动映射成相应的 Java 对象，避免对结果集手工检索（JDBC）
- Mybatis 通过 XML 配置文件对数据库连接进行管理

## 核心类

### SqlSession

SqlSession 相当于一个会话，每次访问数据库都需要这样一个会话，类似 JDBC 中的 Connection，但是现在几乎所有的连接都是使用的连接池技术，用完后直接归还而不会像 SqlSession 一样销毁。

注意 SqlSession 不是线程安全的，应该设置为线程私有。每次创建的 SqlSession 都必须及时关闭它，如果 SQLSession 长期存在就会使数据库连接池的活动资源减少，对系统性能的影响很大，可以在 finally 代码块中将其关闭。此外 SqlSession 存活于一个应用的请求和操作，可以执行多条 SQL语句，保证事务的一致性。

SqlSession 在执行过程中，包括以下对象：

- **Executor**

  即执行器，负责调度 StatementHandler、ParameterHandler、ResultSetHandler 等来执行对应的 SQL 语句。

- **StatementHandler**

  使用数据库的 Statement（PreparedStatement）执行操作。

- **ParamentHandler**

  来处理 SQL 参数。

- **ResultSetHandler**

  封装并处理数据集。

### SqlSessionFactory

SqlSessionFactory 用于创建 SqlSession。每次应用程序访问数据库时, 需要通过 SqlSessionFactory 创建 SqlSession，显然 SqlSessionFactory 和整个 Mybatis 的生命周期是相同的。

SqlSessionFactory 是线程安全的，应用运行迁建不要重复创建多次，因为创建多个可能为消耗尽数据库的连接资源，建议使用单例模式。

### SqlSessionaFactoryBuilder

SqlSessionaFactoryBuilder 用于创建 SqlSessionFactory，该类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域。

### Mapper

Mapper 接口的实例是从 SqlSession 中获取，作用是发送 SQL，然后返回我们需要的结果，或者执行 SQL 从而更改数据库的数据，因此它应该在 SqlSession 的事务方法之内。

在 Spring 管理的 Bean 中, Mapper 是单例的。

## 功能架构

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/mybatis_1.png"/></div>

### 接口层

提供给外部使用的接口 API，开发人员通过这些本地 API 来操作数据库。

接口层一接收到调用请求就会调用数据处理层来完成具体的数据处理。

### 数据处理层

负责具体的SQL查找、SQL解析、SQL执行和执行结果映射处理等。它主要功能是根据调用的请求完成一次数据库操作。

- 加载配置：配置来源有配置文件或者注解，将 SQL 的配置信息加载成为一个个 MappedStatement 对象（包括了传入参数映射配置、执行的 SQL 语句、结果映射配置），存储在内存中。
- SQL解析：当 API 接口层接收到调用请求时，会接收到传入 SQL 的 ID 和传入对象（可以是 Map、JavaBean 或者基本数据类型），Mybatis 会根据 SQL 的 ID 找到对应的 MappedStatement，然后根据传入参数对象对MappedStatement 进行解析，解析后可以得到最终要执行的 SQL 语句和参数。
- SQL执行：将最终得到的 SQL 和参数拿到数据库进行执行，得到操作数据库的结果。
- 结果映射：将操作数据库的结果按照映射的配置进行转换，可以转换成HashMap、JavaBean 或者基本数据类型，并将最终结果返回。

### 基础支撑层

负责最基础的功能支撑，包括连接管理、事务管理、配置加载和缓存处理，这些都是共用的东西，将他们抽取出来作为最基础的组件。为上层的数据处理层提供最基础的支撑。

## 执行流程

- Mybatis 配置

  sqlMapConfig.xml 文件是 Mybatis 的全局配置文件，配置了 Mybatis的运行环境等信息。Mapper.xml 文件即 SQL 映射文件，文件中配置了操作数据库的 SQL 语句，需要在 sqlMapConfig.xml 中加载。

- 通过 Mybatis 环境等配置信息构造 SqlSessionFactory

- 通过 SqlSessionFactory 创建 SqlSession，操作数据库需要通过 SqlSession进行。

- Mybatis 自定义了 Executor 执行器操作数据库，Executor 接口有两个实现，一个是基本执行器、一个是缓存执行器。

- MappedStatement  是 Mybatis 一个底层封装对象，包装 Mybatis 配置信息及 SQL 映射信息等。Mapper.xml 文件中一个 SQL 对应一个 MappedStatement对象，SQL 的 id 即 MappedStatement 的 id。

- MappedStatement 对 SQL 执行输入参数进行定义，包括 HashMap、基本类型、pojo，Executor 通过MappedStatement 在执行 SQL 前将输入的 Java 对象映射至 SQL 中，输入参数映射就是 jdbc 编程中对preparedStatement 设置参数。

- MappedStatement 对 SQL 执行输出结果进行定义，包括 HashMap、基本类型、pojo，Executor 通过MappedStatement 在执行 SQL 后将输出结果映射至 Java对象中，输出结果映射过程相当于 jdbc 编程中对结果的解析处理过程。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/mybatis_2.png"/></div>

## 缓存机制

MyBatis 的缓存分为一级缓存和二级缓存。默认情况下一级缓存是开启的。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/mybatis_3.png"/></div>

### 一级缓存

一级缓存指 SqlSession 级别的缓存，在同一个 SqlSession 中执行相同的 SQL 语句查询时将查询结果集缓存。一级缓存最多能缓存 1024 条 SQL 语句。

当客户端第一次发出一个 SQL 查询语句时，MyBatis 执行 SQL 查询并将查询结果写入 SqlSession 的一级缓存，当第二次有相同的 SQL 查询语句时，则直接从缓存中获取。当同一个 SqlSession 多次发出相同的 SQL 查询语句时，MyBatis 直接从缓存中获取数据。如果两次查询中出现 commit 操作（新增、删除、修改），则认为数据发生了变化，Mybaits 会把该 SqlSession 中的一级缓存区域全部清空，当下次再到缓存中查找时将找不到对应的缓存数据，因此需要再次从数据库中查询数据并将查询的结果写入缓存。

### 二级缓存

二级缓存指跨 SqlSession 的缓存，即 Mapper 级别的缓存。在 Mapper 级别的缓存内，不同的 SqlSession 缓存可以共享。

Mapper 以命名空间为单位创建缓存数据结构，数据结构是 Map 类型，Map 中 Key 为`MapperId + Offset + Limit + SQL + 所有入参`。开启二级缓存后，会使用 CachingExecutor 装饰 Executor ，在进入一级缓存的查询流程前，先在 CachingExecutor 进行二级缓存的查询。

开启二级缓存需要做一下配置：

- 在全局配置中启用二级缓存配置。
- 在对应的 Mappper.xml 中配置 Cache 节点。
- 在对应的 select 查询节点中添加 userCache=true。

# MyBatis 面试题

- [MyBatis 面试题集锦](https://snailclimb.gitee.io/javaguide/#/docs/system-design/framework/mybatis/mybatis-interview)
- [Mybatis 的常见面试题](https://blog.csdn.net/eaphyy/article/details/71190441)
