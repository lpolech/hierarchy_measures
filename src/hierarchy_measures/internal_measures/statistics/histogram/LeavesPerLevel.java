package hierarchy_measures.internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Node;

public class LeavesPerLevel extends CommonPerLevelHistogram {

    protected double[] rec(Node n, int currentHeight, double[] histogram)
    {
        if(n.getChildren().isEmpty())
        {
            histogram[currentHeight] += 1;
        }
        else
        {
            currentHeight += 1;
            for(Node ch: n.getChildren())
            {
                rec(ch, currentHeight, histogram);
            }
        }

        return histogram;
    }
}
