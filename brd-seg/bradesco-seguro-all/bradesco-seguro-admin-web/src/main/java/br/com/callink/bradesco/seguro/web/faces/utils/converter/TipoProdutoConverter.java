package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;


@FacesConverter(value = "ValidacaoVendaProdutoConverter")
public class TipoProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		String tipoPlano = null;
		
		try {
			String[] values = value.split(":");
			if(ValidacaoVendaProduto.VALIDACAO_1.getNome().equals(values[1])) {
				tipoPlano = ValidacaoVendaProduto.VALIDACAO_1.getNome();
			} else if (ValidacaoVendaProduto.VALIDACAO_2.getNome().equals(values[1])){
				tipoPlano = ValidacaoVendaProduto.VALIDACAO_2.getNome();
			}
		} catch (Exception e) {
			return null;
		}
		
		return tipoPlano;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value != null && value instanceof ValidacaoVendaProduto) {
			ValidacaoVendaProduto tipoPlano = (ValidacaoVendaProduto) value;
			return tipoPlano.getCodigo() != null ? tipoPlano.getCodigo() + ":"
					+ tipoPlano.getNome() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}
}