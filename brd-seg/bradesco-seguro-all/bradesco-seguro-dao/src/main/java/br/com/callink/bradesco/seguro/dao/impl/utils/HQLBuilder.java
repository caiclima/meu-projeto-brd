package br.com.callink.bradesco.seguro.dao.impl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitários para construção de HQL
 * 
 * @author michael
 * 
 */
public final class HQLBuilder {
	private final StringBuilder hql = new StringBuilder();
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<String, Object> parameters = new HashMap<>();

	public HQLBuilder add(String hqlFragment) {
		hql.append(hqlFragment);
		return this;
	}

	public HQLBuilder setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
		return this;
	}

	public HQLBuilder setParameter(String name, Object value) {
		if(value != null) {
			this.parameters.put(name, value);
		}
		return this;
	}

	public String build() {
		String output = hql.toString();
		for (String key : parameters.keySet()) {
			output = output.replaceAll(key, getValueAsString(this.parameters.get(key)));
		}
		return output;
	}

	private String getValueAsString(Object o) {
		if (o instanceof Date) {
			return df.format(o);
		}
		if (o instanceof Calendar) {
			return df.format(((Calendar) o).getTime());
		}
		
		return o.toString();
	}
	
	@Override
	public String toString(){
		return hql.toString();
	}
}
