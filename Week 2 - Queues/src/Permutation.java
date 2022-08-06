import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        //Check that there is only 2 arguments
        if (args.length <= 1) {
            StdOut.println("Insufficient Arguments");
            for (String arg: args){
                StdOut.println(arg);
            }
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        /*for (int i = 1; i< args.length; i++){
         randomizedQueue.enqueue(args[i]);
        }*/

        while(!StdIn.isEmpty()){
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++){
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
