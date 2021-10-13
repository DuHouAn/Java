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

参考：https://hadyang.com/interview/docs/fromwork/mybatis/cache/

## 动态代理

参考：https://hadyang.com/interview/docs/fromwork/mybatis/proxy/

# MyBatis 面试题

- [MyBatis 面试题集锦](https://snailclimb.gitee.io/javaguide/#/docs/system-design/framework/mybatis/mybatis-interview)
- [Mybatis 的常见面试题](https://blog.csdn.net/eaphyy/article/details/71190441)