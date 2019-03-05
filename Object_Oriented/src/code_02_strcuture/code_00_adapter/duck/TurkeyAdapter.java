package code_02_strcuture.code_00_adapter.duck;

/**
 * Created by 18351 on 2019/1/2.
 */
public class TurkeyAdapter implements Duck{
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey){
        this.turkey=turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }
}
