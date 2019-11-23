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



### 核心概念





![1574044351197](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574044351197.png)





![1574044966299](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574044966299.png)





![1574045181833](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045181833.png)



![1574045266808](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045266808.png)

![1574045345331](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045345331.png)

![1574045528395](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045528395.png)

![1574045635960](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045635960.png)



![1574045733769](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045733769.png)



![1574044393028](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574044393028.png)

![1574045701316](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045701316.png)



![1574045865612](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574045865612.png)



![1574044458688](C:\Users\DHA\AppData\Roaming\Typora\typora-user-images\1574044458688.png)

