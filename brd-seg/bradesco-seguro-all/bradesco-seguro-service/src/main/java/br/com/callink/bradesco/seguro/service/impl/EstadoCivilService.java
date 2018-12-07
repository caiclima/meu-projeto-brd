package br.com.callink.bradesco.seguro.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.dao.IEstadoCivilDAO;
import br.com.callink.bradesco.seguro.entity.EstadoCivil;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IEstadoCivilService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

@Stateless
@Local(IEstadoCivilService.class)
public class EstadoCivilService extends GenericCrudServiceImpl<EstadoCivil> implements IEstadoCivilService<EstadoCivil> {
	
	@Inject
	private IEstadoCivilDAO dao;

	@Override
	protected IEstadoCivilDAO getDAO() {
		return dao;
	}
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	private final Logger logger = Logger.getLogger(EstadoCivilService.class);
	
	@Override
	protected void aposSalvar(EstadoCivil entity) throws ServiceException {
		clearCacheEstadoCivil();
	}
	
	@Override
	protected void aposAtualizar(EstadoCivil entity) throws ServiceException {
		clearCacheEstadoCivil();
	}
	
	@Override
	protected void aposExcluir(EstadoCivil entity) throws ServiceException {
		clearCacheEstadoCivil();
	}
	
	@Override
	public List<EstadoCivil> findAllCacheable(EstadoCivil estadoCivil) throws ServiceException {
		return getDAO().findAllCacheable();
	}
	
	@Override
	public List<EstadoCivil> findByExampleCacheable(EstadoCivil estadoCivil) throws ServiceException {
		return getDAO().findAllCacheable();
	}
	
	private void clearCacheEstadoCivil() {
		
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.ESTADO_CIVIL.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
