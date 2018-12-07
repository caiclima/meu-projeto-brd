package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IGrauParentescoService<T> extends IGenericCrudService<T> {
	
	ServiceResponse remover(GrauParentesco grauParentesco) throws ServiceException;
	
	ServiceResponse removerLogicamente(GrauParentesco grauParentesco) throws ServiceException;
	
	ServiceResponse findWithoutTitular() throws ServiceException;
	
	List<GrauParentesco> findAllCacheable()
			throws ServiceException;

	List<GrauParentesco> findByExampleCacheable(GrauParentesco grauParentesco)
			throws ServiceException;

	GrauParentesco findById(BigInteger id) throws ServiceException;
}
