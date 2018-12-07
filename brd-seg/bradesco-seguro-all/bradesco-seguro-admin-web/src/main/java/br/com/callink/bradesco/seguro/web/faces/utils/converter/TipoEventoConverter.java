package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.TipoEvento;


@FacesConverter(value = "TipoEventoConverter")
public class TipoEventoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		TipoEvento tipoEvento = null;
		
		try {
			String[] values = value.split(":");
			tipoEvento = new TipoEvento(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return tipoEvento;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof TipoEvento) {
			TipoEvento tipoEvento = (TipoEvento) value;
			return tipoEvento.getId() != null ? tipoEvento.getId() + ":"
					+ tipoEvento.getNomeTipoEvento() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}