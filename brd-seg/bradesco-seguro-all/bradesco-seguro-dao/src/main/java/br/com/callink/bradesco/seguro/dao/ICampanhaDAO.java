package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;

/**
 * Data Access Object (DAO) de Campanha
 * 
 * @author swb.thiagohenrique
 *
 */
public interface ICampanhaDAO extends IGenericDAO<Campanha> {
	
	/**
	 * Busca de Tipo de Evento pelo Id.
	 * 
	 * @param idCampanha
	 * @return
	 */
	Campanha findById(BigInteger idCampanha);
	
	/**
	 * retorna uma lista de campanhas buscando pelo nome.
	 * @param nome
	 * @return
	 * @throws DataException
	 */
	List<Campanha> findCampanhasByNome(String nome) throws DataException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	List<Campanha> findByExampleOrderByNome(Campanha entity);
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Campanha> findByExampleExact(Campanha object);
	
	/**
	 * Busca todas as campanhas ativas ordenando por nome
	 * @return
	 */
	List<Campanha> findCampanhasAtivasOrderByNome();

}
