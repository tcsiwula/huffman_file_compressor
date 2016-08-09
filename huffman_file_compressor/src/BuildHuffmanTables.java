
public class BuildHuffmanTables {
    private HuffNode tree;
    private String myPath = "", tmpPath = "";
    private String[] huffmanTable = new String[255];

    public BuildHuffmanTables(){}
    public BuildHuffmanTables(HuffNode tree)
    {
        this.tree = tree;
    }

    public void run()
    {
        make(tree, myPath);
    }

    public void make(HuffNode tree, String myPath)
    {
        if (tree != null)
        {
            if (tree.left() == null && tree.right() == null)
            {
                char tmp = tree.getChar();
                tmpPath = myPath;
                huffmanTable[(int) tmp] = myPath;
               // System.out.println( tmp + " = " + myPath);
            }
            make(tree.left, myPath + "0");
            make(tree.right, myPath + "1");
        }
    }

    public String[] getTable()
    {
        return huffmanTable;
    }
}
