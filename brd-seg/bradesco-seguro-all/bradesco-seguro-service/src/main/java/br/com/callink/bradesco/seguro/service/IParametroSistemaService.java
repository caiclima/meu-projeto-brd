package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;


/**
 * Implementa interface de serviços para Parâmetros do Sistema
 * 
 * @author michael
 * @param <T>
 * 
 */
public interface IParametroSistemaService<T> extends IGenericCrudService<T> {
	
	/**
	 * Busca parametro pelo Nome informado
	 * 
	 * @param nomeParametro
	 * @return
	 * @throws ServiceException
	 */
	ParametroSistema buscarPorNome(String nomeParametro) throws ServiceException;
	
	/**
	 * 
	 * @return
	 * @throws ValidationException
	 */
	String buscarStatusPropostaAtivo() throws ValidationException;
	
	/**
	 * 
	 * @return
	 * @throws ValidationException
	 */
	String buscarStatusPropostaConcluida() throws ValidationException;
	
	String buscarValorParametro(String nomeParametro) throws ServiceException;
	
	ServiceResponse salvar(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException;

	ServiceResponse atualizar(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException;

	ServiceResponse remover(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException;
}
