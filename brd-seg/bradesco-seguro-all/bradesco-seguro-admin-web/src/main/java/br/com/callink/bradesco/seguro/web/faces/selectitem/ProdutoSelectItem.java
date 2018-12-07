package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Produto;


public class ProdutoSelectItem extends AbstractSelectItem<Produto> {


	private static final long serialVersionUID = -8031198607139917512L;

	protected SelectItem getSelectItemByObject(Produto produto) {
		return new SelectItem(produto, produto.getNome());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Produto> collection) {
		List<Produto> lista = new ArrayList<Produto>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}