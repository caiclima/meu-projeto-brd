package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Cargo'
 * 
 * @author neppo.oldamar
 * 
 */
public interface ICargoService<T> extends IGenericCrudService<T> {

	/**
	 * 
	 * Busca Todos Cargos
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscarTodosCargos() throws ServiceException;

	/**
	 * 
	 * Busca Cargo por id
	 * 
	 * @param idCargo
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscaCargoPorId(BigInteger idCargo)
			throws ServiceException;

	/**
	 * 
	 * @param cargo
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse save(Cargo cargo, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	/**
	 * 
	 * @param cargo
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse update(Cargo cargo, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	/**
	 * 
	 * @param cargo
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse delete(Cargo cargo) throws ServiceException;
	
	/**
	 * 
	 * @param idCargoCorporativo
	 * @return
	 * @throws ServiceException
	 */
	public BigInteger buscarIdCargo(BigInteger idCargoCorporativo) throws ServiceException;
	
	public ServiceResponse findCargosByIds(List<BigInteger> ids) throws ServiceException;

}
