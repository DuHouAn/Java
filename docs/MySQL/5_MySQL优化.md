# MySQL 优化

## SQL 语句优化

### 查询优化

1. 先开启慢查询日志 

```sql
set global slow_query_log = on # 开启慢查询日志，默认是关闭的
set global long_qurey_time=0.5 # 设置慢查询时间阈值，单位：秒
```

2. 定位慢查询语句

3. explain 进行分析，相应字段：

   | 字段  |                             说明                             |
   | :---: | :----------------------------------------------------------: |
   |  id   |           id 值越大，越先执行<br/>无子查询时，id=1           |
   | type  | NULL，执行时甚至不用访问表或索引<br/>eq_ref，使用的是唯一索引<br/>ref，使用的是非唯一索引或者是唯一索引的前缀<br/>index，类似全表扫描，按照索引次序扫描表 |
   | rows  |                         表扫描的行数                         |
   |  key  |              实际用到的索引，为空表没有用到索引              |
   | extra |  十分重要的额外信息<br/>using filesort<br/>using temporary   |

   4. 优化 SQL

   - 将子查询转换为 join 操作
   - 使用 MAX(列)，为该列建立索引
   - 数据值不要为 NULL
   - 根据选择度建索引
   - like 进行模糊查询，第一个位置不要使用 '%'



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

措施一：使用覆盖索引

```sql
SELECT id
FROM product
LIMIT 1000000,20
# id 是主键
```

措施二：使用 id 进行过滤

```sql
SELECT * 
FROM product
WHERE id>=(SELECT id FROM product LIMIT 1000000,1)
LIMIT 20;
```

措施三：使用 jion 

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
