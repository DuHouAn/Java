package code_02_strcuture.code_02_composite.goods;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        //定义所有的组合对象
        Component root = new Composite("服装");
        Component c1 = new Composite("男装");
        Component c2 = new Composite("女装");

        //定义所有的叶子对象
        Component leaf1 = new Leaf("衬衣");
        Component leaf2 = new Leaf("夹克");
        Component leaf3 = new Leaf("裙子");
        Component leaf4 = new Leaf("套装");

        //按照树的结构来组合组合对象和叶子对象
        root.add(c1);
        root.add(c2);

        c1.add(leaf1);
        c1.add(leaf2);
        c2.add(leaf3);
        c2.add(leaf4);
        //调用根对象的输出功能来输出整棵树
        root.doOperation("");
    }
}
