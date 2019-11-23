# RabbitMQ 整合 SpringAMQP

## RabbitAdmin

RabbitAdmin 类可以很好地操作 RabbitMQ，在 Spring 中直接进行注入即可。

### 配置

```java
<dependency>
	<groupId>com.rabbitmq</groupId>
	<artifactId>amqp-client</artifactId>
	<version>3.6.5</version>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



### 注入

```java
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.southeast.spring.*"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("localhost");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setVirtualHost("/");
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
}
```

注意：

- autoStartup 必须要设置为 true，否则容器不会加载 RabbitAdmin 

### 基础功能操作

```java
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1> RabbitAdmin 测试</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	/**
	 * <h2> 测试 RabbitAdmin 的基本基本功能</h2>
	 * @throws Exception
	 */
	@Test
	public void testAdmin() throws Exception {
		// 声明交换机
		rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
		rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
		rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

		// 声明队列
		rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
		rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
		rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

		// 声明绑定：注意有 2 种方式
		rabbitAdmin.declareBinding(new Binding("test.direct.queue",
				Binding.DestinationType.QUEUE,
				"test.direct", "direct", new HashMap<>()));
		
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.topic.queue", false))		//直接创建队列
				.to(new TopicExchange("test.topic", false, false))	//直接创建交换机 建立关联关系
				.with("user.#"));	//指定路由Key
		
		
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.fanout.queue", false))		
				.to(new FanoutExchange("test.fanout", false, false)));
		
		//清空队列数据
		rabbitAdmin.purgeQueue("test.topic.queue", false);
	}
}
```

### RabbitAdmin 源码解析

RabbitMQAdmin 底层实现就是从 Spring 容器中获取 Exchange、Binding、routing key 以及 Queue 的 @Bean 声明，然后使用 RabbitTemplate 的 execute 方法执行对应的声明、修改、删除等一系列 RabbitMQ 基础功能操作。

```java
this.rabbitTemplate.execute(
    new ChannelCallback<Object>() {
      public Object doInRabbit(Channel channel) throws Exception {
                                                            RabbitAdmin.this.declareExchanges(channel, (Exchange[])exchanges.toArray(new Exchange[exchanges.size()]));
                                                            RabbitAdmin.this.declareQueues(channel, (Queue[])queues.toArray(new Queue[queues.size()]));
                                                            RabbitAdmin.this.declareBindings(channel, (Binding[])bindings.toArray(new Binding[bindings.size()]));
      
          return null;
      }
});
```



## SpringAMQP 声明

- RabbitMQ 基础 API 中声明一个 Exchange、一个队列、一个绑定：

  ```java
  // 声明一个交换机
  channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
  // 声明一个队列
  channel.queueDeclare(queueName,false,false,false,null);
  // 绑定:将一个队列绑定到一个交换机上
  channel.queueBind(queueName,exchangeName,routingKey);
  ```

- 使用 SpringAMQP 去声明，使用 @Bean 注解

  ```java
  /**
  	 * <h2> 针对消费者声明交换机、队列、绑定</h2>
  	 * 1. 设置交换机类型
  	 * 2. 将队列绑定到交换机
  	 * - FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
  	 * - HeadersExchange ：通过添加属性key-value匹配
  	 * - DirectExchange:按照routingkey分发到指定队列
  	 * - TopicExchange:多关键字匹配
  	 */
  // 声明 交换机、队列和绑定
  @Bean
  public TopicExchange exchange001() {
      return new TopicExchange("topic001", true, false);
  }
  
  @Bean
  public Queue queue001() {
      return new Queue("queue001", true); //队列持久
  }
  
  @Bean
  public Binding binding001() {
      return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
  }
  
  @Bean
  public TopicExchange exchange002() {
      return new TopicExchange("topic002", true, false);
  }
  
  @Bean
  public Queue queue002() {
      return new Queue("queue002", true); //队列持久
  }
  
  @Bean
  public Binding binding002() {
      return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
  }
  
  @Bean
  public Queue queue003() {
      return new Queue("queue003", true); //队列持久
  }
  
  @Bean
  public Binding binding003() {
      return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
  }
  
  @Bean
  public Queue queue_image() {
      return new Queue("image_queue", true); //队列持久
  }
  
  @Bean
  public Queue queue_pdf() {
      return new Queue("pdf_queue", true); //队列持久
  }
  ```


## RabbitTemplate

RabbitTemplate 即消息模板，是在于 SpringAMQP 整合时进行发送消息的关键类，该类提供了丰富的发送消息方法，包括可靠性投递消息方法、回调监听消息接口 ConfirmCallback、返回值确认接口 ReturnCallback 等等。同样我们需要进行注入到 Spring 容器中，然后直接使用。

```java
@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    return rabbitTemplate;
}
```

```java
@Autowired
private RabbitTemplate rabbitTemplate;

