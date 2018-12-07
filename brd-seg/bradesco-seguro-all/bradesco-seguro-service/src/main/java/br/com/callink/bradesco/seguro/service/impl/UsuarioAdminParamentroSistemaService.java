package br.com.callink.bradesco.seguro.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.IUsuarioAdminParamentroSistemaDAO;
import br.com.callink.bradesco.seguro.entity.UsuarioAdminParametroSistema;
import br.com.callink.bradesco.seguro.service.IUsuarioAdminParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IUsuarioAdminParametroSistemaService.class)
public class UsuarioAdminParamentroSistemaService extends
		GenericCrudServiceImpl<UsuarioAdminParametroSistema> implements
		IUsuarioAdminParametroSistemaService<UsuarioAdminParametroSistema> {

	@Inject
	private IUsuarioAdminParamentroSistemaDAO dao;

	@Override
	protected IUsuarioAdminParamentroSistemaDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarUsuarioAdminPorLogin(String login) throws ServiceException {
		
		UsuarioAdminParametroSistema usuarioAdminParametroSistema = getDAO().buscarUsuarioAdminPorLogin(login);
		ServiceResponse serviceResponse = null;
		if (usuarioAdminParametroSistema != null){
			serviceResponse = ServiceResponseFactory.createWithData(usuarioAdminParametroSistema);
		} else {
			serviceResponse = ServiceResponseFactory.create();
			serviceResponse.addInfo("Nenhum registro encontrado");
		}
		return serviceResponse;
	}

}
