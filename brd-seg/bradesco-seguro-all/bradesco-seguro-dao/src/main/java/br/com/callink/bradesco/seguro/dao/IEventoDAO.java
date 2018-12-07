package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.TipoEvento;

/**
 * Data Access Object (DAO) de Evento
 * 
 * @author swb.thiagohenrique
 *
 */
public interface IEventoDAO extends IGenericDAO<Evento> {
	
	List<Evento> buscarPorExemplo(Evento evento, Date dataCadastroInicio, Date dataCadastroFim) throws ParseException;
	
	/**
	 * Busca de Eventos na campanha.
	 * 
	 * @param idCampanha
	 * @return
	 */
	List<Evento> findEventosNaCampanha(BigInteger idCampanha);
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	<O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Evento> findByExampleExact(Evento object);
	
	/**
	 * BUSCA TODOS OS EVENTOS DE AGENTE ATIVOS
	 * @return
	 * @throws DataException
	 */
	List<Evento> findAllEventosDeAgenteAtivos() throws DataException;

	/**
	 * @param idEventoSelecionado
	 * @param idTipoEventoSelecionado
	 * @return
	 * @throws DataException
	 */
	List<Evento> findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(BigInteger idEventoSelecionado, BigInteger idTipoEventoSelecionado)  throws DataException;

	List<Evento> findEventosNaCampanhaByTipo(TipoEvento tipoEvento, Campanha campanha);

}
