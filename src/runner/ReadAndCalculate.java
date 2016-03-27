package runner;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;

public class ReadAndCalculate {

	public static void main(String[] args) {
		GeneratedCSVReader reader = new GeneratedCSVReader();
		Hierarchy H = reader.load("balancedTree5000.csv", true);
		H.getRoot().printSubtree();
		
//		int setNum = 7;
//		int numberOfIterations = 30;
//		System.out.println("file;standard;partialOrder;adapted;standard TP;partial TP;standard TN;partial TN;standard FP;partial FP;standard FN;partial FN");
//		String setNumber = (String) (setNum < 10? "0" + setNum : String.valueOf(setNum));
//		String directoryPath = "all-in-root/set" + setNumber + "-egt";
//		//set01_a-1_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		//set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_01.gt
//		//set02_a-1_l-1_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		//set03_a-5_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set04_a-5_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set05_a-5_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set06_a-25_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//												 		 //set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		String groundTruthFileNameToLoad = "set" + setNumber + "_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_";
//			
//		for(int j = 0; j < numberOfIterations; j++)
//		{
//			GeneratedCSVReader reader = new GeneratedCSVReader();
//			
//			AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true);
//			StandardFmeasure standardFmeasure = new StandardFmeasure();
//			PartialOrderFmeasure partialOrderFscore = new PartialOrderFmeasure();
//			
//			String numOfIter = (String) (j < 10? "0" + j : String.valueOf(j));
//			
//			String fullPath = directoryPath + "/" + groundTruthFileNameToLoad + numOfIter + ".gt.csv";
//				
//			Hierarchy H = reader.load(fullPath, true);
//			//((BasicNode) H.getRoot()).printSubtree();
//				
//			System.out.println(fullPath + ";" + standardFmeasure.getMeasure(H) + ";" + partialOrderFscore.getMeasure(H) + ";" + adaptedFmeasure.getMeasure(H)
//				+ ";" + standardFmeasure.TP + ";" + partialOrderFscore.TP + ";" + standardFmeasure.TN + ";" + partialOrderFscore.TN
//				+ ";" + standardFmeasure.FP + ";" + partialOrderFscore.FP + ";" + standardFmeasure.FN + ";" + partialOrderFscore.FN);
//		}
		
		
		
//=======================================================================================================================================		
//		String directoryPath = "set00/set00-e";
//		String groundTruthFileNameToLoad = "set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_";
//		String fileNameToLoad =            "set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e";
		
//		boolean imitateFlatClustering = true;
//		String directoryPath = "increasing-random-changes/set07/set07-e";
//		//set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_01.gt
//		//set01_a-1_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		//set02_a-1_l-1_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		//set03_a-5_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set04_a-5_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set05_a-5_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//		//set06_a-25_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
//										  //set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
//		String groundTruthFileNameToLoad = "set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_";
//		String fileNameToLoad =            "set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e";
//		int numberOfE = 10;
//		int numberOfIterations = 30;
//		
//		
////		GeneratedCSVReader reader = new GeneratedCSVReader(imitateFlatClustering);
////		AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true, imitateFlatClustering);
////		Hierarchy H = reader.load("sample1withClasses.csv", true);
////		((BasicNode) H.getRoot()).printSubtree();
////		System.out.println(adaptedFmeasure.getMeasure(H));
//		
//		
//		System.out.println("file;standard;partialOrder;adapted;standard TP;partial TP;standard TN;partial TN;standard FP;partial FP;standard FN;partial FN");
//		for(int j = 0; j < numberOfIterations; j++)
//		{
//			GeneratedCSVReader reader = new GeneratedCSVReader(imitateFlatClustering);
//			
//			AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true, imitateFlatClustering);
//			StandardFmeasure standardFmeasure = new StandardFmeasure();
//			PartialOrderFmeasure partialOrderFscore = new PartialOrderFmeasure();
//			
//			String strNumOfE = "gt";
//			String numOfIter = (String) (j < 10? "0" + j : String.valueOf(j));
//			
//			String fullPath = directoryPath + strNumOfE + "/" + groundTruthFileNameToLoad + numOfIter + ".gt.csv";
//			
//			Hierarchy H = reader.load(fullPath, true);
//			//((BasicNode) H.getRoot()).printSubtree();
//			
//			System.out.println(fullPath + ";" + standardFmeasure.getMeasure(H) + ";" + partialOrderFscore.getMeasure(H) + ";" + adaptedFmeasure.getMeasure(H)
//				+ ";" + standardFmeasure.TP + ";" + partialOrderFscore.TP + ";" + standardFmeasure.TN + ";" + partialOrderFscore.TN
//				+ ";" + standardFmeasure.FP + ";" + partialOrderFscore.FP + ";" + standardFmeasure.FN + ";" + partialOrderFscore.FN);
//		}
//		
//		for(int i = 0; i < numberOfE; i++)
//		{
//			System.out.println("\n\n\n\n");
//			System.out.println("file;standard;partialOrder;adapted;standard TP;partial TP;standard TN;partial TN;standard FP;partial FP;standard FN;partial FN");
//			for(int j = 0; j < numberOfIterations; j++)
//			{
//				GeneratedCSVReader reader = new GeneratedCSVReader(imitateFlatClustering);
//				
//				AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true, imitateFlatClustering);
//				StandardFmeasure standardFmeasure = new StandardFmeasure();
//				PartialOrderFmeasure partialOrderFscore = new PartialOrderFmeasure();
//				
//				String strNumOfE = (String) (i < 10? "0" + i : String.valueOf(i));
//				String numOfIter = (String) (j < 10? "0" + j : String.valueOf(j));
//				
//				String fullPath = directoryPath + strNumOfE + "/" + fileNameToLoad + i + "_" + numOfIter + ".csv";
//				
//				Hierarchy H = reader.load(fullPath, true);
//				//((BasicNode) H.getRoot()).printSubtree();
//				
//				System.out.println(fullPath + ";" + standardFmeasure.getMeasure(H) + ";" + partialOrderFscore.getMeasure(H) + ";" + adaptedFmeasure.getMeasure(H)
//				+ ";" + standardFmeasure.TP + ";" + partialOrderFscore.TP + ";" + standardFmeasure.TN + ";" + partialOrderFscore.TN
//				+ ";" + standardFmeasure.FP + ";" + partialOrderFscore.FP + ";" + standardFmeasure.FN + ";" + partialOrderFscore.FN);
//			}
//		}
//		
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H));
//		System.out.println("H0");
//		Hierarchy H0 = reader.load("denseShallowTree.r.csv", true);
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H0));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H0));
//		System.out.println("H1");
//		Hierarchy H1 = reader.load("lowerPartsDenseShallowTree.r.csv", true);
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H1));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H1));
//		System.out.println("H2");
//		Hierarchy H2 = reader.load("lowerPartsDenseShallowTrees.r.csv", true);
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H2));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H2));
//		System.out.println("H3");
//		Hierarchy H3 = reader.load("sparseDeepTree.r.csv", true);
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H3));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H3));
//		System.out.println("H4");
//		Hierarchy H4 = reader.load("upperPartsDenseDeepTree.r.csv", true);
//		System.out.println("adapted F-measure: " + adaptedFmeasure.getMeasure(H4));
//		System.out.println("partial order F-measure: " + partialOrderFscore.getMeasure(H4));
	}

}
