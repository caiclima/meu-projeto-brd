package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Profissao;

/**
 * Data Access Object (DAO) de Profissao
 * 
 * @author neppo.oldamar
 *
 */
public interface IProfissaoDAO extends IGenericDAO<Profissao> {
	
	/**
	 * Verifica se existe Profissao com o nome.
	 * 
	 * @param nomeProfissao
	 * @return
	 */
	public Long existeProfissaoComNome(String nomeProfissao);
	
	/**
	 * Busca de Profissao por exemplo ordernado por nome.
	 * 
	 * @param entity
	 * @return
	 */
	public List<Profissao> findByExampleOrderByNome(Profissao entity);
	
	/**
	 * Verifica se existe Profissao vinculado a uma venda específica.
	 * 
	 * @param id
	 * @return
	 */
	public Boolean existeProfissaoVinculadoVenda(BigInteger id);
	
	/**
	 * Verifica se existe Profissao vinculado a um dependente específico.
	 * 
	 * @param id
	 * @return
	 */
	public Boolean existeProfissaoVinculadoDependente(BigInteger id);
	
	/**
	 * Busca todos planos a partir de uma lista de PKs.
	 * 
	 * @param idProfissaoList
	 * @return
	 * @throws DataException
	 */
	public List<Profissao> findAllFromPkList(List<BigInteger> idProfissaoList) throws DataException;
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public List<Profissao> findByExampleExact(Profissao object);
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
}
