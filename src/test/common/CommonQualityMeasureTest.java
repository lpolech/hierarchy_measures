package test.common;

import common.CommonQualityMeasure;
import internal_measures.VarianceDeviation2;

public class CommonQualityMeasureTest {

	CommonQualityMeasure measure;
	
	public CommonQualityMeasureTest()
	{
		measure=(CommonQualityMeasure) new VarianceDeviation2();
	}
	
}
