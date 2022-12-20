public class CircularSuffixArray {
    private int length;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("s is null");

        char[] c = s.toCharArray(); //TODO: no need for toChar, remove it after finishing
        char[][] suffixArr = new char[c.length][c.length];

        // store suffixes
        for (int i = 0; i < c.length; i++) {
            for (int j = i; j < c.length; j++) suffixArr[i][j-i] = c[j];
            for (int k = 0; k < i; k++) suffixArr[i][c.length-i+k] = c[k];
        }

        // TODO: Merge sort
    }
    
    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return index[i];
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray cir = new CircularSuffixArray("ABRACADABRA!");
    }
}