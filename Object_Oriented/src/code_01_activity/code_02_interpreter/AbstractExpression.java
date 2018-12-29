package code_01_activity.code_02_interpreter;

/**
 * 定义解释器的接口，
 * 约定解释器的解释操作。
 *
 * 这里的 Context 指的是 String。
 */
public abstract class AbstractExpression {
    public abstract boolean interpret(String context);
}
