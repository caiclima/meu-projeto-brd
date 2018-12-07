package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Converter de Valor
 * 
 * @author bruno.camargo
 * 
 */
@FacesConverter(value = "ValorConverter")
public class ValorConverter implements Converter {
	private final DecimalFormat df = new DecimalFormat("#,##0.00");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.isEmpty(value)) {
			return Double.valueOf(value.replace(".", "").replace(",", "."));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && StringUtils.isEmpty(value.toString())) {
			return "0.00";
		} else {
			return value == null ? "0.00" : df.format(Double.valueOf(value.toString()));
		}
	}
}
