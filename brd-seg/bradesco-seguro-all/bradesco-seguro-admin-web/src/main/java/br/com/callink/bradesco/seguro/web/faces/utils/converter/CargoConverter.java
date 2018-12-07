package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.Cargo;

@FacesConverter(value = "CargoConverter")
public class CargoConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		Cargo cargo= null;
		
		try {
			String[] values = value.split("-");
			cargo = new Cargo(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return cargo;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof Cargo) {
			Cargo cargo = (Cargo) value;
			return cargo.getId() != null ? cargo.getId() + "-"
					+ cargo.getNomeCargo() : null;
					
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}
		
		return null;
	}

}
