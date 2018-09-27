package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharCharDoubleMap {
	private HashMap<Character, CharDoubleMap> map;

	public CharCharDoubleMap(CharDoubleMap initialisingMap) {
		map = new HashMap<>();
		for (char c = 'a'; c <= 'z'; c++) {
			map.put(c,  initialisingMap.clone());
		}
		map.put(' ',  initialisingMap.clone());
		map.put('.',  initialisingMap.clone());
	}

	public CharDoubleMap get(Character character) {
		if (character >= 'A' && character <= 'Z')
			return map.get(Character.toLowerCase(character));
		if ((character == ' ') || (character >= 'a' && character <= 'z'))
			return map.get(character);
		return map.get('.');
	}

	public Set<Map.Entry<Character, CharDoubleMap>> entrySet() {
		return map.entrySet();
	}
}
