package internal_measures;

import java.util.HashMap;
import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;
import common.Utils;

public class VarianceDeviation extends CommonQualityMeasure {
	private double alpha;
	private VarianceDeviation() {
	}
	
	public VarianceDeviation(double alpha)
	{
		this.alpha = alpha;
	}
	
	@Override
	public double getMeasure(Hierarchy h) {
		double sumOfVarianceRatios = 0.0;
		int dataDim = Integer.MIN_VALUE;
        int numberOfSkippedGroups = 0;
		HashMap<Node, Double[]> nodesWithVariances = new HashMap<>(h.getNumberOfGroups(), 1.0f);
		for(Node n: h.getGroups())
		{
			LinkedList<Instance> subtree = n.getSubtreeInstances();
            if(!subtree.isEmpty())
                nodesWithVariances.put(n, Utils.nodeSubtreeVariance(n, true));
            else
                numberOfSkippedGroups += 1;
		}
		
		for(Node n: h.getGroups())
		{
			if(n.getParent() != null)
			{
				Double[] parentVar = nodesWithVariances.get(n.getParent());
				Double[] childVar = nodesWithVariances.get(n);
				dataDim = parentVar.length;
				for(int i = 0; i < dataDim; i++)
				{
					sumOfVarianceRatios += Math.max(this.alpha, parentVar[i] == 0.0? this.alpha: childVar[i]/parentVar[i]);
				}
			}
		}
		
		sumOfVarianceRatios /= ((h.getNumberOfGroups() - 1 - numberOfSkippedGroups) * dataDim) * this.alpha;
		
		return sumOfVarianceRatios;
	}

	@Override
	public double getDesiredValue() {
		return this.alpha;
	}

	@Override
	public double getNotDesiredValue() {
		return Double.MAX_VALUE;
	}

}
