# 一、Rabbit 概述

RabbitMQ 是一个开源的消息代理和队列服务器，用来通过普通协议在完全不同的应用中间共享数据，RabbitMQ 是使用 **Erlang 语言**来编写的，并且 RabbitMQ 是基于 AMQP 协议的。

特点：

- 开源、性能优秀

  **Erlang 语言**最初用在交换机的架构模式，这样使得 RabbitMQ 在 Broker 之间进行数据交互的性能时非常优秀的。Erlang 的优点：Erlang 有着和原生 Socket 一样的延迟。

- 可靠性

  提供可靠性消息投递模式（confirm）、返回模式（return）。

- 扩展性

  多个RabbitMQ 节点可以组成一个集群，也可以根据实际业务情况动态地扩展集群中节点。

- 与 SpringAOP 完美的整合、API 丰富
- 保证数据不丢失的前提做到高可靠性、可用性

# 二、AMQP 协议

AMQP (Advanced Message Queuing Protocol) 即高级消息队列协议，是一个进程间传递**异步消息**的**网络协议**。

## AMQP 模型

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_12.png"/></div>

工作过程如下：首先发布者（Publisher）发布消息（Message），经由交换机 Exchange。交换机根据**路由规则**将收到的消息分发给与该交换机绑定的 Queue。最后 AMQP 代理会将消息投递给订阅了此队列的消费者，或者消费者按照需求自行获取。

关于 AMQP 模型的几点说明：

- 发布者、交换机、队列、消费者都可以有多个。AMQP 是一个网络协议，所以这个过程中的发布者，消费者，消息代理可以分别存在于不同的设备上。
- 布者发布消息时可以给消息指定各种消息属性（Message Meta-data）。有些属性有可能会被消息代理（Brokers）使用，然而其他的属性则是完全不透明的，它们只能被接收消息的应用所使用。
- 从安全角度考虑，网络是不可靠的，又或是消费者在处理消息的过程中意外挂掉，这样没有处理成功的消息就会丢失。基于此原因，AMQP 模块包含了一个消息确认机制：当一个消息从队列中投递给消费者后，不会立即从队列中删除，直到它收到来自消费者的确认回执（Acknowledgement）后，才完全从队列中删除。
- 在某些情况下，例如当一个消息无法被成功路由时（无法从交换机分发到队列），消息或许会被返回给发布者并被丢弃。或者，如果消息代理执行了**延期操作**，消息会被放入一个**死信队列**中。此时，消息发布者可以选择某些参数来处理这些特殊情况。

## Producer & Consumer 

消息生产者（Producer），向 Broker 发送消息的客户端。

消息消费者（Consumer），从 Broker 消费消息的客户端。

## Broker

一个 RabbitMQ Broker 可以简单地看作一个 RabbitMQ 服务节点，或者 RabbitMQ 服务实例。大多数情况下可以将一个 RabbitMQ Broker 看作一台 RabbitMQ 服务器。

## Exchange

Exchange 即交换器，是用来发送消息的 AMQP 实体。Exchange 拿到一个消息之后将它**路由**给一个或零个队列。Exchange 使用哪种路由算法是由**交换机类型**和**绑定（Bindings）规则**所决定的。

### Binding

**Producer 将消息发给 Exchange 时，一般会指定一个 RoutingKey (路由键)，用来指定这个消息的路由规则，而这个 RoutingKey 需要与交换器类型和 BindingKey (绑定键) 联合使用才能最终生效**。

RabbitMQ 中通过 **Binding (绑定)**  将 Exchange 与 Queue(消息队列) 关联起来，在绑定时一般会指定一个 BindingKey，这样 RabbitMQ 就知道如何正确将消息路由到 Queue 中。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则。

生产者将消息发送给交换器，当 BindingKey 和 RoutingKey 相匹配时，消息会被路由到对应的队列中。注意BindingKey 并不是在所有的情况下都生效，它依赖于交换器类型，比如 fanout 类型的交换器就会无视，而是将消息路由到所有绑定到该交换器的队列中。

### Exchange 类型

Exchange 有以下 4 种类型，不同的类型对应着不同的路由策略：

#### direct

Exchange 默认类型。**路由规则是把消息路由到 Bindingkey 与 RoutingKey 完全匹配的 Queue 中**。direct 类型常用在**处理有优先级的任务**，根据任务的优先级把消息发送到对应的队列，这样可以指派更多的资源去处理高优先级的队列。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_13.png"/></div>

