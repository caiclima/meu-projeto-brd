package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.ICargoDAO;
import br.com.callink.bradesco.seguro.entity.Cargo;

public class CargoDAO extends GenericHibernateDAOImpl<Cargo> implements
		ICargoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Cargo> findByExampleExact(Cargo cargo) {

		Criteria criteria = createCriteria();
		criteria.add(Example.create(cargo).enableLike(MatchMode.EXACT));
		return (List<Cargo>) criteria.list();
	}
	
	public BigInteger findIdCargo(BigInteger idCargoCorporativo) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("idCargoCorporativo", idCargoCorporativo));
		criteria.setProjection(Projections.projectionList().add(Projections.property("id").as("id")));
		return (BigInteger) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cargo> findCargosByIds(List<BigInteger> ids){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", ids));
		
		return (List<Cargo>) criteria.list();
	}

}
