package com.southeast.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by DHA on 2018/9/6.
 */
public class TimeNIOServer {
    public static void main(String[] args) throws IOException {
        //创建选择器，监听通道
        Selector selector=Selector.open();

        // 创建socket channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(12345));
        serverSocketChannel.configureBlocking(false);// //ServerSocketChannel设置成非阻塞模式。
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//向选择器中注册该 Channel

        while(true){
            //监听事件
            selector.select();

            //获取到达的事件
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            SelectionKey key = null;
            while (it.hasNext()){
                key=it.next();
                it.remove();
                handleSelectionKey(selector,key);
            }

          //Exception in thread "main" java.util.ConcurrentModificationException,增强for不是线程安全的
          /* Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for(SelectionKey key:selectedKeys){
                handleSelectionKey(selector,key);
                selectedKeys.remove(key);
            }*/
        }
    }

    private static void handleSelectionKey(Selector selector,SelectionKey key) throws IOException {
        if(key.isAcceptable()){
            ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();
            //TODO:服务器会为每个新连接创建一个 SocketChannel
            SocketChannel sChannel = ssChannel1.accept();
            if(sChannel!=null){
                sChannel.configureBlocking(false);//将通道设置为非阻塞的
                sChannel.register(selector, SelectionKey.OP_READ);
            }
        }else if(key.isReadable()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if(socketChannel!=null) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int len = 0;
                while ((len = socketChannel.read(byteBuffer)) != -1) { //从Channel中读取数据到缓冲区
                    byte[] bys = byteBuffer.array();//将缓冲区中数据转换为字节数组
                    byteBuffer.clear();
                    String str = new String(bys, 0, len);
                    System.out.print(str);
                }
            }
        }
    }
}
