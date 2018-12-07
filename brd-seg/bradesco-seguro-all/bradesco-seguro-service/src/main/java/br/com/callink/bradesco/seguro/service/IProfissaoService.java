package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Profissao;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Representa a interface do service de 'Profissao'
 * 
 * @author neppo.oldamar
 *
 */
public interface IProfissaoService<T> extends IGenericCrudService<T> {
	
	/**
	 * 
	 * Busca Todas Profissoes
	 * 
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse buscarTodasProfissoes() throws ServiceException;
	
	/**
	 * 
	 * Busca Profissao por id
	 * 
	 * @param idProfissao
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse buscaProfissaoPorId(BigInteger idProfissao) throws ServiceException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findByExampleOrderByNome(Profissao entity) throws ServiceException;
	
	/**
	 * 
	 * @param vendaList
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	List<Profissao> findByVendaList(List<Venda> vendaList) throws ServiceException, ValidationException;
	
	/**
	 * 
	 * @param profissao
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse salvar(Profissao profissao,String usuarioHost, String usuarioLogado) throws ServiceException;
	
	/**
	 * 
	 * @param profissao
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse atualizar(Profissao profissao,String usuarioHost,String usuarioLogado) throws ServiceException;
	
	ServiceResponse remover(Profissao profissao, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	ServiceResponse removerLogicamente(Profissao profissao, String usuarioHost,
			String usuarioLogado) throws ServiceException;
	
}

