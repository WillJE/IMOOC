package LeetCode.RemoveLinkListElements;

class Solution2 {
    public ListNode removeElements(ListNode head, int val) {

        //有可能头节点就是需要删除的元素
        //有可能删除后的节点的下一个头节点又是需要删除的元素，所以使用循环
        while(head!=null && head.val == val){
            head = head.next;
        }
        //这样操作后，就能确保头节点不是需要删除的元素

        if(head == null){
            return null;
        }

        //接下来处理非头节点的元素

        ListNode preNode = head;
        while(preNode.next!=null){
            if(preNode.next.val == val){
                ListNode removeNode = preNode.next;
                preNode.next = removeNode.next;
                removeNode.next = null;
            }else{
                preNode = preNode.next;
            }
        }
        return head;
    }
}
