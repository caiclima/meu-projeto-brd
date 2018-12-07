package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.GrauParentesco;

/**
 * Data Access Object (DAO) de GrauParentesco
 * 
 * @author neppo.oldamar
 *
 */
public interface IGrauParentescoDAO extends IGenericDAO<GrauParentesco> {

	List<GrauParentesco> findWithoutTitular();

	List<GrauParentesco> findAllCacheable();

	List<GrauParentesco> findByExampleCacheable(GrauParentesco object);

}
