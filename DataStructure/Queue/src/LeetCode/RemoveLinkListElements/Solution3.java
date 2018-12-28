package LeetCode.RemoveLinkListElements;

public class Solution3 {
    public ListNode removeElements(ListNode head, int val) {
        if(head == null){
            return null;
        }else{
//            ListNode resNode = removeElements(head.next, val);
//            if(head.val == val){
//                return resNode;
//            }else{
//                head.next = resNode;
//                return head;
//            }
            head.next = removeElements(head.next, val);
            return head.val == val ? head.next: head;
        }
    }


    public static void main(String[] args) {
        int[] nums = {1,2,3,6,4,5,6};
        ListNode head = new ListNode(nums);
        ListNode rest = new Solution3().removeElements(head, 6);
        System.out.println(rest);
        System.out.println(head);
    }
}
