import util.CharDoubleMap;
import util.CharIntMap;
import util.CharMapMap;

import java.util.*;
import java.util.regex.Pattern;

/**

 Created by Kirill on 13.09.2018.
 */
class EntropyCounter {
	private Map<Character, Double> simpleEntropies;
	private double simpleEntropy;
	private double pairEntropy;

	private CharIntMap charCounts;
	private CharDoubleMap charProbs;
	private CharMapMap pairCounts;
	private CharMapMap pairProbs;

	public static String punctRegex = "[^ A-Za-z]";

	EntropyCounter(String file) {
		init();
		makeCounts(file);
		makeProbs(file.length());
		calcSimpleEntropies();
		simpleEntropy = calcSimpleEntropy(file);
		pairEntropy = calcPairEntropy(file);
	}

	private void init() {
		charCounts = new CharIntMap();
		charProbs = new CharDoubleMap();
		simpleEntropies = new HashMap<>();

		pairCounts = new CharMapMap(charCounts);
		pairProbs = new CharMapMap(charProbs);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void makeCounts(String file) {
		char previousC = '§';
		for (char currentC : file.toCharArray()) {
			if ((currentC >= 'a' && currentC <= 'z') || (currentC >= 'A' && currentC <= 'Z')) {
				Integer currentCCount = charCounts.get(Character.toLowerCase(currentC));
				currentCCount++;
				if (previousC != '§') {
					if ((previousC >= 'a' && previousC <= 'z') || (previousC >= 'A' && previousC <= 'Z')) {
						Integer pairCount = pairCounts.get(Character.toLowerCase(currentC))
								.get(Character.toLowerCase(previousC));
						pairCount++;
					}
					if (previousC == ' ') {
						Integer pairCount = pairCounts.get(Character.toLowerCase(currentC)).get(' ');
						pairCount++;
					}
					if (Pattern.matches(punctRegex, currentC + "")) {
						Integer pairCount = pairCounts.get(Character.toLowerCase(currentC)).get('.');
						pairCount++;
					}
				}
			}
			if (currentC == ' ') {
				charCounts.put(' ', charCounts.get(' ') + 1);
				if (previousC != '§') {
					if ((previousC >= 'a' && previousC <= 'z') || (previousC >= 'A' && previousC <= 'Z')) {
						Integer pairCount = pairCounts.get(' ').get(Character.toLowerCase(previousC));
						pairCount++;
					}
					if (previousC == ' ') {
						Integer pairCount = pairCounts.get(' ').get(' ');
						pairCount++;
					}
					if (Pattern.matches(punctRegex, currentC + "")) {
						Integer pairCount = pairCounts.get(' ').get('.');
						pairCount++;
					}
				}
			}
			if (Pattern.matches(punctRegex, currentC + "")) {
				charCounts.put('.', charCounts.get('.') + 1);
				if (previousC != '§') {
					if ((previousC >= 'a' && previousC <= 'z') || (previousC >= 'A' && previousC <= 'Z')) {
						Integer pairCount = pairCounts.get('.').get(Character.toLowerCase(previousC));
						pairCount++;
					}
					if (previousC == ' ') {
						Integer pairCount = pairCounts.get('.').get(' ');
						pairCount++;
					}
					if (Pattern.matches(punctRegex, currentC + "")) {
						Integer pairCount = pairCounts.get('.').get('.');
						pairCount++;
					}
				}
			}
			previousC = currentC;
		}
	}

	private void makeProbs(int fileSize) {
		for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
			charProbs.put(entry.getKey(), entry.getValue() / (double) fileSize);
		}
		for (Map.Entry<Character, Map<Character, Integer>> entryCurr : pairCounts.entrySet()) {
			for (Map.Entry<Character, Integer> entryPrev : entryCurr.getValue().entrySet()) {
				pairProbs.get(entryCurr.getKey()).put(entryPrev.getKey(),
						entryPrev.getValue() / ((double) fileSize - 1));
			}
		}
	}

	private void calcSimpleEntropies() {
		for (Map.Entry<Character, Double> prob : charProbs.entrySet()) {
			simpleEntropies.put(prob.getKey(), - Math.log(prob.getValue()) / Math.log(2));
		}
	}

	private double calcSimpleEntropy(String file) {
		double entropy = 0;
		for (int i = 0; i < file.length(); i++) {
			if ((file.charAt(i) >= 'a' && file.charAt(i) <= 'z') ||
					Pattern.matches(punctRegex, file.charAt(i) + "")) {
				System.out.println(file.charAt(i));
				double prob = charProbs.get(file.charAt(i));
				entropy -= prob * Math.log(prob) / Math.log(2.0);
			}
		}
		return entropy;
	}

	private double calcPairEntropy(String file) { // todo: make this
		double entropy = 0;
		char previousC = '§';
		for (int i = 0; i < file.length(); i++) {
			if (previousC != '§') {
				double probCurr = charProbs.get(file.charAt(i));
				double probPair = pairProbs.get(file.charAt(i)).get(previousC);
				entropy -= probPair * probCurr * Math.log(probPair) / Math.log(2.0);
			}
			previousC = file.charAt(i);
		}
		return entropy;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Map<Character, Double> getSimpleEntropies() {
		return simpleEntropies;
	}

	double getSimpleEntropy() {
		return simpleEntropy;
	}

	double getPairEntropy() {
		return pairEntropy;
	}
}
