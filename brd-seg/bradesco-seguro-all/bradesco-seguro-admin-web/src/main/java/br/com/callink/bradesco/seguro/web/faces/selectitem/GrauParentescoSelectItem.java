package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.GrauParentesco;

public class GrauParentescoSelectItem extends AbstractSelectItem<GrauParentesco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SelectItem getSelectItemByObject(GrauParentesco grauParentesco) {
		return new SelectItem(grauParentesco, grauParentesco.getDescricao());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<GrauParentesco> collection) {
		List<GrauParentesco> lista = new ArrayList<GrauParentesco>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}