package br.com.callink.bradesco.seguro.dao;

import java.util.List;

/**
 * DAO generico
 * 
 * @author michael
 * @param <T>  - tipo entidade
 * @param <PK> - tipo PK entidade
 */
public interface IGenericDAO<T> {

	/**
	 * Busca todos os objetos do tipo T
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * Salva o objeto informado
	 * @param object
	 * @return objeto persistido.
	 */
	T save(final T object);

	/**
	 * Atualiza o objeto informado
	 * 
	 * @param object
	 */
	void update(final T object);

	/**
	 * Salva ou atualiza o objeto informado, baseado na existencia previa do
	 * mesmo.
	 * 
	 * @param object
	 * @return - objeto persistido ou atualizado.
	 */
	T saveOrUpdate(final T object);

	/**
	 * Exclui o objeto informado.
	 * 
	 * @param object
	 */
	void delete(final T object);
	

	/**
	 * Busca o objeto por usa primary key (informada)
	 * @param pk
	 * @return
	 */
	T findByPK(Object pk);

	/**
	 * Carrega os objetos com propriedades iguais ao objeto informado
	 * 
	 * @param object
	 * @return
	 */
	List<T> findByExample(T object);
	
	List<T> findEnabledAndNotRemoved();

	void clearCacheRegion(String region);

	void clearCacheEntity();
}