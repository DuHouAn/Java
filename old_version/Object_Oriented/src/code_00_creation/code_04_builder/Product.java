package code_00_creation.code_04_builder;

/**
 * 定义一个产品类
 */
public class Product {
    private String master;
    private String screen;
    private String keyboard;
    private String mouse;
    private String audio;

    public void setMaster(String master) {
        this.master = master;
    }


    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getMaster() {
        return master;
    }

    public String getScreen() {
        return screen;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}