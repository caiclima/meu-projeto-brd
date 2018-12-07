package br.com.callink.bradesco.seguro.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

@FacesConverter(value = "CepConverter")
public class CepConverter extends AbstractStringConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.isEmpty(value)){
			
			// cep
			if (value.length() == 8) {
				return String.format("%s-%s", value.substring(0, 5),value.substring(5,8));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String valueString = value.toString();

		if (!StringUtils.isEmpty(valueString)){
			
			// cep
			if (valueString.length() == 8) {
				return String.format("%s-%s", valueString.substring(0, 5),valueString.substring(5,8));
			}
		}
		return null;
	}
	
}
