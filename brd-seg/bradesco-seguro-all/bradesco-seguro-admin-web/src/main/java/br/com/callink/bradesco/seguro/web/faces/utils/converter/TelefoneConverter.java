package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

public class TelefoneConverter extends AbstractStringConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.isEmpty(value) && (value.length() == 10 || value.length() == 11)) {
			return String.format("(%d) %d", value.substring(0, 2),value.substring(2));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String valueString = value.toString();
		if (!StringUtils.isEmpty(valueString) && (valueString.length() == 10 || valueString.length() == 11)) {
			return String.format("(%d) %d", valueString.substring(0, 2),valueString.substring(2));
		}
		return null;
	}
	
}
