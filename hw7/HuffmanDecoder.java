/**
 * Helper class to decode bitstreams into human readable text files.
 * @author: Vinay Maruri
 * @version: 1.0
 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader x = new ObjectReader(args[0]);
        BinaryTrie decoder = (BinaryTrie) x.readObject();
        Integer symbolnum = (Integer) x.readObject();
        BitSequence everything = (BitSequence) x.readObject();
        char[] matched = new char[symbolnum];
        for (int i = 0; i < symbolnum; i++) {
            Match m = decoder.longestPrefixMatch(everything);
            matched[i] = m.getSymbol();
            everything = everything.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], matched);
    }
}
