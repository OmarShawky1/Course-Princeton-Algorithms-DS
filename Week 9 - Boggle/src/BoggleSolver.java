import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

public class BoggleSolver {
    private final RT trie = new RT();
    private BoggleBoard board;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        for (String word: dictionary) trie.put(word);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        HashSet<String> validWords = new HashSet<>();

        boolean[][] path = new boolean[board.rows()][board.cols()];
        this.board = board;
        for (int x = 0; x < board.cols(); ++x)
            for (int y = 0; y < board.rows(); ++y)
                navAdjTiles(x, y, path, trie.getRoot(), validWords, "");

        return validWords;
    }

    // Navigate all 8 neighboring tiles; right, left, down, up and 4 diagonals respectively
    private void navAdjTiles(int x, int y, boolean[][] path, RT.Node node, HashSet<String> validWords, String word) {
        if (x < 0 || x >= board.cols() || y < 0 || y >= board.rows() || path[y][x]) return;

        char letter = board.getLetter(y, x);
        word += (letter == 'Q') ? "QU" : String.valueOf(letter);


        // Backtracking optimization, check if this word exists in the dictionary
        if (!trie.hasPrefix(node, letter)) return;

        // No need to check if the word is already added, "add" already does so.
        if (word.length() > 2 && trie.contains(node, word)) validWords.add(word);

        path[y][x] = true;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                navAdjTiles(x + i, y + j, path, node, validWords, word);
            }
        }
        path[y][x] = false;
    }

    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();

        if (trie.dictionaryContains(word)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}