package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;

/**
 * Data Access Object (DAO) de Plano
 * 
 * @author neppo.oldamar
 *
 */
public interface IPlanoDAO extends IGenericDAO<Plano> {
	
	List<Plano> buscarPlanoPorProduto(Produto produto);

	List<Plano> buscarPlanoPorProdutoAndIdade(Produto produto, String idade);
	
	List<Plano> findByExample(Plano object);
	
	List<Plano> findByExample(Plano plano, String orderBy);
	
	Boolean existeCodigoPlano(Plano plano);

}
