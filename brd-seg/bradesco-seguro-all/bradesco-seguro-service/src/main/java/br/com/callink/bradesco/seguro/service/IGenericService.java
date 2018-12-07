package br.com.callink.bradesco.seguro.service;


import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Interface generica para Camada de Serviços
 * 
 * @author michael
 *
 * @param <T>
 */
public interface IGenericService<T> {

	/**
	 * Busca o objeto por usa primary key (informada)
	 * @param pk
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findByPK(Object pk) throws ServiceException;

	/**
	 * Carrega os objetos com propriedades iguais ao objeto informado
	 * 
	 * @param object
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findByExample(T object) throws ServiceException;
	
	/**
	 * Lista todos os objetos do tipo T
	 * @throws ServiceException
	 */
	ServiceResponse findAll() throws ServiceException;
}
