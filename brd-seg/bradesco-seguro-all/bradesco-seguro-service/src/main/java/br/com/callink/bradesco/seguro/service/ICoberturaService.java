package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.Cobertura;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface ICoberturaService<T> extends IGenericCrudService<T> {

	ServiceResponse salvar(Cobertura cobertura, String usuarioHost,
			String usuarioLogado) throws ServiceException;
}
