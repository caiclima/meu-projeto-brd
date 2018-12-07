package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IEventoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.IEventoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IProdutoEventoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Evento'
 * 
 * @author swb.thiagohenrique
 * 
 */
@Stateless
@Local(IEventoService.class)
public class EventoService extends GenericCrudServiceImpl<Evento> implements IEventoService<Evento> {

	@Inject
	private IEventoDAO dao;
	
	@EJB
	private IProdutoEventoService produtoEventoService;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected IEventoDAO getDAO() {
		return dao;
	}
	
	@Override
	public Evento findById(BigInteger eventoId) throws ServiceException{
		return (Evento) getDAO().findByPK(eventoId);
	}
	
	@Override
	public ServiceResponse buscarPorExemplo(Evento evento, Date dataCadastroInicio, Date dataCadastroFim) throws ServiceException {
		
		List<Evento> eventos;
		ServiceResponse response = null;
		try {
			eventos = getDAO().buscarPorExemplo(evento, dataCadastroInicio, dataCadastroFim);
			response = ServiceResponseFactory.createWithData(eventos);

			if (CollectionUtils.isEmpty(eventos)) {
				return response.addWarning("Nenhum registro encontrado!");
			}
		} catch (ParseException e) {
			throw new ServiceException("Data com formato inválido.");
		}
		
		return response;
	}
	
	@Override
	public ServiceResponse salvarOuAtualizar(Evento evento, String usuarioHost,
			String usuarioLogado) throws ServiceException {
		
		antesSalvar(evento);
		setLogPojo(evento, usuarioHost, usuarioLogado);
		evento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		if (evento.getId() == null || evento.getId().compareTo(BigInteger.ZERO) == 0) {
			getDAO().save(evento);
			aposSalvar(evento);
		} else {
			getDAO().update(evento);
			aposAtualizar(evento);
		}
		aposSalvar(evento);
		ServiceResponse response = ServiceResponseFactory.createWithData(evento);
		response.addInfo("Registro salvo com sucesso.");
		return response;
	}
	
	public ServiceResponse findEventosNaCampanha(Campanha campanha) throws ServiceException, ValidationException {
		 
		ServiceResponse response = null;
		
		if(campanha != null && campanha.getId() != null){
			
			try {
				List<Evento> eventos = getDAO().findEventosNaCampanha(campanha.getId());
				response = ServiceResponseFactory.createWithData(eventos);
			} catch (Exception e) {
				throw new ServiceException(e);
			}
		} else {
			throw new ValidationException("A campanha selecionada não é válida para esta pesquisa.");
		}
		return response;
		
	}
	
	@Override
	protected void antesSalvar(Evento evento) throws ServiceException {
		
		evento.setDataCadastro(new Date());
		if(evento.getIdAspect() != null && 
				evento.getIdAspect().compareTo(BigInteger.ZERO) == 0){
			
			evento.setIdAspect(null);
		}

		evento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		evento.setFlagRemoved(Boolean.FALSE);
		
		validar(evento);
	}
	
	@Override
	protected void antesAtualizar(Evento evento) throws ServiceException {
		
		evento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		validar(evento);
	}
	
	@Override
	public ServiceResponse removerLogicamente(Evento evento,
			String usuarioHost, String usuarioLogado) throws ServiceException {
		
		ServiceResponse serviceResponse;
		antesExcluir(evento);

		Evento eventoPersistence = getDAO().findByPK(evento.getId());
		eventoPersistence.setFlagRemoved(Boolean.TRUE);
		evento.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(evento, usuarioHost, usuarioLogado);
		getDAO().update(eventoPersistence);
		aposExcluir(evento);
		evento = eventoPersistence;

		serviceResponse = ServiceResponseFactory.createWithData(evento);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}
	

	@Override
	protected void antesExcluir(Evento evento) throws ServiceException {
		
		if (evento == null) {
			throw new ServiceException("[Evento] não informado para exclusão.");
		}
		if (evento.getId() == null) {
			throw new ServiceException("[Id do Evento] não informado para exclusão.");
		}
	}
	
	@Override
	protected void aposAtualizar(Evento entity) throws ServiceException {
		clearCacheEvento();
	}
	
	@Override
	protected void aposExcluir(Evento entity) throws ServiceException {
		clearCacheEvento();
	}
	
	@Override
	protected void aposSalvar(Evento entity) throws ServiceException {
		clearCacheEvento();
	}

	private void validar(Evento evento) throws ServiceException {
		
		if (evento == null) {
			throw new ServiceException("[Evento] não informado.");
		}
		if (StringUtils.isEmpty(evento.getNomeEvento())) {
			throw new ServiceException("[Nome do Evento] não informado.");
		}
		if (evento.getTipoEvento() == null || evento.getTipoEvento().getId() == null) {
			throw new ServiceException("[Tipo Evento] não informado.");
		}
		
		if (evento.getTipoFinalizacao() == null) {
			throw new ServiceException("[Tipo Finalização] não informado.");
		}
	}
	
	@Override
	public List<Evento> findAllEventosByCampanha(BigInteger idCampanha) throws ServiceException,
			ValidationException {
		
		try {
			return getDAO().findEventosNaCampanha(idCampanha);
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ServiceResponse findAllEventosDeAgenteAtivos() throws ServiceException {
		
		List<Evento> eventos = getDAO().findAllEventosDeAgenteAtivos();
		return ServiceResponseFactory.createWithData(eventos);
	}

	@Override
	public ServiceResponse findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(BigInteger idEventoSelecionado, BigInteger idTipoEventoSelecionado) throws ServiceException {
		
		List<Evento> eventos = getDAO().findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(idEventoSelecionado, idTipoEventoSelecionado);
		return ServiceResponseFactory.createWithData(eventos);
	}
	
	@Override
	public ServiceResponse associarProdutoNoEvento(Evento evento) throws ServiceException {
		
		produtoEventoService.associarProdutosNoEvento(evento);
		
		ServiceResponse response = ServiceResponseFactory.createWithData(evento);
		response.addInfo("Registro atualizado com sucesso.");
		return response;
	}
	
	private void clearCacheEvento() throws ServiceException {
		
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.EVENTO.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Evento> findEventosNaCampanhaByTipo(TipoEvento tipoEvento, Campanha campanha) throws ServiceException {
		return getDAO().findEventosNaCampanhaByTipo(tipoEvento, campanha);
	}

}