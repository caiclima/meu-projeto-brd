package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Cargo;

/**
 * Data Access Object (DAO) de Cargo
 * 
 * @author neppo.oldamar
 * 
 */
public interface ICargoDAO extends IGenericDAO<Cargo> {

	/**
	 * 
	 * @param object
	 * @return
	 */
	public List<Cargo> findByExampleExact(Cargo object);

	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	/**
	 * 
	 * @param idCargoCorporativo
	 * @return
	 */
	public BigInteger findIdCargo(BigInteger idCargoCorporativo);
	
	
	public List<Cargo> findCargosByIds(List<BigInteger> ids);

}
