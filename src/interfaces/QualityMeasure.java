package interfaces;

import basic_hierarchy.interfaces.Hierarchy;
import internal_measures.statistics.AvgWithStdev;

import java.util.ArrayList;

public interface QualityMeasure {
	double getMeasure(Hierarchy h);
	AvgWithStdev getMeasure(ArrayList<Hierarchy> hierarchies, boolean calculatePopulationStdev);
	double getDesiredValue();
	double getNotDesiredValue();
	public boolean isFirstMeasureBetterThanSecond(double firstMeasure, double secondMeasure);
	boolean shouldMeasureBeMaximised();

    String getName();

}

