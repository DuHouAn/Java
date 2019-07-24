package code_01_activity.code_03_iterator;

/**
 * Aggregate 是聚合类，其中 createIterator() 方法可以产生一个 Iterator；
 */
public interface Aggregate {
    Iterator createIterator();
}