以上图为例，如果发送消息的时候 RoutingKey="booking"，那么消息会路由到 Queue1 和 Queue2。如果在发送消息的时候设置 RoutingKey="create" 或 "confirm"，消息只会路由到Queue2。如果以其他的 RoutingKey 发送消息，则消息不会路由到这两个队列中。

#### fanout

路由规则是把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue 中，不需要做任何判断操作，所以 fanout 类型是所有的交换机类型里面速度最快的。**fanout 类型常用来广播消息**。

#### topic

direct 类型的 Exchange 路由规则是完全匹配 BindingKey 和 RoutingKey ，但是这种严格的匹配方式在很多情况下不能满足实际业务的需求。

topic 类型的 Exchange 在匹配规则上进行了扩展，它与 direct 类型的 Exchange 相似，也是将消息路由到 BindingKey 和 RoutingKey 相匹配的队列中，但这里的匹配规则有些不同，它约定：

- RoutingKey 为一个点号`.`分隔的字符串，其中**被 `.`分隔开的每一段独立的字符串称为一个单词**；
- BindingKey 和 RoutingKey 一样也是点号 `.` 分隔的字符串;
- BindingKey 中可以存在两种特殊字符串 `*` 和 `#`，用于做模糊匹配，其中 `*`  用于匹配一个单词， `#` 用于匹配零个或多个单词。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_14.png"/></div>

以上图为例，如果发送消息的时候 RoutingKey 为

- "com.rabbitmq.client"，那么消息会路由到 Queue1 和 Queue2
- "com.hidden.client"，那么消息只会路由到 Queue2 中
- "com.hidden.demo"，那么消息只会路由到 Queue2 中
- "java.rabbitmq.demo"，那么消息只会路由到 Queue1 中
- "java.util.concurrent"，那么消息将会被丢弃或者返回给生产者，因为它没有匹配任何路由键。

#### headers

headers 类型的 Exchange 不依赖于路由键的匹配规则来路由消息，而是根据发送的消息内容中的 headers 属性进行匹配。在绑定队列和交换器时指定一组键值对，当发送消息到交换器时，RabbitMQ 会获取到该消息的 headers（也是一个键值对的形式)，对比其中的键值对是否完全匹配队列和交换器绑定时指定的键值对，如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers 类型的 Exchange 性能会很差，不推荐使用。

## Queue

Queue 其实是 Message Queue 即消息队列，保存消息并将它们转发给消费者。Queue 是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其消费。

**RabbitMQ 中消息只能存储在队列中**，而 Kafka 将消息存储在 **Topic** 中，即该 Topic 对应的 Partition 中。RabbitMQ 的生产者生产消息并最终投递到队列中，消费者可以从队列中获取消息并消费。

当多个消费者订阅同一个队列时，队列中的消息会被平均分摊（Round-Robin，即轮询）给多个消费者进行处理，而不是每个消费者都收到所有的消息并处理，这样避免消息被重复消费。

### 队列属性

Queue 跟 Exchange 共享某些属性，但是队列也有一些另外的属性：

- Name
- Durable：消息代理重启后，队列依旧存在
- Exclusive：只被一个连接使用，而且当连接关闭后队列即被删除
- Auto-delete：当最后一个消费者退订后即被删除
- Arguments：一些消息代理用他来完成类似与 TTL 的某些额外功能

### 队列创建

队列在声明（declare）后才能被使用。

如果一个队列尚不存在，声明一个队列会创建它。如果声明的队列已经存在，并且属性完全相同，那么此次声明不会对原有队列产生任何影响。如果声明中的属性与已存在队列的属性有差异，那么一个错误代码为 406 的通道级异常就会被抛出。

### 队列持久化

持久化队列（Durable Queues）会被存储在磁盘上，当消息代理（Broker）重启的时候，它依旧存在。没有被持久化的队列称作暂存队列（Transient Queues）。并不是所有的场景和案例都需要将队列持久化。

持久化的队列并不会使得路由到它的消息也具有持久性。倘若消息代理挂掉了，重新启动，那么在重启的过程中持久化队列会被重新声明，无论怎样，**只有经过持久化的消息才能被重新恢复**。

## 消息机制

### 消息确认

AMQP 代理在什么时候删除消息才是正确的？AMQP 0-9-1 规范给我们两种建议：

- **自动确认模式**：当消息代理（Broker）将消息发送给应用后立即删除。（使用 AMQP 方法：basic.deliver 或 basic.get-ok）

