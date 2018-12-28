**Trie**字典树（前缀树）

正确的读法就是Tree，为了区别Tree E；微软真实案例: 手持设备通讯录搜索效率慢，实习生使用Trie解决

数据结构:

映射结构: 字典（多叉树）。**但Trie专门为处理字符串而生**。

如果有n个条目 使用树结构(平衡) 查询的时间复杂度是O(logn);如果有100万个条目(2^20)logn大约为20

Trie 查询每个条目的时间复杂度，**和字典中一共有多少条目无关! 时间复杂度为O(w) w为查询单词的长度**! 因为大多数单词的长度都是小于10的。

![image](http://myphoto.mtianyan.cn/20180814212907_tyty8Q_Screenshot.jpeg)


之前都是一个单词为一个节点进行存储，Trie按字母拆开进行了树形结构的排列。上图中有cat dog dear panda 四个单词。**单词有多少个字母，访问到叶子节点就可以访问到单词**。


Trie中节点的定义: 每个节点有26个指向下个节点的指针,每个节点有能力指向26个孩子。**考虑不同的语言，不同的情境，26个指针有可能是不够的**，也有可能有多余的。

```
class Node{
  char c;
  Node next[26];
}
```
考虑大小写，有52个指针。如果设计的是url，邮箱等，肯定就要扩展更多的指针。更灵活的Trie会不固定指针的数量。
```
class Node{
  char c;
  Map<char, Node> next;
}
```

next就是指一个char和一个node之间的映射，map中存放多少个不知道，但每一个都是节点到字符的映射。

![image](http://myphoto.mtianyan.cn/20180814213913_m2wktU_Screenshot.jpeg)

来到这个节点之前就已经知道这条线通往哪个字母了。因此可以不存值，只存映射。

```
class Node{
  Map<char, Node> next;
}
```
Trie中添加查询即使没有char c也没有问题。我们上面的例子到叶子节点就是一个要查询的单词，但是英语世界中一些单词是另一些的前缀。例如pan panda都要存。 **要有一个标识，记录该节点是否是一个单词的结尾**。

```
class Node{
  boolean isWord;
  Map<char, Node> next;
}
```

最重要的一个部分就是next，既是一个映射，又存放着一个char值。


#### Trie和前缀搜索
![image](http://myphoto.mtianyan.cn/20180814221127_q0MagD_Screenshot.jpeg)

比如查找cat的时候 c ca都是cat的前缀。非常方便查找是否有某一个前缀对应的单词。

#### 更多与Trie相关的话题
Trie的删除操作,通讯录的实现。将通讯录中每一个人名，当做一个单词插入Trie，单词最后一个字母位置存储相应的电话号码等信息。

![image](http://myphoto.mtianyan.cn/20180814235239_0JVAba_Screenshot.jpeg)

**通讯录的删除操作**：

删除deer单词会将eer节点都删除掉，然后保留d。删除pan，把n的isWord置为false即可。

##### Trie的局限性

每个节点只存储了一个字符的信息，节点之间的关联全部由TreeMap映射。存储空间是27n。

解决方案: 

- 压缩字典树Compressed Trie，只有一个后续字符的单链可以进行合并
。

![image](http://myphoto.mtianyan.cn/20180815000947_YS7xbj_Screenshot.jpeg)


空间节省了，但是维护成本高了很多。

- Ternary Search Trie 三分搜索树
- 后缀树：字符串模式识别


##### 字符串数据研究
大多数时候都在与字符串打交道，字符串无处不在。研究无处不在。

**经典问题: 子串查询**，验证某个字符串是不是另外一个的子串。网页,word中搜索关键词都是子串查询。

文件压缩就是对字符串压缩，哈夫曼算法建立一棵树。

模式匹配: 实现一个高效的正则表达式引擎。java代码也是字符串，程序字符串进行解析，**编译原理**。

生物科学领域，DNA超长字符串，寻找模式，寻找特定目标。