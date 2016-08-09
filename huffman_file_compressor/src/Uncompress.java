
public class Uncompress extends BinaryFile
{
    private boolean magicNumber = false;
    private String infile, outfile;
    private char c, g, read, write = 'w';
    private int newNumTrees = 0;
    private boolean verbose, force;
    public BinaryFile reader;
    char first, second;
    private Decode decode;

    public Uncompress(char read, boolean verbose, boolean force, String infile, String outfile )
    {
        super(infile, read);
        this.read = read;
        this.verbose = verbose;
        this.force = force;
        this.infile = infile;
        this.outfile = outfile;
        reader = new BinaryFile(infile, read);
    }

    public void run()
    {
        // 1
        decode = new Decode(infile, outfile, read);
        decode.run();
    }
}
