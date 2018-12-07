package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.ICampanhaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;

/**
 * Data Access Object (DAO) de Campanha.
 * 
 * @author swb.thiagohenrique
 *
 */
public class CampanhaDAO extends GenericHibernateDAOImpl<Campanha> implements ICampanhaDAO {

	public Campanha findById(BigInteger idCampanha) {
		return findByPK(idCampanha);
	}
	
	@Override
	public List<Campanha> findByExample(Campanha campanha) {
		
		campanha.setFlagRemoved(Boolean.FALSE);
		return super.findByExample(campanha);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Campanha> findCampanhasByNome(String nome) throws DataException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT campanha FROM Campanha campanha where 1=1 ");
			if(nome != null) {
				sql.append("	and campanha.nomeCampanha like :NOME ");
				
			}
			
			Query q = session().createQuery(sql.toString());
			 
			if(nome != null) {
				q.setParameter("NOME", new StringBuilder().append(nome).append("%").toString());
				
			}
			 
			return q.setLockMode("campanha", LockMode.NONE).list();
		} catch (Exception ex) {
			throw new DataException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Campanha> findByExampleOrderByNome(Campanha entity) {
		
		Criteria criteria = createCriteria();
		criteria.add(Example.create(entity));
		criteria.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		criteria.addOrder(Order.asc("nomeCampanha"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Campanha> findByExampleExact(Campanha object) {
		Criteria cri = createCriteria();
		cri.add(Example.create(object).enableLike(MatchMode.EXACT));
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Campanha> findCampanhasAtivasOrderByNome() {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		criteria.add(Restrictions.or(Restrictions.isNull("dataFim"), Restrictions.ge("dataFim",new Date())));
		criteria.addOrder(Order.asc("nomeCampanha"));
		
		return criteria.list();
	}
}
