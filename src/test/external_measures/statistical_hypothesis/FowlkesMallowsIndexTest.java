package test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.statistical_hypothesis.FlatHypotheses;
import external_measures.statistical_hypothesis.FowlkesMallowsIndex;
import external_measures.statistical_hypothesis.PartialOrderHypotheses;

public class FowlkesMallowsIndexTest {
	private FowlkesMallowsIndex flatFmeasure;
	private FowlkesMallowsIndex pOFmeasure;

	@Before
	public void setUp() {
		flatFmeasure = new FowlkesMallowsIndex(new FlatHypotheses());
		pOFmeasure = new FowlkesMallowsIndex(new PartialOrderHypotheses());
	}

	@Test
	public void getFlatMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.40824829, flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getFlatMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.40824829, this.flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getPOMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.824957911, pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getPOMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.824957911, this.pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValue() {
		assertEquals(1.0, flatFmeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(1.0, pOFmeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValue() {
		assertEquals(0.0, flatFmeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
		assertEquals(0.0, pOFmeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

}