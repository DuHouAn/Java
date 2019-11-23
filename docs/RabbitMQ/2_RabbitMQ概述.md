# RabbitMQ 概述

## 简介

RabbitMQ 是一个开源的消息代理和队列服务器，用来通过普通协议在完全不同的应用中间共享数据，RabbitMQ 是使用 **Erlang 语言**来编写的，并且 RabbitMQ 是基于 AMQP 协议的。

特点：

- 开源、性能优秀，稳定性好
- 提供可靠性消息投递模式（confirm）、返回模式（return）
- 与 SpringAOP 完美的整合、API 丰富
- 集群模式丰富，表达式配置，HA 模式，镜像队列模型
- 保证数据不丢失的前提做到高可靠性、可用性

RabbitMQ 高性能的原因：

**Erlang 语言**最初用在交换机的架构模式，这样使得 RabbitMQ 在 Broker 之间进行数据交互的性能时非常优秀的。Erlang 的优点：Erlang 有着和原生 Socket 一样的延迟。

## AMQP 协议

AMQP（Advanced Message Queuing Protocol）协议，即高级消息队列协议。

AMQP 是具有现在特征的二进制协议。是一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。

### 协议模型

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_1.png" width="600px"/></div>

### 核心概念

- Server

  又称 Broker，接受客户端的连接，实现 AMQP 实体服务

- Connection

  连接，应用程序与 Broker 的网络连接

- Channel

  网络通信，几乎所有的操作都在 Channel 中进行，Channel 是进行消息读写的通道。客户端可建立多个 Channel，每个 Channel 代表一个会话任务。

- Message

  消息，服务器和应用程序之间传送的数据，由 Properties 和 Body 组成。Properties 可以对消息进行设置，比如消息的优先级、延迟等高级特性；Body 则就是消息体内容。

- Virtual Host

  虚拟主机，用于进行逻辑隔离，最上层的消息路由。

  一个 Virtual Host 里面可以有若干个 Exchange 和 Queue，同一个 Virtual Host 里面不能有相同名称的 Exchange 或 Queue。

- Exchange

  交换机，接受消息，根据路由键转发消息到绑定的队列。

- Binding

  Exchange 和 Queue 之间的虚拟连接，Binding 中可以包含 Routing Key

- Routing Key

  一个路由规则，虚拟机可用它来确定如何路由一个特定消息

- Queue

  也称为 Message Queue（消息队列），保存消息并将它们转发给消费者



## RabbitMQ 整体架构

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_2.png" width="600px"/></div>



## RabbitMQ 消息流转

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_3.png" width="600px"/></div>

## RabbitMQ 简单使用

### 1. 启动服务器

```html
rabbitmq-server start &
```

### 2. 停止服务器

```html
rabbitmqctl stop_app
```

### 3. 插件管理

```html
rabbitmq-plugins enable rabbitmq_management
```

### 4. 查看管控台

```html
http://localhost:15672/

# 用户名 guest
# 密码 guest
```



## 命令行基础操作

### 1. 应用

- 关闭应用

  ```html
  rabbitmqctl stop_app
  ```

- 启动应用

  ```html
  rabbitmqctl start_app
  ```
- 查看节点状态

  ```html
  rabbitmqctl status
  ```

### 2. 用户

- 添加用户

  ```html
  rabbitmqctl add_user username password
  ```

- 删除用户

  ```html
  rabbitmqctl delete_user username
  ```

- 列出所有用户

  ```html
  rabbitmqctl list_users
  ```

- 清除用户权限

  ```html
  rabbitmqctl clear_permissions -p vhostpath username
  ```

- 列出用户权限

  ```html
  rabbitmqctl list_user_permissions username
  ```

- 修改密码

  ```html
  rabbitmqctl change_password username newpassword
  ```

- 设置用户权限

  ```html
  rabbitmqctl set_permissions -p vhostpath username ".*" ".*" ".*"
  ```

### 3. 虚拟主机

- 创建虚拟主机

  ```html
  rabbitmqctl add_vhost vhostpath
  ```

- 删除虚拟主机

  ```html
  rabbitmqctl delete_vhost vhostpath
  ```

- 列出所有虚拟主机

  ```html
  rabbitmqctl list_vhosts
  ```

- 列出虚拟主机上所有权限

  ```html
  rabbitmqctl list_permissions -p vhostpath
  ```

### 4. 队列

- 查看所有队列信息

  ```html
  rabbitmqctl list_queues
  ```

- 清除队列里的消息

  ```html
  rabbitmqctl -p vhostpath purge_queue blue
  ```



## 命令行高级操作

- 移除所有数据

  ```html
  rabbitmqctl reset
  # 要在 rabbitmqctl stop_app 之后使用
  ```

- 组成集群命令

  ```html
  rabbitmqctl join_cluster <clusternode> [--ram]
  ```

- 查看集群状态

  ```html
  rabbitmq cluster_status
  ```

- 修改集群节点的存储形式

  ```html
  rabbitmqctl change_cluser_node_type disc | ram
  ```

- 摘除节点（忘记节点）

  ```html
  rabbitmqctl forget_cluster_node [--offline]
  ```

- 修改节点名称

  ```html
  rabbitmqctl rename_cluster_node oldnode1 newnode1 [oldnode2] [newnode2]
  ```
