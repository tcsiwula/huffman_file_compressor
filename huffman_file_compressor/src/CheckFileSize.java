
public class CheckFileSize {
    public HuffNode tree;
    private int[] input;
    private String[] hTable;
    private boolean flagOption = false;

    public CheckFileSize(HuffNode tree, int[] input, String[] hTable)
    {
        this.tree = tree;
        this.input = input;
        this.hTable = hTable;
    }

    public boolean compute(HuffNode tree)
    {
        int compSize = getCompressedFileSize( tree);
        int unCompSize = getUncompressedFileSize();

        if (compSize < unCompSize || flagOption == true)
            return true;

        return false;
    }

    public int getUncompressedFileSize()
    {
        int sum = 0;
        for ( int i = 0; i < input.length; i++)
        {
            sum++;
        }

        sum = sum*8;

        System.out.println("getUncompressedFileSize() = " + sum);
        return sum;
    }

    public int getCompressedFileSize(HuffNode tree)
    {
        int sum = 0;
        int freq = 0;
        for ( int i = 0; i < 255; i++)
        {
            if ( hTable[i] != null )
            {
                freq = Integer.parseInt(hTable[i]);
                String c = String.valueOf((char) i);
                int l = getCharacterEncodingSize(c, tree);
                sum += (l * freq);
            }
        }

        // get size of tree ( internal nodes +1 and leaf nodes +9)
        sum+= getTreeSize(tree);
        // add extra 16 bits for magic number
        sum += 16;
        // add extra 32 bits for binary file header
        sum += 32;

        if (sum % 8 != 0)
        {
            System.out.println("Sum size = " + sum);
            sum += sum %8;
            System.out.println("new size = " + sum);
        }

        System.out.println("getCompressedFileSize() = " + sum);
        return sum;
    }

    public int getCharacterEncodingSize(String c, HuffNode tree)
    {
        int length = 0;
        Character tmp;

        for(int i = 0; i < hTable.length; i ++)
        {
            tmp = tree.getChar();
            if (c.equals( tmp))
            {
                String thePath = hTable[(int) tmp];
                length = thePath.length();
            }
        }
        System.out.println("getCharacterEncodingSize() = " + length);
        return length;
    }

    public int getTreeSize(HuffNode tree)
    {
        if (tree == null)
            return 0;

        return tree.right() == null && tree.left() == null ? 9 : 1 + getTreeSize(tree.left()) + getTreeSize(tree.right());
    }
}
