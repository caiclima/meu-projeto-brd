package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Campanha'
 * 
 * @author swb.thiagohenrique
 *
 */
public interface ICampanhaService extends IGenericCrudService<Campanha> {
	
	ServiceResponse findById(BigInteger idCampanha) throws ServiceException, ValidationException;

	/**
	 * retorna uma lista de campanhas com o nome passado na pesquisa.
	 * @param nome
	 * @return
	 * @throws ServiceException
	 */
	List<Campanha> findCampanhasByNome(String nome) throws ServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findByExampleOrderByNome(Campanha entity) throws ServiceException;
	
	/**
	 * 
	 * @param campanha
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse saveOrUpdate(Campanha campanha,String usuarioHost,String usuarioLogado) throws ServiceException;

	/**
	 * Busca todas as campanhas ativas ordenando por nome
	 * @return
	 */
	List<Campanha> findCampanhasAtivasOrderByNome();
	
	/**
	 * Clona uma campanha já cadastrada
	 * @param campanha
	 * @return
	 * @throws ServiceException
	 */
	Campanha cloneCampanha(Campanha campanha) throws ServiceException;

	/**
	 * @param campanha
	 * @return 
	 * @throws ServiceException
	 */
	
	ServiceResponse associarEventosNaCampanha(Campanha campanha) throws ServiceException;

	ServiceResponse removerLogicamente(Campanha campanha, String usuarioHost, String usuarioLogado) throws ServiceException;
}
