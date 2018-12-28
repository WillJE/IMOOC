### 集合
承载元素的容器，元素的去重操作。回忆我们上一小节实现的二分搜索树是不能盛放重复元素的，非常好的实现“集合”的底层数据结构。
```
Set<E>
  void add(E)
  void remove(E)
  boolean contains(E)
  int getSize()
  boolean isEmpty()
```
添加元素，删除元素，是否包含，大小，是否为空。连续add两次，只保留一份。典型应用: 客户统计。典型应用：词汇量统计。

BST依然是上一章实现的BST
```

public class BSTSet<E extends Comparable<E>> implements Set<E> {
    private BST<E> bst;

    public BSTSet() {
        bst = new BST<>();
    }

    @Override
    public void add(E e) {
        bst.add(e); // 本身就可以对于重复不理会
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contanins(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.getSize();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
```

```

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * 文件相关操作类
 */
public class FileOperation {

    /**
     * 读取文件名称为filename中的内容，并将其中包含的所有词语放进ArrayList words中
     *
     * @param filename
     * @param words
     * @return
     */
    public static boolean readFile(String filename, ArrayList<String> words){

        if (filename == null || words == null){
            System.out.println("filename is null or words is null");
            return false;
        }

        // 文件读取
        Scanner scanner;

        try {
            File file = new File(filename);
            if(file.exists()){
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                scanner.useLocale(Locale.ENGLISH);
            }
            else
                return false;
        }
        catch(IOException ioe){
            System.out.println("Cannot open " + filename);
            return false;
        }

        // 简单分词
        // 这个分词方式相对简陋, 没有考虑很多文本处理中的特殊问题
        // 在这里只做demo展示用
        if (scanner.hasNextLine()) {

            String contents = scanner.useDelimiter("\\A").next();

            int start = firstCharacterIndex(contents, 0);
            for (int i = start + 1; i <= contents.length(); )
                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {
                    String word = contents.substring(start, i).toLowerCase();
                    words.add(word);
                    start = firstCharacterIndex(contents, i);
                    i = start + 1;
                } else
                    i++;
        }

        return true;
    }

    /**
     * 寻找字符串s中，从start的位置开始的第一个字母字符的位置
     * 
     * @param s
     * @param start
     * @return
     */
    private static int firstCharacterIndex(String s, int start){

        for( int i = start ; i < s.length() ; i ++ )
            if( Character.isLetter(s.charAt(i)) )
                return i;
        return s.length();
    }
}
```
这里是一个文件读取单词后存入一个ArrayList words中的操作。

传入文件名，将每一个单词扔进数组中。使用英文文本，简单的分词，一行一行读取，把每一个词分出来，文本中所有的单词。

NLP中对于一个动词的不同形式等可以分为一个单词，我们这里只是最简单的就是字母不同的单词。

傲慢与偏见，双城记。年代久远，版权可以使用。
```
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words1 = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words1)) {
            System.out.println("Total words: " + words1.size());

            BSTSet<String> set1 = new BSTSet<>();
            for (String word : words1)
                set1.add(word);
            System.out.println("Total different words: " + set1.getSize());
        }

        System.out.println();


        System.out.println("A Tale of Two Cities");

        ArrayList<String> words2 = new ArrayList<>();
        if (FileOperation.readFile("a-tale-of-two-cities.txt", words2)) {
            System.out.println("Total words: " + words2.size());

            BSTSet<String> set2 = new BSTSet<>();
            for (String word : words2)
                set2.add(word);
            System.out.println("Total different words: " + set2.getSize());
        }
    }
}
```

#### 基于链表的集合实现
为什么我们要再进行一下基于链表的实现呢？因为二分搜索树和LinkedList都属于动态数据结构，它们的数据都存储在一个一个的node中。
```
class Node{
  E e;
  Node left;
  Node right;
}
```
二分搜索树中指向左右子树。
```
class Node{
  E e;
  Node next ;
}
```
链表中指向下一个节点。两种不同实现之后我们可以比较性能。
```
public class LinkedListSet<E> implements Set<E>{
    private LinkedList<E> list;

    public LinkedListSet() {
        list = new LinkedList<>();
    }
}
```

