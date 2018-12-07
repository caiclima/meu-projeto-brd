package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.DataBaseAlm;


@FacesConverter(value = "DataBaseAlmConverter")
public class DataBaseAlmConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		DataBaseAlm dataBaseAlm = null;
		
		try {
			String[] values = value.split(":");
			dataBaseAlm = new DataBaseAlm();
			dataBaseAlm.setId(new BigInteger(values[0]));
			dataBaseAlm.setNomeDataBaseAlm(values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return dataBaseAlm;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof DataBaseAlm) {
			DataBaseAlm dataBaseAlm = (DataBaseAlm) value;
			return dataBaseAlm.getId() != null ? dataBaseAlm.getId() + ":"
					+ dataBaseAlm.getNomeDataBaseAlm() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}