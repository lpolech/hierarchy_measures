package runner;

public class CL_ReadSeveralAndCalculateQualityMeasuresSeparately {
	public static void main(String[] args) {
		String[] newArgs = new String[] {
//                "GENERATOR_set00_a-1,0_l-0,5_g-0,2_N-10000_d-2_P-1,0_Q-5,0_minSD-0,05_maxSd-10,0_92.r.csv",
				"set00_a-1_l-05_g-02_N-1000_d-3_P-1_Q-5_minSD-005_maxSd-10.e0_000.csv" };

		ReadSeveralAndCalculateQualityMeasuresSeparately calculator = new ReadSeveralAndCalculateQualityMeasuresSeparately();

		calculator.run(newArgs, "measures.csv", true, false, false, false);
	}
}
