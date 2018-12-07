package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.EventoContatoDTO;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * 
 * @author michael
 * 
 * @param <T>
 */
public interface IHistoricoContatoService<T>  extends IGenericCrudService<T>{

	/**
	 * Busca eventos de contato ao cliente baseado no periodo informado (data
	 * contato)
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @throws ServiceException
	 */
	List<EventoContatoDTO> buscarHistoricoPorPeriodoDataContato(Date dataInicial, Date dataFinal) throws ServiceException;
	
	/**
	 * Atualiza Flag Contato Enviado para os históricos de Contato informados (ids)
	 * para ENVIADO.
	 * 
	 * @param idsHistoricoContato
	 * @return - total registros afetados.
	 * @throws ServiceException
	 */
	int atualizarFlagContatoParaEnviado(Set<BigInteger> idsHistoricoContato) throws ServiceException;
	
	
	/**
	 * salva o historico do Contato pelos atributos passados no método.
	 * 
	 * @param idEvento
	 * @param IdClienteCampanha
	 * @param IdUsuario
	 * @param dataContato
	 * @param dataFimContato
	 * @param logHost
	 * @param logObs
	 * @param logSystem
	 * @param logUid
	 * @throws ServiceException
	 */
//	void salvaHistoricoContato(Long idEvento, Long IdClienteCampanha, Long IdUsuario, Date dataContato, Date dataFimContato, BigInteger idTelefone,
//			String logHost, String logObs, String logSystem, String logUid) throws ServiceException;
	void salvaHistoricoContato(Long idEvento, Long IdClienteCampanha, Date dataContato, Date dataFimContato, BigInteger idTelefone,
			String logHost, String logObs, String logSystem, String logUid) throws ServiceException;

	/**
	 * retorna uma lista de historicoContato a partir do idClienteCampanha.
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 */
	List<HistoricoContato> buscaHistoricoByIdClienteCampanha(BigInteger idClienteCampanha) throws ServiceException;

	/**
	 * Atualiza Flag Contato Enviado para o histórico de Contato informado (id)
	 * para ENVIADO.
	 * @param idHistoricoContato
	 * @return
	 * @throws ServiceException
	 */
	int atualizarFlagContatoParaEnviado(BigInteger idHistoricoContato) throws ServiceException;


	/**
	 * Atualiza Flag Contato para nao Enviado os históricos de Contato 
	 * @return
	 */
	int atualizarFlagContatoParaNaoEnviado();
	
	/**
	 * Busca o históricoContato pelo CallId
	 * @param callId
	 * @return
	 * @throws DataException
	 */
	public HistoricoContato findByCallId(BigInteger callId, BigInteger idClienteCampanha, String logUid) throws ServiceException;

	/**
	 * BUsca HistoricoContato pelo CNPJ
	 * @param cnpj
	 * @return
	 * @throws ServiceException
	 */
	List<HistoricoContato> buscaPorCnpj(String cnpj) throws ServiceException;

	ServiceResponse salvar(HistoricoContato historicoContato, String usuarioHost, String usuarioLogado) throws ServiceException;
	
	ServiceResponse atualizar(HistoricoContato historicoContato, String usuarioHost, String usuarioLogado) throws ServiceException;

	ServiceResponse buscarPorChaveAtendimento(String chaveAtendimento) throws ServiceException;
	
}