import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Objects;

public class BoggleSolver {
    // private final TST<Integer> trie = new TST<>();
    private final TerTrie<Integer> trie = new TerTrie<>();

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        for (int i = 0; i < dictionary.length; ++i) trie.put(dictionary[i], i);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        HashSet<String> validWords = new HashSet<>();

        for (int x = 0; x < board.cols(); ++x)
            for (int y = 0; y < board.rows(); ++y)
                navAdjTiles(board, new Position(x, y), validWords, new boolean[board.rows()][board.cols()], "", trie);

        return validWords;
    }

    // Navigate all 8 neighboring tiles; right, left, down, up and 4 diagonals respectively
    private void navAdjTiles(BoggleBoard board, Position p, HashSet<String> validWords,
                             boolean[][] path, String word, TerTrie<Integer> trie) {
        if (p.x >= 0 && p.x < board.cols() && p.y >= 0 && p.y < board.rows() && !path[p.y][p.x]) {
            String letter = String.valueOf(board.getLetter(p.y, p.x));
            if (letter.equals("Q")) letter = letter + "U";
            word += letter;

            // Backtracking optimization, check if this word exists in the dictionary
            if (!trie.keysWithPrefix(letter).iterator().hasNext()) return;

            /*
            * TODO: Enhancement suggestions by FAQs
            // Exploit that fact that when you perform a prefix query operation, it is usually almost identical to the
            // previous prefix query, except that it is one letter longer.

            // Consider a nonrecursive implementation of the prefix query operation.

            // Precompute the Boggle graph, i.e., the set of cubes adjacent to each cube. But don't necessarily use a
            // heavyweight Graph object.
            */

            // No need to check if the word is already added, "add" already does so.
            // TODO: here is the bug that causes not adding the word; "trie.get(letter) != null"
            // To recreate the problem, set a break point at "word+=letter" with value "word.equals("AR") && letter.equals("S")"
            if (word.length() > 2 && trie.get(letter) != null) validWords.add(word);

            //TODO: use "Techno Chess, Atomic variation" suggestion to set value true and swap it back after recursion
            // instead of cloning entire array

            // Create array of booleans that represents visited tiles with initial values to false
            boolean[][] newPath = new boolean[board.cols()][board.rows()];
            for (int row = 0; row < board.cols(); row++) newPath[row] = path[row].clone(); // clone old bol[][]
            newPath[p.y][p.x] = true; // add current tile as a visited tile

            // instantiate a new TerTrie from current node (to escape unnecessary tree traversal)
            TerTrie<Integer> newTrie = new TerTrie<>(trie.getSubTrie(letter));

            navAdjTiles(board, new Position(p.x + 1, p.y), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x - 1, p.y), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x, p.y + 1), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x, p.y - 1), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x + 1, p.y + 1), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x + 1, p.y - 1), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x - 1, p.y + 1), validWords, newPath, word, newTrie);
            navAdjTiles(board, new Position(p.x - 1, p.y - 1), validWords, newPath, word, newTrie);
        }
    }

    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();

        if (trie.get(word) != null) {
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
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}