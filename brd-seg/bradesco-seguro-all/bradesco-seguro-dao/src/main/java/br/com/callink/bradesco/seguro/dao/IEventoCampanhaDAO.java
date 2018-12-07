package br.com.callink.bradesco.seguro.dao;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;

public interface IEventoCampanhaDAO extends IGenericDAO<EventoCampanha> {

	/**
	 * 
	 * @param campanha
	 */
	public void deleteCampanhaEventoByCampanha(Campanha campanha);
	
	/**
	 * 
	 * @param eventoCampanha
	 */
	public void salvarEventoCampanha(EventoCampanha eventoCampanha);
}
