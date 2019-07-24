package code_01_activity.code_03_iterator;

/**
 * Created by 18351 on 2018/12/29.
 */
public interface Iterator<Item> {
    Item next();
    boolean hasNext();
}
