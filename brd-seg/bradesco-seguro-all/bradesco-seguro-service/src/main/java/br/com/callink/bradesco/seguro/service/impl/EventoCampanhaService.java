package br.com.callink.bradesco.seguro.service.impl;

import java.util.List;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.dao.IEventoCampanhaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IEventoCampanhaService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
public class EventoCampanhaService extends GenericCrudServiceImpl<EventoCampanha> implements IEventoCampanhaService {
	
	@Inject
	private IEventoCampanhaDAO eventoCampanhaDAO;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	@Override
	protected IEventoCampanhaDAO getDAO() {
		return eventoCampanhaDAO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCampanhaEventoByCampanha(Campanha campanha) throws ServiceException, ValidationException {
		
		if(campanha != null && campanha.getId() != null){
			try {
				getDAO().deleteCampanhaEventoByCampanha(campanha);
				
				clearCacheEvento();
			} catch (DataException e) {
				throw new ServiceException(e);
			}
		} else {
			throw new ValidationException("Para esta ação não foi escolhida uma campanha válida.");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void associarEventosNaCampanha(Campanha campanha) throws ServiceException {
		
		try {
			
			if (campanha == null || campanha.getId() == null) {
				throw new ServiceException("A campanha selecionada é inválida");
			}
			
			getDAO().deleteCampanhaEventoByCampanha(campanha);
			
			for(EventoCampanha eventoCampanhaObject : campanha.getEventoCampanhaList()){
				if(eventoCampanhaObject != null){
					getDAO().salvarEventoCampanha(eventoCampanhaObject);
				}
			}
			
			clearCacheEvento();
			
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ServiceResponse associarCampanhaEvento(Campanha campanha, Evento evento) throws ServiceException {
		if (campanha == null || campanha.getId() == null) {
			throw new ServiceException("A campanha selecionada é inválida");
		}
		
		if (evento == null || evento.getId() == null) {
			throw new ServiceException("O evento selecionado é inválido");
		}
		
		EventoCampanha ec = new EventoCampanha(evento, campanha);
		
		ServiceResponse response = ServiceResponseFactory.createWithData(ec);
		response.addInfo("Registro deletado com sucesso.");
		
		clearCacheEvento();
		
		return response;
	}

	@Override
	public ServiceResponse desAssociarCampanhaEvento(Campanha campanha, Evento evento) throws ServiceException {
		if (campanha == null || campanha.getId() == null) {
			throw new ServiceException("A campanha selecionada é inválida");
		}
		
		if (evento == null || evento.getId() == null) {
			throw new ServiceException("O evento selecionado é inválido");
		}
		
		EventoCampanha ec = new EventoCampanha(evento, campanha);
		ServiceResponse temp = this.findByExample(ec);
		
		ec = (EventoCampanha) temp.getData();

		ServiceResponse response = ServiceResponseFactory.createWithData(ec);
		response.addInfo("Registro deletado com sucesso.");
		
		clearCacheEvento();
		
		return response;
	}

	@Override
	public ServiceResponse salvarEventoCampanha(List<EventoCampanha> lst) throws ServiceException {
		if(lst != null && lst.size() > 0){
			for (EventoCampanha eventoCampanha : lst) {
				this.save(eventoCampanha);
			}
		}
		
		clearCacheEvento();
		
		ServiceResponse response = ServiceResponseFactory.createWithData(Level.INFO);
		response.addInfo("Registro salvo com sucesso.");
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

}
