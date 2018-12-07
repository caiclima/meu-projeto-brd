package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.TipoPlano;


@FacesConverter(value = "TipoPlanoConverter")
public class TipoPlanoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		TipoPlano tipoPlano = null;
		
		try {
			String[] values = value.split(":");
			tipoPlano = new TipoPlano(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return tipoPlano;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof TipoPlano) {
			TipoPlano tipoPlano = (TipoPlano) value;
			return tipoPlano.getId() != null ? tipoPlano.getId() + ":"
					+ tipoPlano.getNomeTipoPlano() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}