package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IProdutoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IProdutoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Produto'
 * 
 * @author neppo.oldamar
 * 
 */
@Stateless
@Local(IProdutoService.class)
public class ProdutoService extends GenericCrudServiceImpl<Produto> implements IProdutoService<Produto> {
	
	@Inject
	private IProdutoDAO dao;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected IProdutoDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodosProdutos() throws ServiceException {
		List<Produto> all = getDAO().findAll();
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse buscaProdutoPorId(BigInteger idProduto) throws ServiceException {

		Produto produto = getDAO().findByPK(idProduto);

		ServiceResponse response = ServiceResponseFactory.createWithData(produto);

		if (produto == null) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse findByExample(Produto entity) throws ServiceException {
		List<Produto> all = getDAO().findByExample(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	protected void antesAtualizar(Produto produto) throws ServiceException {
		validarAtualizacao(produto);
		produto.setDescricao(produto.getDescricao().trim());
		
//		if (produto.getId() != null) {
//			Produto previous = getDAO().getFromPersistenceContext(Produto.class, produto.getId());
//			if (previous != null) {
//				String nomeProduto = produto.getDescricao().trim();
//				if (!previous.getDescricao().trim().equalsIgnoreCase(nomeProduto)) {
//					
//					boolean jaExiste = (getDAO().existeProdutoComNome(produto.getDescricao()) > 0) ? true : false;
//					if (jaExiste) {
//						throw new ServiceException("Já existe Produto com a descrição [" + nomeProduto + "]. Outra descrição deve ser informada.");
//					}
//				}
//			}
//		}
	}

	private void validarAtualizacao(Produto produto) throws ServiceException{
		validar(produto);
		
		if (produto.getId() == null) {
			throw new ServiceException("Não foi possível atualizar");
		}
	}

	@Override
	protected void antesSalvar(Produto produto) throws ServiceException {
		validar(produto);
		produto.setDescricao(produto.getDescricao().trim());

//		boolean jaExiste = (getDAO().existeProdutoComNome(produto.getDescricao()) > 0) ? true : false;
//		if (jaExiste) {
//			throw new ServiceException("Já existe Produto com o nome [" + nome + "]. Outro nome deve ser informado.");
//		}
	}
	
	@Override
	public ServiceResponse save(Produto produto,String usuarioHost, String usuarioLogado) throws ServiceException {

		antesSalvar(produto);
		
		setLogPojo(produto, usuarioHost, usuarioLogado);
		produto.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().save(produto);
		aposSalvar(produto);

		serviceResponse = ServiceResponseFactory.createWithData(produto);
		serviceResponse.addInfo("Registro salvo com sucesso.");
		
		return serviceResponse;
	}
	
	@Override
	public ServiceResponse update(Produto produto,String usuarioHost,String usuarioLogado) throws ServiceException {

		antesAtualizar(produto);
			
		setLogPojo(produto,usuarioHost,usuarioLogado);
		produto.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().update(produto);
		aposAtualizar(produto);
		
		serviceResponse = ServiceResponseFactory.createWithData(produto);
		serviceResponse.addInfo("Registro atualizado com sucesso.");
		
		return serviceResponse;
	}

	@Override
	protected void antesExcluir(Produto produto) throws ServiceException {
		if (produto == null) {
			throw new ServiceException("[Produto] não informado para exclusão.");
		}

		if (produto.getId() == null) {
			throw new ServiceException("[Id do Produto] não informado para exclusão.");
		}

//		boolean planos = getDAO().existeProdutoVinculadoPlano(produto.getId());
//		if (planos) {
//			throw new ServiceException("O Produto não pode ser excluído pois existem planos vinculados ao mesmo.");
//		}
		
	}
	
	@Override
	protected void aposSalvar(Produto entity) throws ServiceException {
		clearCacheProduto();
	}
	
	@Override
	protected void aposAtualizar(Produto entity) throws ServiceException {
		clearCacheProduto();
	}
	
	@Override
	protected void aposExcluir(Produto entity) throws ServiceException {
		clearCacheProduto();
	}

	private void validar(Produto produto) throws ServiceException {
		validarCamposVazios(produto);
		validarDataVigencia(produto);
	}
	
	private void validarCamposVazios(Produto produto) throws ServiceException {
		if (produto == null) {
			throw new ServiceException("[Produto] não informado.");
		}

		if (StringUtils.isEmpty(produto.getNome())) {
			throw new ServiceException("[Nome] não informado.");
		}
		
		if (StringUtils.isEmpty(produto.getSigla())) {
			throw new ServiceException("[Sigla] não informado.");
		}
		
		if (StringUtils.isEmpty(produto.getCodigo())) {
			throw new ServiceException("[Codigo do Produto] não informado.");
		}
		
		if (produto.getDataInicioVigencia() == null) {
			throw new ServiceException("[Data Inicio Vigência] não informado");
		}

		if (produto.getFlagEnabled() == null) {
			throw new ServiceException("Não informado se [flag Produto] está ativo ou não.");
		}
	}

	private void validarDataVigencia(Produto produto) throws ServiceException{
		Date inicioVigencia = produto.getDataInicioVigencia();
		Date fimVigencia = produto.getDataFinalVigencia();
		
		if(inicioVigencia != null && fimVigencia != null){
			if(fimVigencia.getTime() < inicioVigencia.getTime()){
				throw new ServiceException("[Data Início Vigência] não pode ser maior que [Data Final Vigência].");
			}
		}
	}

	@Override
	public ServiceResponse findByExampleOrderByNome(Produto entity) throws ServiceException {
		List<Produto> all = getDAO().findByExampleOrderByNome(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}
	
	@Override
	public List<Produto> findByPlanoList(List<Plano> planoList) throws ServiceException, ValidationException {
		List<BigInteger> pkList = new ArrayList<BigInteger>();
		try {
			for(Plano plano: planoList){
				if(pkList.contains(plano.getProduto().getId())){
					continue;
				} else {
					pkList.add(plano.getProduto().getId());
				}
			}
			if(pkList.isEmpty()){
				throw new ValidationException("Não há planos disponíveis");
			}
			return getDAO().findAllFromPkList(pkList);
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}
	
	public ServiceResponse findProdutosNoEvento(Evento evento) throws ServiceException, ValidationException {
		 
		ServiceResponse response = null;
		
		if(evento != null && evento.getId() != null){
			
			try {
				List<Produto> produtos = getDAO().findProdutosNoEvento(evento.getId());
				response = ServiceResponseFactory.createWithData(produtos);
			} catch (Exception e) {
				throw new ServiceException(e);
			}
			
		} else {
			throw new ValidationException("O evento selecionado não é válido para esta pesquisa.");
		}
		
		return response;
		
	}
	
	private void clearCacheProduto() throws ServiceException {
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.PRODUTO.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ServiceResponse findByNomeProduto(String nomeProduto) throws ServiceException {
		List<Produto> all = getDAO().findByNome(nomeProduto);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}
	
}