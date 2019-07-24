package code_07_generic.code_00_genericClass;

/**
 * Created by 18351 on 2018/12/24.
 */
public class  GenericBox<T> {
        // T stands for "Type"
        private T t;
        public void set(T t) { this.t = t; }
        public T get() { return t; }
}
