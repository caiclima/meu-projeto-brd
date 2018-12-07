package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

public class AbstractStringConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		} else {
			return string.replace("'", "").replace("\"", "").trim();
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object string) {
		if (string == null) {
			return null;
		} else {
			return string.toString().replace("'", "").replace("\"", "").trim();
		}
	}
}