package test.internal_measures.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.NumberOfLeaves;

public class NumberOfLeavesTest {
	private NumberOfLeaves measure;

	@Before
	public void setUp() {
		this.measure = new NumberOfLeaves();
	}

	@Test
	public void singleHierarchyCalculate() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(1.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(1.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void multipleHierarchyCalculate() {
		ArrayList<Hierarchy> hierarchies = new ArrayList<>();
		hierarchies.add(TestCommon.getTwoGroupsHierarchy());
		hierarchies.add(TestCommon.getFourGroupsHierarchy());

		AvgWithStdev avgResult = this.measure.calculate(hierarchies, true);
		assertEquals(1.5, avgResult.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.5, avgResult.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

}