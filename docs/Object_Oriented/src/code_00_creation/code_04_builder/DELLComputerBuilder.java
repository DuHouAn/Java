package code_00_creation.code_04_builder;

/**
 * Created by 18351 on 2018/12/28.
 */
public class DELLComputerBuilder extends AbstractComputerBuilder{
    @Override
    public void buildMaster() {
        // TODO Auto-generated method stub
        product.setMaster("i7,32g,1TSSD,1060");
        System.out.println("(i7,32g,1TSSD,1060)的戴尔主机");
    }

    @Override
    public void buildScreen() {
        // TODO Auto-generated method stub
        product.setScreen("4k");
        System.out.println("(4k)的dell显示屏");
    }

    @Override
    public void buildKeyboard() {
        // TODO Auto-generated method stub
        product.setKeyboard("cherry 黑轴机械键盘");
        System.out.println("(cherry 黑轴机械键盘)的键盘");
    }

    @Override
    public void buildMouse() {
        // TODO Auto-generated method stub
        product.setMouse("MI 鼠标");
        System.out.println("(MI 鼠标)的鼠标");
    }

    @Override
    public void buildAudio() {
        // TODO Auto-generated method stub
        product.setAudio("飞利浦 音响");
        System.out.println("(飞利浦 音响)的音响");
    }
}
