package entities;
import java.util.ArrayList;
import java.util.Comparator;


public class FeasibleDomains {
	private int attribute;
	
	private ArrayList<Integer> feasibleValues;
	
	public FeasibleDomains(int lattribute, ArrayList<Integer> lfeasibleDomains) {
		this.attribute = lattribute;
		this.feasibleValues = lfeasibleDomains;
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	public ArrayList<Integer> getFeasibleValues() {
		return feasibleValues;
	}

	public void setFeasibleValues(ArrayList<Integer> feasibleValues) {
		this.feasibleValues = feasibleValues;
	}	
}