package code_02_strcuture.code_02_composite.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Composite extends Component{
    private List<Component> child;

    public Composite(String name){
        super(name);
        if(child==null){
            child=new ArrayList<>();
        }
    }

    @Override
    void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        System.out.println("+" + name);
        for (Component component : child) {
            component.print(level + 1);
        }
    }

    @Override
    public void add(Component component) {
        child.add(component);
    }

    @Override
    public void remove(Component component) {
        child.remove(component);
    }
}
