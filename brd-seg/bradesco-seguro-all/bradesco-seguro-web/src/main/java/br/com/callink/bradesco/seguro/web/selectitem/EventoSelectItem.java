package br.com.callink.bradesco.seguro.web.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Evento;

public class EventoSelectItem extends AbstractSelectItem<Evento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4905709555104152402L;

	protected SelectItem getSelectItemByObject(Evento evento) {
		return new SelectItem(evento, evento.getNomeEvento());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Evento> collection) {
		List<Evento> lista = new ArrayList<Evento>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}