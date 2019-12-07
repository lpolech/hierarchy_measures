package hierarchy_measures.runner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import hierarchy_measures.distance_measures.Euclidean;
import hierarchy_measures.external_measures.AdaptedFmeasure;
import hierarchy_measures.external_measures.information_based.FlatEntropy1;
import hierarchy_measures.external_measures.information_based.FlatEntropy2;
import hierarchy_measures.external_measures.information_based.FlatInformationGain;
import hierarchy_measures.external_measures.information_based.FlatMutualInformation;
import hierarchy_measures.external_measures.information_based.FlatNormalizedMutualInformation;
import hierarchy_measures.external_measures.purity.FlatClusterPurity;
import hierarchy_measures.external_measures.purity.HierarchicalClassPurity;
import hierarchy_measures.external_measures.statistical_hypothesis.FlatHypotheses;
import hierarchy_measures.external_measures.statistical_hypothesis.Fmeasure;
import hierarchy_measures.external_measures.statistical_hypothesis.FowlkesMallowsIndex;
import hierarchy_measures.external_measures.statistical_hypothesis.JaccardIndex;
import hierarchy_measures.external_measures.statistical_hypothesis.PartialOrderHypotheses;
import hierarchy_measures.external_measures.statistical_hypothesis.RandIndex;
import hierarchy_measures.interfaces.DistanceMeasure;
import hierarchy_measures.interfaces.QualityMeasure;
import hierarchy_measures.internal_measures.FlatCalinskiHarabasz;
import hierarchy_measures.internal_measures.FlatDaviesBouldin;
import hierarchy_measures.internal_measures.FlatDunn1;
import hierarchy_measures.internal_measures.FlatDunn2;
import hierarchy_measures.internal_measures.FlatDunn3;
import hierarchy_measures.internal_measures.FlatDunn4;
import hierarchy_measures.internal_measures.FlatReversedDunn2;
import hierarchy_measures.internal_measures.FlatReversedDunn3;
import hierarchy_measures.internal_measures.FlatReversedDunn4;
import hierarchy_measures.internal_measures.FlatWithinBetweenIndex;
import hierarchy_measures.internal_measures.HierarchicalInternalMeasure;
import hierarchy_measures.internal_measures.VarianceDeviation;
import hierarchy_measures.internal_measures.VarianceDeviation2;
import hierarchy_measures.internal_measures.statistics.AvgPathLength;
import hierarchy_measures.internal_measures.statistics.AvgWithStdev;
import hierarchy_measures.internal_measures.statistics.CommonStatistic;
import hierarchy_measures.internal_measures.statistics.Height;
import hierarchy_measures.internal_measures.statistics.NumberOfLeaves;
import hierarchy_measures.internal_measures.statistics.NumberOfNodes;

public class ReadSeveralAndCalculateQualityMeasuresSeparately extends CommonReadSeveralAndCalculate {
	private static final Logger log = LogManager.getLogger(ReadSeveralAndCalculateQualityMeasuresSeparately.class);
	private static final String MSG_DONE = "Done.";
	// parameters
	private boolean useSubtree = true;
	private double informationBasedMeasureslogBase = 2.0;
	private double varianceDeviationAlpha = 1.0;
	private boolean mimicFlatClustering = false;
	private boolean mimicOneCluster = false;
	private DistanceMeasure distanceUsedWithinMeasures;
	// measures
	private static HashMap<String, CommonStatistic> basicStatistics;
	private static AvgPathLength apt;
	private HashMap<String, QualityMeasure> qualityMeasures;

	public ReadSeveralAndCalculateQualityMeasuresSeparately(boolean useSubtree, boolean mimicFlatClustering,
			boolean mimicOneCluster, double informationBasedMeasuresLogBase, double varianceDeviationAlpha,
			DistanceMeasure distanceUsedWithinMeasures) {
		this.useSubtree = useSubtree;
		this.mimicFlatClustering = mimicFlatClustering;
		this.mimicOneCluster = mimicOneCluster;
		this.informationBasedMeasureslogBase = informationBasedMeasuresLogBase;
		this.varianceDeviationAlpha = varianceDeviationAlpha;
		this.distanceUsedWithinMeasures = distanceUsedWithinMeasures;

		createQualityMeasures(varianceDeviationAlpha, distanceUsedWithinMeasures);

		if (mimicFlatClustering && mimicOneCluster) {
			log.error("You can either mimic flat clustering or one cluster but never do both!");
			System.exit(1);
		}
	}

	private void createQualityMeasures(double varianceDeviationAlpha, DistanceMeasure distanceUsedWithinMeasures) {
		ReadSeveralAndCalculateQualityMeasuresSeparately.basicStatistics = createBasicStatics();
		ReadSeveralAndCalculateQualityMeasuresSeparately.apt = new AvgPathLength();
		qualityMeasures = getQualityMeasureHashMap(informationBasedMeasureslogBase, varianceDeviationAlpha,
				distanceUsedWithinMeasures);
	}

