package util;

import java.util.HashMap;
import java.util.Map;

public class CharMapMap {
	private Map<Character, Map> map;

	public CharMapMap(HashMap initialisingMap) {
		map = new HashMap<>();

		for (char c = 'a'; c <= 'z'; c++) {
			map.put(c, (Map) initialisingMap.clone());
		}
		map.put(' ', (Map) initialisingMap.clone());
		map.put('.', (Map) initialisingMap.clone());
	}

	public Map get(Character character) {
		if (character >= 'A' && character <= 'Z')
			return map.get(Character.toLowerCase(character));
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			return map.get(character);
		return map.get('.');
	}
}
