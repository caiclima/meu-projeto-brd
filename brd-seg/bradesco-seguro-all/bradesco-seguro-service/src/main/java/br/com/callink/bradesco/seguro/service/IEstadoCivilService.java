package br.com.callink.bradesco.seguro.service;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.EstadoCivil;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;


public interface IEstadoCivilService<T> extends IGenericCrudService<T> {

	List<EstadoCivil> findAllCacheable(EstadoCivil estadoCivil)
			throws ServiceException;

	List<EstadoCivil> findByExampleCacheable(EstadoCivil estadoCivil)
			throws ServiceException;

}
