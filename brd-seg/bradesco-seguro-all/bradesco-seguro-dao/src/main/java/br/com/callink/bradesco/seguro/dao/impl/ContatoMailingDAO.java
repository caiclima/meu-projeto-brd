package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.callink.bradesco.seguro.dao.IContatoMailingDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ContatoMailing;

/**
 * Data Access Object (DAO) de Contato Mailing.
 * 
 * @author swb.thiagohenrique
 * 
 */
public class ContatoMailingDAO extends GenericHibernateDAOImpl<ContatoMailing> implements IContatoMailingDAO {
	
	@SuppressWarnings("unchecked")
	public List<ContatoMailing> findByIdClienteCampanha(BigInteger idClienteCampanha) throws DataException { 
		
		try {
			
			Criteria criteria = createCriteria();
			criteria.createAlias("clienteCampanhaList", "clienteCampanha", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("clienteCampanha.id", idClienteCampanha));
			return criteria.list();
			
		} catch (Exception e) {
			throw new DataException(e);
		}
	}
	
	@Override
	public ContatoMailing findContatoMailingByIdClienteCampanha(BigInteger idClienteCampanha) throws DataException { 
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT mailing FROM ContatoMailing mailing, ClienteCampanha clienteCampanha ");
			sql.append("	where clienteCampanha.contatoMailing.id = mailing.id and clienteCampanha.id = :ID_CLIENTE ");
			Query q = session().createQuery(sql.toString());
			
			q.setParameter("ID_CLIENTE", idClienteCampanha);
			
			q.setLockMode("mailing", LockMode.NONE);
			q.setLockMode("clienteCampanha", LockMode.NONE);
			 
			return (ContatoMailing) q.uniqueResult();
			
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

}
