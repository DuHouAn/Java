package com.southeast.nio.tcpSelectorChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Channel结合Selector
 * 实现tcp协议通信
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //开启选择器
        Selector selector = Selector.open();

        //开启通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//将通道设置未非阻塞
        //将通道注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        //文件写出流
        RandomAccessFile accessFile=new RandomAccessFile("Copy.txt","rw");
        FileChannel fc=accessFile.getChannel();

        while(true) {
            selector.select();//监听事件
            //获取到达的事件
            Set<SelectionKey> set=selector.selectedKeys();
            Iterator<SelectionKey> it= set.iterator();
            while(it.hasNext()){
                SelectionKey key=it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();
                    //TODO:服务器会为每个新连接创建一个 SocketChannel
                    SocketChannel sChannel = ssChannel1.accept();
                    sChannel.configureBlocking(false);//将通道设置为非阻塞的
                    sChannel.register(selector, SelectionKey.OP_READ);//向选择器注册该通道是可读的
                } else if (key.isReadable()) {
                    SocketChannel sChannel = (SocketChannel) key.channel();
                    if(sChannel!=null){
                        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                        int len=0;
                        while ((len=sChannel.read(byteBuffer))!=-1){//从Channel中读取数据到Buffer中
                            byteBuffer.flip();//切换读为写

                            //从Buffer中写入数据到Channel中
                            fc.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }
                    sChannel.close();
                }
                it.remove();
            }
        }
    }
}
