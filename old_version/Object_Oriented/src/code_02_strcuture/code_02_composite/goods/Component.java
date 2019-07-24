package code_02_strcuture.code_02_composite.goods;

/**
 * 组件抽象类
 */
public abstract class Component {
    /**
     * 输出组件自身的名称
     */
    public abstract void doOperation(String preStr);

    /**
     * 向组合对象中加入组件对象
     * @param child 被加入组合对象中的组件对象
     */
    public void add(Component child) {
        throw new UnsupportedOperationException("对象不支持这个功能");
    }

    /**
     * 从组合对象中移出某个组件对象
     * @param child 被移出的组件对象
     */
    public void remove(Component child) {
        throw new UnsupportedOperationException("对象不支持移除功能");
    }

    /**
     * 返回某个索引对应的组件对象
     * @param index 需要获取的组件对象的索引，索引从0开始
     * @return 索引对应的组件对象
     */
    public Component getChildren(int index) {
        throw new UnsupportedOperationException("对象不支持获取子节点功能");
    }
}