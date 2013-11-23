package jp.namelist.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static <T> List<T> mapToList(Map<String, T> map,
			String... excludeKey) {
		Map<String, T> tmpMap = new LinkedHashMap<String, T>(map);
		for (String exclude : excludeKey) {
			tmpMap.remove(exclude);
		}
		List<T> list = new ArrayList<>();
		for (Map.Entry<String, T> entry : tmpMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

}
