package code_02_strcuture.code_05_flyweight;

import java.util.HashMap;

/**
 * Created by 18351 on 2019/1/3.
 */
public class FlyWeightFactory {
    private HashMap<String,FlyWeight> map;

    public FlyWeightFactory(){
        map=new HashMap<>();
    }

    //根据内部状态
    public FlyWeight getFlyWeight(String intrinsicState){
        if(!map.containsKey(intrinsicState)){
            FlyWeight flyweight = new ConcreteFlyWeight(intrinsicState);
            map.put(intrinsicState, flyweight);
        }
        return map.get(intrinsicState);
    }
}
