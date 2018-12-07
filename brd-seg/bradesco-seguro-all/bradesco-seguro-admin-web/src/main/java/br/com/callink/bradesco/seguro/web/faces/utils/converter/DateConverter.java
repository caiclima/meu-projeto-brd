package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

/**
 * Converter de Data
 * 
 * @author michael
 * 
 */
@FacesConverter(value = "DateConverter")
public class DateConverter implements Converter {
	private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private final Logger logger = Logger.getLogger(DateConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return value == null ? null : df.parse(value);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value == null ? null : df.format(value);
	}

}
