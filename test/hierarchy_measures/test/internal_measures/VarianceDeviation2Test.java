package hierarchy_measures.test.internal_measures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.TestCommon;
import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.internal_measures.VarianceDeviation2;

public class VarianceDeviation2Test {
	private VarianceDeviation2 measure;

	@Before
	public void setUp() {
		measure = new VarianceDeviation2();
	}

	@Test
	public void getMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(1.0, this.measure.getMeasure(h), 0.1);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(Double.NaN, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(Double.MAX_VALUE, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}