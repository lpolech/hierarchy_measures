package hierarchy_measures.test.external_measures.statistical_hypothesis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import basic_hierarchy.TestCommon;
import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.external_measures.statistical_hypothesis.FlatHypotheses;

public class FlatHypothesesTest {
	@Test
	public void calculate() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		FlatHypotheses hypotheses = new FlatHypotheses();
		hypotheses.calculate(h);
		assertEquals(2, hypotheses.getTP());
		assertEquals(2, hypotheses.getFP());
		assertEquals(4, hypotheses.getTN());
		assertEquals(4, hypotheses.getFN());
	}

	@Test
	public void calculateForHierarchyWithEmptyNodes() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		FlatHypotheses hypotheses = new FlatHypotheses();
		hypotheses.calculate(h);
		assertEquals(2, hypotheses.getTP());
		assertEquals(2, hypotheses.getFP());
		assertEquals(4, hypotheses.getTN());
		assertEquals(4, hypotheses.getFN());
	}
}