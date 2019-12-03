package test.internal_measures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatCalinskiHarabasz;
import internal_measures.FlatDaviesBouldin;
import internal_measures.HierarchicalInternalMeasure;

public class HierarchicalInternalMeasureTest {
	private HierarchicalInternalMeasure measureWithCHI;
	private HierarchicalInternalMeasure measureWithDBI;

	@Before
	public void setUp() {
		this.measureWithCHI = new HierarchicalInternalMeasure(new FlatCalinskiHarabasz(new Euclidean()));
		this.measureWithDBI = new HierarchicalInternalMeasure(new FlatDaviesBouldin(new Euclidean()));
	}

	@Test
	public void getMeasureWithCHI() {
		Hierarchy h = TestCommon.getFourGroupsHierarchy();
		assertEquals(1.9796183460586447, this.measureWithCHI.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getDesiredValueWithCHI() {
		assertEquals(Double.MAX_VALUE, measureWithCHI.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithCHI() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchy();
		assertEquals(0.5624999999999999, this.measureWithCHI.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void testGetMeasureForHierarchyWithEmptyNodesWithCHI() {
		Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
		assertEquals(0.5624999999999999, this.measureWithCHI.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
	}

	@Test
	public void getNotDesiredValueWithCHI() {
		assertEquals(0.0, measureWithCHI.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
	}
}