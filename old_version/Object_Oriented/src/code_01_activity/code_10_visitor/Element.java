package code_01_activity.code_10_visitor;

/**
 * Created by 18351 on 2019/1/2.
 */
public interface Element {
    void accept(Visitor visitor);
}
