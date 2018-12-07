package br.com.callink.bradesco.seguro.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author michael
 * 
 */
public class ObjectUtils {
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(obj);
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
		return (T) in.readObject();
	}
	
	public static Set<Short> splitShortHashSet(String value, String delimiter){
		if(!StringUtils.isEmpty(value)){
			String[] split = value.split(delimiter);
			Set<Short> set = new HashSet<Short>();
			for(String s : split){
				set.add(Short.valueOf(s.trim()));
			}
			return set;
		}
		
		return null;
	}
	
	public static String asCommaSeparated(Set<?> s){
		return CollectionUtils.isEmpty(s) ? null : s.toString().replaceAll("[\\[\\]]", "");
	}
}
