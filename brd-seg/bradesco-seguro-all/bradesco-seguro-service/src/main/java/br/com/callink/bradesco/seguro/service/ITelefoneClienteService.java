package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface ITelefoneClienteService<T> extends IGenericCrudService<T> {

	/**
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	List<TelefoneCliente> findTelefoneByClienteCampanha(BigInteger idClienteCampanha) throws ServiceException, ValidationException;

	/**
	 * Retorna uma lista de TelefoneCliente limitando a quantidade de objetos na lista
	 * 
	 * @param idCliente
	 * @param limit
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	List<TelefoneCliente> findTelefoneByClienteCampanha(BigInteger idCliente, int limit) throws ServiceException, ValidationException;
	
	/**
	 * Busca Telefone Cliente.
	 * 
	 * @param cnpj
	 * @return
	 * @throws ServiceException
	 */
//	public ServiceResponse buscaTelefoneCliente(String cnpj) throws ServiceException;
	
	/**
	 * 
	 * @param cnpj
	 * @param idClienteCampanha
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse atualizarTelefoneCliente(String cnpj,BigInteger idClienteCampanha,String usuarioHost,String usuarioLogado) throws ServiceException;
	
}
