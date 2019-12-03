package test.internal_measures.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgPathLength;
import internal_measures.statistics.AvgWithStdev;

public class AvgPathLengthTest {
	private AvgPathLength measure;

	@Before
	public void setUp() {
		this.measure = new AvgPathLength();
	}

	@Test
	public void singleHierarchyCalculate() {
		Hierarchy h = TestCommon.getFourGroupsHierarchy();
		AvgWithStdev result = this.measure.calculate(h);
		assertEquals(1.5, result.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.5, result.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		AvgWithStdev result = this.measure.calculate(h);
		assertEquals(3.0, result.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.0, result.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void multipleHierarchyCalculate() {
		ArrayList<Hierarchy> hierarchies = new ArrayList<>();
		hierarchies.add(TestCommon.getTwoGroupsHierarchy());
		hierarchies.add(TestCommon.getFourGroupsHierarchy());

		AvgWithStdev avgResult = this.measure.calculate(hierarchies, true);
		assertEquals(1.25, avgResult.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.25, avgResult.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}