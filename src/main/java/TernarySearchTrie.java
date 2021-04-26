import java.util.ArrayList;

public class TernarySearchTrie {
    private Node root;

    public TernarySearchTrie(){
        root= new Node();
    }

    // Insert new word into the trie.
    public void insert(String stopName, float stopId){
        if((stopName != null && !stopName.isEmpty()) && (stopId >= 0))
            insert(stopName, stopId, 0, root);
    }

    private Node insert(String stopName, float stopId, int index, Node node) {
        char ch = stopName.charAt(index);
        if (node == null) node = new Node(ch);
        node.stopId.add(stopId);
        if (ch < node.val) node.left = insert(stopName, stopId, index, node.left);
        else if (ch > node.val) node.right = insert(stopName, stopId, index, node.right);
        else if (index < stopName.length() - 1) node.middle = insert(stopName, stopId, index + 1, node.middle);
        else node.end = true;
        return node;
    }

    // Returns all stops that start with a passed in String
    public ArrayList<BusStop> search(String word){
        if(word == null || word.isEmpty())
            return null;
        return search(word, 0, root);
    }

    private ArrayList<BusStop> search(String word, int index, Node node){
        ArrayList<BusStop> busStops= null;
        if (node == null) return null;
        char ch = word.charAt(index);
        if (ch < node.val) return search(word, index, node.left);
        if (ch > node.val) return search(word, index, node.right);
        if (index < word.length() - 1) return search(word, index + 1, node.middle);
        //return graph.getStopsById(node.stopId);
        return busStops;
    }

    
    private class Node {
        char val;
        Node left, middle, right;
        boolean end;
        ArrayList<Float> stopId;

        public Node(){ }

        public Node(char val) {
            this.val = val;
        }
    }
}