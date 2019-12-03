package test.internal_measures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatDunn3;

public class FlatDunn3Test {
	private FlatDunn3 measure;

	@Before
	public void setUp() {
		this.measure = new FlatDunn3(new Euclidean());
	}

	@Test
	public void getMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.25, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.25, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(Double.MAX_VALUE, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(0.0, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}