package runner;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import distance_measures.Euclidean;
import external_measures.AdaptedFmeasure;
import external_measures.information_based.*;
import external_measures.purity.FlatClusterPurity;
import external_measures.purity.HierarchicalClassPurity;
import external_measures.statistical_hypothesis.*;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;
import internal_measures.*;
import internal_measures.statistics.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ReadSeveralAndCalculateQualityMeasuresSeparately extends CommonReadSeveralAndCalculate {
    public static void main(String[] args) {
//        args = new String[]{
////                "GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_92.r.csv",
//                "set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_000.csv"
//        };
        //parameters
        boolean useSubtree = true;
        boolean withClassAttribute = true;
        double logBase = 2.0;
        double varianceDeviationAlpha = 1.0;
        boolean mimicFlatClustering = false;
        boolean mimicOneCluster = false;
        String resultFilePath = "measures.csv";

        if(mimicFlatClustering && mimicOneCluster) {
            System.err.println("You can either mimic flat clustering or one cluster but never do both!");
            System.exit(1);
        }

        DistanceMeasure measure = new Euclidean();

        HashMap<String, CommonStatistic> basicStatistics = new HashMap<>();
        AvgPathLength apt = getAvgPathLengthAndCreateBasicStatics(basicStatistics);

        HashMap<String, QualityMeasure> qualityMeasures = getQualityMeasureHashMap(withClassAttribute, logBase, varianceDeviationAlpha, measure);

        try
        {
            writeHeader(withClassAttribute, resultFilePath, false, true);
            System.out.println("Number of loaded files: " + args.length);
            System.out.println("Calculating..");

            for(int i = 0; i < args.length; i++) {
                System.out.println("========= " + (i+1) + "/" + args.length + " =========");
                String filePath = args[i];
                saveBasicInfo(resultFilePath, filePath, useSubtree, mimicFlatClustering, mimicOneCluster);

                GeneratedCSVReader reader = new GeneratedCSVReader();
                Hierarchy h = reader.load(filePath, false, withClassAttribute, false, false, useSubtree);

                System.out.println(filePath + " loaded");

                if(mimicOneCluster) {
                    System.out.print("Transforming into one-cluster solution..");
                    h = basic_hierarchy.common.Utils.getOneClusterHierarchy(h);
                    System.out.println("Done.");
                }

                if(mimicFlatClustering) {
                    System.out.print("Transforming into flat clustering solution..");
                    h = h.getFlatClusteringWithCommonEmptyRoot();
                    System.out.println("Done.");
                }


                calculateAndSaveAllBasicStatistics(resultFilePath, h, basicStatistics, apt);
                calculateAndSaveAllFlatInternalMeasures(resultFilePath, h, qualityMeasures);
                if(withClassAttribute)
                    calculateAndSaveAllExternalMeasures(resultFilePath, h, qualityMeasures);
                calculateAndSaveAllHIMVariants(resultFilePath, h, qualityMeasures);
                new BufferedWriter(new FileWriter(resultFilePath, true)).append("\n").close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveBasicInfo(String resultFilePath, String instanceFilePath, boolean useSubtree,
                                      boolean mimicFlatClustering, boolean mimicOneCluster) throws IOException {
        BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true));
        resultFile.append(instanceFilePath + ";" + Boolean.toString(useSubtree) + ";"
                + Boolean.toString(mimicFlatClustering) + ";" + Boolean.toString(mimicOneCluster) + ";");
        resultFile.close();
    }

    private static void calculateAndSaveAllHIMVariants(String resultFilePath, Hierarchy hierarchy, HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
//        System.out.println("HIM + VarianceDeviation..");
//        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + VarianceDeviation.class.getName(),
//                hierarchy, qualityMeasures, resultFilePath);
//        System.out.print("Done.\nHIM + VarianceDeviation2..");
//        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + VarianceDeviation2.class.getName(),
//                hierarchy, qualityMeasures, resultFilePath);
        System.out.print(/*"Done.\n*/"HIM + FlatWithinBetweenIndex..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatWithinBetweenIndex.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
//        System.out.print("Done.\nHIM + FlatDunn1..");
//        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDunn1.class.getName(),
//                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatReversedDunn2..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn2.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatReversedDunn3..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn3.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatReversedDunn4..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn4.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDaviesBouldin..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDaviesBouldin.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
//        System.out.print("Done.\nHIM + FlatCalinskiHarabasz..");
//        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatCalinskiHarabasz.class.getName(),
//                hierarchy, qualityMeasures, resultFilePath);
        System.out.println("Done.");
    }

    private static void calculateAndSaveAllExternalMeasures(String resultFilePath, Hierarchy hierarchy, HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
        System.out.print("Flat cluster purity..");
        calculateAndSaveQualityMeasure(FlatClusterPurity.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHierarchcal purity..");
        calculateAndSaveQualityMeasure(HierarchicalClassPurity.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFmasure with flat hypotheses..");
        calculateAndSaveQualityMeasure(Fmeasure.class.getName() + FlatHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFmeasure with partial order hypotheses..");
        calculateAndSaveQualityMeasure(Fmeasure.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nAdapted Fmeasure with instances inheritance..");
        calculateAndSaveQualityMeasure(AdaptedFmeasure.class.getName() + Boolean.toString(true), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nAdapted Fmeasure with NO instances inheritance..");
        calculateAndSaveQualityMeasure(AdaptedFmeasure.class.getName() + Boolean.toString(false), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFowlkes Mallows with flat hypotheses..");
        calculateAndSaveQualityMeasure(FowlkesMallowsIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFowlkes Mallows with partial order hypotheses..");
        calculateAndSaveQualityMeasure(FowlkesMallowsIndex.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nRand with flat hypotheses..");
        calculateAndSaveQualityMeasure(RandIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nRand with partial order hypotheses..");
        calculateAndSaveQualityMeasure(RandIndex.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nJaccard with flat hypotheses..");
        calculateAndSaveQualityMeasure(JaccardIndex.class.getName() + FlatHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nJaccard with partial order hypotheses..");
        calculateAndSaveQualityMeasure(JaccardIndex.class.getName() + PartialOrderHypotheses.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat entropy 1..");
        calculateAndSaveQualityMeasure(FlatEntropy1.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat entropy 2..");
        calculateAndSaveQualityMeasure(FlatEntropy2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat information gain with flat entropy 1..");
        calculateAndSaveQualityMeasure(FlatInformationGain.class.getName() + FlatEntropy1.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat information gain with flat entropy 2..");
        calculateAndSaveQualityMeasure(FlatInformationGain.class.getName() + FlatEntropy2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat mutual information..");
        calculateAndSaveQualityMeasure(FlatMutualInformation.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat normalized mutual information..");
        calculateAndSaveQualityMeasure(FlatNormalizedMutualInformation.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.println("Done.");
    }

    private static void calculateAndSaveAllFlatInternalMeasures(String resultFilePath, Hierarchy hierarchy, HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
        System.out.print("Variance deviation..");
        calculateAndSaveQualityMeasure(VarianceDeviation.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nVariance deviation 2..");
        calculateAndSaveQualityMeasure(VarianceDeviation2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat within between..");
        calculateAndSaveQualityMeasure(FlatWithinBetweenIndex.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Dunn 1..");
        calculateAndSaveQualityMeasure(FlatDunn1.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Dunn 2..");
        calculateAndSaveQualityMeasure(FlatDunn2.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Dunn 3..");
        calculateAndSaveQualityMeasure(FlatDunn3.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Dunn 4..");
        calculateAndSaveQualityMeasure(FlatDunn4.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Davies-Bouldin..");
        calculateAndSaveQualityMeasure(FlatDaviesBouldin.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nFlat Calinski-Charabasz..");
        calculateAndSaveQualityMeasure(FlatCalinskiHarabasz.class.getName(), hierarchy, qualityMeasures, resultFilePath);
        System.out.println("Done.");
    }

    private static void calculateAndSaveAllBasicStatistics(String resultFilePath, Hierarchy hierarchy, HashMap<String, CommonStatistic> basicStatistics, AvgPathLength apt) throws IOException {
        System.out.print("Number of nodes..");
        calculateAndSaveBasicStatistics(NumberOfNodes.class.getName(), hierarchy, basicStatistics, resultFilePath);
        System.out.print("Done.\nNumber of leaves..");
        calculateAndSaveBasicStatistics(NumberOfLeaves.class.getName(), hierarchy, basicStatistics, resultFilePath);
        System.out.print("Done.\nHeight..");
        calculateAndSaveBasicStatistics(Height.class.getName(), hierarchy, basicStatistics, resultFilePath);
        System.out.print("Done.\nAvg path length..");
        try(BufferedWriter resultFileAvgPathLength = new BufferedWriter(new FileWriter(resultFilePath, true))) {
            AvgWithStdev aptResult = apt.calculate(hierarchy);
            resultFileAvgPathLength.append(aptResult.getAvg() + ";" + aptResult.getStdev() + ";");
            System.out.println("Done.");
        }
    }

    private static void calculateAndSaveQualityMeasure(String nameOfTheQualityMeasure, Hierarchy hierarchy, HashMap<String, QualityMeasure> qualityMeasures, String resultFilePath) throws IOException {
        try(BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true))) {
            try {
                resultFile.append(qualityMeasures.get(nameOfTheQualityMeasure).getMeasure(hierarchy) + ";");
            }
            catch(Exception e) {
                resultFile.append("PROBLEM: " + e.toString() + " " + e.getMessage() + " " + e.getLocalizedMessage() + ";");
            }
        }
    }

    private static void calculateAndSaveBasicStatistics(String nameOfTheStatistic, Hierarchy hierarchy,
                                                        HashMap<String, CommonStatistic> basicStatistics,
                                                        String resultFilePath) throws IOException {
        try(BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true))) {
            resultFile.append(basicStatistics.get(nameOfTheStatistic).calculate(hierarchy) + ";");
        }
    }
}
