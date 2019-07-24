package code_02_strcuture.code_02_composite.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合对象，可以包含其它组合对象或者叶子对象
 */
public class Composite extends Component{
    private String name;
    /**
     * 用来存储组合对象中包含的子组件对象
     */
    private List<Component> childComponents;

    public Composite(String name){
        this.name=name;
    }

    @Override
    public void add(Component child) {
        if(childComponents==null){
            childComponents=new ArrayList<>();
        }
        childComponents.add(child);
    }

    /**
     * /**
     * 输出组合对象自身的结构
     * @param preStr 主要是按照层级拼接的空格，实现向后缩进
     */
    @Override
    public void doOperation(String preStr) {
        System.out.println(preStr+"+"+this.name);
        //如果还包含有子组件，那么就输出这些子组件对象
        if(this.childComponents!=null){
            //然后添加一个空格，表示向后缩进一个空格
            preStr+=" ";
            //输出当前对象的子对象了
            for(Component c : childComponents){
                //递归输出每个子对象
                c.doOperation(preStr);
            }
        }
    }
}
