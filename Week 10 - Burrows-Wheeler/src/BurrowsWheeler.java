import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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
            int first = BinaryStdIn.readInt(); // Read "first"

            String tS = BinaryStdIn.readString(); // Read t[]
            char[] t = tS.toCharArray();

            // Obtaining sorted[] via cloning t[] in sorted[] and sorting it
            char[] sorted = tS.toCharArray();
            Arrays.sort(sorted);
            /*
            // Alternative way to obtain sorted[] (just like obtaining next[] below)

            // Key Indexed sorting
            char[] sorted = new char[t.length];

            // Count frequencies (each characters' occurrences)
            // Code from lecture:
            for (int i = 0; i < t.length; i++) counts[t[i] + 1]++; // counts is the chars' count, [t[i] + 1] is a char
            // My code:
            for (char c : t) counts[c + 1]++;

            // move items
            // Code from lecture:

            // t[i] is char c
            // counts[c] is char c freq + offset (freq cumulates)
            for (int i = 0; i < t.length; i++) sorted[counts[t[i]]++] = t[i];

            // My code:
            for (char c : t) sorted[counts[c]++] = c; // access sorted[counts[c]] then counts[c]++
             */

            // obtaining next[]
            // Key Indexed sorting
            int[] next = new int[t.length];

            // Count frequencies (each characters' occurrences)
            int R = 256; // ASCII number of chars
            int[] counts = new int[R + 1];
            /**
             * Code from lecture:
             * for (int i = 0; i < t.length; i++) counts[sorted[i] + 1]++;
             * below is alternative implementation
             */
            for (char c : sorted) counts[c + 1]++; // t & sorted can be used interchangeably

            // compute cumulates
            for (int r = 0; r < R; r++) counts[r + 1] += counts[r];

            // move items
            /**
             * How it works: you can read it simply as "where is char t[i] in sorted[]? it is in sorted[j] then make
             * next[j] point to "i". Sorted & next here are the same and can be used interchangeably.
             * counts[] stores & points to each character -in sorted[]- in order. So, counts[c] means get current chars'
             * position and increment it by 1 (so that when called again, it points to the next same char occurrence).
             * look at the PDF for further illustration.
             * Alternative code:
             * for (int i = 0; i < t.length; i++) {
             *     char c = t[i];
             *     int j = counts[c]++;
             *     next[j] = i;
             * }
             */
            for (int i = 0; i < t.length; i++) next[counts[t[i]]++] = i;

            // Outputting string to standard output stream
            for (int i = 0, index = first; i < t.length; i++, index = next[index]) BinaryStdOut.write(sorted[index]);
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