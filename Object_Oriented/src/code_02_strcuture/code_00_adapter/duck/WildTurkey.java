package code_02_strcuture.code_00_adapter.duck;

/**
 * Adaptee的具体类
 */
public class WildTurkey implements Turkey{
    @Override
    public void gobble() {
        System.out.println("gobble!");
    }
}
