package code_05_nio.socketChannel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //1.通过SocketChannel连接到远程服务器
        SocketChannel sc=SocketChannel.open();
        sc.connect(new InetSocketAddress(InetAddress.getByName("LAPTOP-D9966H06"),8888));

        //2.创建读数据/写数据缓冲区对象来读取服务端数据或向服务端发送数据
        //向通道中写入数据
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes());
        buffer.flip();
        sc.write(buffer);

        //读取从客户端中获取的数据
        ByteBuffer buffer2=ByteBuffer.allocate(1024);
        sc.read(buffer2);
        StringBuffer sb=new StringBuffer();
        buffer2.flip();
        while(buffer2.hasRemaining()){
            sb.append((char)buffer2.get());
        }
        System.out.println(sb.toString());

        //3.关闭SocketChannel
        sc.close();
    }
}
