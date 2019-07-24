package code_03_Object;

/**
 * Created by 18351 on 2018/12/20.
 */
public class ObjectDemo5 {
    public static void main(String[] args){
        ShallowCloneExample e1=new ShallowCloneExample();
        ShallowCloneExample e2=null;
        try{
            e2=e1.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        e1.set(0,12);
        System.out.println(e2.get(0));
    }
}
