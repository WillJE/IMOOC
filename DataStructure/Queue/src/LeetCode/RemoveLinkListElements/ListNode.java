package LeetCode.RemoveLinkListElements;

public class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }

     public ListNode(int[] args){
         if(args == null|| args.length == 0){
             throw new IllegalArgumentException("arr can not be empty");
         }
         this.val = args[0];
         ListNode cur = this;
         for(int i = 1; i < args.length; i++){
             cur.next = new ListNode(args[i]);
             cur = cur.next;
         }
     }

     @Override
     public String toString(){
         StringBuilder str = new StringBuilder();
         ListNode cur = this;
         while (cur != null){
             str.append(cur.val+ "->");
             cur = cur.next;
         }
         str.append("Null");
         return str.toString();
     }

 }
