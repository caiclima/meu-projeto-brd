package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;


public class TipoProdutoSelectItem extends AbstractSelectItem<ValidacaoVendaProduto> {


	private static final long serialVersionUID = -8031198607139917512L;

	protected SelectItem getSelectItemByObject(ValidacaoVendaProduto tipoProduto) {
		return new SelectItem(tipoProduto.getCodigo(), tipoProduto.getNome());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(ValidacaoVendaProduto[] array) {
		List<ValidacaoVendaProduto> lista = new ArrayList<ValidacaoVendaProduto>(Arrays.asList(array));
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
	
}