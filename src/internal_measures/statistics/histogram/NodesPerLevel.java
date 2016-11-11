package internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Node;

public class NodesPerLevel extends CommonPerLevelHistogram {

    protected double[] rec(Node n, int currentHeight, double[] histogram)
    {
        histogram[currentHeight] += 1;

        currentHeight += 1;
        for(Node ch: n.getChildren())
        {
            rec(ch, currentHeight, histogram);
        }

        return histogram;
    }

}
