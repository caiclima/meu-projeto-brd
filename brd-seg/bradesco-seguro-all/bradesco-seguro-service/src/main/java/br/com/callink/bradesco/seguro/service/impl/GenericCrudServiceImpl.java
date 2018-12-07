package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.entity.ILog;
import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;
import br.com.callink.bradesco.seguro.service.IGenericCrudService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.BundleHelper;
import br.com.callink.bradesco.seguro.service.utils.Constantes;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Implementação genérica para Camada de Serviços para operações de CRUD. Aplica
 * Template Method Design pattern para as operações de CRUD.
 * 
 * @author michael
 * @param <T>
 *            - tipo entidade
 */
public abstract class GenericCrudServiceImpl<T extends IdentifiableEntity<?>> extends GenericServiceImpl<T> implements IGenericCrudService<T> {

	protected final static String MSG_BUNDLE = "bundle";
	
    protected void setLogPojo(T pojo,String usuarioHost,String usuarioLogado) {
    	if (pojo instanceof ILog) {
            ILog log = (ILog) pojo;
            log.setLogHost(usuarioHost);
            log.setLogObs(pojo.toString());
            log.setLogSystem(Constantes.NOME_SISTEMA);
            log.setLogUid(usuarioLogado);
            log.setLogDate(new Date());
            log.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
        }
    }
    
    @Override
	public ServiceResponse save(T o, String usuarioHost, String usuarioLogado) throws ServiceException {
    
	    antesSalvar(o);
		setLogPojo(o, usuarioHost, usuarioLogado);
		getDAO().save(o);
		aposSalvar(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		response.addInfo("Registro salvo com sucesso.");
		return response;
    }
    
	@Override
	public ServiceResponse save(T o) throws ServiceException {
		antesSalvar(o);
		getDAO().save(o);
		aposSalvar(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		response.addInfo("Registro salvo com sucesso.");
		return response;
	}
    
    protected T validarSingleObject(List<T> list) throws ValidationException {
    	if(list.size() > 1) {
    		throw new ValidationException(BundleHelper
    				.getMessage("gerror_maisDeUmResultado", MSG_BUNDLE));
    	}
    	return list.isEmpty() ? null : list.get(0);
    }
    

	protected void antesSalvar(T entity) throws ServiceException {
	}

	protected void aposSalvar(T entity) throws ServiceException {
	}
	
	protected void antesAtualizar(T entity) throws ServiceException {
	}

	protected void aposAtualizar(T entity) throws ServiceException {
	}

	@Override
	public ServiceResponse saveOrUpdate(T o) throws ServiceException {
		antesSalvar(o);
		boolean isNew = (o.getId() == null);
		getDAO().saveOrUpdate(o);
		aposSalvar(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		
		if (isNew) {
			response.addInfo("Registro salvo com sucesso.");
		} else {
			response.addInfo("Registro atualizado com sucesso.");
		}
		return response;
	}
	
	@Override
	public ServiceResponse update(T o, String usuarioHost, String usuarioLogado) throws ServiceException {
    
	    antesAtualizar(o);
		setLogPojo(o, usuarioHost, usuarioLogado);
		getDAO().update(o);
		aposAtualizar(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		response.addInfo("Registro atualizado com sucesso.");
		return response;
    }

	@Override
	public ServiceResponse update(T o) throws ServiceException {
		antesAtualizar(o);
		getDAO().update(o);
		aposAtualizar(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		response.addInfo("Registro atualizado com sucesso.");
		return response;
	}

	@Override
	public ServiceResponse findByExample(T entity) throws ServiceException {
		List<T> all = getDAO().findByExample(entity);
		return _find(all);
	}

	@Override
	public ServiceResponse findAll() throws ServiceException {
		List<T> all = getDAO().findAll();
		return _find(all);
	}
	
	@Override
	public ServiceResponse findEnabledAndNotRemoved() throws ServiceException {
		List<T> all = getDAO().findEnabledAndNotRemoved();
		return _find(all);
	}

	private ServiceResponse _find(List<T> all) {
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse delete(T o) throws ServiceException {
		antesExcluir(o);
		getDAO().delete(o);
		aposExcluir(o);
		ServiceResponse response = ServiceResponseFactory.createWithData(o);
		response.addInfo("Registro deletado com sucesso.");
		return response;
	}
	
	@Override
	public ServiceResponse deleteLogico(T o, String usuarioHost, String usuarioLogado) throws ServiceException {
		ServiceResponse serviceResponse = new ServiceResponse();
		if(!(o instanceof IRemovableEntity)) {
			serviceResponse.addError("Não é possível realizar a remoção lógica com este registro.");
			return serviceResponse;
		}
		antesExcluir(o);
		
		((IRemovableEntity) o).setFlagRemoved(Boolean.TRUE);
		setLogPojo(o, usuarioHost, usuarioLogado);
		getDAO().update(o);

		serviceResponse = ServiceResponseFactory.createWithData(o);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	protected void antesExcluir(T entity) throws ServiceException {
	}

	protected void aposExcluir(T entity) throws ServiceException {
	}
}