package br.com.callink.bradesco.seguro.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IProfissaoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Profissao;

/**
 * Data Access Object (DAO) de Profissao.
 * 
 * @author neppo.oldamar
 *
 */
public class ProfissaoDAO extends GenericHibernateDAOImpl<Profissao> implements IProfissaoDAO {
	
	
	@Override
	public List<Profissao> findByExample(Profissao profissao) {
		
		profissao.setFlagRemoved(Boolean.FALSE);
		return super.findByExample(profissao);
	}
	
	public Long existeProfissaoComNome(String descricao) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("descricao", descricao));
		Long valor = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return  valor;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissao> findByExampleOrderByNome(Profissao entity) {
		
		Criteria cri = createCriteria();
		cri.add(Example.create(entity));
		cri.addOrder(Order.asc("nome"));
		
		return cri.list();
	}

	public Boolean existeProfissaoVinculadoVenda(BigInteger id) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("id", id));
		cri.createAlias("planoList", "vendas", JoinType.INNER_JOIN);
		
		Profissao profissao = (Profissao) cri.uniqueResult();
		
		if (CollectionUtils.isEmpty(profissao.getVendaList())) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	public Boolean existeProfissaoVinculadoDependente(BigInteger id) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("id", id));
		cri.createAlias("planoList", "vendas", JoinType.INNER_JOIN);
		
		Profissao profissao = (Profissao) cri.uniqueResult();
		
		if (CollectionUtils.isEmpty(profissao.getVendaList())) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissao> findByExampleExact(Profissao object) {
		Criteria cri = createCriteria();
		cri.add(Example.create(object).enableLike(MatchMode.EXACT));
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissao> findAllFromPkList(List<BigInteger> idProfissaoList) throws DataException {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", idProfissaoList));
		criteria.addOrder(Order.asc("descricao"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Profissao> findEnabledAndNotRemoved() {
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.setCacheable(true)
				.setCacheRegion("profissaoCacheRegion")
				.list();
	}
	
	@Override
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id){
		return super.getFromPersistenceContext(clazz, id);
	}
	
}


