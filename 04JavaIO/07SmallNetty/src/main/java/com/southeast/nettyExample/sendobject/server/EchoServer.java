package com.southeast.nettyExample.sendobject.server;

import com.southeast.nettyExample.sendobject.coder.PersonDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * • 配置服务器功能，如线程、端口 • 实现服务器处理程序，它包含业务逻辑，决定当有一个请求连接或接收数据时该做什么
 * 
 * @author wilson
 *
 */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup eventLoopGroup = null;
		try {
			//创建ServerBootstrap实例来引导绑定和启动服务器
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			//创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
			eventLoopGroup = new NioEventLoopGroup();
			//指定通道类型为NioServerSocketChannel，一种异步模式，OIO阻塞模式为OioServerSocketChannel
			//设置InetSocketAddress让服务器监听某个端口已等待客户端连接。
			serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class).localAddress("localhost",port)
			.childHandler(new ChannelInitializer<Channel>() {
				//设置childHandler执行所有的连接请求
				@Override
				protected void initChannel(Channel ch) throws Exception {
					//注册解码的handler
                    ch.pipeline().addLast(new PersonDecoder());  //IN1  反序列化
					//添加一个入站的handler到ChannelPipeline  
					ch.pipeline().addLast(new EchoServerHandler());   //IN2
				}
					});
			// 最后绑定服务器等待直到绑定完成，调用sync()方法会阻塞直到服务器完成绑定,然后服务器等待通道关闭，因为使用sync()，所以关闭操作也会被阻塞。
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			System.out.println("开始监听，端口为：" + channelFuture.channel().localAddress());
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoServer(20000).start();
	}
}
