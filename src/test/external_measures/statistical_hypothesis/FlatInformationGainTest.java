package test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.information_based.FlatEntropy1;
import external_measures.information_based.FlatInformationGain;

public class FlatInformationGainTest {
	private FlatInformationGain measure;

	@Before
	public void setUp() {
		this.measure = new FlatInformationGain(2.0, new FlatEntropy1(2.0));
	}

	@Test
	public void getMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.8112781244591328 - 0.5, measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.8112781244591328 - 0.5, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
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