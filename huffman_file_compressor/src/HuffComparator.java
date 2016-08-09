/**
 * Created by 415_pro on 3/16/15.
 */

import java.util.*;

public class HuffComparator  {
    public HuffComparator(){}

    public int compare(HuffNode o1, HuffNode o2)
    {
        if ( o1.getFreq() > o2.getFreq())
            return 1;
        if (o1.getFreq() < o2.getFreq())
            return -1;
        return 0;
    }
}
