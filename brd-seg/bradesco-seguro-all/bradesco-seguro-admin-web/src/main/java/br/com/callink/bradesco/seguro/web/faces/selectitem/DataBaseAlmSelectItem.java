package br.com.callink.bradesco.seguro.web.faces.selectitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.entity.DataBaseAlm;


public class DataBaseAlmSelectItem extends AbstractSelectItem<DataBaseAlm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -276996507624619891L;

	protected SelectItem getSelectItemByObject(DataBaseAlm dataBaseAlm) {
		return new SelectItem(dataBaseAlm, dataBaseAlm.getNomeDataBaseAlm());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setListAndOrder(Collection<DataBaseAlm> collection) {
		List<DataBaseAlm> lista = new ArrayList<DataBaseAlm>(collection);
		Collections.sort (lista); 
		super.setList(lista);
		
	}

}