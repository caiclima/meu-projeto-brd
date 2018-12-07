package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IGrauParentescoDAO;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;

/**
 * Data Access Object (DAO) de GrauParentesco.
 * 
 * @author neppo.oldamar
 *
 */
public class GrauParentescoDAO extends GenericHibernateDAOImpl<GrauParentesco> implements IGrauParentescoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<GrauParentesco> findWithoutTitular() {
		return createCriteria()
				.add(Restrictions.ne("id", GrauParentesco.TITULAR.getId()))
				.setCacheable(true)
				.setCacheRegion("grauParentescoCacheRegion")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrauParentesco> findAllCacheable(){
		return createCriteria()
				.setCacheable(true)
				.setCacheRegion("estadoCivilCacheRegion")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrauParentesco> findByExampleCacheable(GrauParentesco object) {
		// TODO Auto-generated method stub
		return createCriteria(object)
				.add(Example.create(object)
				.enableLike(MatchMode.ANYWHERE))	
				.setCacheable(true)
				.setCacheRegion("estadoCivilCacheRegion")
				.list();
	}

}
