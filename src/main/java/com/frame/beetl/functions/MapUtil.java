package com.frame.beetl.functions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * beetle hashmap工具
 * @author don
 * @mail gogo123150@qq.com
 * @date 2014-12-30
 */
public class MapUtil {

	public void put(Object map, Object key, Object value) {
		if (map instanceof Map) {
			Map m = (Map) map;
			m.put(key, value);
		}
	}

	public void putAll(Object map, Object newmap) {
		if (map instanceof Map) {
			Map m = (Map) map;
			if (newmap instanceof Map) {
				m.putAll((Map) newmap);
			}
		}
	}

	public void remove(Object map, Object key) {
		if (map instanceof Map) {
			Map m = (Map) map;
			m.remove(key);
		}
	}

	public boolean containsKey(Object map, Object key) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.containsKey(key);
		}
		return false;
	}

	public boolean containsValue(Object map, Object value) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.containsValue(value);
		}
		return false;
	}

	public void clear(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			m.clear();
		}
	}

	public Object get(Object map, Object key) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.get(key);
		}
		return null;
	}

	public boolean isEmpty(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.isEmpty();
		}
		return true;
	}

	public Set entrySet(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.entrySet();
		}
		return null;
	}

	public Set keySet(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.keySet();
		}
		return null;
	}

	public Collection values(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.values();
		}
		return null;
	}

	public int size(Object map) {
		if (map instanceof Map) {
			Map m = (Map) map;
			return m.size();
		}
		return 0;
	}
}
