package code_07_generic.code_03_typeErase;

public class MyNode<T> extends Node<Integer>{
    public MyNode(Integer data) {
        super(data);
    }

    @Override
    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
