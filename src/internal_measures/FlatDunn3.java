package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

public class FlatDunn3 extends CommonQualityMeasure {
    private DistanceMeasure dist;

    private FlatDunn3() {}

    public FlatDunn3(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        Node[] nodes = h.getGroups();
        double minDistanceBetweenPointsInDifferentClusters = Double.MAX_VALUE;
        double maxCumulativeDistanceWithinCluster = (-1)*Double.MAX_VALUE;
        for(int n1 = 0; n1 < nodes.length; n1++)
        {
            LinkedList<Instance> n1Instances = nodes[n1].getNodeInstances();
            if(!n1Instances.isEmpty()) {
                for (int n2 = n1 + 1; n2 < nodes.length; n2++) {
                    LinkedList<Instance> n2Instances = nodes[n2].getNodeInstances();
                    for (int i1 = 0; i1 < n1Instances.size(); i1++) {
                        for (int i2 = 0; i2 < n2Instances.size(); i2++) {
                            double distance = dist.getDistance(n1Instances.get(i1), n2Instances.get(i2));
                            minDistanceBetweenPointsInDifferentClusters = Math.min(distance,
                                    minDistanceBetweenPointsInDifferentClusters);
                        }
                    }
                }

                if(n1Instances.size() > 1) {
                    double cumulativeDistanceWithinCluster = 0.0;
                    for (int i1 = 0; i1 < n1Instances.size(); i1++) {
                        for (int i2 = 0; i2 < n1Instances.size(); i2++) {
                            if (i1 != i2) {
                                cumulativeDistanceWithinCluster += dist.getDistance(n1Instances.get(i1), n1Instances.get(i2));
                            }
                        }
                    }
                    cumulativeDistanceWithinCluster =
                            cumulativeDistanceWithinCluster / (double) (n1Instances.size() * (n1Instances.size() - 1));
                    maxCumulativeDistanceWithinCluster =
                            Math.max(cumulativeDistanceWithinCluster, maxCumulativeDistanceWithinCluster);
                }
            }
        }

        if(maxCumulativeDistanceWithinCluster == (-1)*Double.MAX_VALUE)
        {
            System.err.println("FlatDunn3::getMeasure - the maxCumulativeDistanceWithinCluster didn't change! It is " +
                    "probably because every cluster contain only 1 instance. Returning NaN");
            return Double.NaN;
        }
        return minDistanceBetweenPointsInDifferentClusters/maxCumulativeDistanceWithinCluster;
    }

    @Override
    public double getDesiredValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }
}
