#### 数据结构之数组

这个系列是在学习慕课网玩转数据结构课程的学习笔记，用`JAVA`语言来重新系统的整理一下数据结构的知识，算是温故而知新吧，只会写一些基础以上的一些内容或者我认为自己还不是很熟练的内容。

首先，数组是一个相对来说简单的数据结构，但是`java`JDK没有给我们很丰富的数组的API，此时我们可以通过自己自定义一个动态数组实现对数据的增删改查，来学习一下整个数组。

这个数组暂时没有使用泛型，类型为`int`，后面会再完善，先增加几个常用方法。

```java
public class Array {
    private int[] data;
    private int size;  //数组内数据长度，第一个不存在内容的下标

    public Array(int capacity){
        data = new int[capacity];
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
    public void addLast(int v){
        if(size == data.length){
            throw new IllegalArgumentException("addLast failed, data is fulled");
        }
        data[size] = v;
        size++;
    }

//往数组任意位置添加一个元素
    public void add(int index, int v){
        if(size == data.length){
            throw new IllegalArgumentException("addLast failed, data is fulled");
        }
        if(index < 0|| index > size){
            throw new IllegalArgumentException("add failed, index should >= 0 and > size");
        }

        for(int i = size-1; i >= index; i--){
            data[i+1] = data[i];
        }
        size++;
    }
}

```
#### 新增元素
根据上面的`add`方法，新增的方法原理为，将`index`索引以后的元素依次后退一位，并将`size++`。
然后根据这个方法，可以改造上方往数组末尾添加元素的方法和新增一个往数组首部新增一个元素的方法。
```java
    //往数组的末尾添加一个元素
    public void addLast(int v){
        add(size, v);
    }

    //往数组的首部添加一个元素
    public void addFirst(int v){
        add(0,v);
    }
```

#### 查询和修改元素
除了修改指定索引的元素之外，我们还重写了`tostring`方法，用于返回该数组。
```java
//返回index索引位置的元素
    public int get(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        return data[index];
    }

    //修改index索引位置的元素
    public void set(int index, int v){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        data[index] = v;
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
```
#### 包含，搜索和删除
删除某个元素，实现原理是将需要删除的元素的索引后面的每个元素依次往前前进一位，同时执行完成之后，`size--`。
同理，根据这个方法可以很轻松的实现删除第一个和删除最后一个元素的方法。

另外，这些方法也有很多局限性，因为数组是可重复的，但是查找和包含的方法只能查找到第一个元素，同理，删除也只能删除一个元素，如果需要删除所有的相同元素可以新增`API`方法或者选用其它的数据结构。


```java
//判断数组是否含有某个元素
    public boolean contains(int v){
        for(int i=0; i < size; i++){
            if(data[i] == v){
                return true;
            }
        }
        return false;
    }

    //删除任意索引的元素，并返回这个索引
    public int remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("get failed, Index is illegal");
        }
        int rst = data[index];
        for (int i=index+1; i<size; i++){
            data[i-1] = data[i];
        }
        size--;
        return rst;
    }

    //删除某个元素
    public void removeElement(int v){
        boolean isExist = contains(v);
        if(isExist){
            int index = find(v);
            remove(index);
        }
    }

    //删除第一个元素
    public int removeFirst(){
        return remove(0);
    }

    //删除最后一个元素
    public int removeLast(){
        return remove(size-1);
    }
```

#### 改造成泛型
使用泛型，可以使得该数据类型能放置**“任意”**的数据类型，该任意打了双引号，是因为这里的数据类型不能是基本数据类型，只能是类对象。

基本数据类型包括
`boolean, byte,char,short,int,long,float,double`

但是，对于基本数据类型，可以使用他们的包装类，也就是：
`Boolean,Byte,Char,Short,Int,Long,Float,Double`.

以下是改造成泛型类后的代码，在构造函数中，不支持直接`new`一个泛型类型的对象，这里需要转一下弯，即先构造一个`Object`类型的数组再强转为泛型类型的即可。
```java
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
        if(size == data.length){
            throw new IllegalArgumentException("addLast failed, data is fulled");
        }
        if(index < 0|| index > size){
            throw new IllegalArgumentException("add failed, index should >= 0 and > size");
        }

        for(int i = size-1; i >= index; i--){
            data[i+1] = data[i];
        }
        data[index] = v;
        size++;
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
```

#### 动态数组
数组本身不支持扩容，并且数组在声明时必须指定长度，这对用户的使用上来说有很大的不便。

于是，我们可以定义一种数组，使其支持动态的扩容和缩小容量，形成真正意义上的动态数组。

此时新增方法修改为这样：
```java
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
```
这里，新增的容量的大小最好定义为当前容量的两倍，或者其他倍数，而不要使用10，100或者1000这样的固定长度。

> ArrayList中的倍数为1.5

新增一个`resize`方法，对数组容量重新赋值。
```java
    //对数组容量进行修改
    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity];
        for(int i=0 ;i<size; i++){
            newData[i] = data[i];
        }
        data = newData;
    }
```
另外，在删除方法中，如果删除了数组中大部分元素，即该数组有大部分空间冗余，为了节省空间需要对多余的空间进行回收。

于是可以修改删除的方法，判断当当前数组元素只有数组长度的一般时，对数组长度缩短为当前长度的一半。

```java
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

        if(size == data.length/2){
            resize(data.length/2);
        }
        return rst;
    }
```

但是这样写在进行复杂度分析时会出现一种极端情况，即刚删除了一半空间后，马上插入一个元素后又会执行新增空间的操作，然后用户再执行了删除元素的操作，此时代码又执行了一遍删除空间。

这样的代码效率很低，因为内存在频繁的新增和删除空间，此时只需要将删除空间的判断逻辑改为当前长度的1/4即可，此时，如果用户还出现上述操作，也不会出现频繁的新增和删除空间的操作了。

```java
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
```







