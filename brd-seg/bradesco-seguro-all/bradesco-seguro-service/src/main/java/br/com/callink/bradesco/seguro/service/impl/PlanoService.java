package br.com.callink.bradesco.seguro.service.impl;

import static br.com.callink.bradesco.seguro.service.utils.Constantes.MAX_VALOR_BIG_DECIMAL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IPlanoDAO;
import br.com.callink.bradesco.seguro.entity.Cobertura;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.enums.TipoPlanoEnum;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;
import br.com.callink.bradesco.seguro.service.ICoberturaService;
import br.com.callink.bradesco.seguro.service.IGrauParentescoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IPlanoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IPlanoService.class)
public class PlanoService extends GenericCrudServiceImpl<Plano> implements
		IPlanoService<Plano> {

	@Inject
	private IPlanoDAO dao;

	@EJB
	private ICoberturaService<Cobertura> coberturaService;
	
	@EJB
	private IGrauParentescoService<GrauParentesco> grauParentescoService;

	@Override
	protected IPlanoDAO getDAO() {
		return dao;
	}

	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	public ServiceResponse buscarTodosPlanos() throws ServiceException {

		List<Plano> planos = getDAO().findAll();
		ServiceResponse serviceReponse = ServiceResponseFactory
				.createWithData(planos);

		if (CollectionUtils.isEmpty(planos)) {
			serviceReponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceReponse;
	}

	@Override
	public ServiceResponse buscarPlanoPorId(BigInteger idPlano)
			throws ServiceException {

		validarVazio(idPlano, "Informe o id do plano.");

		Plano plano = getDAO().findByPK(idPlano);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(plano);

		if (plano == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarPorExemplo(Plano plano)
			throws ServiceException {

		validarVazio(plano, "Informe um plano.");

		List<Plano> planos = getDAO().findByExample(plano, "nome");
		Set<Plano> planosSet = new HashSet<Plano>();
		planosSet.addAll(planos);
		planos.clear();
		planos.addAll(planosSet);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(planos);

		if (CollectionUtils.isEmpty(planos)) {
			serviceResponse.addWarning("Nenhum registro encontrado!");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvar(Plano plano, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		validarInfoLog(usuarioHost, usuarioLogado);

		setLogPojo(plano, usuarioHost, usuarioLogado);

		ServiceResponse serviceResponse = super.save(plano);

		if (!CollectionUtils.isEmpty(plano.getCoberturaList())) {
			for (Cobertura cobertura : plano.getCoberturaList()) {
				validarVazio(plano.getId(), "Erro ao salvar o Plano.");
				cobertura.setIdPlano(plano.getId());
				coberturaService.salvar(cobertura,
						usuarioHost, usuarioLogado);
			}
		}
		return serviceResponse;
	}

	protected void antesSalvar(Plano plano) throws ServiceException {

		validarCamposVazios(plano);
		validarValores(plano);
		validarDataVigencia(plano);
		validarFaixaEtaria(plano);
		if (plano.getProduto().getCodigoValidacaoProduto().equalsIgnoreCase(ValidacaoVendaProduto.VALIDACAO_1.getCodigo())) {
			validarCobertura(plano);
		}
	}

	private void validarCobertura(Plano plano) throws ServiceException {
		List<GrauParentesco> grauParentescoList = null;
		
		if (plano != null && plano.getTipoPlano() != null && plano.getTipoPlano().getId() != null 
				&& plano.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.INDIVIDUAL.getValor())) {
			grauParentescoList = new ArrayList<GrauParentesco>();
			grauParentescoList.add(GrauParentesco.TITULAR);
		} else {
			grauParentescoList = grauParentescoService.findAllCacheable();
		}
		
		if (CollectionUtils.isEmpty(plano.getCoberturaList())) {
			mensagemErro("Informe uma Cobertura para cada tipo de Grau Parentesco");
		}
		
		if (plano.getCoberturaList().size() != grauParentescoList.size()) {
			mensagemErro("Informe uma Cobertura para cada tipo de Grau Parentesco");
		}
		
		boolean existeCobertura;

		for (GrauParentesco grauParentescoTemp : grauParentescoList) {
			existeCobertura = false;
			for (Cobertura cobertura : plano.getCoberturaList()) {
				if (cobertura.getGrauParentesco().equals(grauParentescoTemp)) {
					existeCobertura = true;
					break;
				}
			}
			if (!existeCobertura) {
				mensagemErro("Informe uma Cobertura para cada tipo de Grau Parentesco");
			}
		}
		
	}

	private void validarCamposVazios(Plano plano) throws ServiceException {
		
		validarVazio(plano, "Informe o plano");
		validarVazio(plano.getCodigo(), "Informe o codigo.");
		validarVazio(plano.getNome(), "Informe o nome.");
		validarVazio(plano.getSigla(), "Informe a sigla.");
		validarVazio(plano.getDataInicioVigencia(),
				"Informe data inicio de vigencia.");
		validarVazio(plano.getTipoPlano(), "Informe o tipo do plano.");
		validarVazio(plano.getProduto(), "Informe o produto.");

		if (plano.getProduto().getCodigoValidacaoProduto()
				.equals(ValidacaoVendaProduto.VALIDACAO_1.getCodigo())) {
			validarVazio(plano.getValorPremio(), "Informe o capital prêmio.");
		} else if (plano.getProduto().getCodigoValidacaoProduto()
				.equals(ValidacaoVendaProduto.VALIDACAO_2.getCodigo())) {
			validarVazio(plano.getFaixaEtariaInicial(),
					"Informe a faixa etária inicial.");
			validarVazio(plano.getFaixaEtariaFinal(),
					"Informe a faixa etária final.");
			validarVazio(plano.getValorTitular(), "Informe o valor titular.");
		}
	}

	private void validarValores(Plano plano) throws ServiceException {
		if (plano.getValorPorFilho() != null
				&& validaBigDecimal(plano.getValorPorFilho()) == Boolean.FALSE) {
			mensagemErro("O campo Valor por Filho deve ser um número de 8 digitos e 2 casas decimais.");
		}
		if (plano.getValorPremio() != null
				&& validaBigDecimal(plano.getValorPremio()) == Boolean.FALSE) {
			mensagemErro("O campo Valor Prêmio deve ser um número de 8 digitos e 2 casas decimais.");
		}
		if (plano.getValorTitular() != null
				&& validaBigDecimal(plano.getValorTitular()) == Boolean.FALSE) {
			mensagemErro("O campo Valor Títular deve ser um número de 8 digitos e 2 casas decimais.");
		}
		if (plano.getValorTitularConjuge() != null
				&& validaBigDecimal(plano.getValorTitularConjuge()) == Boolean.FALSE) {
			mensagemErro("O campo Valor Títular + Cônjuge deve ser um número de 8 digitos e 2 casas decimais.");
		}
		if (plano.getCapitalSegurado() != null
				&& validaBigDecimal(plano.getCapitalSegurado()) == Boolean.FALSE) {
			mensagemErro("O campo Capital Segurado deve ser um número de 8 digitos e 2 casas decimais.");
		}
	}

	private Boolean validaBigDecimal(BigDecimal input) {
		return (input.compareTo(new BigDecimal(MAX_VALOR_BIG_DECIMAL)) < 1);
	}

	private void validarDataVigencia(Plano plano) throws ServiceException {
		Date dataInicio = plano.getDataInicioVigencia();
		Date dataFim = plano.getDataFinalVigencia();

		if (dataInicio != null && dataFim != null) {
			if (dataFim.getTime() < dataInicio.getTime()) {
				mensagemErro("O campo Data Final Vigência não pode ser menor que o campo Data Início Vigência.");
			}
		}
	}

	private void validarFaixaEtaria(Plano plano) throws ServiceException {
		String faixaInicial = plano.getFaixaEtariaInicial();
		String faixaFinal = plano.getFaixaEtariaFinal();

		if (faixaInicial != null && faixaFinal != null) {
			if (Integer.parseInt(faixaFinal) < Integer.parseInt(faixaInicial)) {
				mensagemErro("O campo Faixa Etária final não pode ser menor que o campo Faixa Etária inicial.");
			}
		}
	}

	@Override
	public ServiceResponse remover(Plano plano) throws ServiceException {

		antesExcluir(plano);

		if(!CollectionUtils.isEmpty(plano.getCoberturaList())) {
			for(Cobertura cobertura: plano.getCoberturaList()){
				coberturaService.delete(cobertura);
			}
		}
		getDAO().delete(plano);
		
		aposExcluir(plano);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(plano);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}

	protected void antesExcluir(Plano plano) throws ServiceException {

		validarVazio(plano, "Informe um plano.");
		validarVazio(plano.getId(), "Informe o id do plano.");
	}

	@Override
	public ServiceResponse buscarPlanoPorProdutoAndIdade(Produto produto,
			String idade) throws ServiceException {

		validarVazio(produto, "Informe o Produto.");
		validarVazio(produto.getId(), "Informe o id do Produto.");

		List<Plano> planos = null;

		if (produto.getCodigoValidacaoProduto().equalsIgnoreCase(
				ValidacaoVendaProduto.VALIDACAO_2.getCodigo())) {
			if (StringUtils.isEmpty(idade)) {
				mensagemErro("Para listar os Planos vinculados ao Produto entitulado "
						+ produto.getNome()
						+ " , é necessário informar a Data de Nascimento do Titutlar.");
			} else {
				planos = getDAO().buscarPlanoPorProdutoAndIdade(produto, idade);
			}
		} else {
			planos = getDAO().buscarPlanoPorProduto(produto);
		}

		ServiceResponse serviceReponse = ServiceResponseFactory
				.createWithData(planos);

		if (CollectionUtils.isEmpty(planos)) {
			serviceReponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceReponse;
	}

	private void validarInfoLog(String usuarioHost, String usuarioLogado)
			throws ServiceException {

		validarVazio(usuarioHost, "Informe o usuario-host");
		validarVazio(usuarioLogado, "Informe o usuario-logado");
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private void validarVazio(String obj, String mensagem)
			throws ServiceException {

		if (StringUtils.isEmpty(obj)) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}

	@Override
	protected void aposSalvar(Plano entity) throws ServiceException {
		//clearCachePlano();
	}

	@Override
	protected void aposAtualizar(Plano entity) throws ServiceException {
		//clearCachePlano();
	}

	@Override
	protected void aposExcluir(Plano entity) throws ServiceException {
		//clearCachePlano();
	}

//	private void clearCachePlano() throws ServiceException {
//
//		try {
//			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
//			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
//			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
//			final String urlString = urlCacheServ + ClearCacheCommand.PLANO.getQueryString();
//
//			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
//
//		} catch (Exception e) {
//			throw new ServiceException(e);
//		}
//	}

}
