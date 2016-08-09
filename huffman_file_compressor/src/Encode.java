public class Encode extends BinaryFile
{
    // private variables
    private String outfile, infile, myPath = "";
    private char readOrWrite, read = 'r';
    int i = 0;
    private HuffNode tree;
    private BinaryFile encoder;
    public TextFile reader;
    private BuildHuffmanTables huffTable = new BuildHuffmanTables();
    private String[] htable = huffTable.getTable();
    public String encodingLiteral;

    // constructor
    public Encode(String infile, String outfile, char readOrWrite, HuffNode tree)
    {
        super(outfile, readOrWrite);
        this.outfile = outfile;
        this.readOrWrite = readOrWrite;
        this.tree = tree;
        reader = new TextFile(infile, read);
    }

    public void run(BinaryFile encoder)
    {
        encoder = new BinaryFile(outfile, readOrWrite);
        // 1 of 3
        magicNumber(encoder);
    }

    public void magicNumber(BinaryFile encoder)
    {
        System.out.println("Step 1 of 3 in Encode.run(). Encoding magic number ;-)");
        encodingLiteral = "H";
        encodingLiteral += 'F';
        encoder.writeChar('H');
        encoder.writeChar('F');

        // 2 of 3
        System.out.println("Step 2 of 3 in Encode.run(). Pre-order traversal of the tree");
        encodeTree(tree, myPath, encoder);
    }

    // encode tree
    public void encodeTree(HuffNode tree, String myPath, BinaryFile encoder)
    {
        this.myPath = myPath;

        if ( tree == null) {
            System.out.println("tree is null.");
            encoder.writeBit(false);
            encodingLiteral += '0';
            myPath += "null";
        }
        else
        {
            System.out.println("printing nodes ... #" + i++);
            if (  tree.myChar == null)
            {
                // interior node, print single bit with value of 1
                encoder.writeBit(true);
                encodingLiteral += '1';
                System.out.println("encoded 1.");
                myPath += "1";
            }
            else
            {
                // leaf node, print single bit with value of 0
                encoder.writeBit(false);
                encodingLiteral += '0';
                System.out.println(" encoded: " +  tree.myChar);
                encoder.writeChar(tree.myChar);
                encodingLiteral += tree.myChar;
                myPath += "0+char";
            }
            encodeTree(tree.left(), "0" + myPath, encoder);
            encodeTree(tree.right(), "1" + myPath, encoder);
        }

        //3 of 3
        writeCharacters(reader, encoder);
    }

    public void writeCharacters(TextFile reader, BinaryFile encoder)
    {
        System.out.println("Step 3 of 3 in Encode.run(). Pre-order traversal of the tree");
        //huffTable.make(tree, "");
        char c;
        String curr;
        while ( !reader.EndOfFile() )
        {
            char tmp = reader.readChar();
            String t = String.valueOf(tmp);
            for (int i = 0; i < htable.length; i++)
            {
                if (htable[i] != null)
                {
                     c = (char) i;
                     curr = String.valueOf(c);
                    if ( t.equals(curr) )
                    {
                        String data = htable[i];
                        System.out.println("data = " + data);
                        for (int j = 0; j < data.length(); j++)
                        {
                            int test = Integer.parseInt(String.valueOf(data.charAt(j)));
                            int a = Integer.valueOf(data);
                            if ( test == 0)
                            {
                                encoder.writeBit(false);
                                encodingLiteral += '0';

                                System.out.println("wrote 0");
                            }

                            if ( test == 1)
                            {
                                encoder.writeBit(true);
                                encodingLiteral += '1';
                                System.out.println("wrote 1");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("encodingLiteral = " + encodingLiteral);
        encoder.close();
    }
}
