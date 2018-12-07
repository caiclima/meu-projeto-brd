package br.com.callink.bradesco.seguro.service;

import java.util.List;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Evento Campanha'
 * 
 * @author swb.thiagohenrique
 *
 */
public interface IEventoCampanhaService extends IGenericService<EventoCampanha> {
	
	/**
	 * Remover a ligação entre Campanha e Evento a partir da campanha.
	 * 
	 * @param campanha
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	void deleteCampanhaEventoByCampanha(Campanha campanha) throws ServiceException, ValidationException;
	
	/**
	 * 
	 * @param eventos
	 * @param campanha
	 * @throws ServiceException
	 */
	void associarEventosNaCampanha(Campanha campanha) throws ServiceException;

	/**
	 * @param campanha
	 * @param evento
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse associarCampanhaEvento(Campanha campanha, Evento evento) throws ServiceException;

	/**
	 * @param campanha
	 * @param evento
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse desAssociarCampanhaEvento(Campanha campanha, Evento evento) throws ServiceException;

	/**
	 * @param lst
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse salvarEventoCampanha(List<EventoCampanha> lst) throws ServiceException;
	
}
