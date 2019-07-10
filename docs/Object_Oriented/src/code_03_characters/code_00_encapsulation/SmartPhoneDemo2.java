package code_03_characters.code_00_encapsulation;

/**
 * Created by 18351 on 2019/1/14.
 */
public class SmartPhoneDemo2 {
    public static void main(String[] args) {
        SmartPhone sp=new SmartPhone();
        sp.setBrand("IPhone");
        sp.setPrice(6666);
        sp.setColor("白色");

        System.out.println("使用"+sp.getBrand()+"牌、售价为"+sp.getPrice()+"元的"
                +sp.getColor()+"的手机");
        sp.call("Jobs");
        sp.sendMessage("Kuke");
        sp.playGame();

        SmartPhone sp2=new SmartPhone();
        sp2.setBrand("小米");
        sp2.setPrice(1000);
        sp2.setColor("黑色");

        SmartPhone sp3=sp;
        sp.setPrice(3555);
        System.out.println("使用"+sp.getBrand()+"牌、售价为"+sp.getPrice()+"元的"
                +sp.getColor()+"的手机");
    }
}
