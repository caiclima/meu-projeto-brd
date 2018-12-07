package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IEsporteDAO;
import br.com.callink.bradesco.seguro.entity.Esporte;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IEsporteService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
public class EsporteService extends GenericCrudServiceImpl<Esporte> implements
		IEsporteService<Esporte> {

	@Inject
	private IEsporteDAO dao;

	@Override
	protected IEsporteDAO getDAO() {
		return dao;
	}
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	public ServiceResponse buscarTodosEsportes() throws ServiceException {

		List<Esporte> esportes = getDAO().findAll();
		ServiceResponse serviceReponse = ServiceResponseFactory
				.createWithData(esportes);

		if (CollectionUtils.isEmpty(esportes)) {
			serviceReponse.addWarning("Nenhum registro encontrado!");
		}
		return serviceReponse;
	}

	@Override
	public ServiceResponse buscarEsportePorId(BigInteger idEsporte)
			throws ServiceException {

		Esporte esporte = getDAO().findByPK(idEsporte);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(esporte);

		if (esporte == null) {
			serviceResponse.addWarning("Nenhum registro encontrado!");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarPorExemplo(Esporte esporte)
			throws ServiceException {

		List<Esporte> esportes = getDAO().findByExample(esporte);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(esportes);

		if (CollectionUtils.isEmpty(esportes)) {
			serviceResponse.addWarning("Nenhum registro encontrado!");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvar(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesSalvar(esporte);

		setLogPojo(esporte, usuarioHost, usuarioLogado);
		esporte.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		getDAO().save(esporte);
		
		aposSalvar(esporte);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(esporte);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesSalvar(Esporte esporte) throws ServiceException {
		
		validarVazio(esporte, "Informe o esporte.");
		validarVazio(esporte.getNome(), "Informe o Nome.");
		validarVazio(esporte.getCodigo(), "Informe o código.");
		esporte.setFlagRemoved(Boolean.FALSE);
	}

	@Override
	public ServiceResponse atualizar(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesAtualizar(esporte);

		setLogPojo(esporte, usuarioHost, usuarioLogado);
		esporte.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));

		getDAO().update(esporte);
		
		aposAtualizar(esporte);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(esporte);
		serviceResponse.addInfo("Registro atualizado com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(Esporte esporte) throws ServiceException {

		validarVazio(esporte, "Informe o esporte.");
		validarVazio(esporte.getNome(), "Informe o nome.");
		validarVazio(esporte.getCodigo(), "Informe o código.");
	}

	@Override
	public ServiceResponse remover(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesExcluir(esporte);

		setLogPojo(esporte, usuarioHost, usuarioLogado);
		esporte.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));

		getDAO().delete(esporte);
		
		aposExcluir(esporte);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(esporte);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}
	
	@Override
	public ServiceResponse removerLogicamente(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException {
		
		ServiceResponse serviceResponse;
		antesExcluir(esporte);

		Esporte esportePersistence = getDAO()
				.findByPK(esporte.getId());
		esportePersistence.setFlagRemoved(Boolean.TRUE);
		esporte.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(esporte, usuarioHost, usuarioLogado);
		getDAO().update(esportePersistence);
		
		aposExcluir(esporte);
		
		esporte = esportePersistence;

		serviceResponse = ServiceResponseFactory.createWithData(esporte);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
		
	}

	@Override
	protected void antesExcluir(Esporte esporte) throws ServiceException {

		validarVazio(esporte, "Informe o esporte.");
		validarVazio(esporte.getId(), "Informe o id do esporte.");
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private void validarVazio(String obj, String mensagem)
			throws ServiceException {

		if (StringUtils.isEmpty(obj)) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}
	
	@Override
	protected void aposSalvar(Esporte entity) throws ServiceException {
		clearCacheEsporte();
	}
	
	@Override
	protected void aposAtualizar(Esporte entity) throws ServiceException {
		clearCacheEsporte();
	}
	
	@Override
	protected void aposExcluir(Esporte entity) throws ServiceException {
		clearCacheEsporte();
	}
	
	private void clearCacheEsporte() throws ServiceException {
		
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.ESPORTE.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ServiceResponse findByExampleOrderByNome(Esporte esporte)
			throws ServiceException {

		List<Esporte> all = getDAO().findByExample(esporte, "nome");
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

}
