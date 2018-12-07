package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Evento'
 * 
 * @author swb.thiagohenrique
 *
 */
public interface IEventoService<T> extends IGenericCrudService<T> {
	
	
	Evento findById(BigInteger eventoId) throws ServiceException;
	
	ServiceResponse buscarPorExemplo(Evento evento, Date dataCadastroInicio, Date dataCadastroFim) throws ServiceException;
	
	ServiceResponse salvarOuAtualizar(Evento evento, String usuarioHost, String usuarioLogado) throws ServiceException;
	
	ServiceResponse removerLogicamente(Evento evento, String usuarioHost, String usuarioLogado) throws ServiceException;
	/**
	 * Buscar Eventos na campanha
	 * 
	 * @param campanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	ServiceResponse findEventosNaCampanha(Campanha campanha) throws ServiceException, ValidationException;
	
	/**
	 * 
	 * @param idCampanha
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	List<Evento> findAllEventosByCampanha(BigInteger idCampanha) throws ServiceException, ValidationException;
	
	/**
	 * BUSCA TODOS OS EVENTOS DE AGENTE ATIVOS
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findAllEventosDeAgenteAtivos() throws ServiceException;

	/**
	 * @param idEventoSelecionado
	 * @param idTipoEventoSelecionado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(BigInteger idEventoSelecionado, BigInteger idTipoEventoSelecionado) throws ServiceException;

	/**
	 * @param eventi
	 * @return 
	 * @throws ServiceException
	 */
	ServiceResponse associarProdutoNoEvento(Evento evento) throws ServiceException;

	List<Evento> findEventosNaCampanhaByTipo(TipoEvento tipoEvento, Campanha campanha) throws ServiceException;
}
