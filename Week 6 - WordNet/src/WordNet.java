import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class WordNet {
    private final SAP sap;
    private Digraph digraph;
    private HashMap<String, SET<Integer>> nouns;
    private HashMap<Integer, String> synsets;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        parseSynsets(readLines(synsets));
        parseHypernyms(readLines(hypernyms));
        if (!isDAG()) throw new IllegalArgumentException("Not DAG");
        sap = new SAP(digraph);
    }

    // Signature: ((los) --> (list of los) --> (list of integers)) --> digraph
    // Draws digraph from numbers
    private void parseHypernyms(String[] lines) {
        digraph = new Digraph(lines.length);

        // (list of los) --> (list of integers)
        for (String s : lines) {
            String[] splitted = s.split(",");
            int id = Integer.parseInt(splitted[0]);
            for (int i = 1; i < splitted.length; i++) digraph.addEdge(id, Integer.parseInt(splitted[i]));
        }
    }

    // Signature: ((los) --> (list of los)) --> (HashMap<String, SET<Integer>> * HashMap<Integer, String>))
    // Stores id and synset in the first hashmap, stores id and each noun in the second hashmap
    private void parseSynsets(String[] lines) {
        synsets = new HashMap<>(lines.length);
        nouns = new HashMap<>(lines.length);
        for (String s : lines) {
            String[] fields = s.split(",");
            Integer id = Integer.valueOf(fields[0]);
            String tempSynsets = fields[1];

            synsets.put(id, tempSynsets);
            for (String noun : tempSynsets.split(" ")) {
                SET<Integer> knownIds = nouns.get(noun);
                if (knownIds == null) {
                    knownIds = new SET<>();
                    nouns.put(noun, knownIds);
                }
                knownIds.add(id);
            }
        }
    }

    private String[] readLines(String synsets) {
        In in = new In(synsets);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    private boolean isDAG() {
        for (int i = 0, roots = 0; i < digraph.V(); i++) if (digraph.outdegree(i) == 0 && ++roots >= 2) return false;
        return true;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkInput(word);
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkInput(nounA, nounB);
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    private static void checkInput(String... words) {
        for(String word : words) if (word == null) throw new IllegalArgumentException("word is null");
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkInput(nounA, nounB);
        return synsets.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String s1 = StdIn.readString();
            String s2 = StdIn.readString();
            StdOut.println("wordNet.isNoun(s1): " + wordNet.isNoun(s1));
            StdOut.println("wordNet.isNoun(s2): " + wordNet.isNoun(s2));
            StdOut.println("wordNet.sap(s1, s2): " + wordNet.sap(s1, s2));
        }
    }
}
