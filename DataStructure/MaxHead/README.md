#### 树的扩展
树这种数据结构在计算机领域有重要作用，之所以重要，是因为树可以产生很多扩展,面对不同的问题稍微改变或限制树这种数据结构的性质，从而产生不同的数据结构，高效解决不同的问题。

我们将通过以下四种不同的数据结构：
- 堆 
- 线段树 
- 字典树 
- 并查集。

通过这些不同的树的学习，希望大家可以体会到数据结构的灵活之处，以及设计数据结构时的思考。学到四种不同树形结构，也对数据结构有更深认识。

#### 优先队列

普通队列: 先进先出;后进后出

优先队列:出队顺序和入队顺序无关;优先级高者早出队和优先级相关 （医院，患者优先级）

**例子：**

1. 操作系统中任务的调度，动态选择优先级最高的任务执行。如果我们的任务不是动态，那么我们只需要一个排序算法就好了。
2. 游戏中的AI(LOL中的小兵)。同时面对好几个敌人时，使用优先队列，优先打优先级最高的(距离最近或者血最残)。新的敌人在靠近，动态的。

对于我们的优先队列来说，实现这些接口的时候，具体这些接口实现出来的功能会有区别。**最大的区别在于出队和队首元素是谁这两个操作**。此时出队元素应该是优先级最高的元素，队首的元素也是优先级最高的元素，而不是最早进入的那个。

可以使用不同的底层实现:

- 普通线性结构：
    有一项操作"出队"是O(n)级别的，对于n个元素来说，它的时间复杂度就是n^2级别的，相对来说就是一个很慢的了。无论是之前在集合映射中使用链表实现，或者是队列中使用动态数组，因为O(n)的操作而很慢。

- 顺序线性结构: 

    维持顺序需要O(n) 出队就只需要O(1)了

它们都有一方面的劣势，可以自己使用动态数组或链表进行实现作为练习,与堆进行比较。

堆的入队操作和出队操作都可以做到O(logn)级别。

#### 堆的基础表示

堆作为优先队列的底层实现，计算机科学领域，通常见到O(logn)都与树有关，不一定是显式的构造了一棵树;

比如排序中归并排序，快速排序都是nlogn级别的，在排序的过程中没有使用树这种数据结构，但是递归的过程其实形成了一棵递归树。

**堆本身也是一棵树，二叉堆(Binary Heap)**

二叉堆是一棵**完全二叉树**。


- **满二叉树**概念:

除了叶子节点，所有的节点的左右孩子都不为空，就是一棵满二叉树

- 完全二叉树:
不一定是一个满二叉树，但它不满的那部分一定在右下侧。

#### 二叉堆的性质
堆中某个节点的值总是不大于其父节点的值。(所有节点的值都大于等于它的孩子节点的值)

