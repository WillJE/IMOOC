public class ArrayQueue<E> implements Queue<E> {

    private Array<E> array;

    /**
     * 有参的构造
     * @param capacity
     */
    public ArrayQueue(int capacity){
        array = new Array<E>(capacity);
    }

    /**
     * 无参的构造函数
     */
    public ArrayQueue(){
        array = new Array<E>();
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

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue();

        for(int i = 0; i < 10; i++){
            arrayQueue.enqueue(i);
            System.out.println(arrayQueue);

            if(i % 3 == 2){
                arrayQueue.dequeue();
                System.out.println(arrayQueue);
            }
        }
    }
}
