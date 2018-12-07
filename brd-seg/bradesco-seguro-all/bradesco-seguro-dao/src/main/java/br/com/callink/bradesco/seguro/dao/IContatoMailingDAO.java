package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.dao.exception.DataException;

public interface IContatoMailingDAO extends IGenericDAO<ContatoMailing>{
	
	/**
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws DataException
	 */
	List<ContatoMailing> findByIdClienteCampanha(BigInteger idClienteCampanha) throws DataException;

	/**
	 * retorna o contatoMaling pelo Id do cliente Campanha.
	 * @param idClienteCampanha
	 * @return
	 * @throws DataException
	 */
	ContatoMailing findContatoMailingByIdClienteCampanha(BigInteger idClienteCampanha) throws DataException;
	
	

}
