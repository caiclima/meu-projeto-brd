package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;

public interface IContatoMailingService extends IGenericCrudService<ContatoMailing> {

	/**
	 * 
	 * @param idContatoMailing
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	ContatoMailing findById(BigInteger idContatoMailing) throws ServiceException, ValidationException;
	
//	/**
//	 * 
//	 * @param idClienteCampanha
//	 * @return
//	 * @throws ServiceException
//	 * @throws ValidationException
//	 */
//	List<ContatoMailing> findProdutosDeParaByCidadeContrato(BigInteger idClienteCampanha) throws ServiceException, ValidationException;

	/**
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	ContatoMailing findByIdClienteCampanha(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException;
	
	/**
	 * retorna o contatoMaling pelo Id do cliente Campanha.
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 */
	ContatoMailing findContatoMailingByIdClienteCampanha(BigInteger idClienteCampanha) throws ServiceException;

	Boolean compararContatosMailing(ContatoMailing contatoVenda,
			ContatoMailing contatoMailing, List<TelefoneCliente> telefones);

	void atualizarContatoMailing(ContatoMailing contatoVenda,
			ContatoMailing contatoMailing, String usuarioHost,
			String usuarioLogado, List<TelefoneCliente> telefones) throws ServiceException;
	
	/**
	 * 
	 * @param cnpjs
	 * @return
	 * @throws ServiceException
	 */
//	List<ClienteCampanha> buscarContatoMailing(List<String> cnpjs) throws ServiceException;
}
