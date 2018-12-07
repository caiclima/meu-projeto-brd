package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.Campanha;


@FacesConverter(value = "CampanhaConverter")
public class CampanhaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Campanha campanha = null;
		
		try {
			String[] values = value.split(":");
			campanha = new Campanha(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return campanha;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof Campanha) {
			Campanha campanha = (Campanha) value;
			return campanha.getId() != null ? campanha.getId() + ":"
					+ campanha.getNomeCampanha() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}