基于链表的实现中，并不要求传入的类型具有可比性,这是线性数据结构的特点。

```
import cn.mtianyan.linked.LinkedList;

public class LinkedListSet<E> implements Set<E>{
    private LinkedList<E> list;

    public LinkedListSet() {
        list = new LinkedList<>();
    }

    @Override
    public boolean contanins(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    @Override
    public void add(E e) {
        // 保证不能重复e
        if (!list.contains(e))
            list.addFirst(e);
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }
}
```

使用链表执行上面相同的测试可以发现运行速度是远远慢于二分搜索树的。

#### 集合的时间复杂度分析
集合不涉及改。

- 本来链表添加只需要O(1)时间复杂度，但是我们为了保证不重复，先调用了一遍contains,因此变成了O(n)
- contains操作，我们必须要从头到尾扫一遍链表，复杂度O(n)
- remove操作，要先找到待删除元素前一个元素，时间复杂度O(n)

对于二分搜索树的操作：
- 添加一个元素，走左子树，就不会去右子树，节省了很大一部分的寻找开销,最多能经历的节点数是树的高度。
- 删除元素，查找元素都是这样的，对于它来说，时间复杂度为O(h)，h为二分搜索树的高度。

根据高度n和h之间的关系，极端: 满二叉树中,第h-1层，有2^(h-1)个节点。
所以，二分搜索树的时间复杂度为logn。

logn和n的差距是非常大的：
![image](http://myphoto.mtianyan.cn/20180813161251_Cqi5WJ_Screenshot.jpeg)

logn是一个非常快的时间复杂度，很多排序算法是nlogn的，比n^2快了很多很多（前面都乘以n）。但是这里我们的logn必须要注明是平均的，因为是在满二叉树下计算出来的。


二分搜索树会退化为链表,虽然平均来讲是O(logn)级别的，但是当退化成链表的最差情况，会变成O(n)级别的。

![TIM截图20181111175423.png](https://i.loli.net/2018/11/11/5be7fc5d80950.png)

> 解决这个问题的方法就是要来创建**平衡二叉树**


### 映射
#### 定义
映射可以理解为函数，一一对应，或者称其为字典（python中有这样的数据结构）。

![image](http://myphoto.mtianyan.cn/20180813182848_ZW1WnY_Screenshot.jpeg)

#### 实现
看起来要存储两个数据，但是依然可以非常容易的使用链表或者二分搜索树进行实现。将原本存储的e，变成存储两个key 与 Value。

- 使用链表
```
class Node{
  K key;
  V value;
  Node left;
  Node right;
}
```
- 使用二分搜索树
```
class Node{
  K key;
  V Value;
  Node next;
}
```
> 使用二分搜索树还需要实现compareble接口，因为key必须是要可比较的

#### 映射类型

映射分为有序映射和无序映射；

有序是基于映射中的键是否有序，**有序映射的实现为搜索树，无需映射基于哈希表实现**。

- 多重映射
多重映射中的键可以重复

#### 集合和映射的关系

![image](http://myphoto.mtianyan.cn/20180813200555_wr7MdL_Screenshot.jpeg)
集合和映射都可以用链表和二分搜索树来实现，对于映射也可以将其理解为集合，即一个对键值对（K,V）的集合；

集合Set和映射Map，映射Map可以理解为所有value为Null的Set；

> 哈希表的大多数问题也可以使用TreeSet 和Treemap;也可以使用HashSet HashMap。我们提供了两种底层实现，但是接口相同。

Treeset TreeMap 底层是平衡二叉树(红黑树)，

而HashMap HashSet 底层是哈希表。底层不需要管，只需要实现相应的功能，不同实现时间复杂度不同。
