package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ITipoEventoDAO;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.ITipoEventoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Tipo Evento'
 * 
 * @author swb.thiagohenrique
 * 
 */
@Stateless
@Local(ITipoEventoService.class)
public class TipoEventoService extends GenericCrudServiceImpl<TipoEvento>
		implements ITipoEventoService<TipoEvento> {

	@Inject
	private ITipoEventoDAO dao;

	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected ITipoEventoDAO getDAO() {
		return dao;
	}
	
	@Override
	protected void antesSalvar(TipoEvento tipoEvento) throws ServiceException {

		tipoEvento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		tipoEvento.setFlagRemoved(Boolean.FALSE);
		validar(tipoEvento);
	}

	@Override
	protected void antesAtualizar(TipoEvento tipoEvento)
			throws ServiceException {
		
		tipoEvento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		validar(tipoEvento);
	}

	@Override
	public ServiceResponse remover(TipoEvento tipoEvento)
			throws ServiceException {

		ServiceResponse serviceResponse;
		antesExcluir(tipoEvento);
		getDAO().delete(tipoEvento);
		aposExcluir(tipoEvento);
			
		serviceResponse = ServiceResponseFactory.createWithData(tipoEvento);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	public ServiceResponse removerLogicamente(TipoEvento tipoEvento)
			throws ServiceException {
		
		ServiceResponse serviceResponse;
		antesExcluir(tipoEvento);
		
		TipoEvento tipoEventoPersistence = getDAO().findByPK(
				tipoEvento.getId());
		tipoEventoPersistence.setFlagRemoved(Boolean.TRUE);
		tipoEventoPersistence.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		getDAO().update(tipoEventoPersistence);
		aposExcluir(tipoEvento);
		tipoEvento = tipoEventoPersistence;
		
		serviceResponse = ServiceResponseFactory.createWithData(tipoEvento);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	protected void antesExcluir(TipoEvento tipoEvento) throws ServiceException {

		if (tipoEvento == null) {
			throw new ServiceException(
					"[Tipo Evento] não informado para exclusão.");
		}
		if (tipoEvento.getId() == null) {
			throw new ServiceException(
					"[Id do Tipo de Evento] não informado para exclusão.");
		}
	}

	private void validar(TipoEvento tipoEvento) throws ServiceException {

		if (tipoEvento == null) {
			throw new ServiceException("[Tipo Evento] não informado.");
		}
		if (StringUtils.isEmpty(tipoEvento.getNomeTipoEvento().trim())) {
			throw new ServiceException(
					"[Nome do Tipo de Evento] não informado.");
		}
		if (tipoEvento.getFlagEnabled() == null) {
			throw new ServiceException(
					"Não informado se [flag Tipo de Evento] está ativo ou não.");
		}
	}

	@Override
	public ServiceResponse findByExampleOrderByNome(TipoEvento entity)
			throws ServiceException {

		List<TipoEvento> all = getDAO().findByExampleOrderByNome(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	protected void aposSalvar(TipoEvento entity) throws ServiceException {
		clearCacheTipoEvento();
	}

	@Override
	protected void aposAtualizar(TipoEvento entity) throws ServiceException {
		clearCacheTipoEvento();
	}

	@Override
	protected void aposExcluir(TipoEvento entity) throws ServiceException {
		clearCacheTipoEvento();
	}

	private void clearCacheTipoEvento() throws ServiceException {

		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();

			final String urlString = urlCacheServ+ ClearCacheCommand.TIPO_EVENTO.getQueryString();

			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

        @Override
        public List<TipoEvento> findTiposEventosComEventos(Campanha campanha) throws ServiceException {
            return getDAO().findTiposEventosComEventos(campanha);
        }

}