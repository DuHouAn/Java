package code_01_activity.code_08_strategy.duck;

/**
 * 鸭子，就是上下文
 */
public class Duck {
    private QuackBehavior quackBehavior;

    public void perform(){
        if(quackBehavior!=null){
            quackBehavior.quack();
        }
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
