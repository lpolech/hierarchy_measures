package hierarchy_measures.internal_measures;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.DistanceMeasure;

public class FlatDunn4 extends CommonQualityMeasure {
	private DistanceMeasure dist;
	private static final Logger log = LogManager.getLogger(FlatDunn4.class);

	protected FlatDunn4() {
	}

	public FlatDunn4(DistanceMeasure dist) {
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
		double maxAvgPointsDistanceToClusterCenter = 0;

		Instance[] oldRepr = new Instance[nodes.length];
		for (int n = 0; n < nodes.length; n++) {
			oldRepr[n] = ((BasicNode) nodes[n]).recalculateCentroid(false);
		}

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

				double avgPointsDistanceToClusterCenter = 0.0;
				for (Instance inst : n1Instances) {
					avgPointsDistanceToClusterCenter += dist.getDistance(inst, nodes[n1].getNodeRepresentation());
				}
				avgPointsDistanceToClusterCenter = avgPointsDistanceToClusterCenter / (double) n1Instances.size();
				maxAvgPointsDistanceToClusterCenter = Math.max(avgPointsDistanceToClusterCenter,
						maxAvgPointsDistanceToClusterCenter);
			}
		}

		for (int n = 0; n < nodes.length; n++) {
			((BasicNode) nodes[n]).setRepresentation(oldRepr[n]);
		}

		if (minDistanceBetweenPointsInDifferentClusters == Double.MAX_VALUE) {
			log.error("FlatDunn3.getMeasure - the minDistanceBetweenPointsInDifferentClusters  haven't changed, "
					+ "so there should be something wrong with the input hierarchy (maybe there are empty clusters or "
					+ "clusters with single element?). Returning NaN.");

			return Double.NaN;
		}

		if (returnMeasureReversion && minDistanceBetweenPointsInDifferentClusters != 0)
			return maxAvgPointsDistanceToClusterCenter / minDistanceBetweenPointsInDifferentClusters;

		if (maxAvgPointsDistanceToClusterCenter != 0)
			return minDistanceBetweenPointsInDifferentClusters / maxAvgPointsDistanceToClusterCenter;
		return Double.NaN;
	}
}
