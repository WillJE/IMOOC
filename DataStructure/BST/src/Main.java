import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws ParseException {

        BST<Integer> bst = new BST<>();
        int[] nums = {5,3,6,8,4,2};
        /////////////////
        //      5      //
        //     / \     //
        //    3  6     //
        //   / \  \    //
        //  2   4  8   //
        /////////////////
        for (int i: nums){
            bst.add(i);
        }
        // 5 3 2 4 6 8

        bst.preOrder();

        System.out.println(bst);
    }
}
