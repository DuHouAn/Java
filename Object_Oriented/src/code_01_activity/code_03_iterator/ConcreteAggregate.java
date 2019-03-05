package code_01_activity.code_03_iterator;

/**
 * Aggregate 是聚合类，其中 createIterator() 方法可以产生一个 Iterator；
 */
public class ConcreteAggregate implements Aggregate{
    private Integer[] items;

    public ConcreteAggregate() {
        items = new Integer[10];
        for (int i = 0; i < items.length; i++) {
            items[i] = i;
        }
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator<Integer>(items);
    }
}
