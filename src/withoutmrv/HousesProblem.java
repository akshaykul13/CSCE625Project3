package withoutmrv;

import java.util.HashMap;

public class HousesProblem {
	
	private final static int NUMBER_OF_VARIABLES = 25;
	private final static int NUMBER_OF_DOMAINS = 5;
	
	// 1-5 Nationality, 6-10 Color, 11-15 Chocolates, 16-20 Drinks, 21-25 Animals
	// Englishman, Spaniard, Norwegian, Ukranian, Japanese
	// Red, Green, Ivory, Yellow, Blue
	// Hershey, KitKat, Smarties, Snickers, MilkyWay
	// Orange, Tea, Coffee, Milk, Water
	// Dog, Fox, Snails, Horse, Zebra
	private final static int NATIONALITY = 0;
	private final static int ENGLISHMAN = 0;
	private final static int SPANIARD = 1;
	private final static int NORWEGIAN = 2;
	private final static int UKRANIAN = 3;
	private final static int JAPANESE = 4;	
	
	private final static int COLOR = 1;
	private final static int RED = 0;
	private final static int GREEN = 1;
	private final static int IVORY = 2;
	private final static int YELLOW = 3;
	private final static int BLUE = 4;
	
	private final static int CHOCOLATE = 2;
	private final static int HERSHEYS = 0;
	private final static int KITKAT = 1;
	private final static int SMARTIES = 2;
	private final static int SNICKERS = 3;
	private final static int MILKYWAY = 4;
	
	private final static int DRINK = 3;
	private final static int ORANGEJUICE = 0;
	private final static int TEA = 1;
	private final static int COFFEE = 2;
	private final static int MILK = 3;
	private final static int WATER = 4;
	
	private final static int ANIMAL = 4;
	private final static int DOG = 0;
	private final static int FOX = 1;
	private final static int SNAIL = 2;
	private final static int HORSE = 3;
	private final static int ZEBRA = 4;
	
	private static int numberOfStatesChecked = 0;
	
