package code_00_creation.code_05_prototype;

/**
 * 使用原型实例指定要创建对象的类型，通过复制这个原型来创建新对象。
 */
public class Client {
    public static void main(String[] args) {
        //原型
        Prototype prototype = new ConcretePrototype("abc");
        //复制这个原型，创建新对象
        Prototype clone = prototype.myClone();
        System.out.println(prototype.toString());
        System.out.println(clone.toString());
    }
}
