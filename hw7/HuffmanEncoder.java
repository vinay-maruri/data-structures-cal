import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper Class to encode text files into bitstreams
 * for compression purposes into our .huf file format.
 * @author: Vinay Maruri
 * @version: 1.0
 */

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>();
        for (int i = 0; i < inputSymbols.length; i++) {
            if (i == 0) {
                frequencies.put(inputSymbols[i], 1);
            } else if (frequencies.containsKey(inputSymbols[i])) {
                int temp = frequencies.get(inputSymbols[i]) + 1;
                frequencies.remove(inputSymbols[i]);
                frequencies.put(inputSymbols[i], temp);
            } else {
                frequencies.put(inputSymbols[i], 1);
            }
        }
        return frequencies;
    }
    public static void main(String[] args) {
        char[] file = FileUtils.readFile(args[0]);
        Map<Character, Integer> freq = buildFrequencyTable(file);
        ObjectWriter s = new ObjectWriter(args[0] + ".huf");
        BinaryTrie decode = new BinaryTrie(freq);
        s.writeObject(decode);

        Integer symbolnum = file.length;
        s.writeObject(symbolnum);

        Map<Character, BitSequence> look = decode.buildLookupTable();
        ArrayList<BitSequence> sequences = new ArrayList<>();
        for (char i: file) {
            if (look.containsKey(i)) {
                sequences.add(look.get(i));
            }
        }
        BitSequence big = BitSequence.assemble(sequences);
        s.writeObject(big);
    }
}
