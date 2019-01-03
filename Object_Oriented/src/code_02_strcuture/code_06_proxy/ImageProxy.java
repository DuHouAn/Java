package code_02_strcuture.code_06_proxy;

/**
 * Created by 18351 on 2019/1/3.
 */
public class ImageProxy implements Image{
    private HighResolutionImage highResolutionImage;

    public ImageProxy(HighResolutionImage highResolutionImage){
        this.highResolutionImage=highResolutionImage;
    }

    @Override
    public void showImage() {
        while (!highResolutionImage.isLoad()) {
            try {
                System.out.println("Temp Image: " + highResolutionImage.getWidth() + " " + highResolutionImage.getHeight());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        highResolutionImage.showImage();
    }
}
