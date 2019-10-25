<!-- GFM-TOC -->
* [AVL树](#AVL树)
   * [平衡二叉树](#平衡二叉树)
   * [计算节点的高度和平衡因子](#计算节点的高度和平衡因子)
   * [检查二分搜索树和平衡树](#检查二分搜索树和平衡树)
   * [AVL树的左旋转和右旋转](#AVL树的左旋转和右旋转)
   * [LR和RL](#LR和RL)
   * [删除元素](#删除元素)
   * [基于AVL树的集合和映射](#基于AVL树的集合和映射)
<!-- GFM-TOC -->
# AVL树

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_1.png" width="600"/></div>

## 平衡二叉树

平衡二叉树特点：对于任意一个节点，左子树和右子树的高度差不能超过1

下图就是一棵平衡二叉树：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_2.png" width="600"/></div>

- 标注节点的高度：(叶子节点的高度为1)

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_3.png" width="600"/></div>

- 计算平衡因子:(这里是根据左子树高度减去右子树高度进行计算)：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_4.png" width="600"/></div>

## 计算节点的高度和平衡因子
```java
public class AVLTree<K extends Comparable<K>,V>{
    private class Node{
        public K key;
        public V value;
        public Node left,right;
        public int height;
        public Node(K key,V value){
            this.key=key;
            this.value=value;
            this.left=null;
            this.right=null;
            //叶子节点的高度是1
            height=1;
        }
    }

    private Node root;
    private int size;

    public void add(K key, V value) {
        root=add(root,key,value);
    }

    //计算节点的高度
    private int getNodeHeight(Node node){
        if(node==null){
            return 0;
        }
        return node.height;
    }

    //获取节点的平衡因子，左子树高度-右子树高度
    private int getBalancedFactor(Node node){
        if(node==null){
            return 0;
        }
        return getNodeHeight(node.left)-getNodeHeight(node.right);
    }

    private Node add(Node node,K key,V value){
        if(node==null){
            size++;
            return new Node(key,value);
        }
        if(key.compareTo(node.key)<0){
            node.left=add(node.left,key,value);
        }else if(key.compareTo(node.key)>0){
            node.right=add(node.right,key,value);
        }else{
            node.value=value;
        }
        //更新height
        node.height=1+Math.max(getNodeHeight(node.left),getNodeHeight(node.right));
        //计算平衡因子
        int balancedFactor=getBalancedFactor(node);
        if(Math.abs(balancedFactor)>1){
            System.out.println("unblance:"+balancedFactor);
        }
        return node;
    }
}
```

## 检查二分搜索树和平衡树
- 利用BST中序遍历性质，判断是否是BST
```java
//检查该树是否是平衡二叉树
public boolean isBST(){
    List<K> keys=new ArrayList<>();
    inOrder(root,keys);
    for(int i=1;i<keys.size();i++){
        if(keys.get(i-1).compareTo(keys.get(i))>0){
            return false;
        }
    }
    return true;
}

private void inOrder(Node node, List<K> keys){
    if(node==null){
        return;
    }
    inOrder(node.left,keys);
    keys.add(node.key);
    inOrder(node.right,keys);
}
```

- 判断该树是否是平衡树
```java
//判断该二叉树是否是一棵平衡二叉树
public boolean isBalancedTree(){
    return isBalancedTree(root);
}

private boolean isBalancedTree(Node node){
    if(node==null){
        return true;
    }
    int balancedFactor=getBalancedFactor(node);
    if(Math.abs(balancedFactor)>1){
        return false;
    }
    return isBalancedTree(node.left) && isBalancedTree(node.right);
}
```

## AVL树的左旋转和右旋转
- AVL树的右旋转：插入的元素在不平衡的节点的左侧的左侧

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_5.png" width="600"/></div>

- 右旋转针对的情况：以x、z为根节点的子树是平衡的BST树,添加一个元素以y为根节点的子树就不是平衡二叉树了

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_6.png" width="600"/></div>

- 右旋转操作I:**x.right=y**
<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_7.png" width="600"/></div>

- 右旋转操作II:**y.left=T3**
<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_8.png" width="600"/></div>

- 右旋转之后，就是平衡的BST：假设z节点的高度是(h+1),可以验证以x为根节点的BST树是平衡树

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_9.png" width="600"/></div>

- 代码实现：
```java
// 对节点y进行向右旋转操作，返回旋转后新的根节点x
//        y                              x
//       / \                           /   \
//      x   T4     向右旋转 (y)        z     y
//     / \       - - - - - - - ->    / \   / \
//    z   T3                       T1  T2 T3 T4
//   / \
// T1   T2
private Node rightRotate(Node y){
    Node x=y.left;
    Node T3=x.right;

    //向右旋转
    x.right=y;
    y.left=T3;

    //维护树的高度
    y.height=1 + Math.max(getNodeHeight(y.left),getNodeHeight(y.right));
    x.height=1 + Math.max(getNodeHeight(x.left),getNodeHeight(x.right));

    return x;
}

private Node add(Node node,K key,V value){
    if(node==null){
        size++;
        return new Node(key,value);
    }
    if(key.compareTo(node.key)<0){
        node.left=add(node.left,key,value);
    }else if(key.compareTo(node.key)>0){
        node.right=add(node.right,key,value);
    }else{
        node.value=value;
    }
    //更新height
    node.height=1+Math.max(getNodeHeight(node.left),getNodeHeight(node.right));
    //计算平衡因子
    int balancedFactor=getBalancedFactor(node);
    if(Math.abs(balancedFactor)>1){
        System.out.println("unblance:"+balancedFactor);
    }

    //平衡维护-->右旋转
    if(balancedFactor>1 && getBalancedFactor(node.left)>=0){
        return rightRotate(node);
    }
    
    return node;
}
```

- AVL的左旋转

```java
// 对节点y进行向左旋转操作，返回旋转后新的根节点x
//    y                             x
//  /  \                          /   \
// T1   x      向左旋转 (y)       y     z
//     / \   - - - - - - - ->   / \   / \
//   T2  z                     T1 T2 T3 T4
//      / \
//     T3 T4
private Node leftRotate(Node y){
    Node x=y.right;
    Node T2=x.left;

    //向左旋转
    x.left=y;
    y.right=T2;

    //维护高度
    y.height=1+Math.max(getNodeHeight(y.left),getNodeHeight(y.right));
    x.height=1+Math.max(getNodeHeight(x.left),getNodeHeight(x.right));

    return x;
}

private Node add(Node node,K key,V value){
    if(node==null){
        size++;
        return new Node(key,value);
    }
    if(key.compareTo(node.key)<0){
        node.left=add(node.left,key,value);
    }else if(key.compareTo(node.key)>0){
        node.right=add(node.right,key,value);
    }else{
        node.value=value;
    }
    //更新height
    node.height=1+Math.max(getNodeHeight(node.left),getNodeHeight(node.right));
    //计算平衡因子
    int balancedFactor=getBalancedFactor(node);
    if(Math.abs(balancedFactor)>1){
        System.out.println("unblance:"+balancedFactor);
    }

    //平衡维护-->右旋转
    if(balancedFactor>1 && getBalancedFactor(node.left)>=0){
        return rightRotate(node);
    }
    if(balancedFactor<-1 && getBalancedFactor(node.right)<=0){
        return leftRotate(node);
    }
    return node;
}
```

## LR和RL
- LR
<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_10.png" width="400"/></div>

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_11.png" width="600"/></div>

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_12.png" width="600"/></div>

- RL
<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_13.png" width="400"/></div>

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_14.png" width="600"/></div>

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/dataStructure/avl//avl_15.png" width="600"/></div>

```java
//计算平衡因子
int balancedFactor=getBalancedFactor(node);
/*if(Math.abs(balancedFactor)>1){
    System.out.println("unblance:"+balancedFactor);
}*/

//平衡维护
//LL
if(balancedFactor>1 && getBalancedFactor(node.left)>=0){
    return rightRotate(node);
}
//RR
if(balancedFactor<-1 && getBalancedFactor(node.right)<=0){
    return leftRotate(node);
}
//LR
if(balancedFactor>1 && getBalancedFactor(node.left)<0){
    Node x=node.left;
    node.left=leftRotate(x);
    //LL
    return rightRotate(node);
}
//RL
if(balancedFactor<-1 && getBalancedFactor(node.right)>0){
    Node x=node.right;
    node.right=rightRotate(x);
    //RR
    return leftRotate(node);
}
```
## 删除元素

```java
//从AVL中删除值为key的元素
public V remove(K key) {
    Node node=getNode(root,key);
    if(node!=null){
        root=del(root,key);
        size--;
    }
    return null;
}

//获取Map中的最小的key
private Node min(Node node){
    if(node.left==null){
        return node;
    }
    return min(node.left);
}

//删除以node为根结点的Map中的key最小的元素
private Node delMin(Node node){
    if(node.left==null){
        Node nodeRight=node.right;
        node.right=null;
        size--;
        return nodeRight;
    }
    node.left=delMin(node.left);

    //更新height
    node.height=1+Math.max(getNodeHeight(node.left),getNodeHeight(node.right));

    //维护平衡
    return keepBalance(node);
}

////删除以node为根结点的Map中的键值为key的元素
private Node del(Node node, K key){
    if(node==null){
        return null;
    }
    //记录删除元素后，该BST的新的根节点
    Node retNode=null;
    if(key.compareTo(node.key)<0){
        node.left=del(node.left,key);
        retNode=node;
    }else if(key.compareTo(node.key)>0){
        node.right=del(node.right,key);
        retNode=node;
    }else{
        //节点node就是要删除的节点
        //该节点只右有子树
        if(node.left==null){
            Node rightNode=node.right;
            node.right=null;
            size--;
            retNode=rightNode;
        }else if(node.right==null){ //该节点只有左子树
            Node leftNode=node.left;
            node.left=null;
            size--;
            retNode=leftNode;
        }else{
            //删除既有左子树又有右子树的节点
            Node s=min(node.right);
            s.right=delMin(node.right);
            s.left=node.left;

            //删除node
            node.left=node.right=null;
            retNode=s;
        }
    }
    if(retNode==null){
        return retNode;
    }
    //更新height
    retNode.height=1+Math.max(getNodeHeight(retNode.left),getNodeHeight(retNode.right));

    //保持平衡
    return keepBalance(retNode);
}

//维护以node为根节点的二叉树是平衡二叉树
private Node keepBalance(Node node){
    //计算平衡因子
    int balancedFactor=getBalancedFactor(node);

    //平衡维护
    //LL
    if(balancedFactor>1 && getBalancedFactor(node.left)>=0){
        return rightRotate(node);
    }
    //RR
    if(balancedFactor<-1 && getBalancedFactor(node.right)<=0){
        return leftRotate(node);
    }
    //LR
    if(balancedFactor>1 && getBalancedFactor(node.left)<0){
        Node x=node.left;
        node.left=leftRotate(x);
        //LL
        return rightRotate(node);
    }
    //RL
    if(balancedFactor<-1 && getBalancedFactor(node.right)>0){
        Node x=node.right;
        node.right=rightRotate(x);
        //RR
        return leftRotate(node);
    }
    return node;
}
```

## 基于AVL树的集合和映射
- AVLMap

```java
public class AVLMap<K extends Comparable<K>,V> implements Map<K,V> {
    private AVLTree<K,V> avlTree;

    public AVLMap(){
        avlTree=new AVLTree<>();
    }

    @Override
    public void add(K key, V value) {
        avlTree.add(key,value);
    }

    @Override
    public V remove(K key) {
        return avlTree.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return avlTree.contains(key);
    }

    @Override
    public V get(K key) {
        return avlTree.get(key);
    }

    @Override
    public void set(K key, V newValue) {
        avlTree.set(key,newValue);
    }

    @Override
    public int getSize() {
        return avlTree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }
}
```
- AVLSet

```java
public class AVLSet<K extends Comparable<K>> implements Set<K>{
    private AVLTree<K,Object> avlTree;

    public AVLSet(){
        avlTree=new AVLTree<>();
    }

    @Override
    public void add(K k) {
        avlTree.add(k,null);
    }

    @Override
    public void remove(K k) {
        avlTree.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return avlTree.contains(k);
    }

    @Override
    public int getSize() {
        return avlTree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }
}
```