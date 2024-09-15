package hierarchy_measures.runner;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.reader.GeneratedCSVReader;
import hierarchy_measures.distance_measures.Euclidean;
import hierarchy_measures.internal_measures.FlatCalinskiHarabasz;

public class ReadAndCalculate {
	private static final Logger log = LogManager.getLogger(ReadAndCalculate.class);

	public static void main(String[] args) {

		GeneratedCSVReader reader = new GeneratedCSVReader();
		Hierarchy hierarchy = null;
		try {
			hierarchy = reader.load("very-simple.csv", false, true, false, false, false);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		if (hierarchy != null)
			hierarchy.getRoot().printSubtree();
		FlatCalinskiHarabasz db = new FlatCalinskiHarabasz(new Euclidean());
		log.trace(db.getMeasure(hierarchy));
	}

}
