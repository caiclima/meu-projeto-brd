package br.com.callink.bradesco.seguro.commons.utils;

import java.util.Map;

/**
 * 
 * @author michael
 * 
 */
public final class MapUtils {
	public static boolean isEmpty(Map<?, ?> m) {
		return m == null || m.size() == 0;
	}
}
