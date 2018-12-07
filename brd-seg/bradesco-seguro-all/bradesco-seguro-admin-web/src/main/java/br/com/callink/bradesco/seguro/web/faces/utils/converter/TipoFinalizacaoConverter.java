package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.callink.bradesco.seguro.enums.TipoFinalizacao;

@FacesConverter(value = "TipoFinalizacaoConverter")
public class TipoFinalizacaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		TipoFinalizacao tipoEvento = null;
		
		try {
			tipoEvento = TipoFinalizacao.getInstance(value);
			
			if(tipoEvento != null){
				return tipoEvento.name();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		TipoFinalizacao tipo = TipoFinalizacao.getInstance(value);
		
		if(tipo != null){
			return tipo.name();
		}

		return null;
	}
}
