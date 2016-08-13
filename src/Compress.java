
public class Compress
{
    private String infile, outfile;
    private char read, write = 'w';
    private boolean verbose, force, shallWeEncode;
    private int numOfCharacters = 0, totalCharactersInFile = 0;
    private int[] freqTable = new int[255];
    public Encode encoder;

    public Compress(char read, boolean verbose, boolean force, String infile, String outfile )
    {
       // super(infile, read);
        this.read = read;
        this.verbose = verbose;
        this.force = force;
        this.infile = infile;
        this.outfile = outfile;
    }

    public static void test(TextFile reader)
    {
        while ( !reader.EndOfFile() )
        {
            char tmp = reader.readChar();

            System.out.println("Just added '" + tmp + "' to index '" + (int) tmp + "' in freqTable[]. ");
        }
    }

    public TextFile reader = new TextFile(infile, read);

    public void run(TextFile reader )
    {
        // 1 of 5
        // parse
        System.out.println();
        System.out.println("Step 1 of 5 in Compress.run(). parsing file.");

        while ( !reader.EndOfFile() )
        {
            char tmp = reader.readChar();
            totalCharactersInFile++;
            numOfCharacters += (totalCharactersInFile) * 8;
            freqTable[(int) tmp]++;
            System.out.println("Just added '" + tmp + "' to index '" + (int) tmp + "' in freqTable[]. ");
        }
        reader.rewind();

        // 2 of 5
        // build tree
        System.out.println();
        System.out.println("Step 2 of 5 in Compress.run(). building tree.");
        BuildHuffmanTrees huffTree = new BuildHuffmanTrees(freqTable);
        huffTree.make();
        HuffNode tree = huffTree.getTree();

        // 3 of 5
        // build table
        System.out.println();
        System.out.println("Step 3 of 5 in Compress.run(). building table.");
        BuildHuffmanTables huffTable = new BuildHuffmanTables(tree);
        huffTable.make(tree, "");
        String[] hTable = huffTable.getTable();

        // 4 of 5
        // compute
        System.out.println();
        System.out.println("Step 4 of 5 in Compress.run(). computing file.");
        CheckFileSize fileSize = new CheckFileSize(tree, freqTable, hTable);
        shallWeEncode = fileSize.compute(tree);

        // 5 of 5
        // encode file
        System.out.println();
        if ( shallWeEncode == true)
        {
            System.out.println("Step 5 of 5 in Compress.run(). Encoding file.");
            encoder = new Encode(infile, outfile, write, tree);
            //cmp = new Compress(r, verbose, force, infile, outfile);
            // cmp.run(new TextFile(infile, 'r'));
            encoder.run(new BinaryFile(outfile, 'w'));
        }
    }
}
