package internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;

public class ChildPerNodePerLevel extends CommonPerLevelHistogram {
    private int[] nodesPerLevel;

    @Override
    protected double[] calculateWithKnownHeight(Hierarchy hierarchy, int height) {
        nodesPerLevel = new int[height + 1];
        double[] unnormalisedHistogram = rec(hierarchy.getRoot(), 0, new double[height + 1]);

        for(int i = 0; i < unnormalisedHistogram.length; i++)
        {
            unnormalisedHistogram[i] /= nodesPerLevel[i];
        }

        return unnormalisedHistogram;
    }

    protected double[] rec(Node n, int currentHeight, double[] histogram)
    {
        histogram[currentHeight] += n.getChildren().size();
        nodesPerLevel[currentHeight] += 1;

        currentHeight += 1;
        for(Node ch: n.getChildren())
        {
            rec(ch, currentHeight, histogram);
        }

        return histogram;
    }
}
