package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.EventoContatoDTO;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;

/**
 * 
 * @author michael
 *
 */
public interface IHistoricoContatoDAO extends IGenericDAO<HistoricoContato>{
	
	/**
	 * Busca eventos de contato ao cliente baseado no periodo informado (data contato)
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	List<EventoContatoDTO> buscarHistoricoPorPeriodoDataContato(Date dataInicial, Date dataFinal);
	
	/**
	 * Atualiza Flag Contato Enviado para os históricos de Contato informados (ids).
	 * 
	 * @param enviado - true/false
	 * @param idsHistoricoContato
	 * @return - total de registros alterados
	 */
	int atualizarFlagContatoEnviado(Boolean enviado, Set<BigInteger> idsHistoricoContato);

	/**
	 * busca o historico do contato pelo IdClienteCampanha.
	 * @param idClienteCampanha
	 * @return 
	 */
	List<HistoricoContato> buscaHistoricoByIdClienteCampanha(BigInteger idClienteCampanha);

	/**
	 * Atualiza Flag Contato Enviado para o históricos de Contato informado (id).
	 * @param flagContatoEnviado
	 * @param idHistoricoContato
	 * @return
	 */
	int atualizarFlagContatoEnviado(Boolean flagContatoEnviado, BigInteger idHistoricoContato);

	/**
	 * Atualiza Flag Contato Enviado para nao Enviado
	 * @return
	 */
	
	int atualizarFlagContatoParaNaoEnviado();
	
	/**
	 * Busca o históricoContato pelo CallId
	 * @param callId
	 * @return
	 * @throws DataException
	 */
	HistoricoContato findByCallId(BigInteger callId, BigInteger idClienteCampanha, String logUid) throws DataException;

	/**
	 * Buca historicoContato pelo CNPJ
	 * @param cnpj
	 * @return
	 */
	List<HistoricoContato> buscaPorCnpj(String cnpj);
	
	HistoricoContato buscarPorChaveAtendimento(String chaveAtendimento);
	
}