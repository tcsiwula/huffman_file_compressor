
public class CommandLineArguments
{
    private String args[], infile, outfile;
    private boolean compress = false, uncompress = false, verbose, force;
    char r = 'r';

    public Compress cmp;
    public Uncompress uncmp;
    public CommandLineArguments(String args[])
    {
        this.args = args;
    }

    public void parse()
    {
        int length = args.length;
        int last = length - 1;
        int secondLast = length - 2;
        System.out.println("user entered " + length + " arguments.");

        for (int i = 0; i < length; i++)
        {
            String index = args[i];
//            System.out.println("Parsing argument # " + i + " of #" + length);
//            System.out.println("current argument = " + index);
            System.out.println();

            if ( index.equals("-c") || index.equals("-C")) {
                compress = true;
                System.out.println("user typed compress = " + compress);
            }
            if ( index.equals("-u") || index.equals("-U")) {
                uncompress = true;
                System.out.println("user typed uncompress = " + uncompress);
            }
            if ( index.equals("-v") || index.equals("-V")) {
                verbose = true;
                System.out.println("user typed verbose = " + verbose);
            }
            if ( index.equals("-f") || index.equals("-F")) {
                force = true;
                System.out.println("user typed force = " + force);
            }
            if ( i == secondLast) {
                infile = index;
                System.out.println("user secondLast arg for infile = " + infile);
            }
            if ( i == last ) {
                outfile = index;
                System.out.println("user last arg for outfile = " + outfile);
            }
        } // end parsing arguments.

        System.out.println();

        if ( compress == true) {
            cmp = new Compress(r, verbose, force, infile, outfile);
            System.out.println("compress object created in CommandLineArguments. Now calling compress.run()");
            System.out.println();
            cmp.run(new TextFile(infile, 'r'));
        }
        else {
            if (uncompress == true)
            {
                uncmp = new Uncompress(r, verbose, force, infile, outfile);
                System.out.println("uncompress object created in CommandLineArguments. Now calling uncompress.run()");
                System.out.println();
                uncmp.run();

            } else {
                System.out.println();
                System.out.println("There seems to be error. User did not specify compress or uncompress. Try again.");
            }
        }
    }
}
