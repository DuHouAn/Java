package code_01_activity.code_03_iterator;

/**
 * Client 组合了 Aggregate，为了迭代遍历 Aggregate，也需要组合 Iterator。
 */
public class Client {
    public static void main(String[] args) {
        Aggregate aggregate=new ConcreteAggregate();
        Iterator<Integer> it=aggregate.createIterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
