package hierarchy_measures.test.common;

import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.internal_measures.VarianceDeviation2;

public class CommonQualityMeasureTest {

	CommonQualityMeasure measure;

	public CommonQualityMeasureTest() {
		measure = new VarianceDeviation2();
	}

}
