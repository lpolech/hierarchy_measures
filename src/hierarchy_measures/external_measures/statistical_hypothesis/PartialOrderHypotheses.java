package hierarchy_measures.external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import hierarchy_measures.common.Utils;
import hierarchy_measures.interfaces.Hypotheses;

public class PartialOrderHypotheses implements Hypotheses {//TODO: mozna z tej klasy i FlatHypotheses zrobic jedna klase przyjmujaca lambde czy tam funkcje, ktora bedzie odpalana w tych ifach
	private long tp = Integer.MIN_VALUE;
	private long fp = Integer.MIN_VALUE;
	private long tn = Integer.MIN_VALUE;
	private long fn = Integer.MIN_VALUE;

	public void calculate(Hierarchy h) 
	{
		tp = 0;
		fp = 0;
		tn = 0;
		fn = 0;
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
							tp++;
						}
						else
						{
							fn++;
						}
					}
					else
					{
						if(Utils.isTheSameOrSubclass(firstAssignClass, secondAssignClass))
						{
							fp++;
						}
						else
						{
							tn++;
						}
					}
				}
			}
		}
	}

	public long getTP() {
		return tp;
	}

	public long getFP() {
		return fp;
	}

	public long getTN() {
		return tn;
	}

	public long getFN() {
		return fn;
	}
}
