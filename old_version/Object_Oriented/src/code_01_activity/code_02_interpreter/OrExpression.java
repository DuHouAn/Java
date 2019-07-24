package code_01_activity.code_02_interpreter;

/**
 * NonterminalExpression：非终结符解释器，用来实现语法规则中非终结符相关的操作，
 * 通常一个解释器对应一个语法规则，可以包含其它的解释器.
 */
public class OrExpression extends AbstractExpression{
    private AbstractExpression expression1 = null;
    private AbstractExpression expression2 = null;

    public OrExpression(AbstractExpression expression1, AbstractExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean interpret(String context) {
        return expression1.interpret(context) || expression2.interpret(context);
    }
}
