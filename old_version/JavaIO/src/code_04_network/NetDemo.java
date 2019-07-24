package code_04_network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 18351 on 2019/1/5.
 */
public class NetDemo {
    public static void main(String[] args) throws UnknownHostException {
        // public static InetAddress getByName(String host)
        InetAddress address = InetAddress.getByName("LAPTOP-D9966H06");
        //InetAddress address = InetAddress.getByName("223.3.108.211");
        //InetAddress address = InetAddress.getByName("192.168.2.1");

        // 获取两个东西：主机名，IP地址
        // public String getHostName()
        String name = address.getHostName();
        // public String getHostAddress()
        String ip = address.getHostAddress();
        System.out.println(name + "---" + ip);
    }
}
