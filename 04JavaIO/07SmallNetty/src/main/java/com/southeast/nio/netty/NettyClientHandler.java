package com.southeast.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by 18351 on 2018/9/5.
 */
public class NettyClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {
    //TODO：客户端连接服务器后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务器，开始发送数据……");
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf  firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req); //将数据写入 ByteBuf中
        //ctx.write(firstMessage);
        ctx.writeAndFlush(firstMessage);
    }
    //TODO：从服务器接收到数据后调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //从服务器传过来的数据是 ByteBuf 类型的
        System.out.println("client 读取server数据..");
        //输出服务端返回消息
        byte[] req = new byte[msg.readableBytes()];//msg.readableBytes()拿出ByteBuf中可读内容，然后进行序列化（就是转化为字节数组）
        msg.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("服务端数据为 :" + body);
    }
    //•	发生异常时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught..");
        // 释放资源
        ctx.close();
    }

}
