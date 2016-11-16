package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

public class FlatDaviesBouldin extends CommonQualityMeasure {
    private DistanceMeasure dist;

    private FlatDaviesBouldin() {}

    public FlatDaviesBouldin(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        double maxAvgClustersDispersion = (-1)*Double.MAX_VALUE;

        Node[] nodes = h.getGroups();
        double[] groupAvgDispersion = new double[nodes.length];
        int numberOfSkippedEmptyNodes = 0;
        for(int n = 0; n < nodes.length; n++)
        {
            if(nodes[n].getNodeInstances().isEmpty()) {
                numberOfSkippedEmptyNodes += 1;
            }
            else {
                Instance nCenter = nodes[n].getNodeRepresentation();
                for (Instance i : nodes[n].getNodeInstances()) {
                    groupAvgDispersion[n] += dist.getDistance(i, nCenter);
                }
                groupAvgDispersion[n] /= nodes[n].getNodeInstances().size();//because of this division (outside of
                // distanse's root, this measure differs with this in SYNAT report
            }
        }

        for(int n1 = 0; n1 < nodes.length; n1++)
        {
            for(int n2 = n1 + 1; n2 < nodes.length; n2++)
            {
                if(!nodes[n1].getNodeInstances().isEmpty() && !nodes[n2].getNodeInstances().isEmpty()) {
                    double groupsDistance = dist.getDistance(nodes[n1].getNodeRepresentation(),
                            nodes[n2].getNodeRepresentation());
                    double avgGroupsDispersion = (groupAvgDispersion[n1] + groupAvgDispersion[n2]) / groupsDistance;
                    maxAvgClustersDispersion = Math.max(maxAvgClustersDispersion, avgGroupsDispersion);
                }
            }
        }
        return maxAvgClustersDispersion/(double)(nodes.length-numberOfSkippedEmptyNodes);
    }

    @Override
    public double getDesiredValue() {
        return 0;
    }

    @Override
    public double getNotDesiredValue() {
        return Double.MAX_VALUE;
    }
}
