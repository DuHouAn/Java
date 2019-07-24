package code_01_activity.code_09_templateMethod;

/**
 * Created by 18351 on 2019/1/2.
 */
public abstract class Beverage {
    final void prepareRecipe(){
        //烧水
        boilWater();
        //放上饮品
        brew();
        //将水倒入杯中
        pourInCup();
        //放入佐料
        addCondiments();
    }

    public void boilWater() {
        System.out.println("boilWater");
    }

    public abstract void brew();


    public void pourInCup() {
        System.out.println("pourInCup");
    }

    public abstract void addCondiments();
}