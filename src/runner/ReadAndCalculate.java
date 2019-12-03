package runner;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import distance_measures.Euclidean;
import internal_measures.FlatCalinskiHarabasz;

public class ReadAndCalculate {
	private static final Logger log = LogManager.getLogger(ReadAndCalculate.class);

	public static void main(String[] args) {
		GeneratedCSVReader reader = new GeneratedCSVReader();
		Hierarchy h = null;
		try {
			h = reader.load("very-simple.csv", false, true, false, false, false);
			h.getRoot().printSubtree();
			FlatCalinskiHarabasz db = new FlatCalinskiHarabasz(new Euclidean());
			System.out.println(db.getMeasure(h));
		} catch (IOException e) {
			log.error(e);
		}
	}

}
