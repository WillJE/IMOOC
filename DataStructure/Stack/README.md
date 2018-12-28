#### 数据结构之栈

##### 原理
栈是一种比较常见的数据结构，它的操作可以看做是数组的子集，因为栈只能从栈顶取元素和添加元素，并且栈遵循先进后出原则，即`First in last out(FILO)`.

栈的运用很广泛，比如在操作系统中，系统常常在运行过程中，会中途去执行另外的程序，随后再返回执行原程序，那么系统是如何找到之前中断程序的位置的呢？**答案是系统中维护了一个系统栈。**

```java
fun A(){
....
B()
...
}
fun B(){
....
C()
....
}
fun C(){
...
...
...
}
```
比如在上方的程序A中，当A运行到第二行时，程序会去执行B方法，此时系统会将A2压入系统栈，随后再去执行B，当B运行到第二行时，程序又进行跳转到C方法，同理，此时程序会将B2压入栈。

那么此时栈中一共有两个元素，自顶向下分别为B2、A2，于是当程序执行完C方法时，会将B2出栈，那么程序就回到B2行接着往下执行，同理当B方法执行完，A2出栈，程序回到A2行继续执行，直到完成。

#### 栈的基本实现
实现一个栈，这里采用的是构造一个栈结构的接口，写一个实现类实现该接口，接口类变量为上一节实现的动态数组，因为栈的操作可以看做是数组的子集。
具体代码如下：
接口类

```java
public interface Stack<E> {
    int getSize();
    boolean isEmpty();
    void push(E e);
    E pop();
    E peek();
}
```
这里也运用了泛型，使其能够接受任意的类型的变量。

实现类：
```java
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
```
测试类：
```java
public class Main {

    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>();

        for(int i = 0; i < 5; i++){
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);
    }
}
```
测试结果
```java
Stack:[0] top
Stack:[0,1] top
Stack:[0,1,2] top
Stack:[0,1,2,3] top
Stack:[0,1,2,3,4] top
Stack:[0,1,2,3] top
```

这里还缺少了我们实现的动态数组类，详情可以看上一节数组章节。

#### 应用（括号匹配）
利用栈这种类型的数据结构，可以解决一些算法问题。

比如[leetcode](https://leetcode.com/problems/valid-parentheses/description/ "leetcode")中第20题，括号匹配的问题。

即这样的字符串`"{[()]}"`是正确的，但是`"[(}]"`这样的就是错误的，你需要设计一个方法，判断传入的这种类型的字符串是否是正确的。

```
class Solution {
        public boolean isValid(String s){
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++){
                char c = s.charAt(i);

                if(c == '(' || c == '{' || c == '[')
                    stack.push(c);
                else {
                    if(stack.isEmpty())
                        return false;
                    char topChar = stack.pop();
                    if(c == ')' && topChar != '(')
                        return false;
                    if(c == '}' && topChar != '{'){
                        return false;
                    }
                    if(c == ']' && topChar != '['){
                        return false;
                    }
                }
            }
            return stack.isEmpty();
        }
    }
```