	public ReadSeveralAndCalculateQualityMeasuresSeparately() {
		this(true, false, false, 2.0, 1.0, new Euclidean());
	}

	public void run(String[] pathsToHierarchies, String resultFilePath, boolean withClassAttribute,
			boolean withInstanceAttribute, boolean withColumnHeader, boolean fixBreadthGaps) {
		try {
			writeHeaderIfEmptyFile(withClassAttribute, resultFilePath, false, true);
			log.info("Number of loaded files: {}", pathsToHierarchies.length);
			log.info("Calculating..");

			for (int i = 0; i < pathsToHierarchies.length; i++) {
				log.info("========= {}/{} =========", (i + 1), pathsToHierarchies.length);
				String filePath = pathsToHierarchies[i];
				saveBasicInfo(resultFilePath, filePath, useSubtree, mimicFlatClustering, mimicOneCluster);

				GeneratedCSVReader reader = new GeneratedCSVReader();
				Hierarchy h = reader.load(filePath, withInstanceAttribute, withClassAttribute, withColumnHeader,
						fixBreadthGaps, useSubtree);

				log.info("{} loaded", filePath);

				if (mimicOneCluster) {
					log.info("Transforming into one-cluster solution..");
					h = basic_hierarchy.common.HierarchyUtils.getOneClusterHierarchy(h);
					log.info(MSG_DONE);
				}

				if (mimicFlatClustering) {
					log.info("Transforming into flat clustering solution..");
					h = h.getFlatClusteringWithCommonEmptyRoot();
					log.info(MSG_DONE);
				}

				calculateAndSaveAllBasicStatistics(resultFilePath, h, basicStatistics, apt);
				calculateAndSaveAllFlatInternalMeasures(resultFilePath, h, qualityMeasures);
				if (withClassAttribute)
					calculateAndSaveAllExternalMeasures(resultFilePath, h, qualityMeasures);
				calculateAndSaveAllHIMVariants(resultFilePath, h, qualityMeasures);
				try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFilePath, true))) {
					bufferedWriter.append("%n");
				}
			}

		} catch (IOException e) {
			log.error(e);
		}
	}

	private static void saveBasicInfo(String resultFilePath, String instanceFilePath, boolean useSubtree,
			boolean mimicFlatClustering, boolean mimicOneCluster) throws IOException {
		try (BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true))) {
			resultFile.append(instanceFilePath + ";" + Boolean.toString(useSubtree) + ";"
					+ Boolean.toString(mimicFlatClustering) + ";" + Boolean.toString(mimicOneCluster) + ";");
		}
	}

	private static void calculateAndSaveAllHIMVariants(String resultFilePath, Hierarchy hierarchy,
			HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
		log.info(/* "Done.%n */"HIM + FlatWithinBetweenIndex..");
		calculateAndSaveQualityMeasure(
				HierarchicalInternalMeasure.class.getName() + FlatWithinBetweenIndex.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nHIM + FlatReversedDunn2..");
		calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn2.class.getName(),
				hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nHIM + FlatReversedDunn3..");
		calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn3.class.getName(),
				hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nHIM + FlatReversedDunn4..");
		calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn4.class.getName(),
				hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nHIM + FlatDaviesBouldin..");
		calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDaviesBouldin.class.getName(),
				hierarchy, qualityMeasures, resultFilePath);
		log.info(MSG_DONE);
	}

	private static void calculateAndSaveAllExternalMeasures(String resultFilePath, Hierarchy hierarchy,
			HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
		log.info("Flat cluster purity..");
		calculateAndSaveQualityMeasure(FlatClusterPurity.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nHierarchcal purity..");
		calculateAndSaveQualityMeasure(HierarchicalClassPurity.class.getName(), hierarchy, qualityMeasures,
				resultFilePath);
		log.info("Done.%nFmasure with flat hypotheses..");
		calculateAndSaveQualityMeasure(Fmeasure.class.getName() + FlatHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFmeasure with partial order hypotheses..");
		calculateAndSaveQualityMeasure(Fmeasure.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nAdapted Fmeasure with instances inheritance..");
		calculateAndSaveQualityMeasure(AdaptedFmeasure.class.getName() + Boolean.toString(true), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nAdapted Fmeasure with NO instances inheritance..");
		calculateAndSaveQualityMeasure(AdaptedFmeasure.class.getName() + Boolean.toString(false), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFowlkes Mallows with flat hypotheses..");
		calculateAndSaveQualityMeasure(FowlkesMallowsIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFowlkes Mallows with partial order hypotheses..");
		calculateAndSaveQualityMeasure(FowlkesMallowsIndex.class.getName() + PartialOrderHypotheses.class.getName(),
				hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nRand with flat hypotheses..");
		calculateAndSaveQualityMeasure(RandIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nRand with partial order hypotheses..");
		calculateAndSaveQualityMeasure(RandIndex.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nJaccard with flat hypotheses..");
		calculateAndSaveQualityMeasure(JaccardIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nJaccard with partial order hypotheses..");
		calculateAndSaveQualityMeasure(JaccardIndex.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFlat entropy 1..");
		calculateAndSaveQualityMeasure(FlatEntropy1.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat entropy 2..");
		calculateAndSaveQualityMeasure(FlatEntropy2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat information gain with flat entropy 1..");
		calculateAndSaveQualityMeasure(FlatInformationGain.class.getName() + FlatEntropy1.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFlat information gain with flat entropy 2..");
		calculateAndSaveQualityMeasure(FlatInformationGain.class.getName() + FlatEntropy2.class.getName(), hierarchy,
				qualityMeasures, resultFilePath);
		log.info("Done.%nFlat mutual information..");
		calculateAndSaveQualityMeasure(FlatMutualInformation.class.getName(), hierarchy, qualityMeasures,
				resultFilePath);
		log.info("Done.%nFlat normalized mutual information..");
		calculateAndSaveQualityMeasure(FlatNormalizedMutualInformation.class.getName(), hierarchy, qualityMeasures,
				resultFilePath);
		log.info(MSG_DONE);
	}

	private static void calculateAndSaveAllFlatInternalMeasures(String resultFilePath, Hierarchy hierarchy,
			HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
		log.info("Variance deviation..");
		calculateAndSaveQualityMeasure(VarianceDeviation.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nVariance deviation 2..");
		calculateAndSaveQualityMeasure(VarianceDeviation2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat within between..");
		calculateAndSaveQualityMeasure(FlatWithinBetweenIndex.class.getName(), hierarchy, qualityMeasures,
				resultFilePath);
		log.info("Done.%nFlat Dunn 1..");
		calculateAndSaveQualityMeasure(FlatDunn1.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat Dunn 2..");
		calculateAndSaveQualityMeasure(FlatDunn2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat Dunn 3..");
		calculateAndSaveQualityMeasure(FlatDunn3.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat Dunn 4..");
		calculateAndSaveQualityMeasure(FlatDunn4.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat Davies-Bouldin..");
		calculateAndSaveQualityMeasure(FlatDaviesBouldin.class.getName(), hierarchy, qualityMeasures, resultFilePath);
		log.info("Done.%nFlat Calinski-Charabasz..");
		calculateAndSaveQualityMeasure(FlatCalinskiHarabasz.class.getName(), hierarchy, qualityMeasures,
				resultFilePath);
		log.info(MSG_DONE);
	}

	private static void calculateAndSaveAllBasicStatistics(String resultFilePath, Hierarchy hierarchy,
			HashMap<String, CommonStatistic> basicStatistics, AvgPathLength apt) throws IOException {
		log.info("Number of nodes..");
		calculateAndSaveBasicStatistics(NumberOfNodes.class.getName(), hierarchy, basicStatistics, resultFilePath);
		log.info("Done.%nNumber of leaves..");
		calculateAndSaveBasicStatistics(NumberOfLeaves.class.getName(), hierarchy, basicStatistics, resultFilePath);
		log.info("Done.%nHeight..");
		calculateAndSaveBasicStatistics(Height.class.getName(), hierarchy, basicStatistics, resultFilePath);
		log.info("Done.%nAvg path length..");
		try (BufferedWriter resultFileAvgPathLength = new BufferedWriter(new FileWriter(resultFilePath, true))) {
			AvgWithStdev aptResult = apt.calculate(hierarchy);
			resultFileAvgPathLength.append(aptResult.getAvg() + ";" + aptResult.getStdev() + ";");
			log.info(MSG_DONE);
		}
	}

	private static void calculateAndSaveQualityMeasure(String nameOfTheQualityMeasure, Hierarchy hierarchy,
			HashMap<String, QualityMeasure> qualityMeasures, String resultFilePath) throws IOException {
		try (BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true))) {
			try {
				resultFile.append(qualityMeasures.get(nameOfTheQualityMeasure).getMeasure(hierarchy) + ";");
			} catch (Exception e) {
				resultFile.append(
						"PROBLEM: " + e.toString() + " " + e.getMessage() + " " + e.getLocalizedMessage() + ";");
			}
		}
	}

	private static void calculateAndSaveBasicStatistics(String nameOfTheStatistic, Hierarchy hierarchy,
			HashMap<String, CommonStatistic> basicStatistics, String resultFilePath) throws IOException {
		try (BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true))) {
			resultFile.append(basicStatistics.get(nameOfTheStatistic).calculate(hierarchy) + ";");
		}
	}
}
