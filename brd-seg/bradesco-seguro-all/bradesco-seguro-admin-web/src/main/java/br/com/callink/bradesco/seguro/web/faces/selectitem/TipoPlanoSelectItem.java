package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.TipoPlano;


public class TipoPlanoSelectItem extends AbstractSelectItem<TipoPlano> {

	private static final long serialVersionUID = -276996507624619891L;

	protected SelectItem getSelectItemByObject(TipoPlano tipoPlano) {
		return new SelectItem(tipoPlano, tipoPlano.getNomeTipoPlano());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<TipoPlano> collection) {
		List<TipoPlano> lista = new ArrayList<TipoPlano>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}