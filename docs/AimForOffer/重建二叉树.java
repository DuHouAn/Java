/**
 * Created by 18351 on 2019/7/23.
 */
public class 重建二叉树 {
    public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
        return reConstructBinaryTree(pre,in,0,pre.length-1,0,in.length-1);
    }

    private TreeNode reConstructBinaryTree(int[] pre,int[] in,
                                           int preStart,int preEnd,
                                           int inStart,int inEnd){
        if(preStart>preEnd || inStart>inEnd){
            return null;
        }
        TreeNode root = new TreeNode(pre[preStart]);

        int index = -1;
        for(int i=inStart;i<=inEnd;i++){
            if(in[i]==pre[preStart]){
                index = i;
                break;
            }
        }
        root.left = reConstructBinaryTree(pre,in,
                preStart+1,preStart+(index-inStart),inStart,index-1);
        root.right = reConstructBinaryTree(pre,in,
                preStart+(index-inStart)+1,preEnd,index+1,inEnd);
        return root;
    }
}
