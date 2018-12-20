package code_03_Object;

/**
 * clone() 是 Object 的 **protected 方法**，它不是 public，
 一个类不显式去重写 clone()，其它类就不能直接去调用该类实例的 clone() 方法。
 */
public class CloneExample  implements Cloneable{
    private int a;
    private int b;

    @Override
    protected CloneExample clone() throws CloneNotSupportedException {
        return (CloneExample)super.clone();
    }
}
