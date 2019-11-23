# RabbitMQ 入门

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

## 交换机（Exchange）

交换机：接收消息，并根据路由键转发消息所绑定的队列。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_5.png" width="525px"/></div>

### 交换机属性

- Name

  交换机名称

- Type

  交换机类型 direct、topic、fanout、headers

- Durability

  是否需要持久化，true 为持久化

- Auto Delete

  当最后一个绑定到 Exchange 上的队列删除后，自动删除该 Exchange

- Internal

  当前 Exchange 是否用于 RabbitMQ 内部使用，默认为 false

- Arguments

  扩展参数，用于扩展 AMQP 协议

### 交换机类型

#### 1. Direct Exchange

所有发送到 Direct Exchange 的消息被转发到 routing key 中指定的 Queue。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_4.png" width="425px"/></div>

注意：Direct 模式可以使用 RabbitMQ 自带的 Exchange：default Exchange，所以不需要将 Exchange 进行任何绑定（binding）操作，消息传递时，routing key 必须完全匹配才会被队列接收，否则该消息会被抛弃。

##### 生产者

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



##### 消费者

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



#### 2. Topic Exchange

Topic Exchange 将 routing key 与某 Topic 进行模糊匹配，此时队列需要绑定一个 Topic。

可以使用**通配符**进行模糊匹配：

- "#" 表示匹配一个或多个词。"log.#" 能够匹配到 "log.info.oa"
- "*" 表示只能匹配一个词。"log.\*" 值能够匹配到 "log.info"

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_6.png" width="550px"/></div>

##### 生产者

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



##### 消费者

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



#### 3. Fanout Exchange

Fanout Exchange 不处理 routing key，只需要简单的将队列绑定到交换机上，发送到交换机的消息都会被转发到交换机绑定的所有队列上。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/rabbitMQ/rm_7.png" width="475px"/></div>

##### 生产者

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



##### 消费者

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



## 绑定（Binding）

绑定是指 Exchange 和 Exchange、Queue 之间的连接关系。Binding 中可以包含 routing key 或者参数。

## 消息队列（Queue）

消息队列实际用来存储消息数据。常用属性：

- Durability

  是否持久化，Durable：是，Transient：否

- Auto Delete

  yes 表示最后一个监听被移除之后，会自动删除该 Queue

## 虚拟主机（Virtual Host）

虚拟主机用于进行逻辑隔离，是最长层的消息路由。

- 一个虚拟主机中可以有若干个 Exchange 和 Queue
- 同一个虚拟主机中不能有相同名称的  Exchange 或 Queue

## 消息（Message）

消息即服务器和应用程序之间传送的数据。本质上就是一段数据，由 Properties 和 Body（Properties）组成。

常用属性：

- Delivery Mode

- Headers

  自定义属性

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

