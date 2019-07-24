package code_02_strcuture.code_02_composite.goods;

/**
 * 叶子对象
 */
public class Leaf extends Component{
    //叶子对象元素信息
    private String name;

    public Leaf(String name){
        this.name=name;
    }

    @Override
    public void doOperation(String preStr) {
        System.out.println(preStr+"-"+name);
    }
}
