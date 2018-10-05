package com.southeast.nettyExample.sendorder.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoOutHandler2 extends ChannelOutboundHandlerAdapter {

	 @Override
	    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
	        System.out.println("out2");
	        // 执行下一个OutboundHandler
	        /*System.out.println("at first..msg = "+msg);
	        msg = "hi newed in out2";*/
	        super.write(ctx, msg, promise);
	    }

}
