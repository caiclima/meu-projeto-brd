package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

@FacesConverter(value = "SimNaoConverter")
public class SimNaoConverter  extends AbstractStringConverter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		} else if (value.toUpperCase().equals("SIM")) {
			return Boolean.TRUE;
		} else if (value.toUpperCase().equals("NAO")) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		/*if (string == null) {
			return null;
		} else {
			return string.toString().replace("'", "").replace("\"", "").trim();
		}*/
		
		
		if (value == null) {
			return null;
		} else if (value.equals(Boolean.TRUE)) {
			return "SIM";
		} else if (value.equals(Boolean.FALSE)) {
			return "NAO";
		} else {
			return null;
		}
	}
}