package interfaces;

import basic_hierarchy.interfaces.Hierarchy;

public interface Hypotheses {
	public void calculate(Hierarchy h);
	public long getTP();
	public long getFP();
	public long getTN();
	public long getFN();
}
