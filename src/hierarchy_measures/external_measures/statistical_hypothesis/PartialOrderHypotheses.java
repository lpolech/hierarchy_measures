package hierarchy_measures.external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import hierarchy_measures.common.Utils;
import hierarchy_measures.interfaces.Hypotheses;

public class PartialOrderHypotheses implements Hypotheses {//TODO: mozna z tej klasy i FlatHypotheses zrobic jedna klase przyjmujaca lambde czy tam funkcje, ktora bedzie odpalana w tych ifach
	private long tP = Integer.MIN_VALUE;
	private long fP = Integer.MIN_VALUE;
	private long tN = Integer.MIN_VALUE;
	private long fN = Integer.MIN_VALUE;

	public void calculate(Hierarchy h) 
	{
		tP = 0;
		fP = 0;
		tN = 0;
		fN = 0;
		LinkedList<Instance> allInstances = h.getRoot().getSubtreeInstances();
		Instance[] allInstancesArr = allInstances.toArray(new Instance[allInstances.size()]);
		
		for(int i = 0; i < allInstancesArr.length; i++)
		{
			String firstTrueClass = allInstancesArr[i].getTrueClass();
			String firstAssignClass = allInstancesArr[i].getNodeId();
			for(int j = 0; j < allInstancesArr.length; j++)
			{
				if(i != j)
				{
					String secondTrueClass = allInstancesArr[j].getTrueClass();
					String secondAssignClass = allInstancesArr[j].getNodeId();
					
					if(Utils.isTheSameOrSubclass(firstTrueClass, secondTrueClass))
					{
						if(Utils.isTheSameOrSubclass(firstAssignClass, secondAssignClass))
						{
							tP++;
						}
						else
						{
							fN++;
						}
					}
					else
					{
						if(Utils.isTheSameOrSubclass(firstAssignClass, secondAssignClass))
						{
							fP++;
						}
						else
						{
							tN++;
						}
					}
				}
			}
		}
	}

	public long getTP() {
		return tP;
	}

	public long getFP() {
		return fP;
	}

	public long getTN() {
		return tN;
	}

	public long getFN() {
		return fN;
	}
}
