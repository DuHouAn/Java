package code_00_creation.code_04_builder;

/**
 * Created by 18351 on 2018/12/28.
 */
public class HPComputerBuilder extends AbstractComputerBuilder{
    @Override
    public void buildMaster() {
        // TODO Auto-generated method stub
        product.setMaster("i7,16g,512SSD,1060");
        System.out.println("(i7,16g,512SSD,1060)的惠普主机");
    }

    @Override
    public void buildScreen() {
        // TODO Auto-generated method stub
        product.setScreen("4K");
        System.out.println("(4K)的惠普显示屏");
    }

    @Override
    public void buildKeyboard() {
        // TODO Auto-generated method stub
        product.setKeyboard("cherry 青轴机械键盘");
        System.out.println("(cherry 青轴机械键盘)的键盘");
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
