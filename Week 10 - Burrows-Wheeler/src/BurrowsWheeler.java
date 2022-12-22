import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

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

        while (!BinaryStdIn.isEmpty()) {
            // Read "first"
            int first = BinaryStdIn.readInt();
            StdOut.println("first: " + first); //TODO: remove it

            // Read t[]
            String tS = BinaryStdIn.readString();
            StdOut.println("tS: " + tS); //TODO: remove it
            char[] t = tS.toCharArray();

            // create tOrdered[] and clone it from t[] after ordering
            char[] tOrdered = tS.toCharArray();
            Arrays.sort(tOrdered);
            StdOut.println("tOrdered: " + Arrays.toString(tOrdered)); //TODO: remove it
            //TODO: Follow Case 1 & 2 to induce next[]

            //TODO: obtain string "message"
        }

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