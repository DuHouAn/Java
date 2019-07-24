package code_02_strcuture.code_00_adapter.cooker;

/**
 * 想要110V,但是只有220V,就用220V "装" 110V
 */
public class Client {
    public static void main(String[] args) {
        CHINA220 china220=new CHINA220Impl();
        PowerAdapter usa110=new PowerAdapter(china220);
        USAElectricCooker cooker=new USAElectricCooker(usa110);
        cooker.cook();
    }
}
