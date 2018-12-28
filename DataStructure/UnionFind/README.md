#### 定义
另外一种特殊的树结构: 并查集一种很不一样的树形结构

**前面我们接触的树结构都是由父亲指向孩子，但是我们的并查集却是由孩子指向父亲**。这种奇怪的树结构可以非常高效的回答一类问题: **连接问题 Connectivity Problem**

![image](http://myphoto.mtianyan.cn/20180815004608_eu7qOk_Screenshot.jpeg)


如上图一张图中，有很多点，**每两个点之间有没有连接的问题**。即给出任意两点是否有路径相连。

并查集可以非常快的查看**网络**中节点间的连接状态。

网络是个抽象的概念:用户之间形成的网络

并查集 还是数学中的集合类实现,主要操作在求集合的并集。

#### 连接问题 和 路径问题

存在路径一定连接， 不存在路径一定不连接。

回答两个节点之间的连接问题是要比回答路径问题要回答的问题少。

> 连接问题只需要回复是或不是，路径问题，需要返回一个具体的路径。 只想知道连接状态，求解路径会消耗性能。

完全可以使用复杂度更高的算法进行求解，但是之所以复杂度更高，**其实是因为求解出了很多我们问的问题并不关心的内容**。

并查集Union Find 对于一组数据，主要支持两个动作:
```
union(p, q)
isConnected( p, q)
```

逐步优化我们的并查集，首先设计一个并查集接口。
```
public interface UF {
    int getSize(); // 对当下这些元素

    boolean isConnected(int p, int q); // id为p id为q是否相连

    void unionElements(int p, int q);
}
```

#### 并查集的实现
```
/**
 * 我们的第一版Union-Find
 */
public class UnionFind1 implements UF {

    private int[] id;    // 我们的第一版Union-Find本质就是一个数组

    public UnionFind1(int size) {

        id = new int[size];

        // 初始化, 每一个id[i]指向自己, 没有合并的元素
        for (int i = 0; i < size; i++)
            id[i] = i;
    }

    @Override
    public int getSize() {
        return id.length;
    }

    /**
     * 查找元素p所对应的集合编号 O(1)复杂度
     *
     * @param p
     * @return
     */
    private int find(int p) {
        if (p < 0 || p >= id.length)
            throw new IllegalArgumentException("p is out of bound.");

        return id[p];
    }

    /**
     * 查看元素p和元素q是否所属一个集合 O(1)复杂度
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 合并元素p和元素q所属的集合 O(n) 复杂度
     *
     * @param p
     * @param q
     */
    @Override
    public void unionElements(int p, int q) {

        int pID = find(p);
        int qID = find(q);

        if (pID == qID)
            return;

        // 合并过程需要遍历一遍所有元素, 将两个元素的所属集合编号合并
        for (int i = 0; i < id.length; i++)
            if (id[i] == pID)
                id[i] = qID;
    }
}
```

第一版，quick-find下unionElements需要O(n)的复杂度。

使用Quick Union这个版本，标准的并查集实现。思路: **将每一个元素，看做是一个节点**

用一个动画展示就是：
![并查集.gif](https://i.loli.net/2018/12/03/5c04ce040e6bf.gif)


#### 基于size的优化
第一版就是数组。

第二版形成了树结构，孩子指向父亲。通过节点查到根节点。


