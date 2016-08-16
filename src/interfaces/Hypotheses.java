package interfaces;

import basic_hierarchy.interfaces.Hierarchy;

public interface Hypotheses {
	public void calculate(Hierarchy h);
	public int getTP();
	public int getFP();
	public int getTN();
	public int getFN();
}
