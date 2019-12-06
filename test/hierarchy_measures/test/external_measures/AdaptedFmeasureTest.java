package hierarchy_measures.test.external_measures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import hierarchy_measures.external_measures.AdaptedFmeasure;

public class AdaptedFmeasureTest {
	private AdaptedFmeasure withOUTInheritanceMeasure;
	private AdaptedFmeasure withInheritanceMeasure;

	@Before
	public void setUp() {
		withOUTInheritanceMeasure = new AdaptedFmeasure(false);
		withInheritanceMeasure = new AdaptedFmeasure(true);
	}

	@Test
	public void getMeasureWithInheritance() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.9333333333333333, withInheritanceMeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getMeasureWithOUTInheritance() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.8095238095238094, withOUTInheritanceMeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodesWithInheritance() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.9333333333333333, this.withInheritanceMeasure.getMeasure(h),
				TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodesWithOUTInheritance() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.8095238095238094, this.withOUTInheritanceMeasure.getMeasure(h),
				TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(1.0, withOUTInheritanceMeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(1.0, withInheritanceMeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(0.0, withOUTInheritanceMeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.0, withInheritanceMeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

}