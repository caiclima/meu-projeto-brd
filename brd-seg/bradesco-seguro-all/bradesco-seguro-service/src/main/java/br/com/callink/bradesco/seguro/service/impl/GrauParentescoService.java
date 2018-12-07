package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IGrauParentescoDAO;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IGrauParentescoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Grau Parentesco'
 * 
 * @author neppo.oldamar
 * 
 */
@Stateless
@Local(IGrauParentescoService.class)
public class GrauParentescoService extends GenericCrudServiceImpl<GrauParentesco>
		implements IGrauParentescoService<GrauParentesco> {

	private final Logger logger = Logger.getLogger(GrauParentescoService.class);

	@Inject
	private IGrauParentescoDAO dao;

	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected IGrauParentescoDAO getDAO() {
		return dao;
	}
	
	@Override
	public GrauParentesco findById(BigInteger id) throws ServiceException{
		return (GrauParentesco) getDAO().findByPK(id);
	}
	
	@Override
	protected void antesSalvar(GrauParentesco grauParentesco) throws ServiceException {

		grauParentesco.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		validar(grauParentesco);
	}

	@Override
	protected void antesAtualizar(GrauParentesco grauParentesco)
			throws ServiceException {
		
		grauParentesco.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		validar(grauParentesco);
	}

	@Override
	public ServiceResponse remover(GrauParentesco grauParentesco)
			throws ServiceException {

		ServiceResponse serviceResponse;
		antesExcluir(grauParentesco);
		getDAO().delete(grauParentesco);
			
		serviceResponse = ServiceResponseFactory.createWithData(grauParentesco);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	public ServiceResponse removerLogicamente(GrauParentesco grauParentesco)
			throws ServiceException {
		
		ServiceResponse serviceResponse;
		antesExcluir(grauParentesco);
		
		GrauParentesco grauParentescoPersistence = getDAO().findByPK(grauParentesco.getId());
		grauParentescoPersistence.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		getDAO().update(grauParentescoPersistence);
		grauParentesco = grauParentescoPersistence;
		
		serviceResponse = ServiceResponseFactory.createWithData(grauParentesco);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	protected void antesExcluir(GrauParentesco grauParentesco) throws ServiceException {

		if (grauParentesco == null) {
			throw new ServiceException("[Grau Parentesco] não informado para exclusão.");
		}
		
		if (grauParentesco.getId() == null) {
			throw new ServiceException("[Id do Grau Parentesco] não informado para exclusão.");
		}
	}

	private void validar(GrauParentesco grauParentesco) throws ServiceException {

		if (grauParentesco == null) {
			throw new ServiceException("[Grau Parentesco] não informado.");
		}
		if (StringUtils.isEmpty(grauParentesco.getDescricao().trim())) {
			throw new ServiceException(
					"[Descrição do Grau Parentesco] não informado.");
		}
	}
	
	@Override
	public List<GrauParentesco> findAllCacheable() throws ServiceException {
		return getDAO().findAllCacheable();
	}
	
	@Override
	public List<GrauParentesco> findByExampleCacheable(GrauParentesco grauParentesco) throws ServiceException {
		return getDAO().findAllCacheable();
	}

	@Override
	protected void aposSalvar(GrauParentesco entity) throws ServiceException {
		clearCacheGrauParentesco();
	}

	@Override
	protected void aposAtualizar(GrauParentesco entity) throws ServiceException {
		clearCacheGrauParentesco();
	}

	@Override
	protected void aposExcluir(GrauParentesco entity) throws ServiceException {
		clearCacheGrauParentesco();
	}

	@Override
	public ServiceResponse findWithoutTitular() throws ServiceException {
		List<GrauParentesco> all = getDAO().findWithoutTitular();
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}
	
	private void clearCacheGrauParentesco() {

		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String urlString = urlCacheServ + ClearCacheCommand.GRAU_PARENTESCO.getQueryString();

			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}