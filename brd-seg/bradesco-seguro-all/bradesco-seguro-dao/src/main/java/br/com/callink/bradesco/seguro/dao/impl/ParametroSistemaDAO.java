package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IParametroSistemaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;

/**
 * Data Access Object (DAO) de 'Parâmetro de Sistema'
 * 
 * @author swb.thiagohenrique
 * 
 */
public class ParametroSistemaDAO extends GenericHibernateDAOImpl<ParametroSistema> implements IParametroSistemaDAO {
	
	@SuppressWarnings("unchecked")
	public List<ParametroSistema> buscarParametroSistemaPorNome(String nome) throws DataException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("nomeParametroSistema", nome));
		criteria.setCacheable(true);
		criteria.setCacheRegion("parametroSistemaCacheRegion");
		return criteria.list();
	}
	
	/**
	 * Busca parametro pelo Nome informado
	 * 
	 * @param nomeParametro
	 * @return
	 */
	@Override
	public ParametroSistema buscarPorNome(String nomeParametro) {
		Query query = session().getNamedQuery("ParametroSistema.buscarPorNome");
		query.setParameter("nomeParametro", nomeParametro);
		query.setCacheable(true);
		query.setCacheRegion("parametroSistemaCacheRegion");
		return (ParametroSistema) query.uniqueResult();
	}
	
	public String findValorParametro(String nomeParametro) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("nomeParametroSistema", nomeParametro));
		criteria.setProjection(Projections.projectionList().add(Projections.property("valorParametroSistema")));
		criteria.setCacheable(true);
		criteria.setCacheRegion("parametroSistemaCacheRegion");
		return (String) criteria.uniqueResult();
	}
	
}