![image](http://myphoto.mtianyan.cn/20180814012310_h3hRKB_Screenshot.jpeg)

**这样得到的是最大堆，相应的我们也可以定义出最小堆**。某种程度上，最大堆和最小堆是可以统一的，因为什么是大，什么是小是我们可以定义的。

![image](http://myphoto.mtianyan.cn/20180814012700_nR5kUe_Screenshot.jpeg)

我们就可以用过这样的一个数组来表示这个完全二叉树。数组存储节点i的左右孩子的规律。

```
parent(i) = i/2

left child(i) = 2*i;
right child(i) = 2*i+1;
```

一般数组存储二叉堆由1开始，公式如上。如果从0开始，会有一个偏移。

```
parent(i) = (i-1)/2

left child(i) = 2*i+1;
right child(i) = 2*i+2;
```

#### 向堆中添加元素和Sift Up
Sift Up可以看做是堆中元素上浮的一个过程。

![image](http://myphoto.mtianyan.cn/20180814014648_k32MVZ_Screenshot.jpeg)

加入元素是非常简单的，直接添加进数组即可，但是可以看到，此时的树结构就不满足最大堆的要求: 父亲最大，16的儿子比16还大了。因此52要执行上浮操作。一个好好的堆怎么就出问题了呢？唯一的原因只会出现在52这个节点的新增，因此只需要将52不断的与它的父亲节点作比较，如果父亲小，就跟父亲互换，一直上浮互换，直到52不比自己的父亲大。

> 这里52 和 16 和 41 进行了两次互换操作。

#### 取出堆中最大元素和Sift Down

最大堆只能取最大的那个元素，数组为0的位置，拿出去之后。将两棵子树合并的操作比较麻烦。

此时可以这样操作:
>我们可以将最后一个元素填充到堆顶，然后不断的下沉这个元素。每次都与两个孩子进行比较，选择两个孩子中最大的那个元素，如果两个孩子中最大的那个元素比它自己还要大的话，那么它自己就和两个孩子中最大的那个交换位置。16的新位置有可能依然要下沉，再次与孩子比较。

![image](http://myphoto.mtianyan.cn/20180814021310_2Lmah2_Screenshot.jpeg)


### 测试
```java
public class Main {

    public static void main(String[] args) {

        int n = 1000000;

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for(int i = 0 ; i < n ; i ++)
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));

        int[] arr = new int[n];
        for(int i = 0 ; i < n ; i ++)
            arr[i] = maxHeap.extractMax();

        for(int i = 1 ; i < n ; i ++)
            if(arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");

        System.out.println("Test MaxHeap completed.");
    }
}
```
这里就是堆排序，对于100万数字进行了排序，速度还是很快的。真正的堆排序有优化空间，现在是把数据扔进去，再一个一个取出来，但是利用堆组织数据的思想，完全可以做到原地排序。

add和extractMax时间复杂度都是O(logn) 还是二叉树的高度级别O(h) 但是因为堆是一课完全二叉树，它永远不会退化成一个链表。

#### Heapify操作和replace操作

- replace:取出最大元素后，放入一个新元素

实现1: 可以先extractMax,再add,两次O(logn)的操作

实现2: 可以直接将堆顶元素替换以后Sift Down,一次O(logn)的操作

- heapify: 将任意数组整理成堆的形状

```
/**
     * 将任意数组整理成堆的形状
     *
     * @param arr
     */
    public MaxHeap(E[] arr) {
        data = new Array<>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--)
            siftDown(i);
    }
```
Heapify是倒着从最后一个元素的父亲节点开始倒着遍历进行siftdown操作。

#### 基于堆的优先队列

优先队列可以使用动态数组或链表，或者自己维护一个顺序的线性结构(动态数组或链表)。也是可以实现优先队列的，接口是完全一致，底层不同实现性能不同。

数组，入队O(1)复杂度，出队时寻找最大值O(n)复杂度。

顺序的线性结构 入队O(n) 出队O(1)

### LeetCode优先队列相关问题

#### 优先队列的经典问题 

在1000000个元素中选出前100名? 在N个元素中选出前M个元素

**关键是M是远远小于N的**； 

思路：

1. 排序，时间复杂度NlogN
2. 取出前一百个

针对排序算法可以有优化，使用优先队列:NlogM解决问题，效率更高。

优先队列处理思路：

1. 首先维护当前看到的前M个元素
2. 遍历一遍所有元素，如果新元素比优先队列中最小元素大，就替换进去。
3. 所以我们需要使用最小堆才能快速的看到前M个元素中最小的元素。不断替换掉前M中最小的那个。


#### 使用java标准库中的PriorityQueue

java的PriorityQueue内部默认是一个最小堆。

之前在我们自己的代码中，设定了一个属于自己的结构:```Freq implements Comparable<Freq>```。使它可比较，规定它的优先级定义很容易。

很多时候我们想改变的是**java标准库中类相应的比较方式**，自己设计的PriorityQueue是不胜任的。

Java设计的这个PriorityQueue为我们提供了一个解决方案。

```
private class Freq {

        public int e, freq;

        public Freq(int e, int freq) {
            this.e = e;
            this.freq = freq;
        }
    }

    private class FreqComparator implements Comparator<Freq> {

        @Override
        public int compare(Freq a, Freq b) {
            return a.freq - b.freq; // 对于char int型可以这样写。
        }
    }
```
FreqComparator类的比较器 实现Comparator接口; 重写compare方法。

优先队列在构造的时候是可以传入比较器的,这样写的好处，**java中传的是字符串对象，默认字典序比较，如果你有自己定制的字符串比较方案，定义自己的Comparator接口传给优先队列**。

我们自己的优先队列以及它的底层实现堆我们也可以采用这种设计。

因为这个FreqComparator就只会被用在传给优先队列进行设置，只使用这一次,可以使用匿名类。
```
        PriorityQueue<Freq> pq = new PriorityQueue<>(n1ew Comparator<Freq>() {
            @Override
            public int compare(Freq a, Freq b) {
                return a.freq - b.freq;
            }
        });
```

```
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return map.get(a) - map.get(b);
            }
        });
```

#### 和堆相关的更多话题
我们主要实现的是二叉堆。最容易拓展成d叉堆,层数更低，但是相应的代价，下沉时要考虑的节点变多了。

##### 广义队列: 优先队列



