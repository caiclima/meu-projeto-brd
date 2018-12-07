package br.com.callink.bradesco.seguro.web.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;


@FacesConverter(value = "BigDecimalConverterUtil", forClass = BigDecimal.class)
public class BigDecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		BigDecimal bigDecimal = null;
		
		if (!StringUtils.isEmpty(value)) {
			value = value.replace(".", "").replace(",", ".");
		}
		try {
			bigDecimal = new BigDecimal(value);
		} catch (Exception e) {
			return null;
		}
		return bigDecimal;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof BigInteger) {
			BigDecimal bigDecimal = (BigDecimal) value;
			return bigDecimal != null ? bigDecimal.toString() : null ;
		} else if (value instanceof Double || value instanceof Float) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}