	public static void main(String[] args) {
		int[][] solution = new int[5][5];
		// 1-5 Nationality, 6-10 Color, 11-15 Chocolates, 16-20 Drinks, 21-25 Animals
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				solution[i][j] = -1;
			}			
		}
		if (findSolution(solution, 0)) {
			printSolution(solution);
		} else {
			System.out.println("No solution");
		}
		System.out.println("States checked = " + numberOfStatesChecked);
	}

	private static boolean findSolution(int[][] solution, int variableNumber) {
		if (variableNumber >= NUMBER_OF_VARIABLES) {
			return true;
		}
		for (int houseNumber = 0; houseNumber < NUMBER_OF_DOMAINS; houseNumber++) {
			solution[variableNumber/5][variableNumber%5] = houseNumber;
//			if (solution[1][0] == 2 && solution[1][1] == 4 && solution[1][2] == 3) {
//				System.out.println();
//			}
			if (consistencyCheck(solution)) {
				if(findSolution(solution, variableNumber+1)) {
					return true;
				}
			} 
			solution[variableNumber/5][variableNumber%5] = -1;
		}
		return false;
		
	}

	private static void printSolution(int[][] solution) {
		HashMap<Integer, String> map = getVariableNames();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.println(map.get(i*5 + j) + " - House Number " + solution[i][j]);
				// System.out.print(solution[i][j] + " ");
			}
		}
		System.out.println();
	}

	// 1-5 Nationality, 6-10 Color, 11-15 Chocolates, 16-20 Drinks, 21-25 Animals
	// Englishman, Spaniard, Norwegian, Ukranian, Japanese
	// Red, Green, Ivory, Yellow, Blue
	// Hershey, KitKat, Smarties, Snickers, MilkyWay
	// Orange, Tea, Coffee, Milk, Water
	// Dog, Fox, Snails, Horse, Zebra
	private static boolean consistencyCheck(int[][] solution) {
		numberOfStatesChecked++;
		
		if (duplicate(solution[NATIONALITY]) || duplicate(solution[COLOR]) || duplicate(solution[CHOCOLATE]) 
				|| duplicate(solution[DRINK]) || duplicate(solution[ANIMAL])) {
			// System.out.println("Duplicate violation");
			return false;
		}
		
		// The Englishman lives in the red house
		if (solution[NATIONALITY][ENGLISHMAN] != -1 && solution[COLOR][RED] != -1 
				&& solution[NATIONALITY][ENGLISHMAN] != solution[COLOR][RED]) {
			// System.out.println("The Englishman lives in the red house");
			return false;
		}
		
		// The Spaniard owns the dog
		if (solution[NATIONALITY][SPANIARD] != -1 && solution[ANIMAL][DOG] != -1 
				&& solution[NATIONALITY][SPANIARD] != solution[ANIMAL][DOG]) {
			// System.out.println("The Spaniard owns the dog");
			return false;
		}
		
		// The Norwegian lives in the first house on the left
		if (solution[NATIONALITY][NORWEGIAN] != -1 && solution[NATIONALITY][NORWEGIAN] != 0) {
			// System.out.println("The Norwegian lives in the first house on the left");
			return false;
		}
		
		// The green house is immediately to the right of the ivory house
		if (solution[COLOR][GREEN] != -1 && solution[COLOR][IVORY] != -1 
				&& solution[COLOR][GREEN] - solution[COLOR][IVORY] != 1) {
			// System.out.println("The green house is immediately to the right of the ivory house");
			return false;
		}
		
		// The man who eats Hershey bars lives in the house next to the man with the fox
		if (solution[CHOCOLATE][HERSHEYS] != -1 && solution[ANIMAL][FOX] != -1 
				&& Math.abs(solution[CHOCOLATE][HERSHEYS] - solution[ANIMAL][FOX]) != 1) {
			// System.out.println("The man who eats Hershey bars lives in the house next to the man with the fox");
			return false;
		}
		
		// Kits Kats are eaten in the yellow house
		if (solution[CHOCOLATE][KITKAT] != -1 && solution[COLOR][YELLOW] != -1 
				&& solution[CHOCOLATE][KITKAT] != solution[COLOR][YELLOW]) {
			// System.out.println("Kits Kats are eaten in the yellow house");
			return false;
		}
		
		// The Norwegian lives next to the blue house
		if (solution[NATIONALITY][NORWEGIAN] != -1 && solution[COLOR][BLUE] != -1 
				&& Math.abs(solution[NATIONALITY][NORWEGIAN] - solution[COLOR][BLUE]) != 1) {
			// System.out.println("The Norwegian lives next to the blue house");
			return false;
		}
		
		// The Smarties eater owns snails
		if (solution[CHOCOLATE][SMARTIES] != -1 && solution[ANIMAL][SNAIL] != -1 
				&& solution[CHOCOLATE][SMARTIES] != solution[ANIMAL][SNAIL]) {
			// System.out.println("The Smarties eater owns snails");
			return false;
		}
		
		// The Snickers eater drinks orange juice
		if (solution[CHOCOLATE][SNICKERS] != -1 && solution[DRINK][ORANGEJUICE] != -1 
				&& solution[CHOCOLATE][SNICKERS] != solution[DRINK][ORANGEJUICE]) {
			// System.out.println("The Snickers eater drinks orange juice");
			return false;
		}
		
		// The Ukranian drinks tea
		if (solution[NATIONALITY][UKRANIAN] != -1 && solution[DRINK][TEA] != -1 
				&& solution[NATIONALITY][UKRANIAN] != solution[DRINK][TEA]) {
			// System.out.println("The Ukranian drinks tea");
			return false;
		}
		
		// The Japanese person eats Milky Ways
		if (solution[NATIONALITY][JAPANESE] != -1 && solution[CHOCOLATE][MILKYWAY] != -1 
				&& solution[NATIONALITY][JAPANESE] != solution[CHOCOLATE][MILKYWAY]) {
			// System.out.println("The Japanese person eats Milky Ways");
			return false;
		}
		
		// Kit Kats are eaten in a house next to the house where the horse is kept
		if (solution[CHOCOLATE][KITKAT] != -1 && solution[ANIMAL][HORSE] != -1 
				&& Math.abs(solution[CHOCOLATE][KITKAT] - solution[ANIMAL][HORSE]) != 1) {
			// System.out.println("Kit Kats are eaten in a house next to the house where the horse is kept");
			return false;
		}
		
		// Coffee is drunk in the green house
		if (solution[DRINK][COFFEE] != -1 && solution[COLOR][GREEN] != -1 
				&& solution[DRINK][COFFEE] != solution[COLOR][GREEN]) {
			// System.out.println("Coffee is drunk in the green house");
			return false;
		}
		
		// Milk is drunk in the middle house
		if (solution[DRINK][MILK] != -1 && solution[DRINK][MILK] != 2) {
			// System.out.println("Milk is drunk in the middle house");
			return false;
		}		
		
		return true;
	}

	private static boolean duplicate(int[] array) {
		for (int i = 0; i < NUMBER_OF_DOMAINS; i++) {
			for (int j = i+1; j < NUMBER_OF_DOMAINS; j++) {
				if (array[i] != -1 && array[j] != -1 && array[i] == array[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static HashMap<Integer, String> getVariableNames() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(NATIONALITY*5 + ENGLISHMAN, "ENGLISHMAN");
		map.put(NATIONALITY*5 + SPANIARD, "SPANIARD");
		map.put(NATIONALITY*5 + NORWEGIAN, "NORWEGIAN");
		map.put(NATIONALITY*5 + UKRANIAN, "UKRANIAN");
		map.put(NATIONALITY*5 + JAPANESE, "JAPANESE");
		map.put(COLOR*5 + RED, "RED");
		map.put(COLOR*5 + GREEN, "GREEN");
		map.put(COLOR*5 + IVORY, "IVORY");
		map.put(COLOR*5 + YELLOW, "YELLOW");
		map.put(COLOR*5 + BLUE, "BLUE");
		map.put(CHOCOLATE*5 + HERSHEYS, "HERSHEYS");
		map.put(CHOCOLATE*5 + KITKAT, "KITKAT");
		map.put(CHOCOLATE*5 + SMARTIES, "SMARTIES");
		map.put(CHOCOLATE*5 + SNICKERS, "SNICKERS");
		map.put(CHOCOLATE*5 + MILKYWAY, "MILKYWAY");
		map.put(DRINK*5 + ORANGEJUICE, "ORANGEJUICE");
		map.put(DRINK*5 + TEA, "TEA");
		map.put(DRINK*5 + COFFEE, "COFFEE");
		map.put(DRINK*5 + MILK, "MILK");
		map.put(DRINK*5 + WATER, "WATER");
		map.put(ANIMAL*5 + DOG, "DOG");
		map.put(ANIMAL*5 + FOX, "FOX");
		map.put(ANIMAL*5 + SNAIL, "SNAIL");
		map.put(ANIMAL*5 + HORSE, "HORSE");
		map.put(ANIMAL*5 + ZEBRA, "ZEBRA");				
		return map;
	}
}
