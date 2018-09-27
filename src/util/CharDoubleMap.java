package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharDoubleMap {
	private HashMap<Character, Double> map;

	public CharDoubleMap() {
		map = new HashMap<>();

		for (char c = 'a'; c <= 'z'; c++) {
			map.put(c, 0.0);
		}
		map.put(' ', 0.0);
		map.put('.', 0.0);
	}

	private CharDoubleMap(HashMap<Character, Double> map) {
		this.map = map;
	}

	public Double get(Character character) {
		if (character >= 'A' && character <= 'Z')
			return map.get(Character.toLowerCase(character));
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			return map.get(character);
		return map.get('.');
	}

	public void put(Character character, Double aDouble) {
		if (character >= 'A' && character <= 'Z')
			map.put(Character.toLowerCase(character), aDouble);
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			map.put(character, aDouble);
		map.put('.', aDouble);
	}

	public CharDoubleMap clone() {
		return new CharDoubleMap(new HashMap<>(map));
	}

	public Set<Map.Entry<Character,Double>> entrySet() {
		return map.entrySet();
	}
}
