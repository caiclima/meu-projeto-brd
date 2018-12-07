package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dto.ClienteIndicacaoDTO;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IClienteCampanhaService extends
		IGenericCrudService<ClienteCampanha> {

	/**
	 * Busca dados de cliente campanha por nome ou cnpj (ou ambos).
	 * 
	 * @param nome
	 * @param cnpj
	 * @param usuario
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse consultarClienteCampanha(String nome, String cnpj,
			String usuario) throws ServiceException;

	/**
	 * 
	 * @param cliente
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	ClienteCampanha findById(ClienteCampanha cliente) throws ServiceException,
			ValidationException;

	/**
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	ClienteCampanha findById(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException;

	/**
	 * Busca de Cliente Campanha.
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public ClienteCampanha buscarClienteCampanha(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException;

	/**
	 * retorna a lista de clienteCampanha em que o atendente não efetuou ligação
	 * e que esteja no lote mailing que foi finalizado.
	 * 
	 * @param idEvento
	 * @param idLoteMailing
	 * @return
	 * @throws ServiceException
	 */
	List<ClienteCampanha> findClienteCampByIdFinalizacaoAndidLoteMailing(
			Long idEvento, Integer idLoteMailing) throws ServiceException;

	/**
	 * retorna a lista de clienteCampanha em que o atendente não efetuou ligação
	 * e que esteja no lote mailing que foi finalizado.
	 * 
	 * @param idLoteMailing
	 * @param flagGateway
	 * @return
	 * @throws ServiceException
	 */
	List<ClienteCampanha> findClienteCampVirgemByidLoteMailing(
			BigInteger idLoteMailing, boolean flagGateway) throws ServiceException;

	/**
	 * finaliza os clientes do loteMailing que tiverem o id do evento e que não
	 * foram trabalhados e salva o historico do contato.
	 * 
	 * @param idEvento
	 * @param idLoteMailing
	 * @param logHost
	 * @param logObs
	 * @param logSystem
	 * @param logUid
	 * @throws ServiceException
	 */
	void finalizaClientesLoteMailing(Long idEvento, BigInteger idLoteMailing,
			String logHost, String logObs, String logSystem, String logUid)
			throws ServiceException, ValidationException;

	/**
	 * 
	 * @param cnpjs
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findContatoMailingByCnpjs(String cnpjs)
			throws ServiceException;

	/**
	 * 
	 * @param clienteCampanha
	 * @return
	 * @throws ServiceException
	 */
	void salvarGrupoObjetos(ClienteCampanha clienteCampanha)
			throws ServiceException;

	/**
	 * retorna lista com dto para indicação de clientes
	 * 
	 * @param cnpjs
	 * @return
	 * @throws ServiceException
	 */
	public List<ClienteIndicacaoDTO> loadClientesParaIndicacao(String cnpjs)
			throws ServiceException;

	/**
	 * 
	 * @param nome
	 * @param cnpj
	 * @param usuario
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse consultarClienteAtendimento(String nome, String cnpj,
			String usuario) throws ServiceException;

	/**
	 * 
	 * @param nome
	 * @param cnpj
	 * @param usuario
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse consultarClienteAtendimentoReceptivo(String nome,
			String cnpj, String usuario) throws ServiceException;

	/**
	 * Faz pesquisa baseada em cnpjs com o resultado indicando clientes não
	 * indicaveis, indicaveis cadastrados na base de dados e indicaveis sem
	 * cadastro na base de dados.
	 * 
	 * @param cnpjs
	 * @return
	 * @throws ServiceException
	 */
	List<ClienteIndicacaoDTO> buscarClientesParaIndicacao(String cnpjs,
			Campanha campanhaIndicacao) throws ServiceException;

	ClienteCampanha buscarClienteCampanhaPorIdEChaveAtendimento(
			BigInteger idClienteCampanha, String chaveAtendimento, 
			String usuarioHost, String usuarioLogado, String telefone)
			throws ServiceException;

}
