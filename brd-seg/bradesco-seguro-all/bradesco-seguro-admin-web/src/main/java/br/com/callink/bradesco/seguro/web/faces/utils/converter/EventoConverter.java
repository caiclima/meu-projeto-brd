package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.callink.bradesco.seguro.entity.Evento;


@FacesConverter(value = "br.com.callink.bradesco.seguro.web.faces.utils.converter.EventoConverter")
public class EventoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Evento evento = null;
		
		try {
			String[] values = value.split(":");
			evento = new Evento(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return evento;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof Evento) {
			Evento evento = (Evento) value;
			return evento.getId() != null ? evento.getId() + ":"
					+ evento.getNomeEvento() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}