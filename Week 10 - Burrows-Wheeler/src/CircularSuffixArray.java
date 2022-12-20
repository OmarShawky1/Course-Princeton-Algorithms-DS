import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private Integer[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        Integer[] ind = new Integer[s.length()];
        for (int i = 0; i < ind.length; i++) ind[i] = i;

        // Merge sort using Java's implementation
        // passing lambda comparator consumer to sort (instead of implementing one)
        // (first, second) are parameters for compareTo().
        Arrays.sort(ind, (first, second) -> {
            // first/second is suffix_i which is s.charAt(first)
            for (int i = 0; i < s.length(); i++) {
                // suffix starts is from i --> s.length(); so if i > 0, s.length = i-1
                int firInd = (first + i) % s.length(); // if first+i >= length, restart at 0
                int secInd = (second + i) % s.length();
                int result = s.charAt(firInd) - s.charAt(secInd);
                if (result != 0) return result;
            }
            return 0;
        });
        this.indices = ind;
    }

    // length of s
    public int length() {
        return indices.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= indices.length) throw new IllegalArgumentException();
        return indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray cir = new CircularSuffixArray("ABRACADABRA!"); //instead of args[0]
        for (int i = 0; i < cir.length(); i++) StdOut.print(cir.index(i) + " ");
        StdOut.println("");
    }
}