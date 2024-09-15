package hierarchy_measures.test.internal_measures.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.TestCommon;
import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.internal_measures.statistics.AvgWithStdev;
import hierarchy_measures.internal_measures.statistics.NumberOfNodes;

public class NumberOfNodesTest {
	private NumberOfNodes measure;

	@Before
	public void setUp() {
		this.measure = new NumberOfNodes();
	}

	@Test
	public void singleHierarchyCalculate() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(2.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(4.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void multipleHierarchyCalculate() {
		ArrayList<Hierarchy> hierarchies = new ArrayList<>();
		hierarchies.add(TestCommon.getTwoGroupsHierarchy());
		hierarchies.add(TestCommon.getFourGroupsHierarchy());

		AvgWithStdev avgResult = this.measure.calculate(hierarchies, true);
		assertEquals(3.0, avgResult.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(1.0, avgResult.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}