import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;

/**
 * A class to construct a binary trie that holds
 * a frequency table, mapping symbols to their respective frequencies
 * that they occur in an input file. This frequency table is used to
 * represent characters as 0's and 1's in our trie. This allows for
 * decoding of bitstreams into human readable text, mimicking the Huffman
 * Encoding procedure of file compression.
 * @author: Vinay Maruri
 * @version: 1.0
 */

public class BinaryTrie implements Serializable {
    private Element top;
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        top = trieBuild(frequencyTable);
    }
    private static class Element implements Comparable<Element>, Serializable {
        private char c;
        private int frequency;
        private Element left;
        private Element right;
        private Element(char ch, int f, Element l, Element r) {
            this.c = ch;
            this.frequency = f;
            this.left = l;
            this.right = r;
        }
        private boolean isLeaf() {
            if (this.left == null && this.right == null) {
                return true;
            }
            return false;
        }
        @Override
        public int compareTo(Element other) {
            int difference = this.frequency - other.frequency;
            return difference;
        }
    }

    /**
     * @source: https://algs4.cs.princeton.edu/55compression/Huffman.java.html
     * Purpose: Source was used to understand what exactly the trie was
     * supposed to contain as well as help understand corner cases
     * to develop the algorithm.
     */
    private static Element trieBuild(Map<Character, Integer> freq) {
        MinPQ<Element> pq = new MinPQ<>();
        for (Character i: freq.keySet()) {
            if (freq.get(i) > 0) {
                Element temp = new Element(i, freq.get(i), null, null);
                pq.insert(temp);
            }
        }
        if (pq.size() == 1) {
            if (freq.get('\0') == 0) {
                pq.insert(new Element('\0', 0, null, null));
            } else {
                pq.insert(new Element('\1', 0, null, null));
            }
        }
        while (pq.size() > 1) {
            Element left = pq.delMin();
            Element right = pq.delMin();
            Element parent = new Element('\0', left.frequency + right.frequency, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        Element curr = top;
        String match = "";
        for (int i = 0; i < querySequence.length(); i++) {
            if (querySequence.bitAt(i) == 0 && curr.left != null) {
                match = match + '0';
                curr = curr.left;
            } else if (querySequence.bitAt(i) == 1 && curr.right != null) {
                match = match + '1';
                curr = curr.right;
            } else {
                break;
            }
        }
        return new Match(new BitSequence(match), curr.c);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> table = new HashMap<>();
        HashMap<Element, String> other = new HashMap<>();
        other.put(top, "");
        Stack<Element> helper = new Stack<>();
        helper.push(top);
        while (!helper.empty()) {
            Element curr = helper.pop();
            String currseq = other.get(curr);
            if (curr.right != null) {
                helper.push(curr.right);
                other.put(curr.right, currseq + '1');
            }
            if (curr.left != null) {
                helper.push(curr.left);
                other.put(curr.left, currseq + '0');
                other.remove(curr);
            }
        }
        for (Element x: other.keySet()) {
            table.put(x.c, new BitSequence(other.get(x)));
        }
        return table;
    }
}
