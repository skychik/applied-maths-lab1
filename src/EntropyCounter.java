import util.CharCharDoubleMap;
import util.CharCharIntMap;
import util.CharDoubleMap;
import util.CharIntMap;

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
	private CharCharIntMap pairCounts;
	private CharCharDoubleMap pairProbs;

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

		pairCounts = new CharCharIntMap(charCounts);
		pairProbs = new CharCharDoubleMap(charProbs);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void makeCounts(String file) {
		char previousC = '§';
		for (char currentC : file.toCharArray()) {
			charCounts.put(currentC, charCounts.get(currentC) + 1);
			if (previousC != '§') {
				pairCounts.get(currentC).put(previousC, pairCounts.get(currentC).get(previousC) + 1);
			}
			previousC = currentC;
		}
	}

	private void makeProbs(int fileSize) {
		for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
			charProbs.put(entry.getKey(), entry.getValue() / (double) fileSize);
		}
		for (Map.Entry<Character, CharIntMap> entryCurr : pairCounts.entrySet()) {
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
			//System.out.println(file.charAt(i));
			double prob = charProbs.get(file.charAt(i));
			entropy -= prob * Math.log(prob) / Math.log(2.0);
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
				if (probPair != 0.0) {
					entropy -= probPair * probCurr * Math.log(probPair) / Math.log(2.0);
				}
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

	public CharDoubleMap getCharProbs() {
		return charProbs;
	}
}
