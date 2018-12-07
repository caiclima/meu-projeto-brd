package br.com.callink.bradesco.seguro.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.IAgenteDAO;
import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.entity.Agente;
import br.com.callink.bradesco.seguro.service.IAgenteService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

@Stateless
@Local(IAgenteService.class)
public class AgenteService extends GenericCrudServiceImpl<Agente> 
	implements IAgenteService{

	@Inject
	private IAgenteDAO dao;
	
	@Override
	protected IGenericDAO<Agente> getDAO() {
		return dao;
	}

	@Override
	public Agente buscarAgente(String logUid) throws ServiceException {
		Agente agente = new Agente();
		agente.setNomeAgente(logUid);
		
		List<Agente> agentes = dao.findByExample(agente);
		
		return agentes != null && agentes.size() > 0 ? agentes.get(0) : null;
	}

	@Override
	public Agente salvarAgente(Agente agente) throws ServiceException {

		return dao.save(agente);
		
	}
	
}
