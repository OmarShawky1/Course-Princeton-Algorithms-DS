import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        // For each line
        while (!BinaryStdIn.isEmpty()) {
            // Read input and initialize suffixArray from it
            String input = BinaryStdIn.readString();
            CircularSuffixArray cir = new CircularSuffixArray(input);

            // find "first" & t
            int l = input.length();
            char[] t = new char[l];
            int first = 0;
            for (int i = 0; i < input.length(); i++) {
                int temp = cir.index(i);
                t[i] = input.charAt((temp + l - 1) % l);
                if (temp == 0) first = i;
            }

            // print "first" then t.
            BinaryStdOut.write(first);
            for (int i = 0; i < t.length; i++) BinaryStdOut.write(t[i]);

        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {

        // Read "first"
        if (BinaryStdIn.isEmpty()) return;
        int first = BinaryStdIn.readInt();

        //TODO:  Read t[]
        Queue<Character> tQ = new Queue<>();
        while (!BinaryStdIn.isEmpty()) tQ.enqueue(BinaryStdIn.readChar());

        //TODO: create tOrdered[] and clone it from t[] after ordering

        //TODO: Follow Case 1 & 2 to induce next[]

        //TODO: obtain string "message"

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].charAt(0) == '-') transform();
        else if (args[0].charAt(0) == '+') inverseTransform();
        else throw new IllegalArgumentException();
    }
}
