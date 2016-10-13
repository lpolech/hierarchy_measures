package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import interfaces.QualityMeasure;

import java.util.HashMap;

public class VarianceDeviation2 implements QualityMeasure {

	@Override
	public double getMeasure(Hierarchy h) {
		double sumOfVarianceRatios = 0.0;
		int dataDim = Integer.MIN_VALUE;
		HashMap<Node, Double[]> nodesWithVariances = new HashMap<>(h.getNumberOfGroups(), 1.0f);
		for(Node n: h.getGroups())
		{
			nodesWithVariances.put(n, Utils.nodeSubtreeVariance(n));
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
					sumOfVarianceRatios += childVar[i]/parentVar[i];
				}
			}
		}
		
		sumOfVarianceRatios /= ((h.getNumberOfGroups()-1) * dataDim);
		
		return sumOfVarianceRatios;
	}

	@Override
	public double getDesiredValue() {
		return 1;
	}

	@Override
	public double getNotDesiredValue() {
		return Double.MAX_VALUE;
	}

}
