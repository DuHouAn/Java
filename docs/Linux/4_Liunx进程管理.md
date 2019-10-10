# 进程管理

## 查看进程

### 1. ps

查看某个时间点的进程信息

示例一：查看自己的进程

```sh
# ps -l
```

示例二：查看系统所有进程

```sh
# ps aux
```

示例三：查看特定的进程

```sh
# ps aux | grep threadx
```

### 2. pstree

查看进程树

示例：查看所有进程树

```sh
# pstree -A
```

### 3. top

实时显示进程信息

示例：两秒钟刷新一次

```sh
# top -d 2
```

### 4. netstat

查看占用端口的进程

示例：查看特定端口的进程

```sh
# netstat -anp | grep port
```



## 杀死进程

### kill

杀死进程。

```sh
# kill -9 pid
# -9 表示强制终止
# 注意：先用 ps 查找进程，然后用 kill 杀掉
```