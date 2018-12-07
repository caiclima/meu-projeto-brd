package br.com.callink.bradesco.seguro.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.CpfUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

@FacesConverter(value = "CpfCnpjConverter")
public class CpfCnpjConverter extends AbstractStringConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.isEmpty(value)){
			
			// cpf
			if (value.length() == 14) {
				return CpfUtils.removeMask(value);
			} else {
				return value;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String valueString = value.toString();

		if (!StringUtils.isEmpty(valueString)){
			
			// cpf
			if (valueString.length() == 11) {
				return String.format("%s.%s.%s-%s", valueString.substring(0, 3),valueString.substring(3,6),valueString.substring(6,9),valueString.substring(9,11));
			}
			// cnpj
			else if (valueString.length() == 14) {
				return String.format("%s.%s.%s/%s-%s", valueString.substring(0, 2),valueString.substring(2,5),valueString.substring(5,8),valueString.substring(8,12),valueString.substring(12,14));
			}
		}
		return null;
	}
	
}
