package util;

import java.util.HashMap;
import java.util.Map;

public class CharIntMap {
	private HashMap<Character, Integer> map;

	public CharIntMap() {
		map = new HashMap<>();

		for (char c = 'a'; c <= 'z'; c++) {
			map.put(c, 0);
		}
		map.put(' ', 0);
		map.put('.', 0);
	}

	public Integer get(Character character) {
		if (character >= 'A' && character <= 'Z')
			return map.get(Character.toLowerCase(character));
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			return map.get(character);
		return map.get('.');
	}

	public void put(Character character, Integer integer) {
		if (character >= 'A' && character <= 'Z')
			map.put(Character.toLowerCase(character), integer);
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			map.put(character, integer);
		map.put('.', integer);
	}

	public CharIntMap clone() {
		return (CharIntMap) map.clone();
	}
}
