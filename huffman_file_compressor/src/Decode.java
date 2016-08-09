import java.util.ArrayList;
import java.util.List;

public class Decode extends BinaryFile
{
    // private variables
    private String outfile, infile, myPath = "";
    private char readOrWrite, first, second, g, c;
    int i = 0, newNumTrees = 0;
    private boolean magicNumber;
    public HuffNode root;
    public BuildHuffmanTrees tmp = new BuildHuffmanTrees();
    public int max = tmp.getNumTrees();
    public BinaryFile reader, reader2;
    private TextFile writer;
    private BuildHuffmanTrees theTrees;// = new BuildHuffmanTrees();
    public List<HuffNode> dummy = new ArrayList<HuffNode>();


    // constructor
    public Decode(String outfile, String infile, char readOrWrite)
    {
        super(outfile, readOrWrite);
        this.outfile = outfile;
        this.infile = infile;
        this.readOrWrite = readOrWrite;
        writer = new TextFile(infile, 'w');
    }

    public void run() {
        theTrees = new BuildHuffmanTrees();
        //dummy = theTrees.getTrees();
        root = theTrees.getTree();
        reader = new BinaryFile(outfile, 'r');
        reader2 = new BinaryFile(outfile, 'r');

        // 1 of 3
        magicNumber = checkMagicNumber();

        // 2 of 3
        if (magicNumber == true)
        {
            decode(reader, root);

            //theTrees.make();
           // print(root, reader2, writer);

            decodeInput(writer, reader2);

        }
    }

    public int count = 0;
    public int netBits = 0;

    public void decode(BinaryFile reader, HuffNode tree) {
        System.out.println("count = " + count++);
        while (!reader.EndOfFile()) {
            boolean index = reader.readBit();
            netBits++;
            System.out.println("netBits = " + netBits);

            if (netBits > 40) {
                System.out.println("reached end of file.");
                reader.close();

            } else {
                System.out.println("index = " + index);
                if (index == true) {
                    //  TODO throws errors constantly. reader.EndOfFile() < 8 && > 0. Could not catch the bug.
                    //  TODO See workaround on line 66, hard coded it to catch before error. perhaps missing 1 child.
                    char c = reader.readChar();
                    netBits += 8;
                    System.out.println("netBits = " + netBits);
                    // leaf node, print single bit with value of 0
                    HuffNode child = new HuffNode(c, 1, null, null);
                    System.out.println("created new child node = 1");
                    System.out.println("childs c = " + c);
                } else {
                    // interior node, create empty node and recursive over their left/right
                    HuffNode interior = new HuffNode(null, 0, null, null);
                    System.out.println("created new interior node = 0");
                    decode(reader, interior.left());
                    decode(reader, interior.right());
                }
            }
        }
    }

    public void decodeInput(TextFile writer, BinaryFile decoder) {
        char c;
        String input = "";

        // parse file to String
        while (!decoder.EndOfFile())
        {
            boolean index = decoder.readBit();
            if (index == true)
            {
                input += "1";
            } else if (!index)
            {
                input += "0";
            }
        }

        int size = input.length();
        System.out.println("size = " + size);
        String binaryString = "";
        for (int i = 0; i < size; i++)
        {
            char t = input.charAt(i);
            System.out.print(t);
            binaryString += t;
        }

        for (int i = 0; i < binaryString.length(); i++)
        {
            char index = binaryString.charAt(i);
            System.out.println();

           // System.out.println("index = " + index);
            print(root, decoder, writer, index);
        }
    }

    void print(HuffNode tree, BinaryFile r, TextFile w, char index)
    {
        tree = new HuffNode();

        String val = String.valueOf(index);
        int test = Integer.parseInt(String.valueOf(val));

        if (test == 1)
        {
            HuffNode rightNode = new HuffNode(null, 1, null, null);
            //tree = tree.right();
        }

        if (test == 0)
        {
            HuffNode leftNode = new HuffNode(null, 0, null, null);
        }

//        if (tree.isLeaf())
//            System.out.println("root is leaf");
    }

    public boolean checkMagicNumber() {
        while (!reader.EndOfFile()) {
            first = reader.readChar();
            System.out.println("checkMagicNumber 1 of 2. first character = " + first);

            second = reader.readChar();
            System.out.println("checkMagicNumber 2 of 2. second character = " + second);
            break;
        }
        if (first == 'H' && second == 'F') {
            System.out.println("returning true, magic number matches.");
            return true;
        }
        System.out.println("returning false, magic number does not match.");
        return false;
    }
}