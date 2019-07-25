import java.util.Stack;

/**
 * Created by 18351 on 2019/7/23.
 */
//使用两个栈实现队列的扩展
public class MyQueue {
    //用两个栈实现队列
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    //存储队列头元素
    private int front; //方便后面的 peek() 操作

    /** Initialize your data structure here. */
    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int node) {
        if(stack1.isEmpty()){
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

    public int peek(){
        if(stack2.isEmpty()){
            return front;
        }
        return stack2.peek();
    }

    public boolean empty(){
        return (stack1.isEmpty()) && (stack2.isEmpty());
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        System.out.println(queue.peek());  // 返回 1
        System.out.println(queue.pop());   // 返回 1
        queue.empty(); // 返回 false
    }
}
