package br.com.callink.bradesco.seguro.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.enums.Sexo;


@FacesConverter(value = "SexoConverter")
public class SexoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		Sexo sexo = null;
		try {
			sexo = Sexo.getInstance(value);
			
			if(sexo != null){
				return sexo.name();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		Sexo sexo = Sexo.getInstance(value);
		
		if(sexo != null){
			return sexo.name();
		}
		return null;
	}
}