- **显示确认模式**：待 Consumer 发送一个确认回执（acknowledgement）后再删除消息。（使用 AMQP 方法：basic.ack）

  如果一个消费者在尚未发送确认回执的情况下挂掉了，那 AMQP 代理会将消息重新投递给另一个消费者。如果当时没有可用的消费者了，消息代理会死等下一个注册到此队列的消费者，然后再次尝试投递。

### 拒绝消息

当拒绝某条消息时，应用可以告诉消息代理销毁该条消息或者重新将该条消息放入队列。

当此队列只有一个消费者时，有可能存在拒绝消息并将消息重新放入队列的行为而引起消息在同一个消费者身上无限循环的情况。

### 预取消息

在多个消费者共享一个队列时，明确指定在收到下一个确认回执前每个消费者一次可以接受多少条消息是非常有用的。这可以在试图批量发布消息的时候起到**简单的负载均衡和提高消息吞吐量**的作用。

### 消息属性

AMQP 模型中的消息（Message）对象是带有属性（Attributes）的：

| 属性                              | 说明                           |
| --------------------------------- | ------------------------------ |
| Content type                      | 内容类型                       |
| Content encoding                  | 内容编码                       |
| **Routing key**                   | 路由键                         |
| Delivery mode (persistent or not) | 投递模式（持久化 或 非持久化） |
| Message priority                  | 消息优先权                     |
| Message publishing timestamp      | 消息发布的时间戳               |
| Expiration period                 | 消息有效期                     |
| Publisher application id          | 发布应用的 ID                  |

有些属性是被 AMQP 代理所使用的，但是大多数是开放给接收它们的应用解释器用的。有些属性是可选的也被称作消息头（headers）。和 HTTP 协议的 X-Headers 很相似，消息属性需要在消息被发布的时候定义。

### 消息主体

AMQP 的消息除属性外，也含有一个有效载荷 Payload（消息实际携带的数据），它被 AMQP 代理当作不透明的字节数组来对待。

消息代理不会检查或者修改 Payload，消息可以只包含属性而不携带有效载荷，它通常会使用类似 JSON 这种序列化的格式数据。

### 消息持久化

消息能够以持久化的方式发布，AMQP 代理会将此消息存储在磁盘上。如果服务器重启，系统会确认收到的持久化消息未丢失。

简单地将消息发送给一个持久化的交换机或者路由给一个持久化的队列，并不会使得此消息具有持久化性质：它完全取决与消息本身的持久模式（persistence mode）。将消息以持久化方式发布时，会对性能造成一定的影响（就像数据库操作一样，健壮性的存在必定造成一些性能损失）。

# 三、RabbitMQ 命令行操作

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_1.png" width="600px"/></div>

## 启动 & 停止服务器

- 启动服务器

  ```html
  rabbitmq-server start &
  ```

- 停止服务器

  ```html
  rabbitmqctl stop_app
  ```

## 查看管控台

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

# 四、Rabbit MQ 入门

## 简单案例：消息生产与消费

pom.xml 配置

```html
<dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <!-- 3.6.5 是稳定版本 -->
    <version>3.6.5</version>
</dependency>
```

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>简单案例：消息生产与消费</h1>
 * 消息生产者
 * Created by DHA on 2019/11/18.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 通过 chanel 发送数据
        for(int i=0;i<10;i++){
            String data="Hello!";
            channel.basicPublish("","test001",null,data.getBytes());
        }

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
```

### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>简单案例：消息生产与消费</h1>
 * 消息生产者
 * Created by DHA on 2019/11/18.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 通过 chanel 发送数据
        for(int i=0;i<10;i++){
            String data="Hello!";
            channel.basicPublish("","test001",null,data.getBytes());
        }

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
```

## Direct Exchange

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Direct Exchange</h1>
 * 所有发送到 Direct Exchange 的消息被转发到 routing key 中指定的 Queue。
 * 消息生产者
 * Created by DHA on 2019/11/19.
 */
public class Producer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_direct_exchange";
        String routingKey = "test.direct";

        //5 通过 chanel 发送数据
        String msg = "Hello World RabbitMQ 4  Direct Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey , null , msg.getBytes());

        //6 关闭相关连接
        channel.close();
        connection.close();
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Direct Exchange</h1>
 * 所有发送到 Direct Exchange 的消息被转发到 routing key 中指定的 Queue。
 * 消息消费者
 * Created by DHA on 2019/11/19.
 */
