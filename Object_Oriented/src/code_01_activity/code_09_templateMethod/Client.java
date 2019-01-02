package code_01_activity.code_09_templateMethod;

/**
 * 冲咖啡和冲茶都有类似的流程，
 * 但是某些步骤会有点不一样，要求复用那些相同步骤的代码。
 */
public class Client {
    public static void main(String[] args) {
        Beverage caffeineBeverage = new Coffee();
        caffeineBeverage.prepareRecipe();
        System.out.println("-----------");
        caffeineBeverage= new Tea();
        caffeineBeverage.prepareRecipe();
    }
}
