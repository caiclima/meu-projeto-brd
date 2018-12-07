package br.com.callink.bradesco.seguro.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "TelefoneConverterTable")
public class TelefoneConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return (value != null) ? value.replaceAll("[()-]+", "") : null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof String) {
			//return TelefoneUtils.insertCharactersPhone((String) value);
			String phone = (String) value;
			if (phone.length() == 2) {
				return "(" + phone + ")";
			} else if (phone.length() == 8) {
				return phone.substring(0, 4) + "-" + phone.substring(4, 8);
			} else if (phone.length() == 9) {
				return phone.substring(0, 5) + "-" + phone.substring(5, 9);
			}
		}

		return null;
	}
}