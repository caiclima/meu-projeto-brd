package br.com.callink.bradesco.seguro.web.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Esporte;

public class EsporteSelectItem extends AbstractSelectItem<Esporte> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 709008436220687446L;

	protected SelectItem getSelectItemByObject(Esporte esporte) {
		return new SelectItem(esporte, esporte.getNome());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Esporte> collection) {
		List<Esporte> lista = new ArrayList<Esporte>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}