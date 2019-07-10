package code_07_generic.code_00_genericClass;

/**
 * 这样做的一个不好的是Box里面现在只能装入String类型的元素，今后如果我们需要装入Integer等其他类型的元素，
 * 还必须要另外重写一个Box，代码得不到复用，使用泛型可以很好的解决这个问题。
 */
public class Box {
    private String object;

    public void set(String object) {
        this.object = object;
    }

    public String get(){
        return object;
    }
}
