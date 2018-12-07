package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.ClienteCampanhaDTO;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;

/**
 * Data Access Object (DAO) de ClienteCampanha
 * 
 * @author swb.thiagohenrique
 * 
 */
public interface IClienteCampanhaDAO extends IGenericDAO<ClienteCampanha> {

	public List<ClienteCampanhaDTO> consultarClienteCampanha(String nome,
			String cnpj, String usuario, Short codigoDominio);

	public ClienteCampanha buscarClienteCampanha(BigInteger idClienteCampanha)
			throws DataException;

	/**
	 * retorna uma lista de clientes Campanha do mailing que foi finalizado e
	 * que o atendente não ligou.
	 * 
	 * @param idLoteMailing
	 * @param flagGateway
	 * @return
	 * @throws DataException
	 */
	List<ClienteCampanha> findClienteCampVirgemByidLoteMailing(
			BigInteger idLoteMailing, boolean flagGateway) throws DataException;

	/**
	 * retorna uma lista de clientes Campanha do mailing que foi finalizado e
	 * que o atendente nÃ£o ligou.
	 * 
	 * @param idEvento
	 * @param idLoteMailing
	 * @return
	 * @throws DataException
	 */
	List<ClienteCampanha> findClienteCampByIdEventoAndidLoteMailing(
			Long idEvento, Integer idLoteMailing) throws DataException;

	/**
	 * 
	 * @param cnpjs
	 * @return
	 * @throws DataException
	 */
	List<ClienteCampanha> findContatoMailingByCnpjs(List<String> cnpjs)
			throws DataException;

	/**
	 * 
	 * @param nome
	 * @param cnpj
	 * @param nomeUsuario
	 * @param codigoDominio
	 * @return
	 */
	List<ClienteCampanhaDTO> consultarClienteAtendimento(String nome,
			String cnpj, String nomeUsuario, Short codigoDominio)
			throws DataException;

	/**
	 * 
	 * @param nome
	 * @param cnpj
	 * @param nomeUsuario
	 * @param codigoDominio
	 * @return
	 */
	List<ClienteCampanhaDTO> consultarClienteAtendimentoReceptivo(String nome,
			String cnpj, String nomeUsuario, Short codigoDominio)
			throws DataException;

	/**
	 * busca ClienteCampanhas que estejam em campanhas ativas
	 * 
	 * @param cnpjs
	 * @return
	 * @throws DataException
	 */
	public List<ClienteCampanha> findClienteCampanhasInCampanhaAtivaBycnpjs(
			List<String> cnpjs) throws DataException;

	/**
	 * Busca ClienteCampanhas existentes que estejam na base
	 * 
	 * @param cnpjs
	 * @return
	 */
	public List<ClienteCampanha> retornaClientesExistentes(List<String> cnpjs)
			throws DataException;

	ClienteCampanha buscarClienteCampanhaPorId(BigInteger idClienteCampanha)
			throws DataException;
}