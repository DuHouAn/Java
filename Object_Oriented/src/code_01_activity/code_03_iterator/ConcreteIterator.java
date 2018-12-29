package code_01_activity.code_03_iterator;

/**
 * Created by 18351 on 2018/12/29.
 */
public class ConcreteIterator<Item> implements Iterator{
    private Item[] items;
    private int position = 0;

    public ConcreteIterator(Item[] items){
        this.items=items;
    }

    @Override
    public Item next() {
        return items[position++];
    }

    @Override
    public boolean hasNext() {
        return position<items.length;
    }
}
