public class RT {

    private Node root = new Node();

    public void put(String word) {
        put(word, 0);
    }

    private void put(String key, int d) {
        // FAQ suggestion: make call not recursive
        Node node = root;
        for (; d < key.length(); d++) {
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
        Node x = get(root, word);
        return x != null && x.val;
    }

    public boolean dictionaryContains(String word) {
        return contains(root, word);
    }

    private class Node {
        private boolean val;
        private final Node[] next = new Node[26];
    }


}
