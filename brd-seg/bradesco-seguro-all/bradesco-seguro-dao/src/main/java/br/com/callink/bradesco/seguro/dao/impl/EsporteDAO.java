package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IEsporteDAO;
import br.com.callink.bradesco.seguro.entity.Esporte;

/**
 * Data Access Object (DAO) de Esporte.
 * 
 * @author neppo.oldamar
 *
 */
public class EsporteDAO extends GenericHibernateDAOImpl<Esporte> implements IEsporteDAO {
	
	@Override
	public List<Esporte> findByExample(Esporte esporte) {
		
		esporte.setFlagRemoved(Boolean.FALSE);
		return super.findByExample(esporte);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Esporte> findByExample(Esporte esporte, String order) {
		Criteria cri = createCriteria();
		cri.add(Example.create(esporte));
		cri.addOrder(Order.asc(order));
		
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Esporte> findEnabledAndNotRemoved() {
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.setCacheable(true)
				.setCacheRegion("esporteCacheRegion")
				.list();
	}
	
}
