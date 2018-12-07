package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.Agente;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

public interface IAgenteService extends IGenericCrudService<Agente> {

		public Agente buscarAgente(String logUid) throws ServiceException;
		
		public Agente salvarAgente(Agente agente) throws ServiceException;
}
