package hierarchy_measures.test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.TestCommon;
import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.external_measures.statistical_hypothesis.FlatHypotheses;
import hierarchy_measures.external_measures.statistical_hypothesis.JaccardIndex;
import hierarchy_measures.external_measures.statistical_hypothesis.PartialOrderHypotheses;

public class JaccardIndexTest {
	private JaccardIndex flatFmeasure;
	private JaccardIndex pOFmeasure;

	@Before
	public void setUp() {
		flatFmeasure = new JaccardIndex(new FlatHypotheses());
		pOFmeasure = new JaccardIndex(new PartialOrderHypotheses());
	}

	@Test
	public void getFlatMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.25, flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getFlatMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.25, this.flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getPOMeasure() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.7, pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getPOMeasureForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.7, this.pOFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
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