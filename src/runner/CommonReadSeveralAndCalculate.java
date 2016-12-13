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
import internal_measures.statistics.histogram.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class CommonReadSeveralAndCalculate {

    protected static void writeHeader(boolean withClassAttribute, String resultFilePath, boolean withStdev, boolean prependInstanceName) throws IOException {
        BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true));

        String header = "use subtree for internal measures?;mimic flat clustering?;mimic one cluster?;num of nodes;stdev;num of leaves;stdev;height;stdev;" +
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
                    "Flat mutual information;stdev;Flat normalized mutual information;stdev;";
        }

        header += /*"HIM + VarianceDeviation;stdev;HIM + VarianceDeviation2;stdev;*/"HIM + FlatWithinBetweenIndex;stdev;" +
                /*"HIM + FlatDunn1;stdev;*/"HIM + FlatReversedDunn2;stdev;HIM + FlatReversedDunn3;stdev;HIM + FlatReversedDunn4;stdev;" +
                "HIM + FlatDaviesBouldin;stdev;";/*HIM + FlatCalinskiHarabasz;stdev;";*/

        header += "\n";

        if(!withStdev) {
            header = header.replaceAll(";stdev", "");
            header = header.replace("avg path length;", "avg path length;stdev;");
        }

        if(prependInstanceName)
            header = "instance name;" + header;

        resultFile.append(header);
        resultFile.close();
    }

    protected static HashMap<String, CommonPerLevelHistogram> getHistogramsHashMap() {
        HashMap<String, CommonPerLevelHistogram> histograms = new HashMap<>();

        histograms.put(ChildPerNodePerLevel.class.getName(), new ChildPerNodePerLevel());
        histograms.put(InstancesPerLevel.class.getName(), new InstancesPerLevel());
        histograms.put(LeavesPerLevel.class.getName(), new LeavesPerLevel());
        histograms.put(NodesPerLevel.class.getName(), new NodesPerLevel());
        histograms.put(HistogramOfNumberOfChildren.class.getName(), new HistogramOfNumberOfChildren());
        return histograms;
    }

    protected static HashMap<String, QualityMeasure> getQualityMeasureHashMap(boolean withClassAttribute, double logBase, double varianceDeviationAlpha, DistanceMeasure measure) {
        HashMap<String, QualityMeasure> qualityMeasures = new HashMap<>();
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

//        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + VarianceDeviation.class.getName(), new HierarchicalInternalMeasure(new VarianceDeviation(1.0)));
//        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + VarianceDeviation2.class.getName(), new HierarchicalInternalMeasure(new VarianceDeviation2()));
        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatWithinBetweenIndex.class.getName(), new HierarchicalInternalMeasure(new FlatWithinBetweenIndex(new Euclidean())));
//        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatDunn1.class.getName(), new HierarchicalInternalMeasure(new FlatDunn1(new Euclidean())));
        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn2.class.getName(), new HierarchicalInternalMeasure(new FlatReversedDunn2(new Euclidean())));
        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn3.class.getName(), new HierarchicalInternalMeasure(new FlatReversedDunn3(new Euclidean())));
        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatReversedDunn4.class.getName(), new HierarchicalInternalMeasure(new FlatReversedDunn4(new Euclidean())));
        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatDaviesBouldin.class.getName(), new HierarchicalInternalMeasure(new FlatDaviesBouldin(new Euclidean())));
//        qualityMeasures.put(HierarchicalInternalMeasure.class.getName() + FlatCalinskiHarabasz.class.getName(), new HierarchicalInternalMeasure(new FlatCalinskiHarabasz(new Euclidean())));

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
        return qualityMeasures;
    }

    protected static AvgPathLength getAvgPathLengthAndCreateBasicStatics(HashMap<String, CommonStatistic> basicStatistics) {
        basicStatistics.put(Height.class.getName(), new Height());
        basicStatistics.put(NumberOfLeaves.class.getName(), new NumberOfLeaves());
        basicStatistics.put(NumberOfNodes.class.getName(), new NumberOfNodes());
        return new AvgPathLength();
    }

    protected static ArrayList<Hierarchy> loadHierarchies(String[] args, boolean useSubtree, boolean withClassAttribute) {
        ArrayList<Hierarchy> hierarchies = new ArrayList<>();
        for(String filePath: args)
        {
            GeneratedCSVReader reader = new GeneratedCSVReader();
            System.out.println(filePath);
            try {
                hierarchies.add(reader.load(filePath, false, withClassAttribute, false, false, useSubtree));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            System.out.println(filePath + " loaded");
        }

        System.out.println(useSubtree? "USING SUBTREE": "NO SUBTREE");
        System.out.println("Number of loaded files: " + args.length);
        return hierarchies;
    }
}