public class Consumer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        String exchangeName="test_direct_exchange";
        String exchangeType="direct";
        String queueName="test_direct_queue";
        String routingKey="test.direct";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 绑定:将一个队列绑定到一个交换机上
        channel.queueBind(queueName,exchangeName,routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,true,queueingConsumer);

        //7 获取数据
        while(true){
            QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
```



## Topic Exchange

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Topic Exchange</h1>
 * Topic Exchange 将 routing key 与某 Topic 进行模糊匹配，此时队列需要绑定一个 Topic。
 * 消息生产者
 * Created by DHA on 2019/11/19.
 */
public class Producer4TopicExchange {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";

        //5 通过 chanel 发送数据
        String msg = "Hello World RabbitMQ 4  Topic Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2 , null , msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3 , null , msg.getBytes());

        //6 关闭相关连接
        channel.close();
        connection.close();
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Topic Exchange</h1>
 * Topic Exchange 将 routing key 与某 Topic 进行模糊匹配，此时队列需要绑定一个 Topic。
 * 消息消费者
 * Created by DHA on 2019/11/19.
 */
public class Consumer4TopicExchange {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        String exchangeName="test_topic_exchange";
        String exchangeType="topic";
        String queueName="test_topic_queue";
        String routingKey="user.*";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 绑定:将一个队列绑定到一个交换机上
        channel.queueBind(queueName,exchangeName,routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,true,queueingConsumer);

        //7 获取数据
        while(true){
            QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
```



## Fanout Exchange

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Fanout Exchange</h1>
 * Fanout Exchange 不处理 routing key，只需要简单的将队列绑定到交换机上，发送到交换机的消息都会被转发到交换机绑定的所有队列上。
 * 消息生产者
 * Created by DHA on 2019/11/19.
 */
public class Producer4FanoutExchange {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_fanout_exchange";

        //5 通过 chanel 发送数据
        for(int i = 0; i < 10; i ++) {
            String msg = "Hello World RabbitMQ 4 Fanout Exchange Message ...";
            channel.basicPublish(exchangeName, "", null , msg.getBytes());
        }

        //6 关闭相关连接
        channel.close();
        connection.close();
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> Fanout Exchange</h1>
 * Fanout Exchange 不处理 routing key，只需要简单的将队列绑定到交换机上，发送到交换机的消息都会被转发到交换机绑定的所有队列上。
 * 消息消费者
 * Created by DHA on 2019/11/19.
 */
public class Consumer4FanoutExchange {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        String exchangeName="test_fanout_exchange";
        String exchangeType="fanout";
        String queueName="test_fanout_queue";
        String routingKey="";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 绑定:将一个队列绑定到一个交换机上
        channel.queueBind(queueName,exchangeName,routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,true,queueingConsumer);

        //7 获取数据
        while(true){
            QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
```



## 设置消息属性

### 生产者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <h1>消息属性设置</h1>
 * 消息生产者
 * Created by DHA on 2019/11/18.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        // 设置自定义属性
        Map<String, Object> headers = new HashMap<>();
        headers.put("attr1", "111");
        headers.put("attr2", "222");

        //4 设置消息属性
        AMQP.BasicProperties properties=new AMQP.BasicProperties.Builder()
                .deliveryMode(2)  // 2 表示持久化的投递
                .contentEncoding("UTF-8") // 设置内容编码
                .expiration("10000") // 设置过期时间为 10 秒
                .headers(headers) // 自定义属性
                .build();

        //5 通过 chanel 发送数据
        for(int i=0;i<5;i++){
            String data="Hello!";
            channel.basicPublish("","test001",properties,data.getBytes());
        }

        //6 关闭相关连接
        channel.close();
        connection.close();
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <h1>消息属性设置</h1>
 * 消息消费者
 * Created by DHA on 2019/11/18.
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明一个队列
        String queueName="test001";
        channel.queueDeclare(queueName,true,false,false,null);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,true,queueingConsumer);

        //7 获取数据
        while(true){
            Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
            // 获取自定义属性数据
            Map<String,Object> headers=delivery.getProperties().getHeaders();
            System.err.println("headers get attribute attr1 value: " + headers.get("attr1"));
        }
    }
}
```



# 五、RabbitMQ 高级特性

## 消息100%可靠性投递的解决方案

### 生产端可靠性投递

- 保障消息成功发出
- 保障 MQ 节点的成功接收
- 发送端收到 MQ 节点（Broker）确认应答
- **完善的消息补偿机制**

### 解决方案1：消息落库

消息落库，对消息状态进行打标。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_8.png" width="725px"/></div>



### 解决方案2：二次确认，回调检查

消息的延迟投递，做二次确认，回调检查。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_9.png" width="700px"/></div>



## 消费端幂等性操作

- 唯一 ID + 指纹码 机制，利用数据库主键去重

  优点：实现简单

  缺点：高并罚下有数据库写入的性能瓶颈

  解决方案：根据 ID 进行分库分表进行算法路由

- 利用 Redis 原子特性实现

## Confirm 消息机制

消息的确认是指生产者投递消息后，如果 Broker 收到消息，则会给生产者一个应答，生产者进行接收应答，用来确定这条消息是否正常地发送到 Broker。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_10.png" width="500px"/></div>

实现机制：

- 第一步：在 channel 上开启确认模式 

  ```java
  channel.confirmSelect()
  ```

- 第二步：在 channel 上添加监听

  ```java
  channel.addConfirmListener()
  ```

  监听成功和失败的返回结果，根据具体的结果对消息进行重新发送或记录日志等后续处理。

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>Confirm 消息机制</h1>
 * 消息的确认是指生产者投递消息后，如果 Broker 收到消息，则会给生产者一个应答，生产者进行接收应答，用来确定这条消息是否正常地发送到 Broker。
 * 消息生产者
 * Created by DHA on 2019/11/18.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 指定消息投递模式：confirm 模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        //5 通过 chanel 发送数据
        String msg="Hello!";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("------ack!-------");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("------Nack!-------");
            }
        });
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>Confirm 消息机制</h1>
 * 消息的确认是指生产者投递消息后，如果 Broker 收到消息，则会给生产者一个应答，生产者进行接收应答，用来确定这条消息是否正常地发送到 Broker。
 * 消息消费者
 * Created by DHA on 2019/11/18.
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        String exchangeName="test_confirm_exchange";
        String exchangeType="topic";
        String queueName="test_confirm_queue";
        String routingKey="confirm.#";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 绑定:将一个队列绑定到一个交换机上
        channel.queueBind(queueName,exchangeName,routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,queueingConsumer);

        //7 获取数据
        while(true){
            Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
```



## Return 消息机制

消息生产者通过制动一个 Exchange 和 routing key，把消息送达到某一个队列中去，然后消费者监听队列，进行消费处理操作。

在某些情况下，如果我们在发送消息的时候，当前的 **Exchange 不存在**或者指定的 **routing key路由不到**，此时我们需要监听这种不可达的消息，就要使用 Return Listener。

基础 API 有一个配置项 mandatory

- 如果为 true，那么监听器会接收到路由不可达的消息，然后进行后续处理
- 如果为 false, 那么 Broker 端自动删除该消息

 

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_11.png" width="500px"/></div>



### 生产者

```java
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>Return 消息机制</h1>
 * 消息生产者通过制动一个 Exchange 和 routing key，把消息送达到某一个队列中去，然后消费者监听队列，进行消费处理操作。
 * 在某些情况下，如果我们在发送消息的时候，当前的 Exchange 不存在或者指定的 routing key路由不到，此时我们需要监听这种不可达的消息，就要使用 Return Listener。
 * 
 * 消息生产者
 * Created by DHA on 2019/11/18.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 指定消息投递模式：confirmListener 模式
        channel.confirmSelect();

        String exchangeName = "test_return_exchange";
        String routingKey = "returnListener.save";
        String routingKeyError = "return.save";

        //5 通过 chanel 发送数据
        String msg="Hello!";
        // mandatory 如果为 true，那么监听器会接收到路由不可达的消息，然后进行后续处理
        // mandatory 如果为 false, 那么 Broker 端自动删除该消息
        channel.basicPublish(exchangeName,routingKeyError,true,null,msg.getBytes());

        //6 添加一个监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.err.println("---------handle  return----------");
                System.err.println("replyCode: " + replyCode);
                System.err.println("replyText: " + replyText);
                System.err.println("exchange: " + exchange);
                System.err.println("routingKey: " + routingKey);
                System.err.println("properties: " + properties);
                System.err.println("body: " + new String(body));
            }
        });

    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1>Return 消息机制</h1>
 * 消息生产者通过制动一个 Exchange 和 routing key，把消息送达到某一个队列中去，然后消费者监听队列，进行消费处理操作。
 * 在某些情况下，如果我们在发送消息的时候，当前的 Exchange 不存在或者指定的 routing key路由不到，此时我们需要监听这种不可达的消息，就要使用 Return Listener。
 *
 * 消息消费者
 * Created by DHA on 2019/11/18.
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        String exchangeName="test_return_exchange";
        String exchangeType="topic";
        String queueName="test_return_queue";
        String routingKey="returnListener.#";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 绑定:将一个队列绑定到一个交换机上
        channel.queueBind(queueName,exchangeName,routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置 channel
        channel.basicConsume(queueName,queueingConsumer);

        //7 获取数据
        while(true){
            Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
```



## 消费端自定义监听

我们一般在代码中编写 while 循环，进行 consumer.nextDelivery 方法获取下一条消息，然后进行消费处理！

但是，我们使用自定义的 Counsumer 更加方便，解耦性更强，在实际工作中广泛使用。

### 自定义消费者

实现步骤：

- 先继承 `com.rabbitmq.client.DefaultConsumer`
- 再重写 `handleDelivery()` 方法

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <h1>自定义消费者</h1>
 * 1 先继承 DefaultConsumer
 * 2 然后重写 handleDelivery() 方法
 *
 * Created by DHA on 2019/11/20.
 */
public class MyConsumer extends DefaultConsumer{
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        System.err.println("----------consumer message-----------");
        System.err.println("consumerTag:"+consumerTag);
        System.err.println("envelope:"+envelope);
        System.err.println("properties:"+properties);
        System.err.println("body:"+new String(body));
    }
}
```



### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> 消费端自定义监听 </h1>
 * 消息生产者
 * Created by DHA on 2019/11/19.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_consumer_exchange";
        String routingKey = "consumer.save";

        //5 通过 chanel 发送数据
        String msg = "Hello World RabbitMQ 4  Consumer Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey , true,null , msg.getBytes());
    }
}
```



### 消费者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {
	
	public static void main(String[] args) throws Exception {
		//1 创建一个 Connectionfactory,并进行设置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		//3 通过 connecion 创建一个 Channel
		Channel channel = connection.createChannel();

		//4 声明
		String exchangeName = "test_consumer_exchange";
		String exchangeType= "topic";
		String routingKey = "consumer.#";
		String queueName = "test_consumer_queue";

		// 声明一个交换机
		channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
		// 声明一个队列
		channel.queueDeclare(queueName,false,false,false,null);
		// 绑定:将一个队列绑定到一个交换机上
		channel.queueBind(queueName,exchangeName,routingKey);

		/*
		//5 创建消费者
		QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

		//6 设置 channel
		channel.basicConsume(queueName,true,queueingConsumer);

		//7 获取数据
		while(true){
			QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
			String msg=new String(delivery.getBody());
			System.out.println("消费端："+msg);
		}
		 */

		//5 消费端自定义监听 使用 MyConsumer 相应实例
		channel.basicConsume(queueName, true, new MyConsumer(channel));
	}
}
```



