import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.HashSet;
import java.util.Objects;

public class BoggleSolver {
    private final TST trie = new TST<>();

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        for (int i = 0; i < dictionary.length; ++i) trie.put(dictionary[i], i);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        HashSet<String> validWords = new HashSet();

        for (int x = 0; x < board.cols(); ++x)
            for (int y = 0; y < board.rows(); ++y)
                navAdjTiles(board, new Position(x, y), validWords, new HashSet(), "");

        return validWords;
    }

    // Navigate all 8 neighboring tiles; right, left, down, up and 4 diagonals respectively
    private void navAdjTiles(BoggleBoard board, Position p, HashSet<String> validWords, HashSet<Position> path, String word) {
        if (p.x >= 0 && p.x < board.cols() && p.y >= 0 && p.y < board.rows() && !path.contains(p)) {
            String letter = String.valueOf(board.getLetter(p.y, p.x));
            if (letter.equals("Q")) letter = letter + "U";
            word += letter;

            // Backtracking optimization, check if this word exists in the dictionary
            if (!trie.keysWithPrefix(word).iterator().hasNext()) return;

            // No need to check if the word is already added, "add" already does so.
            if (word.length() > 2 && trie.get(word) != null) validWords.add(word);


            HashSet newPath = new HashSet<>(path);
            newPath.add(p);
            navAdjTiles(board, new Position(p.x + 1, p.y), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x - 1, p.y), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x, p.y + 1), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x, p.y - 1), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x + 1, p.y + 1), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x + 1, p.y - 1), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x - 1, p.y + 1), validWords, newPath, word);
            navAdjTiles(board, new Position(p.x - 1, p.y - 1), validWords, newPath, word);
        }
    }

    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();

        /*if (trie.get(word) != null) {
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
        return 0;*/
        switch (word.length()) {
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

    private static class Position {
        private final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            // return x * 997 + y; // 997 is a large prime number provided in lecture 21 Substring search
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Position p = (Position) o;
            return p.x == x && p.y == y;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        // Temp test
        // board that provides only two words, ACER & ACERS
        /* char[][] customChar = {{'S', 'S', 'S', 'S'},
                {'A', 'C', 'E', 'R'},
                {'S', 'S', 'S', 'S'}};
        BoggleBoard board = new BoggleBoard(customChar);
        */
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}