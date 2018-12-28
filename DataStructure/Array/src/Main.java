public class Main {

    public static void main(String[] args) {
        Array data = new Array(10);
        for(int i = 0; i< 10; i++){
            data.addLast(i);
        }
        System.out.println(data.toString());

        data.addFirst(-1);
        System.out.println(data.toString());

        data.remove(2);
        System.out.println(data.toString());
        data.removeElement(4);
        System.out.println(data.toString());
        data.removeLast();
        data.removeLast();
        data.removeLast();
        data.removeLast();
        data.removeLast();
        data.removeLast();
        System.out.println(data.toString());

    }
}
