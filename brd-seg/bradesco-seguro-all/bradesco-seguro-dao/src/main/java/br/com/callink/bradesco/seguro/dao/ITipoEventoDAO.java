package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.TipoEvento;

/**
 * Data Access Object (DAO) de Tipo Evento
 * 
 * @author swb.thiagohenrique
 *
 */
public interface ITipoEventoDAO extends IGenericDAO<TipoEvento> {
	
	/**
	 * Busca de Tipo de Evento por exemplo ordernado por nome.
	 * 
	 * @param entity
	 * @return
	 */
	List<TipoEvento> findByExampleOrderByNome(TipoEvento entity);
	
	/**
	 * Busca todos tipo evento a partir de uma lista de PKs.
	 * 
	 * @param idTipoEventoList
	 * @return
	 * @throws DataException
	 */
	List<TipoEvento> findAllFromPkList(List<BigInteger> idTipoEventoList) throws DataException;
	
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
	List<TipoEvento> findByExampleExact(TipoEvento object);
	
        
        List<TipoEvento> findTiposEventosComEventos(Campanha campanha) throws DataException ;
}
