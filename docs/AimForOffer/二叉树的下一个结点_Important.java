import java.util.Scanner;

/**
 * Created by 18351 on 2019/7/23.
 */
public class 二叉树的下一个结点_Important {
    //二叉树的下一个结点
    public TreeLinkNode GetNext(TreeLinkNode pNode){
        if(pNode==null){
            return null;
        }
        if(pNode.right!=null){
            //先判断 pNode 的右子树不为 null,
            //pNode 的下一个结点就是 pNode 右子树的最左结点
            TreeLinkNode node = pNode.right; //pNode 的右子树
            while(node.left!=null){
                node = node.left;
            }
            return node;
        }else{   //否则，向上找第一个左连接指向的树包含该节点的祖先节点
            while (pNode.next!=null){
                TreeLinkNode parent = pNode.next;
                if(parent.left==pNode){
                    return parent;
                }
                pNode = pNode.next;
            }
        }
        return null;
    }
}
