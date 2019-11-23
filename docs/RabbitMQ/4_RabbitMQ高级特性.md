# RabbitMQ 高级特性

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







