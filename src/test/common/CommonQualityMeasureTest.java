package test.common;

import static org.junit.Assert.*;

import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import common.CommonQualityMeasure;
import internal_measures.VarianceDeviation2;

public class CommonQualityMeasureTest {

	CommonQualityMeasure measure;
	
	public CommonQualityMeasureTest()
	{
		measure=(CommonQualityMeasure) new VarianceDeviation2();
	}
	
	//nie da sie tego przetestowaæ 
	@Test
	public void testGetMeasureHierarchy() {
		
	}

	@Test
	public void testGetMeasureArrayListOfHierarchyBoolean() {
		
	}

	@Test
	public void testGetDesiredValue() {
		
	}

	@Test
	public void testGetNotDesiredValue() {
		
	}

}
