public class ArrayStack<E> implements Stack<E> {
    Array<E> array;

    //有参的构造函数
    public ArrayStack(int capacity){
        array = new Array<E>(capacity);
    }

    //无参的构造函数
    public ArrayStack(){
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
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public String toString(){
        StringBuilder rst = new StringBuilder();
        rst.append("Stack:");
        rst.append("[");
        for(int i = 0; i < array.getSize(); i++){
            rst.append(array.get(i));

            if(i != array.getSize()-1){
                rst.append(",");
            }
        }
        rst.append("] top");
        return rst.toString();
    }
}
