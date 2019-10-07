# MyBatis

> MyBatis 中使用 # 和使用 $ 的区别？ 

- #{xxx} 语句拼接使用的是 PreparedStatement，会有类型转换，比较安全。

  简单讲，#{xxx} 是经过预编译的是安全的

- ${xxx}未经过预编译，仅仅是变量的值，是不安全的，可能会**存在 SQL 注入**的风险。
