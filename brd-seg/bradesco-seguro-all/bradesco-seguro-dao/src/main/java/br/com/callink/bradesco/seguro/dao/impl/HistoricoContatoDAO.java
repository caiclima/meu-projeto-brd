package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import br.com.callink.bradesco.seguro.dao.IHistoricoContatoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.EventoContatoDTO;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;

/**
 * 
 * @author michael
 * 
 */

public class HistoricoContatoDAO extends GenericHibernateDAOImpl<HistoricoContato> implements IHistoricoContatoDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<EventoContatoDTO> buscarHistoricoPorPeriodoDataContato(Date dataInicial, Date dataFinal) {
		Query query = session().getNamedQuery("HistoricoContato.buscarHistoricoPorPeriodoDataContato");
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		query.setResultTransformer(Transformers.aliasToBean(EventoContatoDTO.class));
		return query.list();
	}

	@Override
	public int atualizarFlagContatoEnviado(Boolean flagContatoEnviado, Set<BigInteger> idsHistoricoContato) {
		Query query = session().getNamedQuery("HistoricoContato.atualizarFlagContatoEnviado");
		query.setParameter("flagContatoEnviado", flagContatoEnviado);
		query.setParameterList("ids", idsHistoricoContato);
		return query.executeUpdate();
	}
	@Override
	public int atualizarFlagContatoEnviado(Boolean flagContatoEnviado, BigInteger idHistoricoContato) {
		Query query = session().getNamedQuery("HistoricoContato.atualizarFlagContatoEnviado");
		query.setParameter("flagContatoEnviado", flagContatoEnviado);
		query.setParameter("ids", idHistoricoContato);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoContato> buscaHistoricoByIdClienteCampanha(BigInteger idClienteCampanha) {
		Query query = session().getNamedQuery("HistoricoContato.historicoContatoByIdClienteCampanha");
		query.setParameter("idCLiente", idClienteCampanha);
		query.setParameter("flagGateway", Boolean.FALSE);
		return query.list();
	}

	@Override
	public int atualizarFlagContatoParaNaoEnviado() {
		Query query = session().getNamedQuery("HistoricoContato.atualizarFlagContatoNaoEnviado");
		query.setParameter("flagContatoEnviado", false);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoContato> buscaPorCnpj(String cnpj) {
		
		Query query = session().getNamedQuery("HistoricoContato.historicoContatoByCnpj");
		query.setParameter("cpf", cnpj);
		query.setParameter("flagGateway", Boolean.FALSE);
		return query.list();
		
	}
	
	@Override
	public HistoricoContato findByCallId(BigInteger callId, BigInteger idClienteCampanha, String logUid) throws DataException {
		try{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(" SELECT ");
			stringBuilder.append("	historicoContato 	");
			stringBuilder.append(" FROM HistoricoContato AS historicoContato ");
			stringBuilder.append(" WHERE ");
			stringBuilder.append("	historicoContato.callId = :callId ");
			stringBuilder.append("	AND historicoContato.idClienteCampanha = :idClienteCampanha ");
			stringBuilder.append("	AND historicoContato.logUid= :logUid ");
			stringBuilder.append("	AND historicoContato.flagContatoEnviado is null ");
			stringBuilder.append(" ORDER BY historicoContato.dataContato DESC ");
			
			javax.persistence.Query query = getEntityManager().createQuery(stringBuilder.toString());
			query.setParameter("callId", callId);
			query.setParameter("idClienteCampanha", idClienteCampanha);
			query.setParameter("logUid", logUid);
			
			List<HistoricoContato> result = (List<HistoricoContato>)query.getResultList();
		
			return result != null && result.size() > 0 ? result.get(0):null; 
			
		}catch(NoResultException e){
			throw new DataException("Cliente campanha não foi encontrado! ", e);
			
		}catch (Exception e) {
			throw new DataException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public HistoricoContato buscarPorChaveAtendimento(String chaveAtendimento){
		
		Query query = session().getNamedQuery("HistoricoContato.buscarPorChaveAtendimento");
		query.setString("chaveAtendimento", chaveAtendimento);
		List<HistoricoContato> historicos = (List<HistoricoContato>) query.list();
		
		return historicos != null && !historicos.isEmpty() ? historicos.get(0) : null;
	}
}