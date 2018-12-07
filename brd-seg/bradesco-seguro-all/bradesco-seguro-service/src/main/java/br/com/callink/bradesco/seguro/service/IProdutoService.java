package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Produto'
 * 
 * @author neppo.oldamar
 *
 */
public interface IProdutoService<T> extends IGenericCrudService<T> {
	
	/**
	 * 
	 * Busca Todos Produtos
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscarTodosProdutos() throws ServiceException;
	
	/**
	 * 
	 * Busca Produto por id
	 * 
	 * @param idProduto
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse buscaProdutoPorId(BigInteger idProduto) throws ServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	public ServiceResponse findByExampleOrderByNome(Produto entity) throws ServiceException;
	
	/**
	 * 
	 * @param eventoList
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public List<Produto> findByPlanoList(List<Plano> planoList) throws ServiceException, ValidationException;
	
	
	/**
	 * Buscar Produtos no evento
	 * 
	 * @param evento
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public ServiceResponse findProdutosNoEvento(Evento evento) throws ServiceException, ValidationException;

	public ServiceResponse findByNomeProduto(String nomeProduto) throws ServiceException;
	
}
