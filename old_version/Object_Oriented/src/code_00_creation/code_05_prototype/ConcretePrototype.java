package code_00_creation.code_05_prototype;

/**
 * Created by 18351 on 2018/12/28.
 */
public class ConcretePrototype extends Prototype{
    private String filed;

    public ConcretePrototype(String field){
        this.filed=field;
    }

    @Override
    Prototype myClone() {
        return new ConcretePrototype(filed);
    }

    @Override
    public String toString() {
        return filed;
    }
}
