package code_02_strcuture.code_02_composite.tree;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        Component root=new Composite("root");

        Component node1=new Leaf("1");
        Component node2=new Composite("2");
        Component node3=new Leaf("3");

        Component node21=new Leaf("21");
        Component node22=new Composite("22");

        Component node221=new Leaf("221");

        root.add(node1);
        root.add(node2);
        root.add(node3);

        node2.add(node21);
        node2.add(node22);

        node22.add(node221);

        root.print();
    }
}
