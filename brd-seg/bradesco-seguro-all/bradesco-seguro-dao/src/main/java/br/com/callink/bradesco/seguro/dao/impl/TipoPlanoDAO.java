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
import br.com.callink.bradesco.seguro.dao.ITipoPlanoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.TipoPlano;

/**
 * Data Access Object (DA) de Tipo Plano
 * 
 * @author swb.alisson
 * 
 */
public class TipoPlanoDAO extends GenericHibernateDAOImpl<TipoPlano> implements ITipoPlanoDAO {
	
	public List<TipoPlano> buscarTodosTiposPlano() {
		return findAll();
	}

	public TipoPlano buscaTipoPlanoPorId(BigInteger idTipoPlano) {
		return findByPK(idTipoPlano);
	}
	
	public Long existeTipoPlanoComNome(String nomeTipoPlano) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("nomeTipoPlano", nomeTipoPlano));
		Long valor = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return  valor;
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoPlano> findByExampleOrderByNome(TipoPlano entity) {
		
		Criteria cri = createCriteria();
		cri.add(Example.create(entity));
		cri.addOrder(Order.asc("nomeTipoPlano"));
		
		return cri.list();
	}

	public Boolean existeTipoPlanoVinculadoPlano(BigInteger id) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("id", id));
		cri.createAlias("planoList", "planos", JoinType.INNER_JOIN);
		
		TipoPlano tipoPlano = (TipoPlano) cri.uniqueResult();
		
		if (CollectionUtils.isEmpty(tipoPlano.getPlanoList())) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoPlano> findByExampleExact(TipoPlano object) {
		Criteria cri = createCriteria();
		cri.add(Example.create(object).enableLike(MatchMode.EXACT));
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoPlano> findAllFromPkList(List<BigInteger> idTipoPlanoList) throws DataException {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", idTipoPlanoList));
		criteria.addOrder(Order.asc("nomeTipoPlano"));
		
		return criteria.list();
	}
	
	@Override
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id) {
		return super.getFromPersistenceContext(clazz, id);
	}
	
}
