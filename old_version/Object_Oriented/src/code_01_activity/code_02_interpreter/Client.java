package code_01_activity.code_02_interpreter;

/**
 * Created by 18351 on 2018/12/29.
 */
public class Client {
    /**
     * 构建解析树
     */
    public static AbstractExpression buildInterpreterTree() {
        // Literal
        AbstractExpression terminal1 = new TerminalExpression("A");
        AbstractExpression terminal2 = new TerminalExpression("B");
        AbstractExpression terminal3 = new TerminalExpression("C");
        AbstractExpression terminal4 = new TerminalExpression("D");
        // B C
        AbstractExpression alternation1 = new OrExpression(terminal2, terminal3);
        // A Or (B C)
        AbstractExpression alternation2 = new OrExpression(terminal1, alternation1);
        // D And (A Or (B C))
        return new AndExpression(terminal4, alternation2);
    }

    public static void main(String[] args) {
        AbstractExpression define = buildInterpreterTree();
        String context1 = "D A";
        String context2 = "A B";
        System.out.println(define.interpret(context1));
        System.out.println(define.interpret(context2));
    }
}
