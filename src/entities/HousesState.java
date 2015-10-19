package entities;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HousesState {
	private int[][] solution;
	
	private PriorityQueue<FeasibleDomains> possibleDomains;
	
	public HousesState(int[][] currentSolution) {
		this.solution = currentSolution;
		FeasibleDomainComparator fdc = new FeasibleDomainComparator();
		possibleDomains = new PriorityQueue<FeasibleDomains>(10000, fdc);
	}
	
	public int[][] getSolution() {
		return solution;
	}

	public void setSolution(int[][] solution) {
		this.solution = solution;
	}

	public PriorityQueue<FeasibleDomains> getPossibleDomains() {
		return possibleDomains;
	}
	
	public void clearPossibleDomains() {
		FeasibleDomainComparator fdc = new FeasibleDomainComparator();
		possibleDomains = new PriorityQueue<FeasibleDomains>(10000, fdc);
	}

	public void setPossibleDomains(PriorityQueue<FeasibleDomains> possibleDomains) {
		this.possibleDomains = possibleDomains;
	}

	public static void main(String[] args) {
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(0);
		FeasibleDomains fd1 = new FeasibleDomains(0, list1);
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(0);
		list2.add(1);
		FeasibleDomains fd2 = new FeasibleDomains(0, list2);		
		HousesState state = new HousesState(null);
		state.getPossibleDomains().add(fd1);
		state.getPossibleDomains().add(fd2);
		ArrayList<Integer> temp = state.getPossibleDomains().poll().getFeasibleValues();
		ArrayList<Integer> temp1 = state.getPossibleDomains().poll().getFeasibleValues();
		System.out.println();
	}
}

class FeasibleDomainComparator implements Comparator<FeasibleDomains>
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
