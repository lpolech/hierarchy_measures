package runner;

import java.io.IOException;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import distance_measures.Euclidean;
import internal_measures.FlatCalinskiHarabasz;

public class ReadAndCalculate {

	public static void main(String[] args) {
		GeneratedCSVReader reader = new GeneratedCSVReader();
		Hierarchy H = null;
		try {
			H = reader.load("very-simple.csv", false, true, false, false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		H.getRoot().printSubtree();
		FlatCalinskiHarabasz db = new FlatCalinskiHarabasz(new Euclidean());
		System.out.println(db.getMeasure(H));

	}

}
