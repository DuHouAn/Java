package code_05_nio.datagramChannel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by 18351 on 2019/1/6.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramChannel dc= DatagramChannel.open();
        dc.bind(new InetSocketAddress(InetAddress.getByName("LAPTOP-D9966H06"),8888));


        //创建读数据/写数据缓冲区对象来读取客户端数据或向客户端发送数据
        //读取客户端发送的数据
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        //从通道中读取数据到缓冲区
        dc.receive(buffer);
        StringBuffer sb=new StringBuffer();
        buffer.flip();
        while(buffer.hasRemaining()){
            sb.append((char)buffer.get());
        }
        System.out.println(sb.toString());

        ByteBuffer buffer2=ByteBuffer.allocate(1024);
        //向客户端发送数据
        buffer2.put("data has been received.".getBytes());
        buffer2.flip();
        dc.send(buffer2,new InetSocketAddress(InetAddress.getByName("LAPTOP-D9966H06"),9999));

        dc.close();
    }
}
