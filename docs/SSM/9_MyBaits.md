# MyBatis

> MyBatis 中使用 # 和使用 $ 的区别？ 

- #{xxx} 语句拼接使用的是 PreparedStatement，会有类型转换，比较安全。

  简单讲，#{xxx} 是经过预编译的是安全的

- ${xxx}未经过预编译，仅仅是变量的值，是不安全的，可能会**存在 SQL 注入**的风险。

学习资料：

https://blog.csdn.net/lj1314ailj/article/details/79712305

https://blog.csdn.net/lj1314ailj/article/details/79813840

https://blog.csdn.net/wei_li_2015/article/details/80832806



总纲：

https://snailclimb.gitee.io/javaguide/#/docs/system-design/framework/mybatis/mybatis-interview
