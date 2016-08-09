import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildHuffmanTrees {
    public List<HuffNode> trees = new ArrayList<HuffNode>();
    private List<HuffNode> newTrees = new ArrayList<HuffNode>();
    private int[] freqTable = new int[255];
    private HuffNode tree = new HuffNode();
    public HuffNode merge;
    private int sumFreq;

    public int getNewNumTrees() {
        return numTrees;
    }

    private int numTrees = 0, newNumTrees = 0;
    private int numberOfLetters = 0;

    public int getNumTrees() {
        return numTrees;
    }

    public BuildHuffmanTrees() {
    }

    public BuildHuffmanTrees(int[] freqTable) {
        this.freqTable = freqTable;
    }

    public void make() {
        // logic builds tree list of HuffNodes
        for (int i = 0; i < freqTable.length; i++) // prints out only the non empty index's
        {
            if (freqTable[i] != 0) {
                sumFreq += freqTable[i] * ((char) i);
                HuffNode newNode = new HuffNode((char) i, freqTable[i], null, null);
                trees.add(numTrees, newNode);
                numTrees++;
                numberOfLetters++;
            }
        }

        // logic merges huffTree high to low
        Collections.sort(trees, Collections.reverseOrder());

        // logic merges HuffNode list into a Huffman tree
        for (int i = numTrees - 1; i > 0; i--) {
            HuffNode last = trees.get(i);
            HuffNode secondLast = trees.get(i - 1);
            int total = last.getFreq() + secondLast.getFreq();
            Character c1 = last.getChar();
            Character c2 = secondLast.getChar();
             merge = new HuffNode(null, total, last, secondLast);
            trees.remove(last);
            trees.set(i - 1, merge);
            Collections.sort(trees, Collections.reverseOrder());
        }
    }

    public HuffNode getTree() {
        HuffNode tmp = merge;
        return tmp;
    }

    public List<HuffNode> getTrees() {
        return trees;
    }
}