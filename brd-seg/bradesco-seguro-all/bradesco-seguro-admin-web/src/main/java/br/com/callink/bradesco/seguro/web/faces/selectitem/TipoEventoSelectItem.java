package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.TipoEvento;


public class TipoEventoSelectItem extends AbstractSelectItem<TipoEvento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -276996507624619891L;

	protected SelectItem getSelectItemByObject(TipoEvento tipoEvento) {
		return new SelectItem(tipoEvento, tipoEvento.getNomeTipoEvento());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<TipoEvento> collection) {
		List<TipoEvento> lista = new ArrayList<TipoEvento>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}