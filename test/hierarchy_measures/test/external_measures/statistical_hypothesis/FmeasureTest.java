package hierarchy_measures.test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import hierarchy_measures.external_measures.statistical_hypothesis.FlatHypotheses;
import hierarchy_measures.external_measures.statistical_hypothesis.Fmeasure;
import hierarchy_measures.external_measures.statistical_hypothesis.PartialOrderHypotheses;

public class FmeasureTest {
	private Fmeasure flatFmeasure;
	private Fmeasure pOFmeasure;

	@Before
	public void setUp() {
		flatFmeasure = new Fmeasure(1.0f, new FlatHypotheses());
		pOFmeasure = new Fmeasure(1.0f, new PartialOrderHypotheses());
	}

	@Test
	public void calculateFlatFmeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.4, flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void calculateFlatFmeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.4, this.flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void calculatePOFmeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.823529412, pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void calculatePOFmeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.823529412, this.pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
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