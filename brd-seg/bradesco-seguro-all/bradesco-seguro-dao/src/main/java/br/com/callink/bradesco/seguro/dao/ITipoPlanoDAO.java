package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.TipoPlano;

/**
 * Data Access Object (DAO) de Tipo Plano
 * 
 * @author swb.thiagohenrique
 *
 */
public interface ITipoPlanoDAO extends IGenericDAO<TipoPlano> {
	
	/**
	 * Buscar todos Tipo Plano.
	 * 
	 * @return
	 */
	public List<TipoPlano> buscarTodosTiposPlano();
	
	/**
	 * Busca de Tipo de Plano por id.
	 * 
	 * @param idTipoPlano
	 * @return
	 */
	public TipoPlano buscaTipoPlanoPorId(BigInteger idTipoPlano);
	
	/**
	 * Verifica se existe Tipo de Plano com o nome.
	 * 
	 * @param nomeTipoPlano
	 * @return
	 */
	public Long existeTipoPlanoComNome(String nomeTipoPlano);
	
	/**
	 * Busca de Tipo de Plano por exemplo ordernado por nome.
	 * 
	 * @param entity
	 * @return
	 */
	public List<TipoPlano> findByExampleOrderByNome(TipoPlano entity);
	
	/**
	 * Verifica se existe Tipo de Plano vinculado a um plano específico.
	 * 
	 * @param id
	 * @return
	 */
	public Boolean existeTipoPlanoVinculadoPlano(BigInteger id);
	
	/**
	 * Busca todos tipo plano a partir de uma lista de PKs.
	 * 
	 * @param idTipoPlanoList
	 * @return
	 * @throws DataException
	 */
	public List<TipoPlano> findAllFromPkList(List<BigInteger> idTipoPlanoList) throws DataException;
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public List<TipoPlano> findByExampleExact(TipoPlano object);
	
}
