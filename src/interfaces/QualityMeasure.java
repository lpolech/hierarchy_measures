package interfaces;

import basic_hierarchy.interfaces.Hierarchy;

public interface QualityMeasure {
	double getMeasure(Hierarchy h, DistanceMeasure dist);
}

