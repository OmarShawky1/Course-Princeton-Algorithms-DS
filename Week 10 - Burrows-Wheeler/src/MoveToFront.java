import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // TODO: Initialize a sequence
        char[] seq = initSequence();
        while (!BinaryStdIn.isEmpty()) {
            // TODO: Read char c from STDIn

            // TODO: write c's index

            // TODO: move to front
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // TODO:
    }

    // create ASCII char sequence
    private static char[] initSequence() {
        char[] sequence = new char[256];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = (char) i;
        }
        return sequence;
    }


    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException();
    }
}