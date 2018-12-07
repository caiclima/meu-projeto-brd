package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa Camada de Serviços para operações CRUD
 * 
 * @author michael
 * 
 */
public interface IGenericCrudService<T> {

	/**
	 * Salva o objeto informado.
	 * 
	 * @param o
	 * @throws ServiceException
	 */
	ServiceResponse save(T o) throws ServiceException;

	/**
	 * Salva ou atualiza o objeto informado baseado na existencia previa do
	 * mesmo.
	 * 
	 * @param o
	 * @throws ServiceException
	 */
	ServiceResponse saveOrUpdate(T o) throws ServiceException;

	/**
	 * Atualiza o objeto informado.
	 * 
	 * @param o
	 * @throws ServiceException
	 */
	ServiceResponse update(T o) throws ServiceException;

	/**
	 * Lista todos os objetos do tipo T
	 * 
	 * @throws ServiceException
	 */
	ServiceResponse findAll() throws ServiceException;

	/**
	 * Busca todos os registros por exemplo (entidade informada).
	 * 
	 * @param o
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findByExample(T o) throws ServiceException;

	/**
	 * Exclui o objeto informado.
	 * 
	 * @param o
	 * @throws ServiceException
	 */
	ServiceResponse delete(T o) throws ServiceException;
	
	ServiceResponse save(T o, String usuarioHost, String usuarioLogado) throws ServiceException;
	
	ServiceResponse update(T o, String usuarioHost, String usuarioLogado) throws ServiceException;
	
	/**
	 * Define flagRemoved = true para remoção logica do objeto.
	 * @param o
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse deleteLogico(T o, String usuarioHost, String usuarioLogado) throws ServiceException;

	ServiceResponse findEnabledAndNotRemoved() throws ServiceException;

}