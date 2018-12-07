package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;

/**
 * 
 * @author swb.thiagohenrique
 *
 */
public interface IParametroSistemaDAO extends IGenericDAO<ParametroSistema> {
	
	public List<ParametroSistema> buscarParametroSistemaPorNome(String nome) throws DataException;

	/**
	 * Busca parametro pelo Nome informado
	 * 
	 * @param nomeParametro
	 * @return
	 */
	ParametroSistema buscarPorNome(String nomeParametro);

	public String findValorParametro(String nomeParametro);
}
