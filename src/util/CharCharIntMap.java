package util;

import java.util.HashMap;
import java.util.Map;

public class CharCharIntMap {
	private Map<Character, CharIntMap> map;

	public CharCharIntMap() {
		CharIntMap emptyMap = new CharIntMap();
		for (char c = 'a'; c <= 'z'; c++) {
			emptyMap.put(c, 0);
		}
		emptyMap.put(' ', 0);
		emptyMap.put('.', 0);

		map = new HashMap<>();
		for (char c = 'a'; c <= 'z'; c++) {
			map.put(c, (Map) emptyMap.clone());
		}
		map.put(' ', (Map) emptyMap.clone());
		map.put('.', (Map) emptyMap.clone());
	}

	public Map get(Character character) {
		if (character >= 'A' && character <= 'Z')
			return map.get(Character.toLowerCase(character));
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			return map.get(character);
		return map.get('.');
	}
}
