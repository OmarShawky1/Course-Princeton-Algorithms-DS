public class RT {

    private final Node root = new Node();

    public Node getRoot() {
        return root;
    }

    public void put(String key) {
        // FAQ suggestion: make call not recursive
        Node node = root;
        for (int d = 0; d < key.length(); d++) {
            int i = key.charAt(d) - 'A'; // getting index that is shifted by 'A' or 65
            if (node.next[i] == null) node.next[i] = new Node();
            node = node.next[i];
        }
        node.val = true;
    }

    private Node get(Node x, String key) {
        // FAQ suggestion: make call not recursive
        for (int d = 0; d < key.length(); d++) {
            if (x == null) return null;
            x = x.next[key.charAt(d) - 'A']; // getting index that is shifted by 'A' or 65
        }
        return x;
    }

    public boolean contains(Node node, String word) {
        Node x = get(node, word);
        return x != null && x.val;
    }

    public boolean dictionaryContains(String word) {
        return contains(root, word);
    }

    public boolean hasPrefix(Node node, String letter) {
        return get(node, letter) != null;
    }

    public static class Node {
        private boolean val;
        private final Node[] next = new Node[26];

        public Node getNext(char c) {
            return next[c - 'A']; // getting index that is shifted by 'A' or 65
        }
    }


}
