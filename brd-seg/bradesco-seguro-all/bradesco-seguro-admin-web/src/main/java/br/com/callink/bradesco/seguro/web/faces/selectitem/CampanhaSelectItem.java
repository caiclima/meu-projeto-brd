package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Campanha;


public class CampanhaSelectItem extends AbstractSelectItem<Campanha> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308929433095512281L;

	protected SelectItem getSelectItemByObject(Campanha campanha) {
		return new SelectItem(campanha, campanha.getNomeCampanha());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Campanha> collection) {
		List<Campanha> lista = new ArrayList<Campanha>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}