package br.com.callink.bradesco.seguro.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.callink.bradesco.seguro.dao.IPlanoDAO;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;

/**
 * Data Access Object (DAO) de Plano.
 * 
 * @author neppo.oldamar
 * 
 */
public class PlanoDAO extends GenericHibernateDAOImpl<Plano> implements
		IPlanoDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plano> buscarPlanoPorProduto(Produto produto) {

		Query query = session().getNamedQuery("Plano.buscarPlanoPorProduto");
		query.setParameter("idProduto", produto.getId());
		query.setParameter("flagEnabled", Boolean.TRUE);
		query.setParameter("flagRemoved", Boolean.FALSE);
		query.setParameter("dataAtual", new Date());
		return (List<Plano>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plano> buscarPlanoPorProdutoAndIdade(Produto produto, String idade) {

		Query query = session().getNamedQuery("Plano.buscarPlanoPorProdutoAndIdade");
		query.setParameter("idProduto", produto.getId());
		query.setParameter("idade", Integer.parseInt(idade));
		query.setParameter("flagEnabled", Boolean.TRUE);
		query.setParameter("flagRemoved", Boolean.FALSE);
		query.setParameter("dataAtual", new Date());
		return (List<Plano>) query.list();
	}
	
	public Boolean existeCodigoPlano(Plano plano) {
		
		Query query = session().getNamedQuery("Plano.buscarCodigoExistente");
		query.setParameter("idPlano", plano.getId());
		query.setParameter("codigoPlano", plano.getCodigo());
		return (Long) query.uniqueResult() > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public List<Plano> findByExample(Plano object) {
		return findByExample(object, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plano> findByExample(Plano plano, String orderBy) {
		Criteria cri = createCriteria();
		cri.add(Example.create(plano)
					   .enableLike(MatchMode.ANYWHERE)
					   .excludeNone()
					   .excludeZeroes()
					   .ignoreCase()
					   .excludeProperty("tipoPlano")
					   .excludeProperty("dataInicioVigencia")
					   .excludeProperty("dataFinalVigencia")
					   .excludeProperty("produto"));
		
		if (plano.getTipoPlano() != null && plano.getTipoPlano().getId() != null) {
			cri.createAlias("tipoPlano", "tipoPlano", JoinType.INNER_JOIN);
			cri.add(Restrictions.eq("tipoPlano.id", plano.getTipoPlano().getId()));
		}
		if (plano.getProduto() != null && plano.getProduto().getId() != null) {
			cri.createAlias("produto", "produto", JoinType.INNER_JOIN);
			cri.add(Restrictions.eq("produto.id", plano.getProduto().getId()));
		}
		if (plano.getDataInicioVigencia() != null) {
			cri.add(Restrictions.eq("dataInicioVigencia", plano.getDataInicioVigencia()));
		}
		if (plano.getDataFinalVigencia() != null) {
			cri.add(Restrictions.eq("dataFinalVigencia", plano.getDataFinalVigencia()));
		}
		
		if(orderBy != null){
			cri.addOrder(Order.asc(orderBy));
		}
		cri.add(Restrictions.eq("flagRemoved", Boolean.FALSE));
		return cri.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plano> findEnabledAndNotRemoved(){
		
		Criterion cri1 = Restrictions.isNull("dataFinalVigencia");
		Criterion cri2 = Restrictions.ge("dataFinalVigencia", new Date());
		
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.add(Restrictions.le("dataInicioVigencia", new Date()))
				.add(Restrictions.or(cri1, cri2))
				.list();
	}
}
