package code_03_Object;

import java.util.HashSet;

/**
 * 新建了两个等价的对象，并将它们添加到 HashSet 中。
 * 我们希望将这两个对象当成一样的，只在集合中添加一个对象，
 * 但是因为 EqualExample 没有实现 hasCode() 方法，
 * 因此这两个对象的散列值是不同的，最终导致集合添加了两个等价的对象。
 */
public class ObjectDemo4 {
    public static void main(String[] args) throws CloneNotSupportedException {
       CloneExample cloneExample=new CloneExample();
       CloneExample cloneExample2=cloneExample.clone();
       System.out.println(cloneExample==cloneExample2);
       System.out.println(cloneExample.equals(cloneExample2));
    }
}
