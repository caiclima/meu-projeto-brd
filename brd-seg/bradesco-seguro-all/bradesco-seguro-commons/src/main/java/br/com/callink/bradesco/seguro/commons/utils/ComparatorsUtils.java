package br.com.callink.bradesco.seguro.commons.utils;

import java.util.Comparator;

public class ComparatorsUtils {
	
	public static final Comparator<String> COMPARATOR_STRING = new Comparator<String>(){ 
		@Override
		public int compare(String obj1, String obj2) {
			return obj1.compareTo(obj2);
		}
	};

}
