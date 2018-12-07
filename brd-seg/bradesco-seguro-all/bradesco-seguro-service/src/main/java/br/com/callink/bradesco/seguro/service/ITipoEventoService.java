package br.com.callink.bradesco.seguro.service;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Tipo Evento'
 * 
 * @author swb.thiagohenrique
 *
 */
public interface ITipoEventoService<T> extends IGenericCrudService<T> {
	
	ServiceResponse findByExampleOrderByNome(TipoEvento tipoEvento) throws ServiceException;
	
	ServiceResponse remover(TipoEvento tipoEvento) throws ServiceException;
	
	ServiceResponse removerLogicamente(TipoEvento tipoEvento) throws ServiceException;
	
    List<TipoEvento> findTiposEventosComEventos(Campanha campanha)throws ServiceException;
}
