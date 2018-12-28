#### 数据结构之二分搜索树

##### 定义
二分搜索树（`BST`:Binary search tree）拥有二叉树的所有定义，此外它还具有其特殊的属性：

二分搜索树的**每个节点**的值：

- 大于其左字树的所有节点的值
- 小于其右子树的所有节点的值

即，每一个子树也是一个二分搜索树。

二分搜索树能高效的提高对数据的查找效率，但是首先要保证存储的元素具有可比较性。这就需要实现`Comparable`接口。

##### 实现一个二分搜索树
首先实现一个内部类`Node`，来保存每个节点。
```java
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
}
```

##### 向二分搜索树中插入一个元素

本次实现的二分搜索树是一个不包含重复元素的树，因为在树的定义时，已经规定了，**每个节点大于其左子树，小于其右子树**。没有处理重复的情况。所以当有重复的元素进入时，不做任何操作。

当然，如果想实现能插入重复元素的二分搜索树，只需要重新定义：
**左子树小于等于节点；或者右子树大于等于节点；**

插入元素方法：
```java
/**
     * 插入元素
     */
    public void add(E e){
        if(root == null){
            root = new Node(e);
        }else{
            //先从根节点开始比较，如果e比根节点大，放右子树，如果比根节点小，放左子树，然后依次递归
            add(root, e);
        }
    }
```

插入元素的递归方法，写递归方法，分两个步骤，首先确定递归的终止条件，接着写递归的具体逻辑。
```java
    /**
     * 向以node为根的左子树中，新增元素的递归方法
     * @param node
     * @param e
     */
    private void add(Node node, E e){
        //写递归函数，分两部分写，先写递归终止的条件
        if(e.equals(node.e)){
            return;
        }else if(e.compareTo(node.e) < 0 && node.left == null){
            node.left = new Node(e);
            size++;
            return;
        }else if(e.compareTo(node.e) > 0 && node.right == null){
            node.right = new Node(e);
            size++;
            return;
        }

        //递归终止条件写完，开始写递归的情况
        if(e.compareTo(node.e) < 0){
            add(node.left, e);
        }else{
            //不用比较等于了，因为等于节点的情况在上面判断了
            add(node.right, e);
        }
    }
```

##### 插入元素改进版本

在上一个版本中，功能能够实现，但是显得代码比较冗余，有很多重复的判断，显得不够优雅。
这里直接将判断根节点是否为空放到递归函数中去处理。
```java
/**
     * 插入元素
     */
    public void add(E e){
        root = add(root, e);
    }
```

递归增加函数，首先还是先写递归的终止条件，即只要找到了为空的节点，就可以往这个节点插入这个元素了。
接下来写递归的具体逻辑，分两个判断，左子树和右子树。
```java
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
```

##### 二分搜索树的查询

查询方法和插入方法非常类似，掌握了插入方法，理解查询方法也非常简单。
```java
/**
     * 查询树是否含有某个元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root, e);
    }
```

递归方法
```java
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
```
##### 二分搜索树的遍历
- 前序遍历
```java
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
```

改造的toString方法，用于输出二分搜索树的结构：
```java
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
```
一个这样的二叉树：
```java
		/////////////////
        //      5      //
        //     / \     //
        //    3  6     //
        //   / \  \    //
        //  2   4  8   //
        /////////////////
```
输出结果为

```java
5
--3
----2
------null
------null
----4
------null
------null
--6
----null
----8
------null
------null
```

- 中序遍历
```java
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
```

**中序遍历得到的结果是有序的，由于二分搜索树的性质，左子树小于节点，右子树大于节点，所以中序遍历得到的结果一定是有序的。**

#### 前序遍历的非递归方法

对于前序遍历的非递归方法，需要用到栈作为辅助的数据结构。

思路是：
1. 先将根节点压入栈
2. 从栈顶出栈元素，并输出
3. 由于栈是先进后出的数据结构，所以判断如果出栈元素右子节点有元素则入栈，同理再判断左节点。

循环上述2，3步骤，直到栈为空，则能实现一个非递归的前序遍历。

```java
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
```

#### 层序遍历

层序遍历也叫广度优先遍历，需要用到队列来完成。

#### 深度优先遍历和广度优先遍历
二叉树的深度优先遍历（DFS）与广度优先遍历（BFS）

深度优先遍历：从根节点出发，沿着左子树方向进行纵向遍历，直到找到叶子节点为止。然后回溯到前一个节点，进行右子树节点的遍历，直到遍历完所有可达节点为止。

广度优先遍历：从根节点出发，在横向遍历二叉树层段节点的基础上纵向遍历二叉树的层次。

![image](https://img-blog.csdn.net/20170514174642802?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbWluZ3dhbmdhbnl1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

DFS:ABDECFG

BFS:ABCDEFG

#### 二分搜索树的删除操作

从最简单的，删除二分搜索树的最小值和最大值开始：最小值位于整棵树的最左下角，最大值位于整棵树的最右下角。

##### 求二分搜索树的最小值，和最大值

```
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

```

##### 删除二分搜索树最大值和最小值并返回


```/**
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

```

#### 二分搜索树中删除任意值

在二分搜素树中不能随便删除节点，因为你不确定该节点是否有孩子节点。

所以，针对删除节点，一共有以下几种情况：
1. 删除只有左孩子的节点
2. 删除只有右孩子的节点
3. 删除没有左孩子也没有右孩子的节点，即叶子节点
4. 删除既有左孩子又有右孩子的节点


前面三种情况比较好处理，分别是将该节点删除后，将其左孩子或右孩子拼接回原树即可。

比较复杂的是第四种情况。

处理这个问题，关键就是要找到一个节点作为新的根节点。

- **找到删除节点的右子树的最小值作为新的树的根节点**，（因为新的子树也必须满足二分搜索树的定义，根节点大于左子树，小于右子树。而右子树的最小节点即满足）


```
/**
     * 从二分搜索树中删除元素为e的节点
     */
    public void remove(E e){
        root = remove(root, e);
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
```



