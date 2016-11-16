package runner;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import distance_measures.Euclidean;
import external_measures.AdaptedFmeasure;
import external_measures.information_based.*;
import external_measures.purity.FlatClusterPurity;
import external_measures.purity.HierarchicalClassPurity;
import external_measures.statistical_hypothesis.*;
import interfaces.QualityMeasure;
import internal_measures.*;
import internal_measures.statistics.*;
import internal_measures.statistics.histogram.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadSeveralAndCalculateAll {

    public static void main(String[] args) {
        args = new String[]{"/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_0.gt.csv"};
        boolean useSubtree = true;
        boolean withClassAttribute = true;
        double logBase = 2.0;
        double varianceDeviationAlpha = 1.0;

        ArrayList<Hierarchy> hierarchies = new ArrayList<>();
        ArrayList<Hierarchy> hierarchiesWithoutEmptyNodesForFlatMeasures = new ArrayList<>();
        for(String filePath: args)
        {
            GeneratedCSVReader reader = new GeneratedCSVReader();
            System.out.println(filePath);
            hierarchies.add(reader.load(filePath, false, withClassAttribute, false, useSubtree));
//            hierarchies.getLast().getRoot().printSubtree();
        }

        System.out.println(useSubtree? "USING SUBTREE": "NO SUBTREE");

        Euclidean measure = new Euclidean();

        HashMap<String, CommonStatistic> basicStatistics = new HashMap<>();
        HashMap<String, QualityMeasure> qualityMeasures = new HashMap<>();
        HashMap<String, CommonPerLevelHistogram> histograms = new HashMap<>();

        //below measures are sensitive to useSubtree toggle
        qualityMeasures.put(FlatCalinskiHarabasz.class.getName(), new FlatCalinskiHarabasz(measure));
        qualityMeasures.put(FlatDaviesBouldin.class.getName(), new FlatDaviesBouldin(measure));
        qualityMeasures.put(FlatDunn1.class.getName(), new FlatDunn1(measure));
        qualityMeasures.put(FlatDunn4.class.getName(), new FlatDunn4(measure));
        qualityMeasures.put(FlatWithinBetweenIndex.class.getName(), new FlatWithinBetweenIndex(measure));
        //above measures are sensitive to useSubtree toggle

        qualityMeasures.put(FlatDunn2.class.getName(), new FlatDunn2(measure));
        qualityMeasures.put(FlatDunn3.class.getName(), new FlatDunn3(measure));
        qualityMeasures.put(VarianceDeviation.class.getName(), new VarianceDeviation(varianceDeviationAlpha));
        qualityMeasures.put(VarianceDeviation2.class.getName(), new VarianceDeviation2());

        if(withClassAttribute) {
            qualityMeasures.put(AdaptedFmeasure.class.getName() + Boolean.toString(true), new AdaptedFmeasure(true));
            qualityMeasures.put(AdaptedFmeasure.class.getName() + Boolean.toString(false), new AdaptedFmeasure(false));
            qualityMeasures.put(Fmeasure.class.getName() + FlatHypotheses.class.getName(), new Fmeasure(1.0f, new FlatHypotheses()));
            qualityMeasures.put(Fmeasure.class.getName() + PartialOrderHypotheses.class.getName(), new Fmeasure(1.0f, new PartialOrderHypotheses()));
            qualityMeasures.put(FowlkesMallowsIndex.class.getName() + FlatHypotheses.class.getName(), new FowlkesMallowsIndex(new FlatHypotheses()));
            qualityMeasures.put(FowlkesMallowsIndex.class.getName() + PartialOrderHypotheses.class.getName(), new FowlkesMallowsIndex(new PartialOrderHypotheses()));
            qualityMeasures.put(JaccardIndex.class.getName() + FlatHypotheses.class.getName(), new JaccardIndex(new FlatHypotheses()));
            qualityMeasures.put(JaccardIndex.class.getName() + PartialOrderHypotheses.class.getName(), new JaccardIndex(new PartialOrderHypotheses()));
            qualityMeasures.put(RandIndex.class.getName() + FlatHypotheses.class.getName(), new RandIndex(new FlatHypotheses()));
            qualityMeasures.put(RandIndex.class.getName() + PartialOrderHypotheses.class.getName(), new RandIndex(new PartialOrderHypotheses()));
            qualityMeasures.put(FlatClusterPurity.class.getName(), new FlatClusterPurity());
            qualityMeasures.put(HierarchicalClassPurity.class.getName(), new HierarchicalClassPurity());
            qualityMeasures.put(FlatEntropy1.class.getName(), new FlatEntropy1(logBase));
            qualityMeasures.put(FlatEntropy2.class.getName(), new FlatEntropy2(logBase));
            qualityMeasures.put(FlatInformationGain.class.getName() + FlatEntropy1.class.getName(), new FlatInformationGain(logBase, new FlatEntropy1(logBase)));
            qualityMeasures.put(FlatInformationGain.class.getName() + FlatEntropy2.class.getName(), new FlatInformationGain(logBase, new FlatEntropy2(logBase)));
            qualityMeasures.put(FlatMutualInformation.class.getName(), new FlatMutualInformation(logBase));
            qualityMeasures.put(FlatNormalizedMutualInformation.class.getName(), new FlatNormalizedMutualInformation(logBase));
        }

        AvgPathLength apt = new AvgPathLength();

        basicStatistics.put(Height.class.getName(), new Height());
        basicStatistics.put(NumberOfLeaves.class.getName(), new NumberOfLeaves());
        basicStatistics.put(NumberOfNodes.class.getName(), new NumberOfNodes());

        histograms.put(ChildPerNodePerLevel.class.getName(), new ChildPerNodePerLevel());
        histograms.put(InstancesPerLevel.class.getName(), new InstancesPerLevel());
        histograms.put(LeavesPerLevel.class.getName(), new LeavesPerLevel());
        histograms.put(NodesPerLevel.class.getName(), new NodesPerLevel());
        histograms.put(HistogramOfNumberOfChildren.class.getName(), new HistogramOfNumberOfChildren());

        try(BufferedWriter resultFile = new BufferedWriter(new FileWriter("measuresAndStatictics.csv")))
        {
            System.out.println("Calculating..");

            String header = "use subtree for internal measures?;num of nodes;stdev;num of leaves;stdev;height;stdev;" +
                    "avg path length;stdev;variance deviation;stdev;variance deviation2;stdev;flat within between;stdev;" +
                    "flat dunn1;stdev;flat dunn2;stdev;flat dunn3;stdev;flat dunn4;stdev;flat davis-bouldin;stdev;" +
                    "flat calinski-harabasz;stdev;";


            if(withClassAttribute)
            {
                header += "Flat cluster purity;stdev;Hierarchcal purity;stdev;Fmasure flat hypotheses;stdev;" +
                        "Fmeasure partial order hypotheses;stdev;Adapted Fmeasure instances inheritance;stdev;" +
                        "Adapted Fmeasure NO instances inheritance;stdev;Fowlkes Mallows flat hypotheses;stdev;" +
                        "Fowlkes Mallows partial order hypotheses;stdev;Rand flat hypotheses;stdev;" +
                        "Rand partial order hypotheses;stdev;Jaccard flat hypotheses;stdev;" +
                        "Jaccard partial order hypotheses;stdev;Flat entropy1;stdev;Flat entropy2;stdev;" +
                        "Flat information gain flat entropy1;stdev;Flat information gain flat entropy2;stdev;" +
                        "Flat mutual information;stdev;Flat normalized mutual information;stdev\n";
            }
            resultFile.append(header);

            resultFile.append(Boolean.toString(useSubtree) + ";");
            System.out.println("Number of nodes..");
            resultFile.append(getAvgAndStdevInOutputFormat(basicStatistics.get(NumberOfNodes.class.getName()).calculate(hierarchies)));
            System.out.println("Done.\nNumber of leaves..");
            resultFile.append(getAvgAndStdevInOutputFormat(basicStatistics.get(NumberOfLeaves.class.getName()).calculate(hierarchies)));
            System.out.println("Done.\nHeight..");
            resultFile.append(getAvgAndStdevInOutputFormat(basicStatistics.get(Height.class.getName()).calculate(hierarchies)));
            System.out.println("Done.\nAvg path length..");
            resultFile.append(getAvgAndStdevInOutputFormat(apt.calculate(hierarchies)));
            System.out.println("Done.\nVariance deviation..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(VarianceDeviation.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nVariance deviation 2..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(VarianceDeviation2.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat within between..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatWithinBetweenIndex.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Dunn 1..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatDunn1.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Dunn 2..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatDunn2.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Dunn 3..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatDunn3.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Dunn 4..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatDunn4.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Davies-Bouldin..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatDaviesBouldin.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.\nFlat Calinski-Charabasz..");
            resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatCalinskiHarabasz.class.getName()).getMeasure(hierarchies)));
            System.out.println("Done.");

            if(withClassAttribute) {
                System.out.println("Flat cluster purity..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatClusterPurity.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nHierarchcal purity..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(HierarchicalClassPurity.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFmasure with flat hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(Fmeasure.class.getName() + FlatHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFmeasure with partial order hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(Fmeasure.class.getName() + PartialOrderHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nAdapted Fmeasure with instances inheritance..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(AdaptedFmeasure.class.getName() + Boolean.toString(true)).getMeasure(hierarchies)));
                System.out.println("Done.\nAdapted Fmeasure with NO instances inheritance..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(AdaptedFmeasure.class.getName() + Boolean.toString(false)).getMeasure(hierarchies)));
                System.out.println("Done.\nFowlkes Mallows with flat hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FowlkesMallowsIndex.class.getName() + FlatHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFowlkes Mallows with partial order hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FowlkesMallowsIndex.class.getName() + PartialOrderHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nRand with flat hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(RandIndex.class.getName() + FlatHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nRand with partial order hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(RandIndex.class.getName() + PartialOrderHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nJaccard with flat hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(JaccardIndex.class.getName() + FlatHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nJaccard with partial order hypotheses..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(JaccardIndex.class.getName() + PartialOrderHypotheses.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat entropy 1..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatEntropy1.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat entropy 2..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatEntropy2.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat information gain with flat entropy 1..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatInformationGain.class.getName() + FlatEntropy1.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat information gain with flat entropy 2..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatInformationGain.class.getName() + FlatEntropy2.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat mutual information..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatMutualInformation.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\nFlat normalized mutual information..");
                resultFile.append(getAvgAndStdevInOutputFormat(qualityMeasures.get(FlatNormalizedMutualInformation.class.getName()).getMeasure(hierarchies)));
                System.out.println("Done.\n..");
            }

//            header += ";Nodes per level histogram;;Leaves per level histogram;;Instances per level histogram" +
//                    ";;Child per node per level histogram;;Histogram of number of children;\n";
//            histogramy beda wyswietlane ponizej innych wynikow, najpierw nazwa pozniej biny pozniej wartosci z stdev
            System.out.println("Nodes per level histogram..");
            resultFile.append("\n\n");
            calculateAndWriteHistogramicValues("Nodes per level histogram", hierarchies, histograms,
                    NodesPerLevel.class.getName(), resultFile);
            System.out.println("Done.\nLeaves per level histogram..");
            calculateAndWriteHistogramicValues("Leaves per level histogram", hierarchies, histograms,
                    LeavesPerLevel.class.getName(), resultFile);
            System.out.println("Done.\nInstances per level histogram..");
            calculateAndWriteHistogramicValues("Instances per level histogram", hierarchies, histograms,
                    InstancesPerLevel.class.getName(), resultFile);
            System.out.println("Done.\nChild per node per level histogram..");
            calculateAndWriteHistogramicValues("Child per node per level histogram", hierarchies, histograms,
                    ChildPerNodePerLevel.class.getName(), resultFile);
            System.out.println("Done.\nHistogram of number of children..");
            calculateAndWriteHistogramicValues("Histogram of number of children", hierarchies, histograms,
                    HistogramOfNumberOfChildren.class.getName(), resultFile);
            System.out.println("Done.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculateAndWriteHistogramicValues(String histogramName, ArrayList<Hierarchy> hierarchies, HashMap<String,
            CommonPerLevelHistogram> perLevelHistograms, String histogramicMeasureName, BufferedWriter resultFile) throws IOException {
        AvgWithStdev[] histogram = perLevelHistograms.get(histogramicMeasureName).calculate(hierarchies);
        String histBin = "";
        String histAvg = "";
        String histStdev = "";
        for(int i = 0; i < histogram.length; i++)
        {
            histBin += i + ";";
            histAvg += histogram[i].getAvg() + ";";
            histStdev += histogram[i].getStdev() + ";";
        }
        histBin += "\n";
        histAvg += "\n";
        histStdev += "\n";
        resultFile.append(histogramName + "\n");
        resultFile.append(histBin);
        resultFile.append(histAvg);
        resultFile.append(histStdev);
        resultFile.append("\n");
    }

    private static String getAvgAndStdevInOutputFormat(AvgWithStdev values)
    {
        return values.getAvg() + ";" + values.getStdev() + ";";
    }
}
