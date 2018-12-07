package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IUsuarioAdminParametroSistemaService<T> extends
		IGenericCrudService<T> {

	ServiceResponse buscarUsuarioAdminPorLogin(String login) throws ServiceException;
}
