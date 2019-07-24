package code_03_characters.code_00_encapsulation;

/**
 * Created by 18351 on 2019/1/14.
 */
public class SmartPhone {
    private String brand;
    private double price;
    private String color;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void call(String name){
        System.out.println("打电话给"+name);
    }

    public void sendMessage(String name){
        System.out.println("给"+name+"发短信");
    }

    public void playGame(){
        System.out.println("玩游戏");
    }
}
