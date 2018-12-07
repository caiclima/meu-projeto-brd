package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IDominioService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodosDominios() throws ServiceException;

	ServiceResponse buscarDominioPorId(BigInteger idDominio)
			throws ServiceException;

	ServiceResponse salvar(Dominio dominio) throws ServiceException;

	ServiceResponse atualizar(Dominio dominio) throws ServiceException;

	ServiceResponse remover(Dominio dominio) throws ServiceException;
	
	BigInteger buscarIdDominio(BigInteger idDominioCorporativo) throws ServiceException;

}
