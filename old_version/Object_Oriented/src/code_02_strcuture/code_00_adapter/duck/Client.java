package code_02_strcuture.code_00_adapter.duck;

/**
 * 需要的是鸭子，但是只有火鸡，就用火鸡装鸭子。
 */
public class Client {
    public static void main(String[] args) {
        Turkey turkey=new WildTurkey();
        Duck duck=new TurkeyAdapter(turkey);
        duck.quack();
    }
}
