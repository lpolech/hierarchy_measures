package external_measures.purity;

import java.util.HashMap;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;
import common.Utils;

public class HierarchicalClassPurity implements QualityMeasure {

	@Override
	public double getMeasure(Hierarchy h, DistanceMeasure dist) {
		int sumOfClassPurity = 0;
		
		Node[] allNodes = h.getGroups();//allGroups
		String[] allClasses = h.getClasses();
		HashMap<String, Integer> indexesClasses = new HashMap<>(allClasses.length, 1.0f);//classesWithIndices
		HashMap<Node, Integer[]> indexedPartials = new HashMap<>(allNodes.length, 1.0f);//nodesWithEveryClassPartialPurity
		
		for(int i = 0; i < allClasses.length; i++)
		{
			indexesClasses.put(allClasses[i], i);
		}
		
		for(Node n: allNodes)
		{
			indexedPartials.put(n, new Integer[allClasses.length]);
		}
		
		bottomUpCalculation(h.getRoot(), allClasses, indexesClasses, indexedPartials);
		for(int i = 0; i < allClasses.length; i++)
		{
			sumOfClassPurity = Math.max(indexedPartials.get(h.getRoot())[i], sumOfClassPurity);			
		}
		
		return sumOfClassPurity/(double)h.getNumberOfInstances();
	}

	private void bottomUpCalculation(Node currentNode/*node*/, String[] allClasses, HashMap<String, Integer> indexedClasses, HashMap<Node, Integer[]> indexedPartials)
	{
		for(Node child: currentNode.getChildren())
		{
			bottomUpCalculation(child, allClasses, indexedClasses, indexedPartials);
		}
		
		for(String candidate: allClasses)
		{
			int accumulator = 0;//currentNodeAcc
			for(Instance i: currentNode.getNodeInstances())
			{
				if(i.getClass().equals(candidate))
				{
					accumulator++;
				}
			}
			
			for(Node child: currentNode.getChildren())
			{
				int partialAccumulator = Integer.MIN_VALUE;//childNodeAcc
				for(String candidateChild: allClasses)//candidateChildClass
				{
					if(Utils.isTheSameOrSubclass(candidate, candidateChild))
					{
						partialAccumulator = Math.max(partialAccumulator, indexedPartials.get(child)[indexedClasses.get(candidateChild)]);
					}
				}
				accumulator += partialAccumulator;
			}
			indexedPartials.get(currentNode)[indexedClasses.get(candidate)] = accumulator;
		}
	}
}
