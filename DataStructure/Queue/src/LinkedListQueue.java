public class LinkedListQueue<E> implements Queue<E> {

    /*
    内部node类
     */
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

    private Node head,tail;
    private int size;

    /**
     * 无参构造函数
     */
    public LinkedListQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 入队操作
     * @param e
     */
    @Override
    public void enqueue(E e) {
        //先判断队尾是否空，如果为空需要新建一个Node并把值传入
        //如果队尾为空，队首肯定也为空，所以也要维护一下队首
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else{
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    /**
     * 出队操作
     * @return
     */
    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from empty queue");
        }
        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if(head == null)
            tail = null;
        size --;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Empty queue");
        }
        return head.e;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Queue: front ");
        Node cur = head;
        while (cur != null){
            str.append(cur + "->");
            cur = cur.next;
        }
        str.append("Null tail");
        return str.toString();
    }

    public static void main(String[] args) {
        LinkedListQueue<Integer> queue = new LinkedListQueue();

        for(int i = 0; i < 10; i++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
