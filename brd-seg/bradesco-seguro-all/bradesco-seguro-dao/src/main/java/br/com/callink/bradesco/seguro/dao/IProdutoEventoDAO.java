package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.ProdutoEvento;

/**
 * Data Access Object (DAO) de ProdutoEvento
 * 
 * @author neppo.oldamar
 *
 */
public interface IProdutoEventoDAO extends IGenericDAO<ProdutoEvento> {
	
	void deleteEventoProdutoByEvento(Evento evento);

	List<ProdutoEvento> buscarPorEvento(Evento evento);

}
