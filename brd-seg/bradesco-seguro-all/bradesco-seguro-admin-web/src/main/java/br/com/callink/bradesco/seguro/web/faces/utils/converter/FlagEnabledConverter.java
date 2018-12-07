package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.enums.SimNao;

@FacesConverter(value = "FlagEnabledConverter")
public class FlagEnabledConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (!StringUtils.isEmpty(value)) {
			
			if (value.equals(SimNao.SIM.toString()) || value.equals(Boolean.TRUE.toString())) {
				return SimNao.SIM.toString();
			} else if (value.equals(SimNao.NAO.toString()) || value.equals(Boolean.FALSE.toString())) {
				return SimNao.NAO.toString();
			}
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value != null) {
			if (value.toString().equals(SimNao.SIM.toString()) || value.equals(Boolean.TRUE.toString())) {
				return SimNao.SIM.toString();
			} else if (value.toString().equals(SimNao.NAO.toString()) || value.equals(Boolean.FALSE.toString())) {
				return SimNao.NAO.toString();
			}
		}
		
		return "";
	}

	 
	
}
