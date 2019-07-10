package code_02_strcuture.code_02_composite.tree;

/**
 * Created by 18351 on 2019/1/2.
 */
public abstract class Component {
    protected String name;

    public Component(String name){
        this.name=name;
    }

    public void print() {
        print(0);
    }

    abstract void print(int level);

    abstract public void add(Component component);

    abstract public void remove(Component component);
}
