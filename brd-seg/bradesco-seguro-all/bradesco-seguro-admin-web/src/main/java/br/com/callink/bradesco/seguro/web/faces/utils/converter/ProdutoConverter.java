package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.entity.Produto;


@FacesConverter(value = "ProdutoConverter")
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		Produto produto = null;
		
		try {
			String[] values = value.split(":");
			produto = new Produto(new BigInteger(values[0]), values[1]);
		} catch (Exception e) {
			return null;
		}
		
		return produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null && value instanceof Produto) {
			Produto produto = (Produto) value;
			return produto.getId() != null ? produto.getId() + ":"
					+ produto.getDescricao() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}