package code_03_Object;

import java.util.HashSet;

/**
 * 新建了两个等价的对象，并将它们添加到 HashSet 中。
 * 我们希望将这两个对象当成一样的，只在集合中添加一个对象，
 * 但是因为 EqualExample 没有实现 hasCode() 方法，
 * 因此这两个对象的散列值是不同的，最终导致集合添加了两个等价的对象。
 */
public class ObjectDemo3 {
    public static void main(String[] args) {
        EqualExample2 x = new EqualExample2(1,1,1);
        EqualExample2 y = new EqualExample2(1,1,1);
        System.out.println(x.equals(y)); // true
        System.out.println(x == y);      // false

        HashSet<EqualExample2> set=new HashSet<>();
        set.add(x);
        set.add(x);
        System.out.println(set.size()); //未重写hashCode方法输出为2，重写hashCode方法输出为1
    }
}
