package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IProdutoEventoDAO;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.ProdutoEvento;

/**
 * Data Access Object (DAO) de ProdutoEvento.
 * 
 * @author neppo.oldamar
 *
 */
public class ProdutoEventoDAO extends GenericHibernateDAOImpl<ProdutoEvento> implements IProdutoEventoDAO{

	public void deleteEventoProdutoByEvento(Evento evento) {
		Query query = session().getNamedQuery("ProdutoEvento.deleteByIdEvento");
		query.setParameter("idEvento", evento.getId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEvento> buscarPorEvento(Evento evento) {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("evento", evento)).setFetchMode("produto", FetchMode.JOIN);
		return (List<ProdutoEvento>) criteria.list();
	}
}
