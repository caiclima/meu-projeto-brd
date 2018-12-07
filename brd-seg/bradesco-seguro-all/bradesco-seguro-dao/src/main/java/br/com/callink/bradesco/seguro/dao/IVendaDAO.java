package br.com.callink.bradesco.seguro.dao;

import java.util.Date;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Venda;

/**
 * Data Access Object (DAO) de Venda
 * 
 * @author neppo.oldamar
 *
 */
public interface IVendaDAO extends IGenericDAO<Venda> {

	void updateDataEnvioNuvem(Date dataEnvio) throws DataException;

}
