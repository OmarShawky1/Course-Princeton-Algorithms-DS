import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    private final SAP sap;
    private Digraph digraph;
    private HashMap<String, Integer> synsets;
    private HashMap<Integer, String> nouns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        parseSynsets(readLines(synsets));
        parseHypernyms(readLines(hypernyms));
        sap = new SAP(digraph);
    }

    // Signature ((los) --> (list of los) --> (list of integers)) --> digraph
    // Draws digraph from numbers
    private void parseHypernyms(String[] lines) {
        digraph = new Digraph(lines.length);
        for (String s : lines) {
            String[] splitted = s.split(",");
            int id = Integer.parseInt(splitted[0]);
            for (int i = 1; i < splitted.length; i++) digraph.addEdge(id, Integer.parseInt(splitted[i]));
        }
    }

    private void parseSynsets(String[] lines) {
        synsets = new HashMap<>(lines.length);
        nouns = new HashMap<>(lines.length);
        for (String s : lines) {
            String[] splitted = s.split(",");
            Integer id = Integer.valueOf(splitted[0]);
            String noun = splitted[1];
            synsets.put(noun, id);
            nouns.put(id, noun);
        }
    }

    private String[] readLines(String synsets) {
        In in = new In(synsets);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.values();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsets.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(synsets.get(nounA), synsets.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return nouns.get(sap.ancestor(synsets.get(nounA), synsets.get(nounB)));
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
