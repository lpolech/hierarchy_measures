package internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.Height;

import java.util.ArrayList;

public abstract class CommonPerLevelHistogram {

    protected abstract double[] rec(Node n, int currentHeight, double[] histogram);

    public double[] calculate(Hierarchy hierarchy)
    {
        int hierarchyHeight = (int)new Height().calculate(hierarchy);
        return this.calculateWithKnownHeight(hierarchy, hierarchyHeight);
    }

    protected double[] calculateWithKnownHeight(Hierarchy hierarchy, int height) {
        return rec(hierarchy.getRoot(), 0, new double[height + 1]);
    }

    public AvgWithStdev[] calculate(ArrayList<Hierarchy> hierarchies)
    {
        double[][] histograms = new double[hierarchies.size()][];
        int maxHierarchyHeight = 0;
        for(int i = 0; i < hierarchies.size(); i++)
        {
            int hierarchyHeight = (int)new Height().calculate(hierarchies.get(i));
            maxHierarchyHeight = Math.max(maxHierarchyHeight, hierarchyHeight);
            histograms[i] = this.calculateWithKnownHeight(hierarchies.get(i), hierarchyHeight);
        }

        return aggregateHistogramsAndCalculateMeanAndStdev(histograms, maxHierarchyHeight);
    }

    protected AvgWithStdev[] aggregateHistogramsAndCalculateMeanAndStdev(double[][] finalHistogram, int maxHierarchyHeight) {
        AvgWithStdev[] result = new AvgWithStdev[maxHierarchyHeight + 1];

        for(int histogramNumber = 0; histogramNumber < finalHistogram.length; histogramNumber++)
        {
            double[] normalisedLengthHistogram = new double[result.length];
            System.arraycopy(finalHistogram[histogramNumber], 0, normalisedLengthHistogram, 0, finalHistogram[histogramNumber].length);
            finalHistogram[histogramNumber] = normalisedLengthHistogram;
        }

        for(int level = 0; level < result.length; level++)
        {
            double[] sample = new double[finalHistogram.length];
            for(int histogramNumber = 0; histogramNumber < finalHistogram.length; histogramNumber++)
            {
                sample[histogramNumber] = finalHistogram[histogramNumber][level];
            }
            double mean = Utils.populationMean(sample);
            double stdev = Utils.populationStdev(sample, mean);
            result[level] = new AvgWithStdev(mean, stdev);
        }
        return result;
    }
}
