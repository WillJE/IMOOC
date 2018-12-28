import java.util.TreeMap;

class Trie2 {

    /**
     * 节点类：isWord，Map<c,node>
     */
    private class Node {

        public boolean isWord;
        public TreeMap<Character, Node> next; // 中文单词界限模糊

        // 传入isWord
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        // 无参构造函数
        public Node() {
            this(false);
        }
    }

    private Node root;  // 根节点

    /**
     * Trie默认构造函数
     */
    public Trie2() {
        root = new Node();
    }

    /**
     * 向Trie中添加一个新的单词word
     *
     * @param word
     */
    public void insert(String word) {
        // 非递归写法，字符串拆成字符
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 如果为空，新建节点。
            if (cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c); // 走到下一个节点
        }
        // 这个节点以前不表示单词结尾
        if (!cur.isWord) {
            cur.isWord = true;
        }
    }

    /**
     * 查询单词word是否在Trie中
     * @param word
     * @return
     */
    public boolean search(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            // 没有这个字符，return false
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        // 比如trie中有panda，查pan。虽然到节点，但是并没有这个单词。
        return cur.isWord;
    }

    /**
     * 查询是否在Trie中有单词以prefix为前缀
     *
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix){

        Node cur = root;
        for(int i = 0 ; i < prefix.length() ; i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }

        return true; // 单词本身也是该单词的前缀。
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */