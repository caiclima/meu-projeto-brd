package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "defaultDecimal")
public class DefaultDecimalConverter implements Converter {
	public static final Pattern strPattern = Pattern.compile("^(-?)(\\d{1,3}(?:\\.\\d{3})*),(\\d{2})$");
	public static final Pattern objPattern = Pattern.compile("^(-?)(\\d{1,3})((?:\\d{3})*)\\.(\\d{2})$");
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String param) {
		try {
			if (param == null || param.equals("")) {
				return null;
			}
			
			if (param.length() == 1 || param.length() == 2) {
				param = param + ",00";
			}
			
			Matcher matcher = DefaultDecimalConverter.strPattern.matcher(param);
			
			if (!matcher.matches()) {
				return null;
			}
			
			StringBuilder valueBuilder = new StringBuilder();
			valueBuilder.append(matcher.group(1));
			valueBuilder.append(matcher.group(2).replace(".", ""));
			valueBuilder.append(".");
			valueBuilder.append(matcher.group(3));
			
			BigDecimal value = new BigDecimal(valueBuilder.toString());
			value.setScale(2, RoundingMode.HALF_UP);
			
			return value;
		} catch (Throwable e) {
			throw new ConverterException(e);
		}
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object param) {
		try {
			if (param == null) {
				return null;
			}
			
			BigDecimal obj = (BigDecimal) param;
			obj = obj.setScale(2, RoundingMode.HALF_UP);
			
			Matcher matcher = DefaultDecimalConverter.objPattern.matcher(obj.toString());
			
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
			throw new ConverterException(e);
		}
		
		return null;
	}
}