## 消费端限流

RabbitMQ 提供了一种 QoS（服务质量保证） 功能，**在非自动确认消息的前提下**，如果一定数目的消息（通过基于 Consume 或者 Channel 设置 QoS 值）未被确认前，不进行消费新的消息。

涉及到的方法：

```erlang
void BasicQoS(unit prefetchSize,ushort prefetchCount,bool global)
```

- prefetchSize：0
- prefetchCount：告知 RabbitMQ 不要同时给一个消费者推送多个 N 个消息，即一旦有 N 个消息还没有 ACK，则该 Consumer 将 block 掉，一直到有消息 ack
- golbal：true 表示将上面设置应用于 Channel；true 表示将上面设置应用于 Consumer。

注意：

- prefetchSize 和 global 这两项，RabbitMQ 没有实现，暂且不研究
- prefetchCount 在 no_ask-false 的情况下生效，即在自动应答的情况下是不生效的

### 生产者

```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <h1> 消费端限流 </h1>
 * 消息生产者
 * Created by DHA on 2019/11/19.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_qos_exchange";
        String routingKey = "qos.save";

        //5 通过 chanel 发送数据
        for(int i=0;i<5;i++){
            String msg = "Hello World RabbitMQ 4  Qos Message ... ";
            channel.basicPublish(exchangeName, routingKey , true,null , msg.getBytes());
        }
    }
}
```



