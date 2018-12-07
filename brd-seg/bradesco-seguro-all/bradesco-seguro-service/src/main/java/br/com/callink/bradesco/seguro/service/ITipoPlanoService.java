package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.TipoPlano;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Tipo Plano'
 * 
 * @author swb.alisson
 *
 */
public interface ITipoPlanoService<T> extends IGenericCrudService<T> {
	
	/**
	 * 
	 * Busca Todos Tipos de Plano
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscarTodosTiposPlano() throws ServiceException;
	
	/**
	 * 
	 * Busca Tipo de Plano por id
	 * 
	 * @param idTipoPlano
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscaTipoPlanoPorId(BigInteger idTipoPlano) throws ServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse findByExampleOrderByNome(TipoPlano entity) throws ServiceException;
	
	/**
	 * 
	 * @param planoList
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public List<TipoPlano> findByPlanoList(List<Plano> planoList) throws ServiceException, ValidationException;
	
	/**
	 * 
	 * @param tipoPlano
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse save(TipoPlano tipoPlano,String usuarioHost, String usuarioLogado) throws ServiceException;
	
	/**
	 * 
	 * @param tipoPlano
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse update(TipoPlano tipoPlano,String usuarioHost,String usuarioLogado) throws ServiceException;
	
}
