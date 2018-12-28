import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int count = 100000;

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<Integer>();
        double time1 = testQueue(arrayQueue, count);

        System.out.println("ArrayQueue,time:"+ time1 +"s");

        LoopQueue<Integer> loopQueue = new LoopQueue<Integer>();
        double time2 = testQueue(loopQueue, count);
        System.out.println("LoopQuere,time:" + time2 + "s");
    }

    public static double testQueue(Queue<Integer> q, int opCount){

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0; i < opCount; i++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i < opCount; i++){
            q.dequeue();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0 ;
    }
}
