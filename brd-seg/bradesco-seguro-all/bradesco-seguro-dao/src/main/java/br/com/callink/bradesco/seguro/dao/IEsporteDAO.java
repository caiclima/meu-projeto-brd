package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.Esporte;

/**
 * Data Access Object (DAO) de Esporte
 * 
 * @author neppo.oldamar
 *
 */
public interface IEsporteDAO extends IGenericDAO<Esporte> {

	List<Esporte> findByExample(Esporte esporte, String order);

}
