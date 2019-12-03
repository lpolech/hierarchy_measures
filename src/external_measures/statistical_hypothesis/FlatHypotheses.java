package external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import interfaces.Hypotheses;

public class FlatHypotheses implements Hypotheses {
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
					
					if(firstTrueClass.equals(secondTrueClass))
					{
						if(firstAssignClass.equals(secondAssignClass))
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
						if(firstAssignClass.equals(secondAssignClass))
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
