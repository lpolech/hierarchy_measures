package hierarchy_measures.internal_measures;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.DistanceMeasure;

public class FlatDunn3 extends CommonQualityMeasure {
	private DistanceMeasure dist;
	private static final Logger log = LogManager.getLogger(FlatDunn3.class);

	protected FlatDunn3() {
	}

	public FlatDunn3(DistanceMeasure dist) {
		this.dist = dist;
	}

	@Override
	public double getMeasure(Hierarchy h) {
		return calculate(h, false);
	}

	@Override
	public double getDesiredValue() {
		return Double.MAX_VALUE;
	}

	@Override
	public double getNotDesiredValue() {
		return 0;
	}

	protected double calculate(Hierarchy h, boolean returnMeasureReversion) {
		Node[] nodes = h.getGroups();
		double minDistanceBetweenPointsInDifferentClusters = Double.MAX_VALUE;
		double maxCumulativeDistanceWithinCluster = 0;
		for (int n1 = 0; n1 < nodes.length; n1++) {
			ArrayList<Instance> n1Instances = new ArrayList<>(nodes[n1].getNodeInstances());
			if (!n1Instances.isEmpty()) {
				for (int n2 = n1 + 1; n2 < nodes.length; n2++) {
					ArrayList<Instance> n2Instances = new ArrayList<>(nodes[n2].getNodeInstances());
					for (int i1 = 0; i1 < n1Instances.size(); i1++) {
						for (int i2 = 0; i2 < n2Instances.size(); i2++) {
							double distance = dist.getDistance(n1Instances.get(i1), n2Instances.get(i2));// TODO: random
																											// access od
																											// linkedlist
																											// is slow
							minDistanceBetweenPointsInDifferentClusters = Math.min(distance,
									minDistanceBetweenPointsInDifferentClusters);
						}
					}
				}

				if (n1Instances.size() > 1) {
					double cumulativeDistanceWithinCluster = 0.0;
					for (int i1 = 0; i1 < n1Instances.size(); i1++) {
						for (int i2 = 0; i2 < n1Instances.size(); i2++) {
							if (i1 != i2) {
								cumulativeDistanceWithinCluster += dist.getDistance(n1Instances.get(i1),
										n1Instances.get(i2));
							}
						}
					}
					cumulativeDistanceWithinCluster = cumulativeDistanceWithinCluster
							/ (double) (n1Instances.size() * (n1Instances.size() - 1));
					maxCumulativeDistanceWithinCluster = Math.max(cumulativeDistanceWithinCluster,
							maxCumulativeDistanceWithinCluster);
				}
			}
		}

		if (minDistanceBetweenPointsInDifferentClusters == Double.MAX_VALUE) {
			log.error("FlatDunn3.getMeasure - the minDistanceBetweenPointsInDifferentClusters  haven't changed, "
					+ "so there should be something wrong with the input hierarchy (maybe there are empty clusters or "
					+ "clusters with single element?) Returning NaN.");
			return Double.NaN;
		}

		if (returnMeasureReversion && minDistanceBetweenPointsInDifferentClusters != 0)
			return maxCumulativeDistanceWithinCluster / minDistanceBetweenPointsInDifferentClusters;

		if (maxCumulativeDistanceWithinCluster != 0)
			return minDistanceBetweenPointsInDifferentClusters / maxCumulativeDistanceWithinCluster;
		return Double.NaN;
	}
}
