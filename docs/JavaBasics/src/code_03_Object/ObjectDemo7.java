package code_03_Object;

/**
 * Created by 18351 on 2018/12/20.
 */
public class ObjectDemo7 {
    public static void main(String[] args){
        CloneConstructorExample e1=new CloneConstructorExample();
        CloneConstructorExample e2=new CloneConstructorExample(e1);
        e1.set(0,12);
        System.out.println(e1.get(0));
        System.out.println(e2.get(0));
    }
}
