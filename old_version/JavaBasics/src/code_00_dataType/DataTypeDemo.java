package code_00_dataType;

/**
 * new Integer(123) 与 Integer.valueOf(123) 的区别在于：
  （1） new Integer(123) 每次都会新建一个对象；
  （2）Integer.valueOf(123) 会使用缓存池中的对象，多次调用会取得同一个对象的引用。
 */
public class DataTypeDemo {
    public static void main(String[] args) {
        //基本类型都有对应的包装类型，基本类型与其对应的包装类型之间的赋值使用自动 装箱与拆箱完成
        Integer m = 2; // 装箱
        int n = m; // 拆箱
        System.out.println(m==n);

        Integer x = new Integer(123);
        Integer y = new Integer(123);
        System.out.println(x == y);    // false-->x和y不是同一个对象
        Integer z = Integer.valueOf(123);
        Integer k = Integer.valueOf(123);
        System.out.println(z == k);   // true -->z和k是同一对象
    }
}
