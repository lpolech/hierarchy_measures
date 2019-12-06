package hierarchy_measures.internal_measures;

import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.DistanceMeasure;
import basic_hierarchy.interfaces.Hierarchy;

import java.util.ArrayList;

public class FlatWithinBetweenIndex extends CommonQualityMeasure {
    private DistanceMeasure dist;

    private FlatWithinBetweenIndex() {}

    public FlatWithinBetweenIndex(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
	public double getMeasure(Hierarchy h) {
		double maxWithinNodeDistance = (-1)*Double.MAX_VALUE;
        double minNodesCentersDistance = Double.MAX_VALUE;

        Node[] nodes = h.getGroups();

        Instance[] oldRepr = new Instance[nodes.length];
        for(int n = 0; n < nodes.length; n++) {
            oldRepr[n] = ((BasicNode)nodes[n]).recalculateCentroid(false);
        }

        for(int n1 = 0; n1 < nodes.length; n1++)
		{
            ArrayList<Instance> n1Instances = new ArrayList<>(nodes[n1].getNodeInstances());
            if(!nodes[n1].getNodeInstances().isEmpty()) {
                for (int i1 = 0; i1 < n1Instances.size(); i1++) {
                    for (int i2 = i1; i2 < n1Instances.size(); i2++) {
                        double distance = dist.getDistance(n1Instances.get(i1), n1Instances.get(i2));//TODO: random index access is slow on linked lists
                        maxWithinNodeDistance = Math.max(distance, maxWithinNodeDistance);
                    }
                }

                for (int n2 = n1 + 1; n2 < nodes.length; n2++) {
                    if (!nodes[n2].getNodeInstances().isEmpty()){
                        double distance =
                                dist.getDistance(nodes[n1].getNodeRepresentation(), nodes[n2].getNodeRepresentation());
                        minNodesCentersDistance = Math.min(distance, minNodesCentersDistance);
                    }
                }
            }
		}

        for(int n = 0; n < nodes.length; n++) {
            ((BasicNode)nodes[n]).setRepresentation(oldRepr[n]);
        }

        if(maxWithinNodeDistance == (-1)*Double.MAX_VALUE) {
            System.err.println("FlatWithinBetweenIndex.getMeasure maxWithinNodeDistance didn't change! It is probably " +
                    "because every cluster contain at maximum 1 instance. Returning NaN.");
            return Double.NaN;
        }
        if(minNodesCentersDistance == Double.MAX_VALUE) {
            System.err.println("FlatWithinBetweenIndex.getMeasure minNodesCentersDistance haven't changed, so there " +
                    "should be something wrong with the input hierarchy (maybe there are empty clusters or clusters " +
                    "with single element?). Returning NaN. ");
            return Double.NaN;
        }
        return maxWithinNodeDistance/minNodesCentersDistance;
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
