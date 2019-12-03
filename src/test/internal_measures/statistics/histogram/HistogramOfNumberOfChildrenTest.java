package test.internal_measures.statistics.histogram;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.histogram.HistogramOfNumberOfChildren;

public class HistogramOfNumberOfChildrenTest {
	private HistogramOfNumberOfChildren measure;

	@Before
	public void setUp() {
		this.measure = new HistogramOfNumberOfChildren();
	}

	@Test
	public void singleHierarchyCalculate() {
		Hierarchy h = TestCommon.getFourGroupsHierarchy();
		assertArrayEquals(new double[] { 2, 1, 1 }, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertArrayEquals(new double[] { 1.0, 3.0 }, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void multipleHierarchyCalculate() {
		ArrayList<Hierarchy> hierarchies = new ArrayList<>();
		hierarchies.add(TestCommon.getTwoGroupsHierarchy());
		hierarchies.add(TestCommon.getFourGroupsHierarchy());

		AvgWithStdev[] perLevelAvgResult = this.measure.calculate(hierarchies);
		assertEquals(3, perLevelAvgResult.length);
		assertEquals(1.5, perLevelAvgResult[0].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.5, perLevelAvgResult[0].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(1.0, perLevelAvgResult[1].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.0, perLevelAvgResult[1].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.5, perLevelAvgResult[2].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.5, perLevelAvgResult[2].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}