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

    private class Node {
        private boolean val;
        private final Node[] next = new Node[26];
    }


}
