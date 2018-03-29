package test.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import internal_measures.statistics.AvgWithStdev;
import basic_hierarchy.implementation.BasicInstance;
import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Instance;



public class UtilsTest {

	@Test
	public void testGetClassInstancesWithinNode() {
		BasicNode node = new BasicNode ("0", null, null );
		assertEquals(new ArrayList<>(), Utils.getClassInstancesWithinNode(node, "test",false, false));
		assertEquals(new ArrayList<>(), Utils.getClassInstancesWithinNode(node, "test",true, false));
		assertEquals(new ArrayList<>(), Utils.getClassInstancesWithinNode(node, "test",true, true));
		
		
		BasicNode node2 = new BasicNode ("0", null, new BasicInstance("instanceName", "0", new double [] {1,2}));
		LinkedList<Node> list= new LinkedList<>();
		list.add(node2);
		
		LinkedList<Instance> linklist= new LinkedList<>();
		linklist.add(new BasicInstance("instanceName", "0", new double [] {1,2}));
		BasicNode node3 = new BasicNode ("0", null,list,linklist ,new BasicInstance("instanceName", "0", new double [] {1,2}));
		
		assertEquals(new ArrayList<>(), Utils.getClassInstancesWithinNode(node3, "test",true, true));
	}

	@Test
	public void testIsTheSameOrSubclass() {
		assertEquals(false, Utils.isTheSameOrSubclass("CommonQualityMeasure", "QualityMeasure"));
		assertEquals(false, Utils.isTheSameOrSubclass("QualityMeasure", "CommonQualityMeasure"));
		assertEquals(true, Utils.isTheSameOrSubclass("test", "test.nic"));
		assertEquals(true, Utils.isTheSameOrSubclass("test", "test"));
	}

	@Test
	public void testIsSubclass() {
		assertEquals(false, Utils.isSubclass("CommonQualityMeasure", "QualityMeasure"));
		assertEquals(false, Utils.isSubclass("QualityMeasure", "CommonQualityMeasure"));
		assertEquals(true, Utils.isSubclass("test", "test.nic"));
	}

	@Test (expected= AssertionError.class)
	public void testNodeSubtreeVarianceLinkedListOfInstanceBooleanExpectError() {
		LinkedList<Instance> list = new LinkedList<>();
		assertEquals(1.0, Utils.nodeSubtreeVariance(list, true));
	}
	

	@Test 
	public void testNodeSubtreeVarianceNodeBoolean() {
		BasicNode node2 = new BasicNode ("0", null, new BasicInstance("instanceName", "0", new double [] {1,2}));
		LinkedList<Node> list= new LinkedList<>();
		list.add(node2);
		
		LinkedList<Instance> linklist= new LinkedList<>();
		linklist.add(new BasicInstance("instanceName", "0", new double [] {1,2}));
		BasicNode node = new BasicNode ("0", null,list,linklist ,new BasicInstance("instanceName", "0", new double [] {1,2}));
		
		assertEquals(2,  Utils.nodeSubtreeVariance(node, true).length, 0.1);
		assertEquals(2,  Utils.nodeSubtreeVariance(node, false).length, 0.1);
		
	}
	
	@Test
	public void testNodeSubtreeVarianceLinkedListOfInstanceBoolean() {

		LinkedList<Instance> linklist= new LinkedList<>();
		linklist.add(new BasicInstance("instanceName", "0", new double [] {1,2}));
		
		assertEquals(2,  Utils.nodeSubtreeVariance(linklist, true).length, 0.1);
	}

	@Test
	public void testMeanDoubleArray() {
		assertEquals(2, Utils.mean( new double [] {1,2,3}), 0.1);
	}

	@Test
	public void testMeanIntArray() {
		assertEquals(2, Utils.mean( new int [] {1,2,3}), 0.1);
	}

	@Test
	public void testVarianceDoubleArrayBoolean() {
		assertEquals(1.0, Utils.variance(new double[] {1,2,3}, false), 0.1);
		assertEquals(0.6, Utils.variance(new double[] {1,2,3}, true), 0.1);
	}

	@Test
	public void testVarianceDoubleArrayDoubleBoolean() {
		assertEquals (1.0, Utils.variance( new double[] {1,2,3}, 2, false), 0.1);
		assertEquals (0.6, Utils.variance( new double[] {1,2,3}, 2, true), 0.1);
	}

	@Test
	public void testVarianceIntArrayDoubleBoolean() {
		assertEquals(1.0, Utils.variance(new int [] {1,2,3}, 2.0, false), 0.1);
		assertEquals(0.6, Utils.variance(new int [] {1,2,3}, 2.0, true), 0.1);
	}

	@Test
	public void testStdevDoubleArrayBoolean() {
		assertEquals(1.0, Utils.stdev( new double[] {1,2,3}, false),0.1);
		assertEquals(0.8, Utils.stdev( new double[] {1,2,3}, true),0.1);
	}

	@Test
	public void testStdevDoubleArrayDoubleBoolean() {
		assertEquals(1.0, Utils.stdev(new double[] {1,2,3}, 2, false ),0.1);
		assertEquals(0.8, Utils.stdev( new double[] {1,2,3},2,  true),0.1);
	}

	@Test
	public void testStdevIntArrayDoubleBoolean() {
		assertEquals(1.0, Utils.stdev(new int[] {1,2,3}, 2, false ),0.1);
		assertEquals(0.8, Utils.stdev( new int[] {1,2,3},2,  true),0.1);
	}

	@Test
	public void testToPrimitiveDoubles() {
		assertEquals(null, Utils.toPrimitiveDoubles(null));
		LinkedList<Integer> list=new LinkedList<>();
		list.add(1);
		list.add(2);
		assertArrayEquals(new double[] {1,2}, Utils.toPrimitiveDoubles(list), 0.1);
	}

	@Test
	public void testPopulationMeanAndStdev() {
		AvgWithStdev avgWithStdev=Utils.populationMeanAndStdev(new double[] {1,2,3}, false);
		assertEquals(2, avgWithStdev.getAvg(), 0.1);
		assertEquals(1, avgWithStdev.getStdev(), 0.1);
	}

}
