package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Produto;

/**
 * Data Access Object (DAO) de Produto
 * 
 * @author neppo.oldamar
 *
 */
public interface IProdutoDAO extends IGenericDAO<Produto> {
	
	/**
	 * Verifica se existe Produto com o nome.
	 * 
	 * @param nomeProduto
	 * @return
	 */
	public Long existeProdutoComNome(String nomeProduto);
	
	/**
	 * Busca de Produto por exemplo ordernado por nome.
	 * 
	 * @param entity
	 * @return
	 */
	public List<Produto> findByExampleOrderByNome(Produto entity);
	
	/**
	 * Verifica se existe Produto vinculado a um plano específico.
	 * 
	 * @param id
	 * @return
	 */
	public Boolean existeProdutoVinculadoPlano(BigInteger id);
	
	/**
	 * Busca todos planos a partir de uma lista de PKs.
	 * 
	 * @param idProdutoList
	 * @return
	 * @throws DataException
	 */
	public List<Produto> findAllFromPkList(List<BigInteger> idProdutoList) throws DataException;
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public List<Produto> findByExampleExact(Produto object);
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	/**
	 * Busca de Produtos no Evento.
	 * 
	 * @param idProduto
	 * @return
	 */
	public List<Produto> findProdutosNoEvento(BigInteger idEvento);

	public List<Produto> findByNome(String nome);
	
}
