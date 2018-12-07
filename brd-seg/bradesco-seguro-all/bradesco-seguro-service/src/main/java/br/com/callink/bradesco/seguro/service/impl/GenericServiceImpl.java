package br.com.callink.bradesco.seguro.service.impl;

import java.util.List;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.service.IGenericService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Implementação genérica para Camada de Serviços
 * 
 * @author michael
 *
 * @param <T>
 */
public abstract class GenericServiceImpl<T> implements IGenericService<T> {

	@Override
	public ServiceResponse findByPK(Object pk) throws ServiceException {
		T result = getDAO().findByPK(pk);
		ServiceResponse response = ServiceResponseFactory.createWithData(result);
		
		if(result == null){
			return response.addWarning("Nenhum registro encontrado!");
		}
		
		return response;
	}
	
	/**
	 * Obtem o DAO que representa a camada de acesso a dados para o objeto de Dominio tambem 
	 * manipulado por esta Camada de Serviço
	 * @return
	 */
	protected abstract IGenericDAO<T> getDAO();

	@Override
	public ServiceResponse findByExample(T object) throws ServiceException {
		List<T> all = getDAO().findByExample(object);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);
		
		if(CollectionUtils.isEmpty(all)){
			return response.addWarning("Nenhum registro encontrado!");
		}
		
		return response;
	}
	
	@Override
	public ServiceResponse findAll() throws ServiceException {
		List<T> all = getDAO().findAll();
		return _find(all);
	}
	
	private ServiceResponse _find(List<T> all){
		ServiceResponse response = ServiceResponseFactory.createWithData(all);
		
		if(CollectionUtils.isEmpty(all)){
			return response.addWarning("Nenhum registro encontrado!");
		}
		
		return response;
	}
	
}