### 消费者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <h1>自定义消费者</h1>
 * 1 先继承 DefaultConsumer
 * 2 然后重写 handleDelivery() 方法
 *
 * Created by DHA on 2019/11/20.
 */
public class MyConsumer extends DefaultConsumer{
    
    // channel 进行签收
    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        System.err.println("----------consumer message-----------");
        System.err.println("consumerTag:"+consumerTag);
        System.err.println("envelope:"+envelope);
        System.err.println("properties:"+properties);
        System.err.println("body:"+new String(body));

        // false 表示不支持批量签收
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
```



```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <h1> 消费端限流 </h1>
 * 消息消费者
 *
 * basicQoS(prefetchSize,refetchCount,global)
 * - prefetchSize：0
 * - prefetchCount：告知 RabbitMQ 不要同时给一个消费者推送多个 N 个消息，即一旦有 N 个消息还没有 ACK，
 * 			则该 Consumer 将 block 掉，一直到有消息 ack
 * - golbal：true 表示将上面设置应用于 Channel；true 表示将上面设置应用于 Consumer。 
 * 
 * Created by DHA on 2019/11/19.
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		//1 创建一个 Connectionfactory,并进行设置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		//3 通过 connecion 创建一个 Channel
		Channel channel = connection.createChannel();

		//4 声明
		String exchangeName = "test_qos_exchange";
		String exchangeType= "topic";
		String routingKey = "qos.#";
		String queueName = "test_qos_queue";

