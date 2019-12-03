package test.internal_measures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatReversedDunn4;

public class FlatReversedDunn4Test {
	private FlatReversedDunn4 measure;

	@Before
	public void setUp() {
		this.measure = new FlatReversedDunn4(new Euclidean());
	}

	@Test
	public void getMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(2, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(2, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(0.0, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(Double.MAX_VALUE, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

}