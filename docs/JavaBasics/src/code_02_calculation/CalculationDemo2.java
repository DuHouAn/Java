package code_02_calculation;

/**
 * Java 的参数是以**值传递**的形式传入方法中，而不是引用传递。
 以下代码中 Dog dog 的 dog 是一个指针，存储的是对象的地址。
 在将一个参数传入一个方法时，本质上是**将对象的地址以值的方式传递到形参中**。
 因此在方法中使指针引用其它对象，那么这两个指针此时指向的是完全不同的对象，
 在一方改变其所指向对象的内容时对另一方没有影响。
 */
public class CalculationDemo2 {
    public static void main(String[] args) {
        String s = "a";
        switch (s) {
            case "a":
                System.out.println("aaa");
                break;
            case "b":
                System.out.println("bbb");
                break;
        }
    }
}
