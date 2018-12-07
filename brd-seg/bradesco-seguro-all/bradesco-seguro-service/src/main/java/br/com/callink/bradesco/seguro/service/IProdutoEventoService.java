package br.com.callink.bradesco.seguro.service;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.ProdutoEvento;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Produto Evento'
 * 
 * @author neppo.oldamar
 *
 */
public interface IProdutoEventoService extends IGenericService<ProdutoEvento> {
	
	/**
	 * Remover a ligação entre Evento e Produto a partir da Evento.
	 * 
	 * @param evento
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	void deleteEventoProdutoByEvento(Evento evento) throws ServiceException, ValidationException;
	
	/**
	 * 
	 * @param eventos
	 * @param produto
	 * @throws ServiceException
	 */
	void associarProdutosNoEvento(Evento evento) throws ServiceException;

	/**
	 * @param produto
	 * @param evento
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse associarEventoProduto(Evento evento, String usuarioHost, String usuarioLogado) throws ServiceException;

	/**
	 * @param produto
	 * @param evento
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse desAssociarEventoProduto( Evento evento, Produto produto, String usuarioHost, String usuarioLogado) throws ServiceException;

	/**
	 * @param lst
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse salvarProdutoEvento(List<ProdutoEvento> lst) throws ServiceException;

	ServiceResponse buscarPorEvento(Evento entity) throws ServiceException;
	
}