/**
	 * <h2> rabbitTemplate 测试发送消息</h2>
	 * @throws Exception
	 */
@Test
public void testSendMessage() throws Exception {
    //1 创建消息
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.getHeaders().put("desc", "信息描述..");
    messageProperties.getHeaders().put("type", "自定义消息类型..");
    Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);

    rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            System.err.println("------添加额外的设置---------");
            message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
            message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
            return message;
        }
    });
}

@Test
public void testSendMessage2() throws Exception {
    //1 创建消息
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("text/plain");
    Message message = new Message("mq 消息1234".getBytes(), messageProperties);

    rabbitTemplate.send("topic001", "spring.abc", message);

    rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello object message send!");
    rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello object message send!!!");
}
```

## SimpleMessageListenerContainer

SimpleMessageListenerContainer 即简单消息监听容器。

该类非常强大，我们可以对他进行很多设置，对于消费者的配置项，这个类都可以满足。

使用 SimpleMessageListenerContainer  可以监听多个队列、设置消费者数量、批量消费。

```java
/**
	 * <h2> 设置 简单消息监听容器</h2>
	 * @param connectionFactory
	 * @return {@link SimpleMessageListenerContainer}
	 */
@Bean
public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {

    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

    //监听多个队列
    container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
    container.setConcurrentConsumers(1);
    container.setMaxConcurrentConsumers(5);
    container.setDefaultRequeueRejected(false);
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);
    container.setConsumerTagStrategy(new ConsumerTagStrategy() {
        @Override
        public String createConsumerTag(String queue) {
            return queue + "_" + UUID.randomUUID().toString();
        }
    });

    //设置监听器
    container.setMessageListener(new ChannelAwareMessageListener() {
        @Override
        public void onMessage(Message message, Channel channel) throws Exception {
            String msg = new String(message.getBody());
            System.err.println("消费者: " + msg);
        }
    });

    return container;
}
```



## MessageListenerAdapter

MessageListenerAdapter 即消息监听适配器，通过 MessageListenerAdapter 的代码我们可以看出如下核心属性：

- defaultListenerMethod：默认监听方法名称

- Delegate 委托对象：实际真实的委托对象，用于处理消息

- queueOrTagToMathodName：队列标识与方法名称组成的集合

  可以将队列和方法名称绑定，即指定队列里的消息会被绑定的方法所接受处理

### Adpater

```java
/**
	 * <h2> 设置 简单消息监听容器</h2>
	 * @param connectionFactory
	 * @return {@link SimpleMessageListenerContainer}
	 */
