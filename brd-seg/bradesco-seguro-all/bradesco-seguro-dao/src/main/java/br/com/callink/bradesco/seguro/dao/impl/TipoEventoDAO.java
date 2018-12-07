package br.com.callink.bradesco.seguro.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.ITipoEventoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.TipoEvento;

/**
 * Data Access Object (DA) de Tipo Evento
 * 
 * @author swb.thiagohenrique
 * 
 */
public class TipoEventoDAO extends GenericHibernateDAOImpl<TipoEvento> implements ITipoEventoDAO {
	
	@Override
	public List<TipoEvento> findByExample(TipoEvento tipoEvento) {
		
		tipoEvento.setFlagRemoved(Boolean.FALSE);
		return super.findByExample(tipoEvento);
	}
	
        
    @SuppressWarnings("unchecked")
	@Override
    public List<TipoEvento> findTiposEventosComEventos(Campanha campanha) throws DataException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT DISTINCT tipoEvento FROM TipoEvento AS tipoEvento ");
        stringBuilder.append("INNER JOIN FETCH tipoEvento.eventoList AS evento ");
        stringBuilder.append("INNER JOIN evento.eventoCampanhaList AS eventoCampanha ");
        stringBuilder.append("WHERE evento.flagEnabled = :flagEnabledEvento and eventoCampanha.campanha.id = :idCampanha and tipoEvento.flagEnabled = :flagEnabledTipoEvento ");
        stringBuilder.append("and evento.flagRemoved = :flagRemovedEvento and tipoEvento.flagRemoved = :flagRemovedTipoEvento");

        Query query = session().createQuery(stringBuilder.toString());
        query.setParameter("flagEnabledEvento", Boolean.TRUE);
        query.setParameter("flagEnabledTipoEvento", Boolean.TRUE);
        query.setParameter("flagRemovedEvento", Boolean.FALSE);
        query.setParameter("flagRemovedTipoEvento", Boolean.FALSE);
        query.setParameter("idCampanha", campanha.getId());
        query.setCacheable(true);
        query.setCacheRegion("tipoEventoCacheRegion");

        return query.list();
    }
 
	public Long existeTipoEventoComNome(String nomeTipoEvento) {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("nomeTipoEvento", nomeTipoEvento));
		Long valor = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return  valor;
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoEvento> findByExampleOrderByNome(TipoEvento tipoEvento) {
		
		Criteria criteria = createCriteria();
		criteria.add(Example.create(tipoEvento));
		criteria.addOrder(Order.asc("nomeTipoEvento"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoEvento> findByExampleExact(TipoEvento tipoEvento) {
		
		Criteria criteria = createCriteria();
		criteria.add(Example.create(tipoEvento).enableLike(MatchMode.EXACT));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoEvento> findAllFromPkList(List<BigInteger> idTipoEventoList) throws DataException {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", idTipoEventoList));
		criteria.addOrder(Order.asc("nomeTipoEvento"));
		return criteria.list();
	}
	
	@Override
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id) {
		return super.getFromPersistenceContext(clazz, id);
	}
	
}
