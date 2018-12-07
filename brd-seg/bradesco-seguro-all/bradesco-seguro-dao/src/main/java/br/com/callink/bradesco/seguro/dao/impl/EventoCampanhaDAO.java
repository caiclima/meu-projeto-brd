package br.com.callink.bradesco.seguro.dao.impl;

import javax.persistence.Query;

import br.com.callink.bradesco.seguro.dao.IEventoCampanhaDAO;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;

/**
 * Data Access Object (DAO) de Evento Campanha
 * 
 * @author swb.thiagohenrique
 * 
 */
public class EventoCampanhaDAO extends GenericHibernateDAOImpl<EventoCampanha> implements IEventoCampanhaDAO {

	public void deleteCampanhaEventoByCampanha(Campanha campanha) {
		Query query = getEntityManager().createNamedQuery("EventoCampanha.deleteByIdCampanha");
		query.setParameter("idCampanha", campanha.getId());
		query.executeUpdate();
	}
	
	public void salvarEventoCampanha(EventoCampanha eventoCampanha) {
		saveOrUpdate(eventoCampanha);
	}
}
