package br.com.callink.bradesco.seguro.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IProdutoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Produto;

/**
 * Data Access Object (DAO) de Produto.
 * 
 * @author neppo.oldamar
 *
 */
@Stateless
@Local(IProdutoDAO.class)
public class ProdutoDAO extends GenericHibernateDAOImpl<Produto> implements IProdutoDAO {
	
	public Long existeProdutoComNome(String nomeProduto) {
		Criteria criteria = createCriteriaWithFlagRemoved(Boolean.FALSE);
		criteria.add(Restrictions.eq("descricao", nomeProduto));
		Long valor = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return  valor;
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByExampleOrderByNome(Produto entity) {
		
		Criteria cri = createCriteriaWithFlagRemoved(Boolean.FALSE);
		cri.add(Example.create(entity));
		cri.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		cri.addOrder(Order.asc("nome"));
		
		return cri.list();
	}

	public Boolean existeProdutoVinculadoPlano(BigInteger id) {
		Criteria cri = createCriteriaWithFlagRemoved(Boolean.FALSE);
		cri.add(Restrictions.eq("id", id));
		cri.createAlias("planoList", "planos", JoinType.LEFT_OUTER_JOIN);
		
		Produto produto = (Produto) cri.uniqueResult();
		
		if (CollectionUtils.isEmpty(produto.getPlanoList())) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findByExampleExact(Produto object) {
		Criteria cri = createCriteriaWithFlagRemoved(Boolean.FALSE);
		cri.add(Example.create(object).enableLike(MatchMode.EXACT));
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAllFromPkList(List<BigInteger> idProdutoList) throws DataException {
		
		Criteria criteria = createCriteriaWithFlagRemoved(Boolean.FALSE);
		criteria.add(Restrictions.in("id", idProdutoList));
		criteria.addOrder(Order.asc("descricao"));
		
		return criteria.list();
	}
	
	@Override
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id){
		return super.getFromPersistenceContext(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findProdutosNoEvento(BigInteger idEvento) {
		
		Query query = session().getNamedQuery("Produto.buscarProdutoNoEvento");
		query.setParameter("eventoFlagEnabled", Boolean.TRUE);
		query.setParameter("idEvento", idEvento);
		query.setCacheable(true);
		query.setCacheRegion("produtoCacheRegion");
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findEnabledAndNotRemoved(){
		
		Criterion cri1 = Restrictions.isNull("dataFinalVigencia");
		Criterion cri2 = Restrictions.ge("dataFinalVigencia", new Date());
		
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.add(Restrictions.le("dataInicioVigencia", new Date()))
				.add(Restrictions.or(cri1, cri2))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findByExample(Produto produto){
		return createCriteria(produto)
				.add(Example.create(produto)
				.enableLike(MatchMode.ANYWHERE))
				.setCacheable(true)
				.setCacheRegion("produtoCacheRegion")
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findByNome(String nome) {
		Criterion cri1 = Restrictions.isNull("dataFinalVigencia");
		Criterion cri2 = Restrictions.ge("dataFinalVigencia", new Date());
		
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.add(Restrictions.le("dataInicioVigencia", new Date()))
				.add(Restrictions.or(cri1, cri2))
				.add(Restrictions.eq("nome", nome))
				.list();
	}
	
}

