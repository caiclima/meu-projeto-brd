package br.com.callink.bradesco.seguro.web.util;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "BigIntegerConverter", forClass = BigInteger.class)
public class BigIntegerConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		BigInteger bigInteger = null;
		try {
			bigInteger = new BigInteger(value);
		} catch (Exception e) {
			return null;
		}
		return bigInteger;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof BigInteger) {
			BigInteger bigInteger = (BigInteger) value;
			return bigInteger != null ? bigInteger.toString() : null ;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}