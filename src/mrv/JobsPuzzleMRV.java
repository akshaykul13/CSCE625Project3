package mrv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import entities.FeasibleDomains;
import entities.JobsState;

public class JobsPuzzleMRV {
	public final static Integer CHEF = 0;
	public final static Integer GUARD = 1;
	public final static Integer NURSE = 2;
	public final static Integer CLERK = 3;
	public final static Integer POLICE = 4;
	public final static Integer TEACHER = 5;
	public final static Integer ACTOR = 6;
	public final static Integer BOXER = 7;
	private final static int NUMBER_OF_VARIABLES = 8; 
	
	private final static int ROBERTA = 0;
	private final static int THELMA = 1;
	private final static int STEVE = 2;
	private final static int PETE = 3;
	private final static int NUMBER_OF_DOMAINS = 4;
	
	private static int numberOfStatesSearched = 0;
	
	public static void main(String[] args) {
		int[] solution = new int[NUMBER_OF_VARIABLES];
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++) {
			solution[i] = -1;
		}
		int[] finalSolution = findSolution(solution);
		if (finalSolution != null) {			
			printSolution(finalSolution);
			System.out.println("Number of states searched = " + numberOfStatesSearched);
		} else {
			System.out.println("No solution");
		}
	}	

	private static int[] findSolution(int[] solution) {
		Stack<JobsState> solutionStack = new Stack<JobsState>();
		for (int i=0; i < NUMBER_OF_DOMAINS; i++) {
			int[] newSolution = copySolution(solution);
			JobsState initialState = new JobsState(newSolution);
			initialState.getSolution()[CHEF] = i;			
			calculateFeasibleDomains(initialState);
			solutionStack.push(initialState);
		}	
		while (!solutionStack.isEmpty()) {			
			JobsState currentState = solutionStack.peek();		
			if (isComplete(currentState.getSolution())) {
				System.out.println("Found Solution");
				return currentState.getSolution();
			}
			FeasibleDomains top = currentState.getPossibleDomains().peek();			
			if (!currentState.getPossibleDomains().peek().getFeasibleValues().isEmpty()) {
				FeasibleDomains domain = currentState.getPossibleDomains().poll();
				int nextAttribute = domain.getAttribute();
				int nextValue = domain.getFeasibleValues().get(0);
				domain.getFeasibleValues().remove(domain.getFeasibleValues().indexOf(nextValue));
				currentState.getPossibleDomains().add(domain);
				int[] newSolution = copySolution(currentState.getSolution());
				JobsState newState = new JobsState(newSolution);
				newState.getSolution()[nextAttribute] = nextValue;				
				calculateFeasibleDomains(newState);
				solutionStack.push(newState);
				numberOfStatesSearched++;
			} else {
				JobsState poppedState = solutionStack.pop();					
			}
		}
		return null;
	}

	private static boolean isComplete(int[] solution) {
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++) {
			if (solution[i] == -1) {
				return false;
			}
		}
		return true;
	}

	private static void calculateFeasibleDomains(JobsState state) {
		state.clearPossibleDomains();
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++) {			
			if (state.getSolution()[i] == -1) {
				ArrayList<Integer> possibleValues = new ArrayList<Integer>();
				for (int k = 0; k < NUMBER_OF_DOMAINS; k++) {						
					state.getSolution()[i] = k;
					if (consistencyCheck(state.getSolution())) {
						possibleValues.add(k);
					}
					state.getSolution()[i] = -1;
				}
				FeasibleDomains fd = new FeasibleDomains(i, possibleValues);
				state.getPossibleDomains().add(fd);
			}			
		}		
	}

	private static int[] copySolution(int[] solution) {
		int[] newSolution = new int[NUMBER_OF_VARIABLES];
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++) {
			newSolution[i] = solution[i];
		}
		return newSolution;
	}

	private static void printSolution(int[] solution) {
		HashMap<Integer, String> jobNames = getJobsNames();
		HashMap<Integer, String> personNames = getPersonNames();
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++) {
			System.out.println(jobNames.get(i) + ": " + personNames.get(solution[i]));
		}		
	}
	
	private static boolean consistencyCheck(int[] solution) {
		if (eachHoldsTwoJobs(solution) && maleNurse(solution) && chefIsFemale(solution) && clerkIsMale(solution) && robertaNotBoxer(solution) && 
				peteHasNoHigherEdu(solution) && actorIsMale(solution) && robertaIsNotChefAndPolice(solution) && chefIsNotPolice(solution)) {
			return true;
		}
		return false;
	}

	private static boolean eachHoldsTwoJobs(int[] solution) {
		 int roberta = 0;
		 int thelma = 0;
		 int steve = 0;
		 int pete = 0;
		 for (int i = 0; i < solution.length; i++) {
			 switch (solution[i]) {
			case ROBERTA:
				roberta++;
				break;
			case THELMA:
				thelma++;
				break;
			case STEVE:
				steve++;
				break;
			case PETE:
				pete++;
				break;
			default:
				break;
			}
		 }
		 if (roberta > 2 || thelma > 2 || steve > 2 || pete > 2) {
			 return false;
		 }
		 return true;
	}

	private static boolean maleNurse(int[] solution) {
		if (solution[NURSE] == ROBERTA || solution[NURSE] == THELMA) {
			return false;
		}
		return true;
	}

	private static boolean chefIsFemale(int[] solution) {
		if (solution[CHEF] == PETE || solution[CHEF] == STEVE) {
			return false;
		}
		return true;
	}

	private static boolean clerkIsMale(int[] solution) {
		if (solution[CLERK] == ROBERTA || solution[CLERK] == THELMA) {
			return false;
		}
		return true;
	}

	private static boolean robertaNotBoxer(int[] solution) {
		if (solution[BOXER] == ROBERTA) {
			return false;
		}
		return true;
	}

	private static boolean peteHasNoHigherEdu(int[] solution) {
		if (solution[NURSE] == PETE || solution[POLICE] == PETE || solution[TEACHER] == PETE) {
			return false;
		}
		return true;
	}

	private static boolean actorIsMale(int[] solution) {
		if (solution[ACTOR] != -1 && (solution[ACTOR] == ROBERTA || solution[ACTOR] == THELMA)) {
			return false;
		}
		return true;
	}

	private static boolean robertaIsNotChefAndPolice(int[] solution) {
		if (solution[CHEF] != -1 && solution[POLICE] != -1 && (solution[CHEF] == ROBERTA || solution[POLICE] == ROBERTA)) {
			return false;
		}
		return true;
	}

	private static boolean chefIsNotPolice(int[] solution) {
		if (solution[CHEF] != -1 && solution[POLICE] != -1 && solution[CHEF] == solution[POLICE]) {
			return false;
		}
		return true;
	}
	
	private static HashMap<Integer, String> getJobsNames() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(CHEF, "CHEF");
		map.put(GUARD, "GUARD");
		map.put(NURSE, "NURSE");
		map.put(CLERK, "CLERK");
		map.put(POLICE, "POLICE");
		map.put(TEACHER, "TEACHER");
		map.put(ACTOR, "ACTOR");
		map.put(BOXER, "BOXER");		
		return map;
	}
	
	private static HashMap<Integer, String> getPersonNames() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(ROBERTA, "ROBERTA");
		map.put(THELMA, "THELMA");
		map.put(STEVE, "STEVE");
		map.put(PETE, "PETE");
		return map;
	}
}
