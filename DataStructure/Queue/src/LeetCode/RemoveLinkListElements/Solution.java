package LeetCode.RemoveLinkListElements;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 * Remove all elements from a linked list of integers that have value val.
 */
//使用虚拟头节点解决，增加while循环，删除链表中重复元素
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dumyHead = new ListNode(-1);
        dumyHead.next = head;

        ListNode preNode = dumyHead;
        while(head!=null && preNode.next!=null){
            if(preNode.next.val == val){
                ListNode removeNode = preNode.next;
                preNode.next = removeNode.next;
                removeNode.next = null;
            }else{
                preNode = preNode.next;
            }
        }
        return dumyHead.next;
    }
}
