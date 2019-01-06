package code_05_nio.selector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 18351 on 2019/1/6.
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",8888);

        OutputStream os=socket.getOutputStream();
        os.write("hello".getBytes());

        socket.close();
    }
}