@Bean
public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {

    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

    //监听多个队列
    container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
    container.setConcurrentConsumers(1);
    container.setMaxConcurrentConsumers(5);
    container.setDefaultRequeueRejected(false);
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);
    container.setConsumerTagStrategy(new ConsumerTagStrategy() {
        @Override
        public String createConsumerTag(String queue) {
            return queue + "_" + UUID.randomUUID().toString();
        }
    });

    //设置监听器
    /*container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				String msg = new String(message.getBody());
				System.err.println("消费者: " + msg);
			}
		});*/

    /**
		 * defaultListenerMethod 方式
		 */
    /*// 设置适配器，作为参数
		MessageListenerAdapter adapter=new MessageListenerAdapter(new MessageDelegate());
		// 未指定监听方法，由默认的 handleMessage 处理
		container.setMessageListener(adapter);*/

    /**
		 * 指定自定义监听方法方式
		 */
    /*MessageListenerAdapter adapter=new MessageListenerAdapter(new MessageDelegate());
		// 指定 consumeMessage 监听方法，参数是字节数组
		adapter.setDefaultListenerMethod("consumeMessage");
		// 也可以添加一个转换器: 从字节数组转换为String
		adapter.setMessageConverter(new TextMessageConverter());
		container.setMessageListener(adapter);*/

    /**
		 * 将队列和方法名称绑定
		 */
    MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());

    Map<String, String> queueOrTagToMethodName = new HashMap<>();
    queueOrTagToMethodName.put("queue001", "method1");
    queueOrTagToMethodName.put("queue002", "method2");
    adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);

    //自定义的方法参数是 String 类型
    adapter.setMessageConverter(new TextMessageConverter());
    container.setMessageListener(adapter);

    return container;
}
```



### Delegate

```java
/**
 * <h1> 消息委托 </h1>
 * 默认方法是 handleMessage(byte[])
 * 自定义方法是 consumeMessage(byte[]) 和 consumeMessage(String )
 * Created by DHA on 2019/11/22.
 */
public class MessageDelegate {

    /**
     * <h2> 默认方法 </h2>
     * @param messageBody 消息体，注意是字节数组
     */
    public void handleMessage(byte[] messageBody) {
        System.err.println("默认方法, 消息内容:" + new String(messageBody));
    }

    /**
     * <h2> 自定义方法 </h2>
     * @param messageBody 消息体，注意是字节数组
     */
    public void consumeMessage(byte[] messageBody) {
        System.err.println("字节数组方法, 消息内容:" + new String(messageBody));
    }

    /**
     * <h2> 自定义方法 </h2>
     * @param messageBody 消息体，注意是字符串，需要自定义转换器
     */
    public void consumeMessage(String messageBody) {
        System.err.println("字符串方法, 消息内容:" + messageBody);
    }

    /**
     * <h2> 自定义方法 </h2>
     *  method1 方法与 queue001 匹配
     * @param messageBody
     */
    public void method1(String messageBody) {
        System.err.println("method1 收到消息内容:" + new String(messageBody));
    }

    /**
     * <h2> 自定义方法 </h2>
     * method2 方法与 queue002 匹配
     * @param messageBody
     */
    public void method2(String messageBody) {
        System.err.println("method2 收到消息内容:" + new String(messageBody));
    }
}
```

### TextMessageConverter

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class TextMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		return new Message(object.toString().getBytes(), messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		String contentType = message.getMessageProperties().getContentType();
		if(null != contentType && contentType.contains("text")) {
			return new String(message.getBody());
		}
		return message.getBody();
	}
}
```



## MessageConverter

MessageConverter 即消息转换器，在进行发送消息的时候，正常情况下消息体为二进制的数据方式进行传输，如果希望内部帮我们进行转换，或者指定自定义的转换器，就需要使用 MessageConverter。

### 消息委托

```java
import com.southeast.spring.entity.Order;
import com.southeast.spring.entity.Packaged;

import java.io.File;
import java.util.Map;

/**
 * <h1> 消息委托 </h1>
 */
public class MyMessageDelegate {

	/**
	 * <h2> 支持 Json 格式 </h2>
	 * @param messageBody json 实际上就是 map
	 */
	public void consumeMessage(Map messageBody) {
		System.err.println("map方法, 消息内容:" + messageBody);
	}

	/**
	 * <h2> 支持 Java 对象转换 </h2>
	 * @param order {@link Order}
	 */
	public void consumeMessage(Order order) {
		System.err.println("order对象, 消息内容, id: " + order.getId() + 
				", name: " + order.getName() + 
				", content: "+ order.getContent());
	}

	/**
	 * <h2> 支持 Java 对象多隐射转换 </h2>
	 * @param pack {@link Packaged}
	 */
	public void consumeMessage(Packaged pack) {
		System.err.println("package对象, 消息内容, id: " + pack.getId() + 
				", name: " + pack.getName() + 
				", content: "+ pack.getDescription());
	}
	
	/**
	 * <h2> 支持自定义转换器 </h2>
	 * @param file
	 */
	public void consumeMessage(File file) {
		System.err.println("文件对象 方法, 消息内容:" + file.getName());
	}
}
```



