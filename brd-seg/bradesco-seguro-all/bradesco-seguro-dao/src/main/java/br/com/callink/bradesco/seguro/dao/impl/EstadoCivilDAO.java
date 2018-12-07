package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import br.com.callink.bradesco.seguro.dao.IEstadoCivilDAO;
import br.com.callink.bradesco.seguro.entity.EstadoCivil;

/**
 * Data Access Object (DAO) de EstadoCivil.
 * 
 * @author neppo.oldamar
 *
 */
public class EstadoCivilDAO extends GenericHibernateDAOImpl<EstadoCivil> implements IEstadoCivilDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCivil> findAllCacheable(){
		return createCriteria()
				.setCacheable(true)
				.setCacheRegion("estadoCivilCacheRegion")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCivil> findByExampleCacheable(EstadoCivil object) {
		// TODO Auto-generated method stub
		return createCriteria(object)
				.add(Example.create(object)
				.enableLike(MatchMode.ANYWHERE))	
				.setCacheable(true)
				.setCacheRegion("estadoCivilCacheRegion")
				.list();
	}
}
