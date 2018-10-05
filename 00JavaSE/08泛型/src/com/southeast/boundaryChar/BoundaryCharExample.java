package com.southeast.boundaryChar;

public class BoundaryCharExample {
    /**
     * 查找一个泛型数组中大于某个特定元素的个数
     * @param array
     * @param elm
     * @param <T>
     * @return
     */
    /*public static <T> int countGreaterThan(T[] array,T elem){
        int count = 0;
        for (T e : array) //compiler error
            if (e > elem)  // compiler error
                ++count;
        return count;
    }*/
    /*
    * comliler error:但是这样很明显是错误的，
    * 因为除了short, int, double, long, float, byte, char等原始类型，其他的类并不一定能使用操作符 >
    *
    * 解决-->使用通配符
    * */
}
