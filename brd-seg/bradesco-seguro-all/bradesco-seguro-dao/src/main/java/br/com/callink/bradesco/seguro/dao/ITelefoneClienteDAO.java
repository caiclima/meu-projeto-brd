package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;


public interface ITelefoneClienteDAO extends IGenericDAO<TelefoneCliente> {
	
	/**
	 * 
	 * @param idClienteCampanha
	 * @return
	 * @throws DataException
	 */
	List<TelefoneCliente> findTelefonesCadastrados(BigInteger idClienteCampanha) throws DataException;
	
	/**
	 * Busca de telefones por cnpj.
	 * 
	 * @param cnpj
	 * @return
	 * @throws DataException
	 */
	List<TelefoneCliente> buscarTelefoneCliente(String cnpj) throws DataException;

}
