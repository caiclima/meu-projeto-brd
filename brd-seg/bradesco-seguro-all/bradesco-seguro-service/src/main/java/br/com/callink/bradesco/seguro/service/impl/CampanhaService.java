package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ICampanhaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;
import br.com.callink.bradesco.seguro.service.ICampanhaService;
import br.com.callink.bradesco.seguro.service.IEventoCampanhaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
public class CampanhaService extends GenericCrudServiceImpl<Campanha> implements ICampanhaService {

	@Inject
	private ICampanhaDAO campanhaDAO;
	
	@EJB
	private IEventoCampanhaService eventoCampanhaService;

	protected ICampanhaDAO getDAO() {
		return campanhaDAO;
	}
	
	@Override
	public ServiceResponse findById(BigInteger idCampanha) throws ServiceException,
			ValidationException {
		
		if(idCampanha == null) {
			throw new ServiceException("Informe o Id da Campanha.");
		}
		Campanha campanha = getDAO().findById(idCampanha);
		
		ServiceResponse response = ServiceResponseFactory.createWithData(campanha);
		return response;
	}
	
	@Override
	protected void antesSalvar(Campanha campanha) throws ServiceException {
		
		validar(campanha);
		campanha.setFlagRemoved(Boolean.FALSE);
	}
	
	private void validar(Campanha campanha) throws ServiceException {
		
		if (campanha == null) {
			throw new ServiceException("[Campanha] não informada.");
		}
		if (StringUtils.isEmpty(campanha.getNomeCampanha())) {
			throw new ServiceException("[Nome da Campanha] não informado.");
		}
	}

	@Override
	public List<Campanha> findCampanhasByNome(String nome) throws ServiceException {
		
		try {
			return getDAO().findCampanhasByNome(nome);
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public ServiceResponse findByExampleOrderByNome(Campanha campanha) throws ServiceException {
		
		List<Campanha> all = getDAO().findByExampleOrderByNome(campanha);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ServiceResponse saveOrUpdate(Campanha campanha, String usuarioHost, String usuarioLogado) throws ServiceException {
		
		antesSalvar(campanha);
		setLogPojo(campanha, usuarioHost, usuarioLogado);
		campanha.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		if (campanha.getId() == null || campanha.getId().compareTo(BigInteger.ZERO) == 0) {
			getDAO().save(campanha);
		} else {
			getDAO().update(campanha);
		}
		ServiceResponse response = ServiceResponseFactory.createWithData(campanha);
		response.addInfo("Registro salvo com sucesso.");
		return response;
	}

	@Override
	public List<Campanha> findCampanhasAtivasOrderByNome() {
		return getDAO().findCampanhasAtivasOrderByNome();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Campanha cloneCampanha(Campanha campanha) throws ServiceException{
		
		try {
			List<EventoCampanha> eventoCampanhas = campanha.getEventoCampanhaList();
			
			Campanha newCampanha = campanha.clone();
			newCampanha.setEventoCampanhaList(new ArrayList<EventoCampanha>());
			
			for(EventoCampanha ec : eventoCampanhas){
				newCampanha.getEventoCampanhaList().add(ec.clone(ec.getEvento(), newCampanha));
			}
			return newCampanha;
		} catch (CloneNotSupportedException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ServiceResponse associarEventosNaCampanha(Campanha campanha) throws ServiceException {
		
		eventoCampanhaService.associarEventosNaCampanha(campanha);
		
		ServiceResponse response = ServiceResponseFactory.createWithData(campanha);
		response.addInfo("Registro atualizado com sucesso.");
		return response;
	}
	
	@Override
	public ServiceResponse removerLogicamente(Campanha campanha,
			String usuarioHost, String usuarioLogado) throws ServiceException {

		ServiceResponse serviceResponse;
		antesExcluir(campanha);

		Campanha campanhaPersistence = getDAO().findById(campanha.getId());
		campanhaPersistence.setFlagRemoved(Boolean.TRUE);
		campanha.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(campanha, usuarioHost, usuarioLogado);
		getDAO().update(campanhaPersistence);
		campanha = campanhaPersistence;

		serviceResponse = ServiceResponseFactory.createWithData(campanha);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}
	
	@Override
	protected void antesExcluir(Campanha campanha) throws ServiceException {
		
		if (campanha == null) {
			throw new ServiceException("[Campanha] não informado para exclusão.");
		}
		if (campanha.getId() == null) {
			throw new ServiceException("[Id da Campanh] não informado para exclusão.");
		}
	}
}

