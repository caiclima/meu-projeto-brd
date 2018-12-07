package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IParametroSistemaDAO;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.enums.ParametroSistemaEnum;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a Camada de Serviço para o Domínio 'Parâmetro de Sistema'
 * 
 * @author michael
 * 
 */
@Stateless
@Local(IParametroSistemaService.class)
public class ParametroSistemaService extends GenericCrudServiceImpl<ParametroSistema> implements IParametroSistemaService<ParametroSistema> {

	@Inject
	private IParametroSistemaDAO dao;

	@Override
	public IParametroSistemaDAO getDAO() {
		return this.dao;
	}

	@Override
	public ServiceResponse save(ParametroSistema o) throws ServiceException {
		return super.save(o);
	}

	@Override
	public ServiceResponse salvar(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException {
		
		parametroSistema.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(parametroSistema, usuarioHost, usuarioLogado);
		return super.save(parametroSistema);
	}

	@Override
	public ServiceResponse atualizar(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException {
		
		parametroSistema.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(parametroSistema, usuarioHost, usuarioLogado);
		return super.update(parametroSistema);
	}

	@Override
	public ServiceResponse remover(ParametroSistema parametroSistema, String usuarioHost, String usuarioLogado) 
			throws ServiceException {
		
		parametroSistema.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(parametroSistema, usuarioHost, usuarioLogado);
		return super.delete(parametroSistema);
	}
	
	@Override
	public ParametroSistema buscarPorNome(String nomeParametro) throws ServiceException {
		
		if (StringUtils.isEmpty(nomeParametro)) {
			throw new ServiceException("Nome do Parâmetro de Sistema deve ser informado.");
		}
		return getDAO().buscarPorNome(nomeParametro);
	}

	@Override
	protected void antesSalvar(ParametroSistema parametroSistema) throws ServiceException {
		validar(parametroSistema);
	}
	
	@Override
	protected void antesAtualizar(ParametroSistema parametroSistema)
			throws ServiceException {
		validar(parametroSistema);
	}

	private void validar(ParametroSistema parametroSistema)
			throws ServiceException {
		
		if (StringUtils.isEmpty(parametroSistema.getNomeParametroSistema())) {
			throw new ServiceException("[O Nome do Parâmetro] deve ser informado.");
		}
		if (StringUtils.isEmpty(parametroSistema.getValorParametroSistema())) {
			throw new ServiceException("[Valor do Parâmetro] deve ser informado.");
		}
		if (parametroSistema.getFlagAdminRole() == null) {
			throw new ServiceException("[Parametro de Administrador] deve ser informado.");
		}
	}

	public String buscarStatusPropostaAtivo() throws ValidationException {

		List<ParametroSistema> parametroSistemaList = dao.buscarParametroSistemaPorNome(ParametroSistemaEnum.STATUS_PROPOSTA_ATIVOS.value());

		if (parametroSistemaList == null || parametroSistemaList.isEmpty()) {
			throw new ValidationException("Parâmetro de Sistema não existe.");
		}
		return parametroSistemaList.get(0).getValorParametroSistema();
	}

	public String buscarStatusPropostaConcluida() throws ValidationException {

		List<ParametroSistema> parametroSistemaList = dao.buscarParametroSistemaPorNome(ParametroSistemaEnum.STATUS_PROPOSTA_CONCLUIDA.value());

		if (parametroSistemaList == null || parametroSistemaList.isEmpty()) {
			throw new ValidationException("Parâmetro de Sistema não existe.");
		}
		return parametroSistemaList.get(0).getValorParametroSistema();
	}
	
	@Override
	public String buscarValorParametro(String nomeParametro) throws ServiceException {
		return getDAO().findValorParametro(nomeParametro);
	}
	
	@Override
	protected void aposAtualizar(ParametroSistema parametroSistema) throws ServiceException {
		clearCacheParametroSistema();
	}
	
	@Override
	protected void aposExcluir(ParametroSistema parametroSistema) throws ServiceException {
		clearCacheParametroSistema();
	}
	
	@Override
	protected void aposSalvar(ParametroSistema parametroSistema) throws ServiceException {
		clearCacheParametroSistema();
	}
	
	private void clearCacheParametroSistema() throws ServiceException {
		try {
			//final String urlCacheServ = dao.buscarPorNome("URL_CACHE_SERVLET_LOCAL").getValorParametroSistema();
			final String urlCacheServ = dao.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = dao.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = dao.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.PARAMETRO_SISTEMA.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}