package entities;

import java.util.Comparator;
import java.util.PriorityQueue;

public class JobsState {
	private int[] solution;
	
	private PriorityQueue<FeasibleDomains> possibleDomains;
	
	public JobsState(int[] currentSolution) {
		this.solution = currentSolution;
		FeasibleDomainComparator1 fdc = new FeasibleDomainComparator1();
		possibleDomains = new PriorityQueue<FeasibleDomains>(10000, fdc);
	}
	
	public int[] getSolution() {
		return solution;
	}

	public void setSolution(int[] solution) {
		this.solution = solution;
	}

	public PriorityQueue<FeasibleDomains> getPossibleDomains() {
		return possibleDomains;
	}
	
	public void clearPossibleDomains() {
		FeasibleDomainComparator1 fdc = new FeasibleDomainComparator1();
		possibleDomains = new PriorityQueue<FeasibleDomains>(10000, fdc);
	}

	public void setPossibleDomains(PriorityQueue<FeasibleDomains> possibleDomains) {
		this.possibleDomains = possibleDomains;
	}

}

class FeasibleDomainComparator1 implements Comparator<FeasibleDomains>
{
	@Override
	public int compare(FeasibleDomains d1, FeasibleDomains d2) {
		if (d1.getFeasibleValues().size() < d2.getFeasibleValues().size()) {
			return -1;
		}
		if (d1.getFeasibleValues().size() > d2.getFeasibleValues().size()) {
			return 1;
		}
		return 0;
	}
}