		// 声明一个交换机
		channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
		// 声明一个队列
		channel.queueDeclare(queueName,false,false,false,null);
		// 绑定:将一个队列绑定到一个交换机上
		channel.queueBind(queueName,exchangeName,routingKey);

		// 第二个参数为 1，表示一次处理一条消息
		// 第三个参数为 false，表示应用到 Consumer 级别
		channel.basicQos(0,1,false);

		//5 消费端自定义监听
		// 首先将第二个参数设置为 false,进行手动签收
		channel.basicConsume(queueName, false, new MyConsumer(channel));
	}
}
```



## 消费端 ACK 与重回队列

- **消费端的手工 ACK 和 NACK**

  消费端进行消费时：

  如果由于业务异常，我们可以进行日志的记录，然后进行补偿；

  如果由于服务器宕机等严重问题，那么需要手工进行 ACK 保障消费端消费成功

- **消费端的重回队列**

  消费端重回队列是为了对没有成功的消息， 消息会被重新投递给 Broker。一般在使用应用中，都会关闭重回队列，即设置为 false。

### 生产者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <h1> 消费端的手工 ACK 和 NACK </h1>
 * 消息生产者
 * 
 * Created by DHA on 2019/11/19.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1 创建一个 Connectionfactory,并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过 connecion 创建一个 Channel
        Channel channel = connection.createChannel();

        //4 声明
        //声明 exchange 名称
        String exchangeName="test_ack_exchange";
        String routingKey = "ack.save";

        //5 通过 chanel 发送数据
        for(int i =0; i<5; i ++){

            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("num", i);

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();
            String msg = "Hello RabbitMQ ACK Message " + i;
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }
    }
}
```



### 消费者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <h1>消费端的重回队列</h1>
 * 消费端重回队列是为了对没有成功的消息， 消息会被重新投递给 Broker。
 * 一般在使用应用中，都会关闭重回队列，即设置为 false。
 *
 * Created by DHA on 2019/11/20.
 */
public class MyConsumer extends DefaultConsumer{

    // channel 进行签收
    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        System.err.println("-----------consume message----------");
        System.err.println("body: " + new String(body));

        // 为了实验效果明显
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Integer num=(Integer) properties.getHeaders().get("num");
        if(num==0){
            // 第二个参数表示是否支持批量签收，如果为 false,表示不支持批量签收
            // 第三个参数表示是否重回队列，如果为 true,表示支持重回队列，则会重回到队列的尾端
            channel.basicNack(envelope.getDeliveryTag(),false,true);
        }else{
            // false 表示不支持批量签收
            channel.basicAck(envelope.getDeliveryTag(),false);
        }
        //channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
```



```java
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <h1> 消费端的手工 ACK 和 NACK </h1>
 * 消息消费者
 *
 * Created by DHA on 2019/11/19.
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		//1 创建一个 Connectionfactory,并进行设置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		//3 通过 connecion 创建一个 Channel
		Channel channel = connection.createChannel();

