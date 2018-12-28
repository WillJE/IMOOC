#### 数据结构之队列和循环队列

队列的操作也可以看做是数组的子集，和栈不同的是，队列是先进先出，即`First in first out(FIFO)`。对于队列的操作，一般有入队，出队，查看队首等操作。

首先，先创建一个队列接口。
```java
public interface Queue<E> {
    int getSize();  //队列元素个数

    boolean isEmpty();  //判断队列是否为空

    void enqueue(E e);  //入队

    E dequeue();  //出队

    E getFront();  //查看队首元素
}
```

接下来具体实现这个队列类，还是使用之前创建的动态数组来实现一个队列。

```java
public class ArrayQueue<E> implements Queue<E> {

    private Array<E> array;

    /**
     * 有参的构造
     * @param capacity
     */
    public ArrayQueue(int capacity){
        array = new Array<>(capacity);
    }

    /**
     * 无参的构造函数
     */
    public ArrayQueue(){
        array = new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public String toString(){
        StringBuffer rst = new StringBuffer();
        rst.append("Queue：");

        rst.append("front [");
        for(int i = 0; i < array.getSize(); i++){
            rst.append(array.get(i));
            if(i != array.getSize()-1){
                rst.append(",");
            }
        }
        rst.append("] rear/tail");
        return rst.toString();
    }
}
```

#### 循环队列

正常的队列的操作很简单，但是由于简单队列实际上是动态数组，队列在每次出队的时间复杂度是`O(n)`级别的（将出队后面的元素依次往前移动一位），而且对于出队后的元素，由于队列只能在队尾增加元素，所以前面出队的空出来的元素都浪费了空间，于是，我们设计出了循环队列（可以将他理解为时钟）。

循环队列相对普通队列新增了一个队首的指针`front`，队尾的指针为`tail`，即初始化时队列的队首和队尾指向同一个位置。

在入队时，队尾的指针不再是向后加一位，而是在向后加一位的基础上还要对队列的长度取余运算。因为当队尾的指针在最后一位时，如果此时队列未满，即表明队列前方还有空余的空间，所以此时队尾需要对队列长度取余回到队列头部空余的位置。

具体代码如下：
```java
public class LoopQueue<E> implements Queue<E>  {

    private E[] data; //存放数据的数组
    private int front; //队首指针
    private int tail;  //队尾（队列第一个没有元素的位置）指针
    private int size;  //队列内元素个数

    /**
     * 有参构造函数
     * @param capacity
     */
    public LoopQueue(int capacity){
        data = (E[])new Object[capacity + 1];  //因为循环队列下会浪费一个元素空间
        front = 0;
        tail = 0;
        size = 0;
    }

    /**
     * 无参构造
     */
    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail ;   //判断循环队列是否为空，即判断队尾和队首是否指向同一处
    }

    /**
     * 入队
     * @param e
     */
    @Override
    public void enqueue(E e) {
        if((tail + 1) % data.length == front){   //队列满的条件
            resize(getCapacity() * 2);  //扩容，容积为当前数据容积的两倍
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }

    private void resize(int newCapacity){
        //先新建一个newCapacity的数组
        E[] newData = (E[]) new Object[newCapacity + 1 ];

        //将原循环队列中的元素放入新的队列中
        for(int i = 0; i < size; i++){
            //因为是循环队列，队列队首元素有可能不是原本队列的首元素
            newData[i] = data[(front + i) % data.length];
        }

        data = newData;
        front = 0;
        tail = size;  //size是循环队列中元素的个数，扩容操作不会影响size，因为没有元素入队或者出队
    }

    /**
     * 出队
     * @return
     */
    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot not dequeue from empty queue");
        }

        E ret = data[front];
        data[front] = null;  //因为出队了，将出队后的元素置为Null
        front = (front + 1) % data.length;
        size --;
        //如果出队的元素很多，队列中有很多空间浪费，则进行缩容
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0){
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty");
        }
        return data[front];
    }

    @Override
    public String toString(){
        StringBuffer rst = new StringBuffer();
        rst.append(String.format("Queue: size = %d, Array: capacity = %d\n", size, getCapacity()));

        rst.append("front [");
        for(int i = front; i != tail; i = (i + 1) % data.length ){
            rst.append(data[i]);
            if((i + 1) % data.length != tail ){
                rst.append(", ");
            }
        }
        rst.append("] tail");
        return rst.toString();
    }
}
```

使用了循环队列后，入队和出队操作的时间复杂度就都为O（1）了（均摊的情况下）。
接下来用一个实例来测试一下：
```java
public static void main(String[] args) {
        int count = 100000;

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = testQueue(arrayQueue, count);

        System.out.println("ArrayQueue,time:"+ time1 +"s");

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double time2 = testQueue(loopQueue, count);
        System.out.println("LoopQuere,time:" + time2 + "s");
    }

    public static double testQueue(Queue<Integer> q, int opCount){

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0; i < opCount; i++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i < opCount; i++){
            q.dequeue();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0 ;
    }
```
测试结果：
>ArrayQueue,time:5.760347271s
LoopQuere,time:0.02244578s

这只是在十万条数据的处理下的差异，如果数量越多，这两种数据结构间的差异将更加明显。由此可见，一个好的数据结构对算法处理性能的提升是十分巨大的。

#### 队列的应用




