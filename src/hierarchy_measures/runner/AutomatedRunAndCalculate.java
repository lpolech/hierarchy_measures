package hierarchy_measures.runner;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import hierarchy_measures.external_measures.AdaptedFmeasure;
import hierarchy_measures.external_measures.statistical_hypothesis.FlatHypotheses;
import hierarchy_measures.external_measures.statistical_hypothesis.Fmeasure;
import hierarchy_measures.external_measures.statistical_hypothesis.PartialOrderHypotheses;

public class AutomatedRunAndCalculate {
	private static final String MSG_PARTIAL_ORDER_F_MEASURE = "partial order F-measure: {}";
	private static final String MSG_ADAPTED_F_MEASURE = "adapted F-measure: {}";
	private static final String MEASURE_HEADER = "file;standard;partialOrder;adapted;standard TP;partial TP;standard TN;partial TN;standard FP;partial FP;standard FN;partial FN";
	private static final Logger log = LogManager.getLogger(AutomatedRunAndCalculate.class);

	public static void main(String[] args) {

		try {
			if (args != null && args.length > 0 && "1".equals(args[0]))
				analizeFirst();
			else
				analizeSecond();
		} catch (IOException e) {
			log.error(e);
		}
	}

	private static void analizeFirst() throws IOException {
		int setNum = 7;
		int numberOfIterations = 30;
		log.trace(MEASURE_HEADER);
		String setNumber = setNum < 10 ? "0" + setNum : String.valueOf(setNum);
		String directoryPath = "all-in-root/set" + setNumber + "-egt";
		// set01_a-1_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		// set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_01.gt
		// set02_a-1_l-1_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		// set03_a-5_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set04_a-5_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set05_a-5_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set06_a-25_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		String groundTruthFileNameToLoad = "set" + setNumber + "_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_";

		for (int j = 0; j < numberOfIterations; j++) {
			GeneratedCSVReader reader = new GeneratedCSVReader();

			AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true);
			Fmeasure standardFmeasure = new Fmeasure(0.5f, new FlatHypotheses());
			Fmeasure partialOrderFscore = new Fmeasure(0.5f, new PartialOrderHypotheses());

			String numOfIter = j < 10 ? "0" + j : String.valueOf(j);

			File file = new File(directoryPath, groundTruthFileNameToLoad + numOfIter + ".gt.csv");
			String filePath = file.getPath();

			Hierarchy hierarchy = reader.load(filePath, false, false, false, false, true);
			((BasicNode) hierarchy.getRoot()).printSubtree();
			if (log.isTraceEnabled())
				log.trace(createParametrMessage(adaptedFmeasure, standardFmeasure, partialOrderFscore, hierarchy,
						filePath));

		}
	}

	private static void analizeSecond() throws IOException {
		// set00/set00-e
		String directoryPath = "increasing-random-changes/set07/set07-e";

		// set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_01.gt
		// set01_a-1_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		// set02_a-1_l-1_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		// set03_a-5_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set04_a-5_l-1_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set05_a-5_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set06_a-25_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_00.h
		// set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_00
		// "set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e"
		// "set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_"
		String groundTruthFileNameToLoad = "set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10_";
		String fileNameToLoad = "set07_a-25_l-05_g-1_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e";
		int numberOfE = 10;
		int numberOfIterations = 30;

		GeneratedCSVReader reader = new GeneratedCSVReader();
		AdaptedFmeasure adaptedFmeasure = new AdaptedFmeasure(true);
		Fmeasure standardFmeasure = new Fmeasure(0.5f, new FlatHypotheses());
		Fmeasure partialOrderFscore = new Fmeasure(0.5f, new PartialOrderHypotheses());
		Hierarchy hierarchy = reader.load("sample1withClasses.csv", false, false, false, false, true);
		((BasicNode) hierarchy.getRoot()).printSubtree();
		log.trace(adaptedFmeasure.getMeasure(hierarchy));

		log.trace(MEASURE_HEADER);
		for (int j = 0; j < numberOfIterations; j++) {
			String strNumOfE = "gt";
			String numOfIter = (j < 10 ? "0" + j : String.valueOf(j));

			File file = new File(directoryPath + strNumOfE, groundTruthFileNameToLoad + numOfIter + ".gt.csv");
			String fullPath = file.getPath();

			hierarchy = loadHierarchyAndLogParams(reader, adaptedFmeasure, standardFmeasure, partialOrderFscore,
					fullPath);
		}

		for (int i = 0; i < numberOfE; i++) {
			log.trace("%n%n%n%n");
			log.trace(MEASURE_HEADER);
			for (int j = 0; j < numberOfIterations; j++) {
				String strNumOfE = i < 10 ? "0" + i : String.valueOf(i);
				String numOfIter = j < 10 ? "0" + j : String.valueOf(j);

				File file = new File(directoryPath + strNumOfE, fileNameToLoad + i + "_" + numOfIter + ".csv");
				String fullPath = file.getPath();

				hierarchy = loadHierarchyAndLogParams(reader, adaptedFmeasure, standardFmeasure, partialOrderFscore,
						fullPath);
			}
		}

		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(hierarchy));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(hierarchy));
		log.trace("H0");
		Hierarchy h0 = reader.load("denseShallowTree.r.csv", false, false, false, false, true);
		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(h0));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(h0));
		log.trace("H1");
		Hierarchy h1 = reader.load("lowerPartsDenseShallowTree.r.csv", false, false, false, false, true);
		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(h1));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(h1));
		log.trace("H2");
		Hierarchy h2 = reader.load("lowerPartsDenseShallowTrees.r.csv", false, false, false, false, true);
		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(h2));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(h2));
		log.trace("H3");
		Hierarchy h3 = reader.load("sparseDeepTree.r.csv", false, false, false, false, true);
		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(h3));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(h3));
		log.trace("H4");
		Hierarchy h4 = reader.load("upperPartsDenseDeepTree.r.csv", false, false, false, false, true);
		log.trace(MSG_ADAPTED_F_MEASURE, adaptedFmeasure.getMeasure(h4));
		log.trace(MSG_PARTIAL_ORDER_F_MEASURE, partialOrderFscore.getMeasure(h4));
	}

	private static Hierarchy loadHierarchyAndLogParams(GeneratedCSVReader reader, AdaptedFmeasure adaptedFmeasure,
			Fmeasure standardFmeasure, Fmeasure partialOrderFscore, String fullPath) throws IOException {
		Hierarchy hierarchy;
		hierarchy = reader.load(fullPath, false, false, false, false, true);
		((BasicNode) hierarchy.getRoot()).printSubtree();

		if (log.isTraceEnabled())
			log.trace(
					createParametrMessage(adaptedFmeasure, standardFmeasure, partialOrderFscore, hierarchy, fullPath));
		return hierarchy;
	}

	private static String createParametrMessage(AdaptedFmeasure adaptedFmeasure, Fmeasure standardFmeasure,
			Fmeasure partialOrderFscore, Hierarchy hierarchy, String fullPath) {
		return fullPath + ";" + standardFmeasure.getMeasure(hierarchy) + ";" + partialOrderFscore.getMeasure(hierarchy)
				+ ";" + adaptedFmeasure.getMeasure(hierarchy) + ";" + standardFmeasure.getHypotheses().getTP() + ";"
				+ partialOrderFscore.getHypotheses().getTP() + ";" + standardFmeasure.getHypotheses().getTN() + ";"
				+ partialOrderFscore.getHypotheses().getTN() + ";" + standardFmeasure.getHypotheses().getFP() + ";"
				+ partialOrderFscore.getHypotheses().getFP() + ";" + standardFmeasure.getHypotheses().getFN() + ";"
				+ partialOrderFscore.getHypotheses().getFN();
	}
}
