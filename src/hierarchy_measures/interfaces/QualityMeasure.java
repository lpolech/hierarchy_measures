package hierarchy_measures.interfaces;

import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.internal_measures.statistics.AvgWithStdev;

import java.util.ArrayList;

public interface QualityMeasure {
	double getMeasure(Hierarchy h);
	AvgWithStdev getMeasure(ArrayList<Hierarchy> hierarchies, boolean calculatePopulationStdev);
	double getDesiredValue();
	double getNotDesiredValue();
}

