package br.com.callink.bradesco.seguro.web.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.Profissao;

public class ProfissaoSelectItem extends AbstractSelectItem<Profissao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658323715627227655L;

	protected SelectItem getSelectItemByObject(Profissao profissao) {
		return new SelectItem(profissao, profissao.getNome());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<Profissao> collection) {
		List<Profissao> lista = new ArrayList<Profissao>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}
	
	
}