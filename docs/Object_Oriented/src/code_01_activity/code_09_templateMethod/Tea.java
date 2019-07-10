package code_01_activity.code_09_templateMethod;

/**
 * 通过模板方法，子类可以重新定义算法的某些步骤，而不用改变算法的结构。
 */
public class Tea extends Beverage {
    @Override
    public void brew() {
        System.out.println("Tea.brew");
    }

    @Override
    public void addCondiments() {
        System.out.println("Tea.addCondiments");
    }
}
