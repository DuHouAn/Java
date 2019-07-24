package code_00_creation.code_02_factoryMethod;

/**
 * Factory是抽象类
 */
public abstract class Factory {
    //Factory中有一个doSomething()方法，这个方法需要用到一个产品对象。
    public void  doSometing(){
        Product p=fatoryMethod();
        //对象由factoryMethod()方法创建，该方法是抽象的，需要由子类去实现。
    }

    public abstract Product fatoryMethod();
}