package br.com.callink.bradesco.seguro.web.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.EstadoCivil;

public class EstadoCivilSelectItem extends AbstractSelectItem<EstadoCivil> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5127402544536824399L;

	protected SelectItem getSelectItemByObject(EstadoCivil estadoCivil) {
		return new SelectItem(estadoCivil, estadoCivil.getDescricao());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<EstadoCivil> collection) {
		List<EstadoCivil> lista = new ArrayList<EstadoCivil>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}