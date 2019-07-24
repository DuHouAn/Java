package code_02_strcuture.code_06_proxy;

import java.net.URL;

/**
 * Created by 18351 on 2019/1/3.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        String image = "http://image.jpg";
        URL url = new URL(image);
        HighResolutionImage highResolutionImage = new HighResolutionImage(url);
        ImageProxy imageProxy = new ImageProxy(highResolutionImage);
        imageProxy.showImage();
    }
}
