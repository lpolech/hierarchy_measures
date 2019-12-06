package hierarchy_measures.test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import hierarchy_measures.external_measures.statistical_hypothesis.PartialOrderHypotheses;

public class PartialOrderHypothesesTest {
	@Test
	public void calculate() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		PartialOrderHypotheses hypotheses = new PartialOrderHypotheses();
		hypotheses.calculate(h);
		assertEquals(7, hypotheses.getTP());
		assertEquals(2, hypotheses.getFN());
		assertEquals(1, hypotheses.getFP());
		assertEquals(2, hypotheses.getTN());
	}

	@Test
	public void calculateForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		PartialOrderHypotheses hypotheses = new PartialOrderHypotheses();
		hypotheses.calculate(h);
		assertEquals(7, hypotheses.getTP());
		assertEquals(2, hypotheses.getFN());
		assertEquals(1, hypotheses.getFP());
		assertEquals(2, hypotheses.getTN());
	}

}