package com.southeast.boundaryChar;

public class BoundaryCharExample2 {
    /**
     * 查找一个泛型数组中大于某个特定元素的个数
     * (使用边界符)
     * @param array
     * @param elem
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> int countGreaterThan(T[] array,T elem){
        int count = 0;
        for (T e : array)
            if (e .compareTo(elem)> 0)  // compiler error
                ++count;
        return count;
    }
}
