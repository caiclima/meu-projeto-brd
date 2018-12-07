package br.com.callink.bradesco.seguro.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IEventoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.TipoEvento;

/**
 * Data Access Object (DAO) de Evento.
 * 
 * @author swb.thiagohenrique
 * 
 */
public class EventoDAO extends GenericHibernateDAOImpl<Evento> implements IEventoDAO {
	
	@SuppressWarnings("unchecked")
	public List<Evento> findEventosNaCampanha(BigInteger idCampanha) {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT evento FROM Evento AS evento ");
		stringBuilder.append("INNER JOIN FETCH evento.eventoCampanhaList AS eventoCampanha ");
		stringBuilder.append("INNER JOIN FETCH evento.tipoEvento AS tipoEvento ");
		stringBuilder.append("INNER JOIN FETCH eventoCampanha.campanha AS campanha ");
		stringBuilder.append("WHERE evento.flagEnabled = :eventoFlagEnabled AND ");
		stringBuilder.append("campanha.id = :campanhaId ");
		stringBuilder.append("ORDER BY evento.nomeEvento");
		
		Query query = session().createQuery(stringBuilder.toString());
		query.setParameter("eventoFlagEnabled", Boolean.TRUE);
		query.setParameter("campanhaId", idCampanha);
		query.setCacheable(true);
		query.setCacheRegion("eventoCacheRegion");
		
		return query.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> findByExampleExact(Evento object) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("flagRemoved", Boolean.FALSE));
		cri.add(Example.create(object).enableLike(MatchMode.EXACT));
		return cri.list();
	}
	
	@Override
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id){
		return super.getFromPersistenceContext(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> findAllEventosDeAgenteAtivos() throws DataException{
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		crit.add(Restrictions.eq("flagGateway", Boolean.FALSE));
		crit.add(Restrictions.eq("flagRemoved", Boolean.FALSE));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(BigInteger idEventoSelecionado, BigInteger idTipoEventoSelecionado) throws DataException {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		crit.add(Restrictions.eq("flagGateway", Boolean.FALSE));
		crit.add(Restrictions.eq("flagRemoved", Boolean.FALSE));
		if(idEventoSelecionado != null){
			crit.add(Restrictions.eq("id", idEventoSelecionado));	
		}
		
		if(idTipoEventoSelecionado != null){
			crit.add(Restrictions.eq("tipoEvento.id", idTipoEventoSelecionado));	
		}
		
		return crit.list();
	}
	
	@Override
	public List<Evento> findByExample(Evento evento) {
		
		if (evento.getIdAspect().compareTo(BigInteger.ZERO) == 0){
			evento.setIdAspect(null);
		}
		evento.setFlagRemoved(Boolean.FALSE);
		return super.findByExample(evento);
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> buscarPorExemplo(Evento evento, Date dataCadastroInicio, Date dataCadastroFim) throws ParseException {
		
		Criteria criteria = createCriteria();
		if (evento.getTipoEvento() != null && evento.getTipoEvento().getId() != null){
			criteria.add(Restrictions.eq("tipoEvento", evento.getTipoEvento()));
		}
		if (!StringUtils.isEmpty(evento.getNomeEvento())){
			criteria.add(Restrictions.like("nomeEvento", '%'+evento.getNomeEvento()+'%'));
		}
		if (evento.getTipoEvento() != null && evento.getTipoEvento().getId() != null){
			criteria.add(Restrictions.eq("tipoEvento", evento.getTipoEvento()));
		}
		if (evento.getFlagGateway() != null){
			criteria.add(Restrictions.eq("flagGateway", evento.getFlagGateway()));
		}
		if (evento.getFlagEnabled() != null){
			criteria.add(Restrictions.eq("flagEnabled", evento.getFlagEnabled()));
		}
		if (evento.getFlagVenda() != null){
			criteria.add(Restrictions.eq("flagVenda", evento.getFlagVenda()));
		}
		if (!StringUtils.isEmpty(evento.getCodigoAspect())) {
			criteria.add(Restrictions.eq("codigoAspect", evento.getCodigoAspect()));
		}
		if (!StringUtils.isEmpty(evento.getCodigoExterno())) {
			criteria.add(Restrictions.eq("codigoExterno", evento.getCodigoExterno()));
		}
		if (evento.getTipoFinalizacao() != null && evento.getTipoFinalizacao() != 0) {
			criteria.add(Restrictions.eq("tipoFinalizacao", evento.getTipoFinalizacao()));
		}
		if (dataCadastroInicio != null && dataCadastroFim != null){
			
		    Date inicioDate = DateUtils.aplicar0H0m0s(dataCadastroInicio);
		    Date fimDate = DateUtils.aplicar23H59m59s(dataCadastroFim);

		    Conjunction and = Restrictions.conjunction();
		    and.add( Restrictions.ge("dataCadastro", inicioDate) );
		    and.add( Restrictions.lt("dataCadastro", fimDate) ); 
			criteria.add(and);
		}
		criteria.add(Restrictions.eq("flagRemoved",Boolean.FALSE));
		evento.setFlagRemoved(Boolean.FALSE);
		return (List<Evento>) criteria.list();
	}

	@Override
	public Evento findByPK(Object pk){
		return (Evento) createCriteria()
				.add(Restrictions.eq("id", pk))
				.setCacheable(true)
				.setCacheRegion("eventoCacheRegion")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> findEventosNaCampanhaByTipo(TipoEvento tipoEvento, Campanha campanha) {
		Query query = session().getNamedQuery("Evento.buscarEventosNaCampanhaPorTipo");
		query.setParameter("flagEnabled", Boolean.TRUE);
		query.setParameter("flagRemoved", Boolean.FALSE);
		query.setParameter("flagImplicitoUsuario", Boolean.FALSE);
		query.setParameter("idCampanha", campanha.getId());
		query.setParameter("idTipoEvento", tipoEvento.getId());
		query.setCacheable(true);
		query.setCacheRegion("eventoCacheRegion");
		return query.list();
	}
	
}
