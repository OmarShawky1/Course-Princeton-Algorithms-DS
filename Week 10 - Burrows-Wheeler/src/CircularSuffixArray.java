public class CircularSuffixArray {
    private int length;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
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
        
    }
}