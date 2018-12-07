package br.com.callink.bradesco.seguro.commons.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author michael
 * 
 */
public class NumberUtils {
	
	private static final Pattern strPattern = Pattern.compile("^(-?)(\\d{1,3}(?:\\.\\d{3})*),(\\d{2})$");
	private static final Pattern objPattern = Pattern.compile("^(-?)(\\d{1,3})((?:\\d{3})*)\\.(\\d{2})$");

	/**
	 * Indica se um valor 'number' é nullable, sendo: null ou 0.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullable(Number value) {
		return value == null || value.intValue() == 0 ? true : false;
	}
	
	public static boolean isGreaterThanZero(Integer value) {
		return value != null && value > 0;
	}
	
	public static Integer toInteger(String value){
		return StringUtils.isEmpty(value) ? null : Integer.valueOf(value);
	}
	
	public static Integer toInteger(Long value){
		return value == null ? null : value.intValue();
	}
	
	public static Long toLong(String value){
		return StringUtils.isEmpty(value) ? null : Long.valueOf(value);
	}
	
	public static Long toLong(Integer value){
		return value == null ? null : value.longValue();
	}
			
	
	public static Double toDouble(BigDecimal bd){
		if(bd != null){
			return bd.doubleValue();
		}
		return null;
	}
	
	/*
	 * Retorna o BigDecimal como double ou Zero (0.00) caso seja nulo
	 */
	public static Double toZeroDouble(BigDecimal bd){
		if(bd != null){
			return bd.doubleValue();
		}
		return 0.00;
	}
	
	public static Boolean toBitBoolean(Integer value){
		if(value != null){
			if(value.equals(1)){
				return true;
			}
			if(value.equals(0)){
				return false;
			}
		}
		return false;
	}
	
	public static Boolean toBitBoolean(String value){
		if(value != null){
			if(value.trim().equals("1")){
				return true;
			}
			if(value.trim().equals("0")){
				return false;
			}
		}
		return false;
	}
	
	public static Boolean toBoolean(Boolean b){
		return b == null ? false : b;
	}
	
	public static BigInteger toBigInteger(String value){
		return StringUtils.isEmpty(value)  ? null : BigInteger.valueOf( toLong(value) );
	}
	
	public static Integer toBitInteger(Boolean value){
		return value != null && value ? 1 : 0;
	}
	
	public static Long toBitLong(Boolean value){
		return value != null && value ? 1l : 0l;
	}
	
	public static BigDecimal toBigDecimal(String value){
		return StringUtils.isEmpty(value)  ? null : BigDecimal.valueOf( toDouble(value) );
	}
	
	public static BigDecimal toBigDecimalENLocale(String value){
		return StringUtils.isEmpty(value)  ? null : BigDecimal.valueOf( toDoubleENLocale(value) );
	}
	
	public static Double toDouble(String value){
		return StringUtils.isEmpty(value) ? null : Double.valueOf(value);
	}
	
	public static Double toDoubleENLocale(String value){
		try{
			return StringUtils.isEmpty(value) ? null : Double.valueOf(value.replaceAll("\\.", "").replaceAll(",", "."));
		}catch(Exception e){}
		
		return null;
	}
	
	public static String formatDecimalMask(Object param) throws Exception {
	
		try {
			if (param == null) {
				return null;
			}
			
			BigDecimal obj = (BigDecimal) param;
			obj = obj.setScale(2, RoundingMode.HALF_UP);
			
			Matcher matcher= objPattern.matcher(obj.toString());
			
			if (matcher.matches()) {
				StringBuilder valueBuilder = new StringBuilder();
				valueBuilder.append(matcher.group(1));
				valueBuilder.append(matcher.group(2));
				
				String substr = matcher.group(3);
				
				for (int i = 0; i < substr.length(); i += 3) {
					valueBuilder.append(".");
					valueBuilder.append(substr.substring(i, i + 3));
				}
				
				valueBuilder.append(",");
				valueBuilder.append(matcher.group(4));
				
				String value = valueBuilder.toString();
				
				return value;
			}
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return null;
	}
	
}
