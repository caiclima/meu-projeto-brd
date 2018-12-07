package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IProfissaoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Profissao;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IProfissaoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Profissao'
 * 
 * @author neppo.oldamar
 * 
 */
@Stateless
@Local(IProfissaoService.class)
public class ProfissaoService extends GenericCrudServiceImpl<Profissao> implements IProfissaoService<Profissao> {
	
	@Inject
	private IProfissaoDAO dao;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected IProfissaoDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodasProfissoes() throws ServiceException {
		
		List<Profissao> profissoes = getDAO().findAll();
		ServiceResponse response = ServiceResponseFactory.createWithData(profissoes);

		if (CollectionUtils.isEmpty(profissoes)) {
			return response.addWarning("Nenhum registro encontrado!");
		}
		return response;
	}

	@Override
	public ServiceResponse buscaProfissaoPorId(BigInteger idProfissao) throws ServiceException {

		Profissao profissao = getDAO().findByPK(idProfissao);
		ServiceResponse response = ServiceResponseFactory.createWithData(profissao);

		if (profissao == null) {
			return response.addWarning("Nenhum registro encontrado!");
		}
		return response;
	}

	@Override
	public ServiceResponse findByExample(Profissao entity) throws ServiceException {
		
		List<Profissao> profissoes = getDAO().findByExample(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(profissoes);

		if (CollectionUtils.isEmpty(profissoes)) {
			return response.addWarning("Nenhum registro encontrado!");
		}
		return response;
	}

	@Override
	public ServiceResponse salvar(Profissao profissao, String usuarioHost, String usuarioLogado) throws ServiceException {

		antesSalvar(profissao);
		
		setLogPojo(profissao, usuarioHost, usuarioLogado);
		profissao.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		getDAO().save(profissao);
		aposSalvar(profissao);

		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(profissao);
		serviceResponse.addInfo("Registro salvo com sucesso.");
		return serviceResponse;
	}
	
	@Override
	protected void antesSalvar(Profissao profissao) throws ServiceException {
		
		validar(profissao);
		profissao.setFlagRemoved(Boolean.FALSE);
	}
	
	@Override
	public ServiceResponse atualizar(Profissao profissao, String usuarioHost, String usuarioLogado) throws ServiceException {

		antesAtualizar(profissao);
			
		setLogPojo(profissao,usuarioHost,usuarioLogado);
		profissao.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		getDAO().update(profissao);
		aposAtualizar(profissao);
		
		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(profissao);
		serviceResponse.addInfo("Registro atualizado com sucesso.");
		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(Profissao profissao) throws ServiceException {
		validar(profissao);
	}
	
	private void validar(Profissao profissao) throws ServiceException {
		
		if (profissao == null) {
			throw new ServiceException("[Profissao] não informado.");
		}
		if (StringUtils.isEmpty(profissao.getNome())) {
			throw new ServiceException("[Nome da Profissao] não informado.");
		}
		if (StringUtils.isEmpty(profissao.getCodigo())) {
			throw new ServiceException("[Codigo da Profissao] não informado.");
		}
	}
	
	@Override
	public ServiceResponse remover(Profissao profissao, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesExcluir(profissao);

		setLogPojo(profissao, usuarioHost, usuarioLogado);
		profissao.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));

		getDAO().delete(profissao);
		
		aposExcluir(profissao);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(profissao);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	public ServiceResponse removerLogicamente(Profissao profissao,
			String usuarioHost, String usuarioLogado) throws ServiceException {
		
		ServiceResponse serviceResponse;
		antesExcluir(profissao);

		Profissao profissaoPersistence = getDAO()
				.findByPK(profissao.getId());
		profissaoPersistence.setFlagRemoved(Boolean.TRUE);
		profissao.setLogTransaction(BigInteger.valueOf(System
				.currentTimeMillis()));
		setLogPojo(profissao, usuarioHost, usuarioLogado);
		getDAO().update(profissaoPersistence);
		
		aposExcluir(profissao);
		
		profissao = profissaoPersistence;

		serviceResponse = ServiceResponseFactory.createWithData(profissao);
		serviceResponse.addInfo("Registro removido com sucesso.");
		return serviceResponse;
	}

	@Override
	protected void antesExcluir(Profissao profissao) throws ServiceException {
		
		if (profissao == null) {
			throw new ServiceException("[Profissao] não informada para exclusão.");
		}
		if (profissao.getId() == null) {
			throw new ServiceException("[Id do Profissao] não informada para exclusão.");
		}
	}

	@Override
	public ServiceResponse findByExampleOrderByNome(Profissao entity) throws ServiceException {
		
		List<Profissao> all = getDAO().findByExampleOrderByNome(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}
	
	@Override
	public List<Profissao> findByVendaList(List<Venda> vendaList) throws ServiceException, ValidationException {
		
		List<BigInteger> pkList = new ArrayList<BigInteger>();
		try {
			for(Venda venda: vendaList){
				if(pkList.contains(venda.getProfissao().getId())){
					continue;
				} else {
					pkList.add(venda.getProfissao().getId());
				}
			}
			if(pkList.isEmpty()){
				throw new ValidationException("Não há vendas disponíveis");
			}
			return getDAO().findAllFromPkList(pkList);
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	protected void aposSalvar(Profissao entity) throws ServiceException {
		clearCacheProfissao();
	}
	
	@Override
	protected void aposAtualizar(Profissao entity) throws ServiceException {
		clearCacheProfissao();
	}
	
	@Override
	protected void aposExcluir(Profissao entity) throws ServiceException {
		clearCacheProfissao();
	}
	
	private void clearCacheProfissao() throws ServiceException {
		
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.PROFISSAO.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}