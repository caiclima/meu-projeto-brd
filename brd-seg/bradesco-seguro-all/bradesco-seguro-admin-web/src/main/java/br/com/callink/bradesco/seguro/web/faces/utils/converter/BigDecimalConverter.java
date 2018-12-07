package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "BigDecimalConverter", forClass = BigDecimal.class)
public class BigDecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			DecimalFormat df = new DecimalFormat();
			df.setParseBigDecimal(true);
			return (BigDecimal) df.parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof BigDecimal) {
			BigDecimal big = (BigDecimal) value;
			return big != null ? big.toString() : null ;
		}

		return null;
	}
}