public class LinkedList<E> {

    private Node dummyHead;  //链表的头部元素(虚拟头节点）
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

    /**
     * 链表无参构造函数
     */
    public LinkedList(){
        dummyHead = new Node(null, null);
        size = 0;
    }

    /**
     * 返回链表中元素的个数
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * 判断链表是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

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


    /**
     * 在链表头添加元素
     * @param e
     */
    public void addFirst(E e){
        add(0,e);
    }

    /**
     * 在链表末尾添加元素
     * @param e
     */
    public void addLast(E e){
        add(size, e);
    }

    /**
     * 获得链表第index个元素
     */
    public E get(int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Get failed, Illegal index!");
        }

        Node cur = dummyHead.next;   //虚拟节点的下一节点，这里要和前面插入元素做区分
        for(int i = 0; i < index; i++){
            cur = cur.next;
        }

        return cur.e;
    }

    /**
     * 获得第一个元素
     * @return
     */
    public E getFirst(){
        return get(0);
    }

    /**
     * 获得最后一个元素
     * @return
     */
    public E getLast(){
        return get(size-1);
    }

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

    /**
     * 删除第一个节点
     * @return
     */
    public E removeFirst(){
        return remove(0);
    }

    /**
     * 删除最后一个节点
     * @return
     */
    public E removeLast(){
        return remove(size-1);
    }



    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        Node cur = dummyHead.next;

        while (cur != null){
            str.append(cur + "->");
            cur = cur.next;
        }
        str.append("NULL");
        return str.toString();
    }

}
