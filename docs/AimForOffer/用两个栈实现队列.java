import java.util.Stack;

/**
 * Created by 18351 on 2019/7/23.
 */
public class 用两个栈实现队列 {
    //用两个栈实现队列
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    int front; //用来记录第一个出栈元素

    public void push(int node) {
        if (stack1.isEmpty()){
            front = node;
        }
        stack1.push(node);
    }

    public int pop() {
        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public static void main(String[] args) {
        用两个栈实现队列 q = new 用两个栈实现队列();
        q.push(1);
        q.push(2);
        q.push(3);
        q.pop();
    }
}
