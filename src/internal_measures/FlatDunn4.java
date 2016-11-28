package internal_measures;

import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.ArrayList;
import java.util.LinkedList;

public class FlatDunn4 extends CommonQualityMeasure {
    private DistanceMeasure dist;

    private FlatDunn4() {}

    public FlatDunn4(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        Node[] nodes = h.getGroups();
        double minDistanceBetweenPointsInDifferentClusters = Double.MAX_VALUE;
        double maxAvgPointsDistanceToClusterCenter = (-1)*Double.MAX_VALUE;

        Instance[] oldRepr = new Instance[nodes.length];
        for(int n = 0; n < nodes.length; n++) {
            oldRepr[n] = ((BasicNode)nodes[n]).recalculateCentroid(false);
        }

        for(int n1 = 0; n1 < nodes.length; n1++)
        {
            ArrayList<Instance> n1Instances = new ArrayList<>(nodes[n1].getNodeInstances());

            if(!n1Instances.isEmpty()) {
                for (int n2 = n1 + 1; n2 < nodes.length; n2++) {
                    ArrayList<Instance> n2Instances = new ArrayList<>(nodes[n2].getNodeInstances());
                    for (int i1 = 0; i1 < n1Instances.size(); i1++) {
                        for (int i2 = 0; i2 < n2Instances.size(); i2++) {
                            double distance = dist.getDistance(n1Instances.get(i1), n2Instances.get(i2));//TODO: random access od linkedlist is slow
                            minDistanceBetweenPointsInDifferentClusters = Math.min(distance,
                                    minDistanceBetweenPointsInDifferentClusters);
                        }
                    }
                }

                double avgPointsDistanceToClusterCenter = 0.0;
                for (Instance inst : n1Instances) {
                    avgPointsDistanceToClusterCenter += dist.getDistance(inst, nodes[n1].getNodeRepresentation());
                }
                avgPointsDistanceToClusterCenter =
                        avgPointsDistanceToClusterCenter / (double) n1Instances.size();
                maxAvgPointsDistanceToClusterCenter =
                        Math.max(avgPointsDistanceToClusterCenter, maxAvgPointsDistanceToClusterCenter);
            }
        }

        for(int n = 0; n < nodes.length; n++) {
            ((BasicNode)nodes[n]).setRepresentation(oldRepr[n]);
        }

        if(maxAvgPointsDistanceToClusterCenter == (-1)*Double.MAX_VALUE)
        {
            System.err.println("FlatDunn3.getMeasure - the maxAvgPointsDistanceToClusterCenter didn't change! It is " +
                    "probably because every cluster contain at maximum 1 instance");
        }
        if(minDistanceBetweenPointsInDifferentClusters == Double.MAX_VALUE) {
            System.err.println("FlatDunn3.getMeasure - the minDistanceBetweenPointsInDifferentClusters  haven't changed, " +
                    "so there should be something wrong with the input hierarchy (maybe there are empty clusters or " +
                    "clusters with single element?): ");
        }

        return minDistanceBetweenPointsInDifferentClusters/maxAvgPointsDistanceToClusterCenter;
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
