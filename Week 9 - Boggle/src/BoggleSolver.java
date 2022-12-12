import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.HashSet;

public class BoggleSolver {
    private final TST trie = new TST<>();

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        for (int i = 0; i < dictionary.length; ++i) trie.put(dictionary[i], i);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        HashSet<String> validWords = new HashSet();

        for (int x = 0; x < board.cols(); ++x) {
            for (int y = 0; y < board.rows(); ++y) {
                navAdjTiles(board, new Position(this, x, y), validWords, new HashSet(), "");
            }
        }

        return validWords;
    }

    private void navAdjTiles(BoggleBoard board, Position position, HashSet<String> validWords, HashSet<Position> path, String word) {
        if (position.x >= 0 && position.x < board.cols()) {
            if (position.y >= 0 && position.y < board.rows()) {
                //TODO: Not correctly checking if path contains
                if (!path.contains(position)) {
                    path.add(position);
                    String letter = String.valueOf(board.getLetter(position.y, position.x));
                    if (letter.equals("Q")) letter = letter + "U";

                    word += letter;
                    if (word.length() > 2 && trie.get(letter) != null && !validWords.contains(word)) {
                        validWords.add(word);
                    }

                    HashSet newPath = new HashSet<>(path);
                    newPath.add(position);
                    this.navAdjTiles(board, new Position(this, position.x + 1, position.y), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x - 1, position.y), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x, position.y + 1), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x, position.y - 1), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x + 1, position.y + 1), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x + 1, position.y - 1), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x - 1, position.y + 1), validWords, newPath, word);
                    this.navAdjTiles(board, new Position(this, position.x - 1, position.y - 1), validWords, newPath, word);
                }
            }
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

    private class Position {
        private final BoggleSolver boggleSolver;
        private final int x, y;

        public Position(BoggleSolver boggleSolver, int x, int y) {
            this.boggleSolver = boggleSolver;
            this.x = x;
            this.y = y;
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