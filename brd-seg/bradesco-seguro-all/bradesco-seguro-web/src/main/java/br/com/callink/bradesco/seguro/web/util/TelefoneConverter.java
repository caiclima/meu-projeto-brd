package br.com.callink.bradesco.seguro.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

@FacesConverter(value = "TelefoneConverter")
public class TelefoneConverter extends AbstractStringConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.isEmpty(value) && (value.length() == 10 || value.length() == 11 || value.length() == 12)) {
			if (value.length() == 12) {
				return String.format("(%s) %s", value.substring(0, 3),value.substring(2));
			} else {
				return String.format("(%s) %s", value.substring(0, 2),value.substring(2));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String valueString = value.toString();
		if (!StringUtils.isEmpty(valueString) && (valueString.length() == 10 || valueString.length() == 11 || valueString.length() == 12)) {
			if (valueString.length() == 12) {
				return String.format("(%s) %s", valueString.substring(0, 3),valueString.substring(2));
			} else {
				return String.format("(%s) %s", valueString.substring(0, 2),valueString.substring(2));
			}
		}
		return null;
	}
	
}
