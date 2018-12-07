package br.com.callink.bradesco.seguro.web.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Plano;

public class PlanoSelectItem extends AbstractSelectItem<Plano> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3480370549638506026L;

	protected SelectItem getSelectItemByObject(Plano plano) {
		return new SelectItem(plano, plano.getNome() + " - " + plano.getTipoPlano().getNomeTipoPlano());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Plano> collection) {
		List<Plano> lista = null;
		if (collection != null) {
			lista = new ArrayList<Plano>(collection);
			Collections.sort (lista); 
		}
		
		super.setList(lista);
		
	}
	
	
}