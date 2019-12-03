package internal_measures.statistics.histogram;

import java.util.ArrayList;
import java.util.HashMap;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import internal_measures.statistics.AvgWithStdev;

public class HistogramOfNumberOfChildren extends CommonPerLevelHistogram {

	@Override
	protected double[] rec(Node n, int currentHeight, double[] histogram) {
		return new double[0];
	}

	@Override
	public double[] calculate(Hierarchy h) {
		HashMap<Integer, Integer> histogramBins = new HashMap<>(h.getNumberOfGroups(), 1.0f);
		int maxNumberOfChildren = buildHistogramBins(h, histogramBins);

		return postprocessHistogram(histogramBins, maxNumberOfChildren);
	}

	private double[] postprocessHistogram(HashMap<Integer, Integer> histogramBins, int maxNumberOfChildren) {
		double[] histogram = new double[maxNumberOfChildren + 1];
		for (int i = 0; i <= maxNumberOfChildren; i++) {
			if (histogramBins.containsKey(i)) {
				histogram[i] = histogramBins.get(i);
			}
		}
		return histogram;
	}

	public AvgWithStdev[] calculate(ArrayList<Hierarchy> hierarchies) {
		double[][] histograms = new double[hierarchies.size()][];
		int highestBinNumber = 0;
		for (int i = 0; i < hierarchies.size(); i++) {
			histograms[i] = this.calculate(hierarchies.get(i));
			highestBinNumber = Math.max(highestBinNumber, histograms[i].length);
		}

		return aggregateHistogramsAndCalculateMeanAndStdev(histograms, highestBinNumber - 1, true);
	}

	private int buildHistogramBins(Hierarchy h, HashMap<Integer, Integer> histogram) {
		int maxNumberOfChildren = 0;
		for (Node n : h.getGroups()) {
			int numberOfChildren = n.getChildren().size();
			maxNumberOfChildren = Math.max(maxNumberOfChildren, numberOfChildren);
			if (histogram.containsKey(numberOfChildren)) {
				Integer incrementedCount = histogram.get(numberOfChildren) + 1;
				histogram.put(numberOfChildren, incrementedCount);
			} else {
				histogram.put(numberOfChildren, 1);
			}
		}
		return maxNumberOfChildren;
	}
}
