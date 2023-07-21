# MySQL 调优

## SQL 语句优化

### 查询优化

#### 1. Explain 分析

- 先开启慢查询日志 

```sql
set global slow_query_log = on # 开启慢查询日志，默认是关闭的
set global long_qurey_time=0.5 # 设置慢查询时间阈值，单位：秒
```

- 定位慢查询语句
- explain 进行分析，相应字段：
  - id：id 值越大，越先执行。无子查询时，id=1
  - type：
    - NULL，执行时甚至不用访问表或索引
    - eq_ref，使用的是唯一索引
    - ref，使用的是非唯一索引或者是唯一索引的前缀
    - index，类似全表扫描，按照索引次序扫描表
  - rows：表扫描的行数
  - key：实际用到的索引，为空表没有用到索引
  - extra：十分重要的额外信息
    - using filesort
    - using temporary

#### 2.  优化数据访问

**减少请求的数据量**

- 只返回必要的列：最好不要使用 SELECT * 语句。
- 只返回必要的行：使用 LIMIT 语句来限制返回的数据。
- 缓存重复查询的数据：使用缓存可以避免在数据库中进行查询，特别在要查询的数据经常被重复查询时，缓存带来的查询性能提升将会是非常明显的。

**减少服务器端扫描的行数**

最有效的方式是使用索引来覆盖查询。

#### 3. 重构查询方式

**切分大查询**

一个大查询如果一次性执行的话，可能一次锁住很多数据、占满整个事务日志、耗尽系统资源、阻塞很多小的但重要的查询。

**分解大连接查询**

将一个大连接查询分解成对每一个表进行一次单表查询，然后在应用程序中进行关联，这样做的好处有：

- 让缓存更高效。对于连接查询，如果其中一个表发生变化，那么整个查询缓存就无法使用。而分解后的多个查询，即使其中一个表发生变化，对其它表的查询缓存依然可以使用。

- 分解成多个单表查询，这些单表查询的缓存结果更可能被其它查询使用到，从而减少冗余记录的查询。

- 减少锁竞争；

- 在应用层进行连接，可以更容易对数据库进行拆分，从而更容易做到高性能和可伸缩。

- 查询本身效率也可能会有所提升。例如下面的例子中，使用 IN() 代替连接查询，可以让 MySQL 按照 ID 顺序进行查询，这可能比随机的连接要更高效。

  ```sql
  SELECT * FROM tag
  JOIN tag_post ON tag_post.tag_id=tag.id
  JOIN post ON tag_post.post_id=post.id
  WHERE tag.tag='mysql';
  ```

  ```sql
  SELECT * FROM tag WHERE tag='mysql';
  SELECT * FROM tag_post WHERE tag_id=1234;
  SELECT * FROM post WHERE post.id IN (123,456,567,9098,8904);
  ```

### limit 优化

```sql
limit m,n 
-- m 表示从第 (m+1) 条记录开始检索
-- n 表示取出 n 条数据
```

当一个数据库过于庞大，SQL 查询语句会比较慢。

```sql
SELECT * 
FROM product
LIMIT 1000000,20
```

优化措施：

#### 1. 使用覆盖索引

```sql
SELECT id
FROM product
LIMIT 1000000,20
# id 是主键
```

#### 2. 使用 id 进行过滤

```sql
SELECT * 
FROM product
WHERE id>=(SELECT id FROM product LIMIT 1000000,1)
LIMIT 20;
```

#### 3. 使用 jion 

```sql
SELECT *
FROM product a
JION (SELECT id FROM product LIMIT 1000000,20) b
ON a.id=b.id;
```



## 数据库设计优化

- 可以适当地违反第三范式（3NF）,减少 join 操作

- 设计一些中间表

- 对表格进行垂直拆分

  不常用的字段单独放在一个表中；常用的字段单独放在一个表中；一些大字段单独放在一个表中。

- 对表格进行水平拆分

  水平切分是将同一个表中的记录拆分到多个结构相同的表中。

  常用的水平拆分策略：

  - 哈希取模：hash(key) % N
  - 范围：可以是 ID 范围也可以是时间范围

- 主键尽量用自增



## 数据库参数配置

最主要是调整内存：show status 确定内存大小

- 关闭一些不必要的二进制文件
- 增加 MySQL 的最大连接数
- 删除大量数据行后，使用 OPTIMIZE TABLE 命令进行**碎片整理**



## 切分







