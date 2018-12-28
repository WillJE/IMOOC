import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//二分搜索树
public class BST<E extends Comparable<E>> {
    private class Node{
        public  E e;
        public Node left,right;

        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    /**
     * 返回树中元素个数
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 判断树是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 插入元素
     */
    public void add(E e){
        root = add(root, e);
    }

    /**
     * 向以node为根的左子树中，新增元素的递归方法
     * 返回插入新节点之后二分搜索树的根
     * @param node
     * @param e
     */
    private Node add(Node node, E e){
        //写递归函数，分两部分写，先写递归终止的条件
        if(node == null){
            size++;
            return new Node(e);
        }

        //递归终止条件写完，开始写递归的情况
        if(e.compareTo(node.e) < 0){
            node.left = add(node.left, e);
        }else if(e.compareTo(node.e) > 0){
            //不用比较等于了，因为等于节点的情况在上面判断了
            node.right = add(node.right, e);
        }
        return node;
    }

    /**
     * 查询树是否含有某个元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root, e);
    }

    /**
     * 查询树是否含有某个元素，递归方法
     * @param e
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node, E e){
        //递归的终止条件，下一个节点为空，没有找到节点，直接返回false
        if(node == null){
            return false;
        }

        if(e.compareTo(node.e) == 0 ){
            return true;
        }else if(e.compareTo(node.e) < 0 ){
            return contains(node.left,e);
        }else{
            return contains(node.right, e);
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的二分搜索树，递归算法
     */
    private void preOrder(Node node){
        //先写遍历终止条件
        if(node == null)
            return;

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    //前序遍历的非递归遍历
    public void preOrderNR(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);

            if(cur.right!=null)
                stack.push(cur.right);
            if(cur.left!=null)
                stack.push(cur.left);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder(){
        inOrder(root);
    }

    /**
     * 中序遍历递归算法
     * @param node
     */
    private void inOrder(Node node){
        if(node == null){
            return;
        }

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 后序遍历递归算法
     * @return
     */
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    //层序遍历
    public void levelOrder(){
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()){
            Node curNode = q.remove();

            System.out.println(curNode.e);
            if(curNode.left!=null)
                q.add(curNode.left);
            if(curNode.right!=null)
                q.add(curNode.right);
        }
    }

    /**
     * 从二分搜索树中删除元素为e的节点
     */
    public void remove(E e){
        root = remove(root, e);
    }

    /**
     * 寻找二分搜索树的最小元素(面向用户)
     *
     * @return
     */
    public E minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty");

        Node minNode = minimum(root);
        return minNode.e;
    }

    /**
     * 返回以node为根的二分搜索树的最小值所在的节点
     *
     * @param node
     * @return
     */
    private Node minimum(Node node){
        if( node.left == null )
            return node;

        return minimum(node.left);
    }

    /**
     * 寻找二分搜索树的最大元素（面向用户）
     *
     * @return
     */
    public E maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty");

        return maximum(root).e;
    }

    /**
     * 返回以node为根的二分搜索树的最大值所在的节点
     *
     * @param node
     * @return
     */
    private Node maximum(Node node){
        if( node.right == null )
            return node;

        return maximum(node.right);
    }

    /**
     * 从二分搜索树中删除最小值所在节点, 返回最小值
     *
     * @return
     */
    public E removeMin(){
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    /**
     * 删除掉以node为根的二分搜索树中的最小节点 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 从二分搜索树中删除最大值所在节点
     * @return
     */
    public E removeMax(){
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    /**
     * 删除掉以node为根的二分搜索树中的最大节点 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @return
     */
    private Node removeMax(Node node){

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    /**
     *  删除掉以node为根的二分搜索树中值为e的节点, 递归算法 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @param e
     * @return
     */
    private Node remove(Node node, E e){

        if( node == null )
            return null;

        if( e.compareTo(node.e) < 0 ){
            node.left = remove(node.left , e);
            return node;
        }
        else if(e.compareTo(node.e) > 0 ){
            node.right = remove(node.right, e);
            return node;
        }
        else{   // e.compareTo(node.e) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }


    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        generateBSTString(root,0,str);
        return str.toString();
    }

    /**
     * 生成以node为节点，深度为depth的描述二叉树的depth
     * @param node
     * @param depth
     * @param str
     * @return
     */
    private void generateBSTString(Node node, int depth, StringBuilder str){
        if(node == null){
            str.append(generateDepthString(depth)+"null\n");
            return;
        }

        str.append(generateDepthString(depth) + node.e + "\n");
        generateBSTString(node.left, depth+1,str);
        generateBSTString(node.right, depth+1,str);
    }

    private String generateDepthString(int depth){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < depth; i++){
            str.append("--");
        }
        return str.toString();
    }

}
