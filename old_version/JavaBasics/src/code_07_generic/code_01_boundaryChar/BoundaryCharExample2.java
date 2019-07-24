package code_07_generic.code_01_boundaryChar;

/**
 * Created by 18351 on 2018/12/24.
 */
public class BoundaryCharExample2 {
    public static <T extends Comparable<T>> int countGreaterThan(T[] array,T elem){
        int count = 0;
        for (T e : array) {
            if (e.compareTo(elem)>0) {
                ++count;
            }
        }
        return count;
    }
}
