package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IDominioDAO;
import br.com.callink.bradesco.seguro.entity.Dominio;

public class DominioDAO extends GenericHibernateDAOImpl<Dominio> implements IDominioDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Dominio> findByExampleExact(Dominio dominio) {

		Criteria criteria = createCriteria();
		criteria.add(Example.create(dominio).enableLike(MatchMode.EXACT));
		return (List<Dominio>) criteria.list();
	}
	
	public BigInteger findIdDominio(BigInteger idDominioCorporativo) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("idDominioCorporativo", idDominioCorporativo));
		criteria.setProjection(Projections.projectionList().add(Projections.property("id").as("id")));
		return (BigInteger) criteria.uniqueResult();
	}

}
