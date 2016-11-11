package internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import internal_measures.statistics.AvgWithStdev;

import java.util.ArrayList;
import java.util.HashMap;

public class HistogramOfNumberOfChildren {

    public double[] calculate(Hierarchy h)
    {
        HashMap<Integer, Integer> histogramBins = new HashMap<>(h.getNumberOfGroups(), 1.0f);
        int maxNumberOfChildren = buildHistogramBins(h, histogramBins);

        double[] histogram = new double[maxNumberOfChildren + 1];
        for(int i = 0; i <= maxNumberOfChildren; i++)
        {
            if(histogramBins.containsKey(i))
            {
                histogram[i] = histogramBins.get(i);
            }
        }

        return histogram;
    }

    public AvgWithStdev[] calculate(ArrayList<Hierarchy> hierarchies)
    {
        double[][] histograms = new double[hierarchies.size()][];
        int highestBinNumber = 0;
        for(int i = 0; i < hierarchies.size(); i++)
        {
            histograms[i] = this.calculate(hierarchies.get(i));
            highestBinNumber = Math.max(highestBinNumber, histograms[i].length);
        }

        return aggregateHistogramsAndCalculateMeanAndStdev(histograms, highestBinNumber);
    }

    private int buildHistogramBins(Hierarchy h, HashMap<Integer, Integer> histogram) {
        int maxNumberOfChildren = 0;
        for(Node n: h.getGroups())
        {
            int numberOfChildren = n.getChildren().size();
            maxNumberOfChildren = Math.max(maxNumberOfChildren, numberOfChildren);
            if(histogram.containsKey(numberOfChildren))
            {
                Integer incrementedCount = histogram.get(numberOfChildren) + 1;
                histogram.put(numberOfChildren, incrementedCount);
            }
            else
            {
                histogram.put(numberOfChildren, 1);
            }
        }
        return maxNumberOfChildren;
    }

    protected AvgWithStdev[] aggregateHistogramsAndCalculateMeanAndStdev(double[][] finalHistogram, int highestBinNumber) {
        AvgWithStdev[] result = new AvgWithStdev[highestBinNumber];

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
