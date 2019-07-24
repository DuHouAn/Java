package code_01_activity.code_02_interpreter;

import java.util.StringTokenizer;

/**
 * TerminalExpression：终结符解释器，用来实现语法规则中和终结符相关的操作，
 *  不再包含其它的解释器。
 */
public class TerminalExpression extends AbstractExpression{
    private String literal=null;

    public TerminalExpression(String str){
        literal=str;
    }

    @Override
    public boolean interpret(String context) {
        StringTokenizer st = new StringTokenizer(context);
        while (st.hasMoreTokens()) {
            String test = st.nextToken();
            if (test.equals(literal)) {
                return true;
            }
        }
        return false;
    }
}
