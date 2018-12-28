#### 数据结构之链表

前面我们学习了三种线性结构的数据结构，动态数组，栈和队列，但是这三种数据结构其实说到底都是数组，即时动态数组也是数组，他只不过是通过`resize()`来重新分配存储空间。

接下来学习另外一个线性数据结构**链表**（`LinkList`），就是真正的动态数据结构。

首先看一下他的基本结构：
```java
class Node{
	E e;
	Node next;
}
```

每一个数据节点都保存着下一个节点的连接，但是这样的连接不能无限，所以当一个节点的`next`指向`null`时，这个节点即为末尾节点。

#### 链表和数组比较
链表优点：
- 真正的动态，不需要处理固定容量的问题

链表的缺点：
- 丧失了随机访问的能力
数组是在创建时即在内存中开辟了一段连续的空间，所以数组可以很容易的通过下标找到元素，但是链表是通过每个元素之间的连接连起来的，在内存中不是连续的空间，所以失去了随机访问的能力。

#### 链表的实现

首先创建一个`LinkedList`类，支持泛型，使用内部类`Node`保存节点元素和下一个节点。

```java
public class LinkedList<E> {

    private Node head;  //链表的头部元素
    private int size;   //链表的元素数量

    private class Node{
        public E e;
        public Node next;

        public Node(E e, Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }

        @Override
        public String toString(){
            return e.toString();
        }
    }
}
```

**新增节点**
这里有两点需要注意，第一点是新增节点时，如果在头节点新增时，需要特殊处理一下，因为头节点没有`pre`节点，后续我们会使用**虚拟头节点**来解决这个问题。

第二点是，在`index`处插入节点，是先将新增节点的下一节点链接到`pre`节点的`next`节点，再将`pre`节点链接到新增节点。**两种操作的顺序不能错。**
```java
/**
     * 在索引index处插入一个元素
     * 这不是链表的常规操作，因为使用链表就表示要摈弃索引
     * @param index
     * @param e
     */
    public void add(int index, E e){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed, Illegal index!");
        }
        if(index == 0 ){
            addFirst(e);
        }else{

            Node pre = head;
            for(int i = 0; i < index - 1; i++){
                pre = pre.next;
            }

//            Node node = new Node(e);
//            node.next = pre.next;
//            pre.next = node;

            //同理，以上三行代码也可以用下面一行表示
            pre.next = new Node(e, pre.next);
            size ++;
        }
    }
```

有了上面的方法能轻松实现在链表头和链表尾新增节点。
```java
    /**
     * 在链表头添加元素
     * @param e
     */
    public void addFirst(E e){
//        Node node = new Node(e);
//        node.next = head;
//        head = node;
        //以上三行代码可以用下面一行代替
        head = new Node(e, head);

        size ++;
    }

    /**
     * 在链表末尾添加元素
     * @param e
     */
    public void addLast(E e){
        add(size, e);
    }
```

#### 使用链表的虚拟头节点
首先修改链表的构造函数，修改节点类`dummyHead`，此时这个虚拟头节点不再是`null`了，而是新增一个节点，初始化其值和下一节点都为`null`。
```java
public class LinkedList<E> {
	private Node dummyHead;  //链表的头部元素(虚拟头节点）
	private int size;   //链表的元素数量
    /**
    * 链表无参构造函数
    */
    public LinkedList(){
        dummyHead = new Node(null, null);
        size = 0;
    }
}
```

于是，新增节点可以修改为
```java
/**
     * 在索引index处插入一个元素
     * 这不是链表的常规操作，因为使用链表就表示要摈弃索引
     * @param index
     * @param e
     */
    public void add(int index, E e){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed, Illegal index!");
        }
        Node pre = dummyHead;
        for(int i = 0; i < index; i++){
            pre = pre.next;
        }

//      Node node = new Node(e);
//      node.next = pre.next;
//      pre.next = node;

        //同理，以上三行代码也可以用下面一行表示
        pre.next = new Node(e, pre.next);
        size ++;
    }
```

修改链表`index`索引下的值：
```java
/**
     * 修改index索引下的值
     * @param index
     * @param e
     */
    public void set(int index, E e){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Set failed, Illegal index!");
        }

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++){
            cur = cur.next;
        }
        cur.e = e;
    }
```

判断链表中是否存在某个元素
```java
/**
     * 判断链表中是否存在某个元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        Node cur = dummyHead.next;
        while(cur.next !=null){
            if(cur.e.equals(e)){
                return true;
            }
            cur.next = cur;
        }
        return false;
    }
```

以上，对于链表的增，查，改操作都实现了，接下来是对于链表的删除操作。

链表的删除操作，对于链表的删除，首先要找到需要删除链表的前置元素`pre`，再将该元素的`next`指向删除元素的`next`，最后将删除的元素脱离链表，以便GC将内存回收。

```java
/**
     * 删除某个索引下的值
     * @param index
     * @return
     */
    public E remove(int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Remove failed, Illegal index!");
        }
        //先找到需要删除的索引的前一个元素pre
        Node pre = dummyHead;
        for( int i = 0; i < index; i++){
            pre = pre.next;
        }
        Node retNode = pre.next;  //删除的节点，需要返回
        pre.next = retNode.next;
        retNode.next = null;  //被删除节点脱离链表，被垃圾回收机制回收
        size --;


        return retNode.e;
    }
```








