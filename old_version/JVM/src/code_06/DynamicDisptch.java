package code_06;

/**
 * 在运行期根据实际类型确定方法执行版本的分派过程称为动态分派。
 * 动态分派最典型的应用就是方法重写。
 */
public class DynamicDisptch {
    static abstract class Human {
        abstract void sayhello();
    }

    static class Man extends Human {
        /**
         * 在Human的子类Man中重写sayHello()方法
         */
        @Override
        void sayhello() {
            System.out.println("man");
        }
    }

    static class Woman extends Human {
        /**
         * 在Human的子类Woman中重写sayHello()方法
         */
        @Override
        void sayhello() {
            System.out.println("woman");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayhello();
        woman.sayhello();
        man = new Woman();
        man.sayhello();
    }
}
