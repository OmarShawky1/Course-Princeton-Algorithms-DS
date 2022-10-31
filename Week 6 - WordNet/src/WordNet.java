import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    private final SAP sap;
    private Digraph digraph;
    private HashMap<String, Integer> nouns;
    private HashMap<Integer, String> synsets;

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
            String[] fields = s.split(",");
            Integer id = Integer.valueOf(fields[0]);

            String tempSynsets = fields[1];
            synsets.put(id, tempSynsets);

            for (String noun: tempSynsets.split(" ")) nouns.put(noun, id);
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
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
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