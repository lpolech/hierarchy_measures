package test.external_measures.information_based;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.information_based.FlatMutualInformation;

public class FlatMutualInformationTest {
	private FlatMutualInformation measure;

	@Before
	public void setUp() {
		this.measure = new FlatMutualInformation(2.0);
	}

	@Test
	public void getMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.3112781244591328, measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.3112781244591328, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(Double.MAX_VALUE, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(0, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}