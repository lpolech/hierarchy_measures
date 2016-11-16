package external_measures;

import common.CommonQualityMeasure;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.HashMap;
import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;

public class AdaptedFmeasure extends CommonQualityMeasure {

	/*
	 * Consider each instances from child groups as belonging also to parent group
	 */
	private boolean withInstancesInheritance;
	
	public AdaptedFmeasure(boolean instancesInheritance) {
		this.withInstancesInheritance = instancesInheritance;
	}

	@Override
	public double getMeasure(Hierarchy h) {
		int numberOfGroups = h.getNumberOfGroups();
		int numberOfClasses = h.getNumberOfClasses();
		HashMap<String, Integer> eachGroupIndex = new HashMap<String, Integer>(numberOfGroups, 1.0f);//we have the max size of map
		HashMap<String, Integer> eachClassIndex = new HashMap<String, Integer>(numberOfClasses, 1.0f);//we have the max size of map
		int[] eachGroupNumberOfInstances = new int[numberOfGroups];
		int[][] eachClassInGroupNumberOfInstances = new int[numberOfClasses][numberOfGroups];
		String[] classes = h.getClasses();
		Node[] groups = h.getGroups();
		//indexise classes
		for(int i = 0; i < numberOfClasses; i++)
		{
			eachClassIndex.put(classes[i], i);
		}
		
		//precision and recall nad F-measure
		double[][] classToGroupPrecision = new double[numberOfClasses][numberOfGroups];
		double[][] classToGroupRecall = new double[numberOfClasses][numberOfGroups];
		double[][] classToGroupFMeasure = new double[numberOfClasses][numberOfGroups];

		//traverse each group
		//TODO REFACTOR: ponizsza funckje mozna zamknac w funkcje przyjmujaca jakas lambde i 
		//ta lambda moze wykonywac jedna funkcje na kazdym wezle porzejzanego drzewa
		for(int i = 0; i < numberOfGroups; i++)
		{
			Node currentGroup = groups[i];
			String currentId = currentGroup.getId();
			//indexise groups
			eachGroupIndex.put(currentId, i);
			//calculate needed values
			LinkedList<Instance> instances = currentGroup.getSubtreeInstances();
			int numOfInstances = instances.size();
			eachGroupNumberOfInstances[i] = numOfInstances;
			
			for(Instance instance: instances)
			{
				String instanceClass = instance.getTrueClass();
				eachClassInGroupNumberOfInstances[eachClassIndex.get(instanceClass)][i]++;
				if(withInstancesInheritance)
				{
					//threat instances as belonging also to parent classes
					for(int j = 0; j < numberOfClasses; j++)
					{
						String potentialParentClass = classes[j];						
						if(potentialParentClass.length() < instanceClass.length())
						{
							if(instanceClass.startsWith(
									potentialParentClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR))
							{
								eachClassInGroupNumberOfInstances[eachClassIndex.get(potentialParentClass)][i]++;
							}
						}
					}
				}
			}
			
			//calculate Fmeasure related to each class
			for(int j = 0; j < numberOfClasses; j++)
			{
				if(eachGroupNumberOfInstances[i] == 0)//empty node added to fill gaps
				{
					classToGroupPrecision[j][i] = 0;
				}
				else
				{
					classToGroupPrecision[j][i] = 
							eachClassInGroupNumberOfInstances[j][i]/(double)eachGroupNumberOfInstances[i];
				}
				
				classToGroupRecall[j][i] = 
						eachClassInGroupNumberOfInstances[j][i]/(double)h.getClassCount(classes[j], withInstancesInheritance);
				double precision = classToGroupPrecision[j][i];
				double recall = classToGroupRecall[j][i];
				
				double fMeasureDenominator = precision + recall; 
				if(fMeasureDenominator != 0)
				{
					classToGroupFMeasure[j][i] = (2.0*precision*recall)/(precision + recall);
					if(classToGroupFMeasure[j][i] > 1)
					{
						System.out.println("Fmeasure is bigger than 1!");
					}
				}
				else
				{
					classToGroupFMeasure[j][i] = 0;
				}
			}			
		}
		
		double[] eachClassMaxFMeasure = new double[numberOfClasses];
		for(int i = 0; i < numberOfClasses; i++)
		{
			eachClassMaxFMeasure[i] = (-1)*Double.MAX_VALUE;
			for(int j = 0; j < numberOfGroups; j++)
			{
				if(eachClassMaxFMeasure[i] < classToGroupFMeasure[i][j])// HERE I CAN STORE INFORMATION ABOUT THE GROUP NAME WHICH
				{ 														// MAXIMIZES FMEASURE FO CLASS
					eachClassMaxFMeasure[i] = classToGroupFMeasure[i][j];
				}
			}
		}
		
		//calculate F-measure for whole hierarchy
		double FMeasure = 0.0;
		
		for(int i = 0; i < numberOfClasses; i++)
		{
			FMeasure += h.getClassCount(classes[i], withInstancesInheritance)*eachClassMaxFMeasure[i];
		}
		
		int normalisingFactor = 0;
		for(int i = 0; i < classes.length; i++)
		{
			normalisingFactor += h.getClassCount(classes[i], withInstancesInheritance);
		}
		
		FMeasure /= normalisingFactor;
		return FMeasure;
	}

	@Override
	public double getDesiredValue() {
		return 1.0;
	}

	@Override
	public double getNotDesiredValue() {
		return 0.0;
	}

}
