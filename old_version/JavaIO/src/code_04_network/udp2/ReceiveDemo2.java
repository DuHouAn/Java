package code_04_network.udp2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by 18351 on 2019/1/5.
 */
public class ReceiveDemo2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket(12345);

        while (true){
            byte[] bys=new byte[1024];
            DatagramPacket dp=new DatagramPacket(bys,bys.length);

            ds.receive(dp);
            //阻塞式

            String ip=dp.getAddress().getHostAddress();
            String s = new String(dp.getData(), 0, dp.getLength());
            System.out.println(ip + "传递的数据是:" + s);
        }

        //接收端应该一直开着等待接收数据，不需要释放资源
        //ds.close();
    }
}
