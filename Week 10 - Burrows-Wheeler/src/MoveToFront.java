import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] seq = initSequence(); // 1. Initialize a sequence

        while (!BinaryStdIn.isEmpty()) {
            char cRead = BinaryStdIn.readChar(); // 2. Read char c from StdIn

            // 3. write c's index
            // could've copy/pasted quicksort implementation from lecture 6 but too much lines of code
            int i;
            for (i = 0; i < seq.length; i++) if (seq[i] == cRead) break;
            BinaryStdOut.write((char) i);

            // 4. move to front
            moveToFront(seq, cRead, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] seq = initSequence(); // 1. Initialize a sequence

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar(); // 2. Read int i (but treat it as an int 0-->256 (char))

            // 3. write the ith character in the sequence
            char cRead = seq[i];
            BinaryStdOut.write(cRead);

            // 4. move to front
            moveToFront(seq, cRead, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // create ASCII char sequence
    private static char[] initSequence() {
        char[] sequence = new char[256];
        for (int i = 0; i < sequence.length; i++) sequence[i] = (char) i;
        return sequence;
    }

    private static void moveToFront(char[] sequence, char c, int i) {
        for (int j = i; j > 0; j--) sequence[j] = sequence[j - 1];
        sequence[0] = c;
    }


    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException();
    }
}