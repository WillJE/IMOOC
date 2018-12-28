public class Array<E> {
    private E[] data;
    private int size;  //数组内数据长度

    public Array(int capacity){
        data = (E[])new Object[capacity];
        size = 0;
    }

    public Array(){
        this(10);
    }

    //获取数组中元素的个数
    public int getSize(){
        return size;
    }

    //返回数组容量
    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //往数组的末尾添加一个元素
    public void addLast(E v){
        add(size, v);
    }

    //往数组的首部添加一个元素
    public void addFirst(E v){
        add(0,v);
    }

    //给数组任意位置新增一个元素
    public void add(int index, E v){
        if(index < 0|| index > size){
            throw new IllegalArgumentException("add failed, index should >= 0 and > size");
        }

        if(size == data.length){
            resize(2 * data.length);
        }

        for(int i = size-1; i >= index; i--){
            data[i+1] = data[i];
        }
        data[index] = v;
        size++;
    }

    //对数组扩容
    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity];
        for(int i=0 ;i<size; i++){
            newData[i] = data[i];
        }
        data = newData;
    }

    //返回index索引位置的元素
    public E get(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        return data[index];
    }

    //修改index索引位置的元素
    public void set(int index, E v){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        data[index] = v;
    }


    //查找某个元素的索引
    public int find(E v){
        for(int i=0; i < size; i++){
            if(data[i].equals(v)){
                return i;
            }
        }
        return -1;
    }

    //判断数组是否含有某个元素
    public boolean contains(E v){
        for(int i=0; i < size; i++){
            if(data[i].equals(v)){
                return true;
            }
        }
        return false;
    }

    //删除任意索引的元素，并返回这个索引
    public E remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        E rst = data[index];
        for (int i=index+1; i<size; i++){
            data[i-1] = data[i];
        }
        size--;
        data[size] = null;

        if(size == data.length / 4 && data.length / 2 !=0){
            resize(data.length/2);
        }
        return rst;
    }

    //删除某个元素
    public void removeElement(E v){
        boolean isExist = contains(v);
        if(isExist){
            int index = find(v);
            remove(index);
        }
    }

    //删除第一个元素
    public E removeFirst(){
        return remove(0);
    }

    //删除最后一个元素
    public E removeLast(){
        return remove(size-1);
    }



    @Override
    public String toString(){
        StringBuffer rst = new StringBuffer();
        rst.append(String.format("Array: size = %d, Array: capacity = %d\n",size,data.length));

        rst.append("[");
        for(int i = 0; i < size; i++){
            rst.append(data[i]);
            if(i != size-1){
                rst.append(",");
            }
        }
        rst.append("]");
        return rst.toString();
    }
}
