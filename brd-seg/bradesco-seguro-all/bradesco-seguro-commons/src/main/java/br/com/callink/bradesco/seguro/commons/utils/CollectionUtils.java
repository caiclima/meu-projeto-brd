package br.com.callink.bradesco.seguro.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Classe utilitária para coleções
 * 
 * @author michael
 * 
 */
public final class CollectionUtils {
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}
	
	/**
	 * Retorna um {@link Set} contendo a relação de itens duplicados na {@link Collection} informada.
	 * 
	 * @param c
	 * @return
	 */
	public static <T> Set<T> duplicatedOnly(Collection<T> c){
		List<T> byCopy = new ArrayList<T>( c ); //by copy
		Set<T> out = new HashSet<T>();
		for (Iterator<T> iterator = byCopy.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			if(Collections.frequency(c, t) > 1){
				out.add(t);
			}
		}
		return out;
	}
}
