package code_02_strcuture.code_00_adapter.cooker;

/**
 * 该电器在110V下工作
 */
public class USAElectricCooker {
    private USA110 usa110;

    public USAElectricCooker(USA110 usa110) {
        this.usa110 = usa110;
    }

    public void cook(){
        usa110.connect();
        System.out.println("开始煮饭...");
    }
}
