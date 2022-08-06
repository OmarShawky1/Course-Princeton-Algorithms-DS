import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        //Check that there is only 2 arguments
        if (args.length <= 1) {
            StdOut.println("Insufficient Arguments");
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        for (int i = 1; i< args.length; i++){
         randomizedQueue.enqueue(args[i]);
        }
    }
}
