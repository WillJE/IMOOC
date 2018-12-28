import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>();

        for(int i = 0; i < 5; i++){
            stack.push(i);
            System.out.println(stack);
        }

        ValidParentheses validParentheses = new ValidParentheses();
        ValidParentheses.Solution solution = validParentheses.new Solution();

        System.out.println(solution.isValid("(]"));

    }
}
