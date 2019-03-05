package code_02_strcuture.code_02_composite.tree;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Leaf extends Component {
    public Leaf(String name) {
        super(name);
    }

    @Override
    void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        System.out.println("-"+name);
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("对象不支持这个功能");
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException("对象不支持这个功能");
    }
}
