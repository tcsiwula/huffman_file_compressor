/**
 * Created by 415_pro on 3/16/15.
 */
public class HuffNode implements Comparable<HuffNode> {


    public Integer frequency;
    public Character myChar; // only leaf nodes will use
    public HuffNode left; // aka 0
    public HuffNode right; // aka 1

    protected HuffNode()
    {}

    private HuffNode(HuffNode l, HuffNode r)
    {
        left = l;
        right = r;
    }

    public HuffNode(Character c, int f, HuffNode l, HuffNode r) {
        myChar = c;
        frequency = f;
        left = l;
        right = r;
    }


    public boolean isLeaf()
    {  return left == null && right == null;  }

    public HuffNode left() {
        return left;
    }

    public HuffNode right() {
        return right;
    }

    public void setLeft(HuffNode left) {
        this.left = left;
    }

    public void setRight(HuffNode right) {
        this.right = right;
    }

    public Character getChar() {
        return myChar;
    }

    public int getFreq() {
        return frequency;
    }

    public int compareTo(HuffNode o) {
        return this.frequency.compareTo(o.getFreq());
    }
}
