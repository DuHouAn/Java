# RabbitMQ 整合 SpringBoot

## 生产者

### application.properties 配置

```html
spring.rabbitmq.addresses=localhost:5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.rabbitmq.virtual-host=/
spring.rabbitmq.connection-timeout=15000

spring.rabbitmq.publisher-confirms=true
spring.rabbitmq.publisher-returns=true
# 注意：
# mandatory 如果为 true，那么监听器会接收到路由不可达的消息，然后进行后续处理
# mandatory 如果为 false, 那么 Broker 端自动删除该消息
spring.rabbitmq.template.mandatory=true
```

### MainConfig

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.southeast.springboot.*"})
public class MainConfig {

    @Bean
    public TopicExchange exchange_1(){
        return new TopicExchange("exchange-1",true,false);
    }

    @Bean
    public Queue queue_1(){
        return new Queue("queue-1",true);
    }

    @Bean
    public Binding binding_1(){
        return BindingBuilder.bind(queue_1()).to(exchange_1()).with("springboot.#");
    }

    @Bean
    public TopicExchange exchange_2(){
        return new TopicExchange("exchange-2",true,false);
    }

    @Bean
    public Queue queue_2(){
        return new Queue("queue-2",true);
    }

    @Bean
    public Binding binding_2(){
        return BindingBuilder.bind(queue_2()).to(exchange_2()).with("springboot.*");
    }
}
```



### RabbitSender

- publisher-confirms

  实现一个监听器用于监听 Broker 端给我们返回的确认请求

- publisher-confirms

  保证消息对 Broker 端是可达的，如果出现 routing key 不可达的情况，则使用监听器对不可达的消息进行后续的处理，保证消息的路由成功。

```java
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.southeast.springboot.entity.Order;
/**
 * <h2> 消息发送者 </h2>
 */
@Component
public class RabbitSender {

	//自动注入RabbitTemplate模板类
	@Autowired
	private RabbitTemplate rabbitTemplate;  
	
	//回调函数: confirm确认
	final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.err.println("correlationData: " + correlationData);
			System.err.println("ack: " + ack);
			if(!ack){
				System.err.println("异常处理....");
			}
		}
	};
	
	//回调函数: return返回
	final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
				String exchange, String routingKey) {
			System.err.println("return exchange: " + exchange + ", routingKey: "
					+ routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
		}
	};

	/**
	 * <h2> 发送消息方法调用: 构建Message消息 </h2>
	 * @param message
	 * @param properties
	 * @throws Exception
	 */
	public void send(Object message, Map<String, Object> properties) throws Exception {
		MessageHeaders mhs = new MessageHeaders(properties);
		Message msg = MessageBuilder.createMessage(message, mhs);
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);

		CorrelationData correlationData = new CorrelationData();
		//id + 时间戳 全局唯一
		correlationData.setId("123456");
		rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
	}

	/**
	 * <h2> 发送消息方法调用: 构建自定义对象消息 </h2>
	 * @param order
	 * @throws Exception
	 */
	public void sendOrder(Order order) throws Exception {
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		//id + 时间戳 全局唯一
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId("678910");
		rabbitTemplate.convertAndSend("exchange-2", "spring.def", order, correlationData);
	}
	
}
```



## 消费者

### 消费端核心配置

```html
# 配置为手工确认模式
spring.rabbitmq.listener.simple.acknowledge-mode=manual
# 设置消费端的监听个数和最大个数，控制消费端的并发数量
spring.rabbitmq.listener.simple.concurrency=5
spring.rabbitmq.listener.simple.max-concurrency=10
    
# 交换机、队列、routing key 配置
spring.rabbitmq.listener.order.queue.name=queue-2
spring.rabbitmq.listener.order.queue.durable=true
spring.rabbitmq.listener.order.exchange.name=exchange-2
spring.rabbitmq.listener.order.exchange.durable=true
spring.rabbitmq.listener.order.exchange.type=topic
spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
spring.rabbitmq.listener.order.key=springboot.*
```



### @RabbitListener 注解

@RabbitListener 是一个组合注解，里面可以配置注解 @QueueBinding、@Queue、@Exchange 直接通过这个注解一次性配置交换机、队列、绑定、 routing key。

```java
@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1", 
			durable="true"),
			exchange = @Exchange(value = "exchange-1", 
			durable="true", 
			type= "topic", 
			ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
```

将配置写在代码中，不太灵活，所以使用配置文件改进：在 `application.peoperties` 文件中

```html
spring.rabbitmq.listener.msg.queue.name=queue-1
spring.rabbitmq.listener.msg.queue.durable=true
spring.rabbitmq.listener.msg.exchange.name=exchange-1
spring.rabbitmq.listener.msg.exchange.durable=true
spring.rabbitmq.listener.msg.exchange.type=topic
spring.rabbitmq.listener.msg.exchange.ignoreDeclarationExceptions=true
spring.rabbitmq.listener.msg.key=springboot.*
```



改进后的代码：



```java
@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.msg.queue.name}", 
			durable="${spring.rabbitmq.listener.msg.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.msg.exchange.name}", 
			durable="${spring.rabbitmq.listener.msg.exchange.durable}", 
			type= "${spring.rabbitmq.listener.msg.exchange.type}", 
			ignoreDeclarationExceptions = "${spring.rabbitmq.listener.msg.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.msg.key}"
			)
	)
```



### RabbitReceiver

```java
import java.util.Map;

import com.southeast.springboot.entity.Order;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitReceiver {

	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1", 
			durable="true"),
			exchange = @Exchange(value = "exchange-1", 
			durable="true", 
			type= "topic", 
			ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端Payload: " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
	

    // 使用 配置文件
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}", 
			durable="${spring.rabbitmq.listener.order.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}", 
			durable="${spring.rabbitmq.listener.order.exchange.durable}", 
			type= "${spring.rabbitmq.listener.order.exchange.type}", 
			ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.order.key}"
			)
	)
	@RabbitHandler
	public void onOrderMessage(@Payload Order order,
			Channel channel, 
			@Headers Map<String, Object> headers) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端order: " + order.getId());
		Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}	
}
```