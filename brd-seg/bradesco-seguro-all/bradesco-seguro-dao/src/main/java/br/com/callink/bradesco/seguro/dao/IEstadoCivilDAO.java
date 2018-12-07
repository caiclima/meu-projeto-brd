package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.EstadoCivil;

/**
 * Data Access Object (DAO) de EstadoCivil
 * 
 * @author neppo.oldamar
 *
 */
public interface IEstadoCivilDAO extends IGenericDAO<EstadoCivil> {

	List<EstadoCivil> findAllCacheable();

	List<EstadoCivil> findByExampleCacheable(EstadoCivil object);

}
