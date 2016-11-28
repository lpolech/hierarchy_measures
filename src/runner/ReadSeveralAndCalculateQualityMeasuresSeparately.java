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
//                "GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_92.r.csv"
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_43.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_21.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_82.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_10.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_20.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_19.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_1.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_94.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_14.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_66.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_60.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_72.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_68.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_56.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_91.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_18.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_45.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_34.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_96.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_39.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_89.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_73.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_98.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_16.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_7.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_27.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_11.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_46.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_0.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_48.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_75.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_71.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_67.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_36.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_88.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_33.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_65.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_69.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_58.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_23.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_81.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_9.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_8.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_24.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_54.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_51.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_63.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_4.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_62.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_79.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_41.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_13.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_44.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_38.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_3.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_25.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_74.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_31.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_87.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_5.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_84.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_53.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_86.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_6.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_57.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_37.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_99.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_35.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_22.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_59.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_47.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_15.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_80.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_90.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_17.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_85.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_52.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_28.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_70.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_76.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_40.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_97.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_55.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_92.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_42.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_50.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_64.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_29.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_95.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_26.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_61.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_77.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_78.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_2.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_32.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_49.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_83.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_12.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_93.gt.csv",
//                "/home/tosterr/Desktop/FINAL_100_repeats/set00/GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_30.gt.csv",
//        };
        //parameters
        boolean useSubtree = false;
        boolean withClassAttribute = true;
        double logBase = 2.0;
        double varianceDeviationAlpha = 1.0;
        String resultFilePath = "measures.csv";

        DistanceMeasure measure = new Euclidean();

        HashMap<String, CommonStatistic> basicStatistics = new HashMap<>();
        AvgPathLength apt = getAvgPathLengthAndCreateBasicStatics(basicStatistics);

        HashMap<String, QualityMeasure> qualityMeasures = getQualityMeasureHashMap(withClassAttribute, logBase, varianceDeviationAlpha, measure);

        try
        {
            writeHeader(useSubtree, withClassAttribute, resultFilePath, false, true);
            System.out.println(useSubtree? "USING SUBTREE": "NO SUBTREE");
            System.out.println("Number of loaded files: " + args.length);
            System.out.println("Calculating..");

            for(int i = 0; i < args.length; i++) {
                String filePath = args[i];
                saveBasicInfo(resultFilePath, filePath, useSubtree);

                GeneratedCSVReader reader = new GeneratedCSVReader();
                Hierarchy h = reader.load(filePath, false, withClassAttribute, false, useSubtree);
                System.out.println(filePath + " loaded");

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

    private static void saveBasicInfo(String resultFilePath, String instanceFilePath, boolean useSubtree) throws IOException {
        BufferedWriter resultFile = new BufferedWriter(new FileWriter(resultFilePath, true));
        resultFile.append(instanceFilePath + ";" + Boolean.toString(useSubtree) + ";");
        resultFile.close();
    }

    private static void calculateAndSaveAllHIMVariants(String resultFilePath, Hierarchy hierarchy, HashMap<String, QualityMeasure> qualityMeasures) throws IOException {
        System.out.println("HIM + VarianceDeviation..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + VarianceDeviation.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + VarianceDeviation2..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + VarianceDeviation2.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatWithinBetweenIndex..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatWithinBetweenIndex.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDunn1..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDunn1.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDunn2..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDunn2.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDunn3..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDunn3.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDunn4..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDunn4.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatDaviesBouldin..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatDaviesBouldin.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
        System.out.print("Done.\nHIM + FlatCalinskiHarabasz..");
        calculateAndSaveQualityMeasure(HierarchicalInternalMeasure.class.getName() + FlatCalinskiHarabasz.class.getName(),
                hierarchy, qualityMeasures, resultFilePath);
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
