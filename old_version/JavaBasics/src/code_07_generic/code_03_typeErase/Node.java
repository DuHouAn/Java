package code_07_generic.code_03_typeErase;

/**
 * Created by 18351 on 2018/12/24.
 */
public class Node<T> {
    private T data;

    public Node(T data) {
        this.data = data;
    }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}
/*public class Node<T extends Comparable<T>> {
    //Node<T extends Comparable<T>> 是Comaparable即其子类
    private T data;
    private Node<T> next;
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
    public T getData() { return data; }
}*/
/**
 * 编译器做完相应的类型检查之后，实际上到了运行期间上面这段代码实际上将转换成：
     public class Node {
        private Object data;
        private Node next;
        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
        public Object getData() {
            return data;
        }
        // ...
 }
 */