### Json 转换器

```java
/**
		 * 支持json格式的转换器
		 */
MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMessageDelegate());
adapter.setDefaultListenerMethod("consumeMessage");

Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
adapter.setMessageConverter(jackson2JsonMessageConverter);

container.setMessageListener(adapter);
```



### Java 对象转换

```java
/**
		 * 支持 java 对象转换
		 * DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
		 */
MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMessageDelegate());
adapter.setDefaultListenerMethod("consumeMessage");

Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

adapter.setMessageConverter(jackson2JsonMessageConverter);
container.setMessageListener(adapter);
```



### Java 对象多映射转换

```java
/**
		 * 支持java对象多映射转换
		 * DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
		 */
MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMessageDelegate());
adapter.setDefaultListenerMethod("consumeMessage");
Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
idClassMapping.put("order", com.southeast.spring.entity.Order.class);
idClassMapping.put("packaged", com.southeast.spring.entity.Packaged.class);

javaTypeMapper.setIdClassMapping(idClassMapping);

jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
adapter.setMessageConverter(jackson2JsonMessageConverter);
container.setMessageListener(adapter);
```



### 自定义二进制转换器

```java
/**
		 * 自定义转换器
		 */
MessageListenerAdapter adapter = new MessageListenerAdapter(new MyMessageDelegate());
adapter.setDefaultListenerMethod("consumeMessage");

//全局的转换器:
ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

TextMessageConverter textConvert = new TextMessageConverter();
convert.addDelegate("text", textConvert);
convert.addDelegate("html/text", textConvert);
convert.addDelegate("xml/text", textConvert);
convert.addDelegate("text/plain", textConvert);

Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
convert.addDelegate("json", jsonConvert);
convert.addDelegate("application/json", jsonConvert);

ImageMessageConverter imageConverter = new ImageMessageConverter();
convert.addDelegate("image/png", imageConverter);
convert.addDelegate("image", imageConverter);

PDFMessageConverter pdfConverter = new PDFMessageConverter();
convert.addDelegate("application/pdf", pdfConverter);


adapter.setMessageConverter(convert);
container.setMessageListener(adapter);
```

自定义消息转换器，需要实现 MessageConverter 接口，然后重写：

```java
toMessage() // Java 对象转换为 Message
fromMessage() // Message 对象转换为 Java 对象
```



#### 1. pdf 转换器

```java
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class PDFMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		throw new MessageConversionException(" convert error ! ");
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		System.err.println("-----------PDF MessageConverter----------");
		
		byte[] body = message.getBody();
		String fileName = UUID.randomUUID().toString();
		String path = "F:/Projects/RabbitMQ/rabbitmq-spring/testForConverterDest/" + fileName + ".pdf";
		File f = new File(path);
		try {
			Files.copy(new ByteArrayInputStream(body), f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

}
```

#### 2. 图片转换器

```java
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class ImageMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		throw new MessageConversionException(" convert error ! ");
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		System.err.println("-----------Image MessageConverter----------");
		
		Object _extName = message.getMessageProperties().getHeaders().get("extName");
		String extName = _extName == null ? "png" : _extName.toString();
		
		byte[] body = message.getBody();
		String fileName = UUID.randomUUID().toString();
		String path = "F:/Projects/RabbitMQ/rabbitmq-spring/testForConverterDest/" + fileName + "." + extName;
		File f = new File(path);
		try {
			Files.copy(new ByteArrayInputStream(body), f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
}
```