		//4 声明
		String exchangeName = "test_ack_exchange";
		String exchangeType= "topic";
		String routingKey = "ack.#";
		String queueName = "test_ack_queue";

		// 声明一个交换机
		channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
		// 声明一个队列
		channel.queueDeclare(queueName,false,false,false,null);
		// 绑定:将一个队列绑定到一个交换机上
		channel.queueBind(queueName,exchangeName,routingKey);

		//5 消费端自定义监听
		// 首先将第二个参数 autoACK 设置为 false,进行手动签收
		channel.basicConsume(queueName, false, new MyConsumer(channel));
	}
}
```



## TTL

TTL（Time To Live）即生存时间。

- RabbitMQ 支持**消息**的过期时间，在消息发送时可以进行指定
- RabbitMQ 支持**队列**的过期时间，从消息如队列开始计算，只要超过了队列的超时时间配置，那么会自动清除消息

## 死信队列（DLX，Dead-Letter-Exchange ）

利用 DLX，当消息在一个队列中变成死信（dead message）之后，其能被重新 publish 到另一个 Exchange，这个 Exchange 就是 DLX。

消息变成死信的几种情况：

- 消息被拒绝（basic.reject / basic.nack），并且 requeue=false
- 消息 TTL 过期
- 队列达到最大长度

注意：

- DLX 也是一个正常的 Exchange，和一般的 Exchange 没有区别，它能在任何队列上被指定，实际上就是设置某个队列的属性。

- 当这个队列中有死信时，RabbitMQ 就会自动的将这个消息重新发布到设置的 Exchange 上去，进而被路由到另一个队列。

- 死信队列设置需要设置 Exchange 和 队列，然后绑定

  ```java
  channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
  channel.queueDeclare("dlx.queue", true, false, false, null);
  channel.queueBind("dlx.queue", "dlx.exchange", "#");
  ```

  然后我们进行正常声明 Exchange、队列和绑定，此时需要在队列上加上参数 arguments

  ```java
  Map<String, Object> agruments = new HashMap<String, Object>();
  agruments.put("x-dead-letter-exchange", "dlx.exchange");
  //这个agruments属性，要设置到声明队列上
  channel.queueDeclare(queueName, true, false, false, agruments);
  ```

### 生产者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <h1>死信队列</h1>
 * 利用 DLX，当消息在一个队列中变成死信（dead message）之后，
 * 其能被重新 publish 到另一个 Exchange，这个 Exchange 就是 DLX。
 *
 * 消息生产者
 * Created by DHA on 2019/11/20.
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";
		
		String msg = "Hello RabbitMQ DLX Message";

		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				.deliveryMode(2)
				.contentEncoding("UTF-8")
				.expiration("10000")
				.build();
		channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
	}
}
```



### 消费者

```java
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * <h1>自定义消费者</h1>
 * 1 先继承 DefaultConsumer
 * 2 然后重写 handleDelivery() 方法
 *
 * Created by DHA on 2019/11/20.
 */
public class MyConsumer extends DefaultConsumer{
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        System.err.println("----------consumer message-----------");
        System.err.println("consumerTag:"+consumerTag);
        System.err.println("envelope:"+envelope);
        System.err.println("properties:"+properties);
        System.err.println("body:"+new String(body));
    }
}
```



```java
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <h1>死信队列</h1>
 * 利用 DLX，当消息在一个队列中变成死信（dead message）之后，
 * 其能被重新 publish 到另一个 Exchange，这个 Exchange 就是 DLX。
 *
 * 消息消费者
 * Created by DHA on 2019/11/20.
 */
public class Consumer {
	
    public static void main(String[] args) throws Exception {
	
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		// 这就是一个普通的交换机 和 队列 以及路由
		String exchangeName = "test_dlx_exchange";
		String routingKey = "dlx.#";
		String queueName = "test_dlx_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		Map<String, Object> agruments = new HashMap<String, Object>();
		agruments.put("x-dead-letter-exchange", "dlx.exchange");
		//这个agruments属性，要设置到声明队列上
		channel.queueDeclare(queueName, true, false,false,agruments);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//要进行死信队列的声明:
		channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
		channel.queueDeclare("dlx.queue", true, false, false, null);
		channel.queueBind("dlx.queue", "dlx.exchange", "#");
		
		channel.basicConsume(queueName, true, new MyConsumer(channel));
	}
}
```

