package br.com.callink.bradesco.seguro.service.utils;

import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.CpfUtils;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.EmailUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.commons.utils.TelefoneUtils;
import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.enums.TipoBeneficiario;
import br.com.callink.bradesco.seguro.enums.TipoPlanoEnum;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;
import br.com.callink.bradesco.seguro.service.IContatoMailingService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;

@Stateless
public class ValidacaoVendaUtils {

	private static final Integer IDADE_MINIMA_TITULAR_PRODUTO_1 = 18;

	private static final Integer IDADE_MAXIMA_TITULAR_PRODUTO_1 = 64;

	private static final Integer IDADE_MINIMA_TITULAR_PRODUTO_2 = 21;

	private static final Integer IDADE_MAXIMA_TITULAR_PRODUTO_2 = 64;
	
	private static final Integer IDADE_MINIMA_TITULAR_PRODUTO_3 = 21;

	private static final Integer IDADE_MAXIMA_TITULAR_PRODUTO_3 = 64;
	
	private static final Integer IDADE_MINIMA_CONJUGE = 18;

	private static final Integer IDADE_MAXIMA_CONJUGE = 64;
	
	private static final Integer IDADE_MAXIMA_FILHO_ENTEADO = 24;

	private final String VALIDAR_VENDA = "1";

	private final String VALIDAR_CLIENTE = "2";

	@EJB
	private IContatoMailingService contatoMailingService;

	public void validarClienteVendaDependente(Venda venda)
			throws ServiceException {
		validarVenda(venda, VALIDAR_VENDA);
	}

	public void validarClienteVenda(Venda venda) throws ServiceException {
		validarVenda(venda, VALIDAR_CLIENTE);
	}

	private void validarVenda(Venda venda, String nivelValidacao)
			throws ServiceException {

		Plano planoSelecionado = null;
		int countTitularCadastrado = 0;

		validarVazio(venda, "Informe a Venda");
		validarVazio(venda.getBeneficiarioList(), "Informe um Beneficiário.");
		for (Beneficiario beneficiario : venda.getBeneficiarioList()) {

			validarVazio(beneficiario.getBeneficiarioPlanoList(),
					"Informe o Plano");
			for (BeneficiarioPlano beneficiarioPlano : beneficiario
					.getBeneficiarioPlanoList()) {

				if (beneficiarioPlano.getPlano() != null
						&& beneficiarioPlano.getPlano().getId() != null) {
					planoSelecionado = beneficiarioPlano.getPlano();
				} else {
					mensagemErro("Informe o Plano");
				}

				validarVazio(beneficiarioPlano.getPlano().getProduto(),
						"Informe o Produto.");
				validarVazio(beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto(),
						"Informe o Codigo de Validação do Produto.");

				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_1.getCodigo())) {

					switch (nivelValidacao) {
					case VALIDAR_VENDA:
						validacaoProduto1(venda, beneficiario, planoSelecionado);
						break;
					case VALIDAR_CLIENTE:
						validacaoClienteProduto1(venda, beneficiario,
								planoSelecionado);
						break;
					default:
						throw new ServiceException(
								"Falha na validação de Venda.");
					}
				}
				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_2.getCodigo())) {

					switch (nivelValidacao) {
					case VALIDAR_VENDA:
						validacaoProduto2(venda, beneficiario, planoSelecionado);
						break;
					case VALIDAR_CLIENTE:
						validacaoClienteProduto2(venda, beneficiario,
								planoSelecionado);
						break;
					default:
						throw new ServiceException(
								"Falha na validação de Venda.");
					}
				}
				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_3.getCodigo())) {

					switch (nivelValidacao) {
					case VALIDAR_VENDA:
						validacaoProduto3(venda, beneficiario, planoSelecionado);
						break;
					case VALIDAR_CLIENTE:
						validacaoClienteProduto3(venda, beneficiario,
								planoSelecionado);
						break;
					default:
						throw new ServiceException(
								"Falha na validação de Venda.");
					}
				}
			}
			if (beneficiario.getTipoBeneficiario() != null
					&& beneficiario.getTipoBeneficiario().equals(
							TipoBeneficiario.TITULAR.getValor())) {
				countTitularCadastrado++;
			}
		}
		if (countTitularCadastrado == 0) {
			mensagemErro("Informe o Titular.");
		}
	}

	public void validarDependente(Venda venda, Beneficiario dependente)
			throws ServiceException {

		if (dependente.getTipoBeneficiario() != null
				&& !dependente.getTipoBeneficiario().equals(
						TipoBeneficiario.TITULAR.getValor())) {

			Plano planoSelecionado = null;

			validarVazio(venda, "Informe a Venda");
			validarVazio(venda.getId(), "Confirme uma Venda antes de salvar o Dependente");
			validarVazio(venda.getBeneficiarioList(),
					"Informe um Beneficiário.");
			validarVazio(dependente, "Informe o Dependente.");

			validarVazio(dependente.getBeneficiarioPlanoList(),
					"Informe o Plano");

			for (BeneficiarioPlano beneficiarioPlano : dependente
					.getBeneficiarioPlanoList()) {

				if (beneficiarioPlano.getPlano() != null) {
					planoSelecionado = beneficiarioPlano.getPlano();
				} else {
					mensagemErro("Informe o Plano");
				}

				validarVazio(beneficiarioPlano.getPlano().getProduto(),
						"Informe o Produto.");
				validarVazio(beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto(),
						"Informe o Codigo de Validação do Produto.");

				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_1.getCodigo())) {

					validacaoDependenteProduto1(venda, dependente,
							planoSelecionado);
				}
				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_2.getCodigo())) {

					validacaoDependenteProduto2(venda, dependente,
							planoSelecionado);
				}
				if (beneficiarioPlano.getPlano().getProduto()
						.getCodigoValidacaoProduto()
						.equals(ValidacaoVendaProduto.VALIDACAO_3.getCodigo())) {

					validacaoDependenteProduto3(venda, dependente,
							planoSelecionado);
				}
			}
		}
	}

	// PROTECAO_PESSOAL_BRADESCO
	private void validacaoProduto1(Venda venda, Beneficiario beneficiario,
			Plano planoSelecionado) throws ServiceException {

		validacaoComumProduto1(venda, beneficiario);
		validarPlanoFamiliar(venda, planoSelecionado);
		validarCPFUnicidade(venda, beneficiario);
		validarIdadeDependentePlanoFamiliar(beneficiario, planoSelecionado);
	}

	// PROTECAO_PLUS_BRADESCO
	private void validacaoProduto2(Venda venda, Beneficiario beneficiario,
			Plano planoSelecionado) throws ServiceException {

		validacaoComumProduto2(venda, beneficiario);
		validarPlanoFamiliar(venda, planoSelecionado);
		validarCPFUnicidade(venda, beneficiario);
		validarIdadeDependentePlanoFamiliar(beneficiario, planoSelecionado);
	}
	
	// PROTECAO_DHI
	private void validacaoProduto3(Venda venda, Beneficiario beneficiario,
			Plano planoSelecionado) throws ServiceException {

		validacaoComumProduto3(venda, beneficiario);
		validarPlanoFamiliar(venda, planoSelecionado);
		validarCPFUnicidade(venda, beneficiario);
		validarIdadeDependentePlanoFamiliar(beneficiario, planoSelecionado);
	}

	// PROTECAO_PESSOAL_BRADESCO - CLIENTE
	private void validacaoClienteProduto1(Venda venda,
			Beneficiario beneficiario, Plano planoSelecionado)
			throws ServiceException {
		validacaoComumProduto1(venda, beneficiario);
	}

	// PROTECAO_PLUS_BRADESCO - CLIENTE
	private void validacaoClienteProduto2(Venda venda,
			Beneficiario beneficiario, Plano planoSelecionado)
			throws ServiceException {
		validacaoComumProduto2(venda, beneficiario);
	}
	
	// DHI - CLIENTE
	private void validacaoClienteProduto3(Venda venda,
			Beneficiario beneficiario, Plano planoSelecionado)
			throws ServiceException {
		validacaoComumProduto3(venda, beneficiario);
	}

	// PROTECAO_PESSOAL_BRADESCO - DEPENDENTES
	private void validacaoDependenteProduto1(Venda venda,
			Beneficiario dependente, Plano planoSelecionado)
			throws ServiceException {

		validarCamposObrigatoriosBeneficiario(dependente);
		validacaoComumBeneficiarioProduto1(dependente);
		validarCPFUnicidade(venda, dependente);
		validarIdadeDependentePlanoFamiliar(dependente, planoSelecionado);
	}

	// PROTECAO_PLUS_BRADESCO - DEPENDENTES
	private void validacaoDependenteProduto2(Venda venda,
			Beneficiario dependente, Plano planoSelecionado)
			throws ServiceException {

		validarCamposObrigatoriosBeneficiario(dependente);
		validacaoComumBeneficiarioProduto2(dependente);
		validarCPFUnicidade(venda, dependente);
		validarIdadeDependentePlanoFamiliar(dependente, planoSelecionado);
	}
	
	// DHI - DEPENDENTES
		private void validacaoDependenteProduto3(Venda venda,
				Beneficiario dependente, Plano planoSelecionado)
				throws ServiceException {

			validarCamposObrigatoriosBeneficiario(dependente);
			validacaoComumBeneficiarioProduto3(dependente);
			validarCPFUnicidade(venda, dependente);
			validarIdadeDependentePlanoFamiliar(dependente, planoSelecionado);
		}

	private void validacaoComumProduto(Venda venda, Beneficiario beneficiario)
			throws ServiceException {

		validarCamposObrigatoriosVenda(venda, beneficiario);
		validarCamposObrigatoriosBeneficiario(beneficiario);
		validarEmail(venda);
		validarTelefones(venda);
		validarCPFInformadoEMailing(venda);
	}

	private void validacaoComumProduto1(Venda venda, Beneficiario beneficiario)
			throws ServiceException {
		validacaoEspecificasProduto1(venda, beneficiario);
		validacaoComumProduto(venda, beneficiario);
		validacaoComumBeneficiarioProduto1(beneficiario);
		validarIdadeTitular(venda, IDADE_MINIMA_TITULAR_PRODUTO_1,
				IDADE_MAXIMA_TITULAR_PRODUTO_1);
	}

	private void validacaoEspecificasProduto1(Venda venda,
			Beneficiario beneficiario) throws ServiceException {
		validarVazio(venda.getEsporte(), "Informe o Esporte.");
		validarVazio(venda.getEsporte().getId(), "Informe o Esporte.");
		venda.setIdEsporte(venda.getEsporte().getId());
		validarVazio(beneficiario.getEsporte(), "Informe o Esporte.");
		validarVazio(beneficiario.getEsporte().getId(), "Informe o Esporte");
		beneficiario.setIdEsporte(beneficiario.getEsporte().getId());
	}

	private void validacaoComumProduto2(Venda venda, Beneficiario beneficiario)
			throws ServiceException {

		validacaoEspecificasProduto2(venda, beneficiario);
		validacaoComumProduto(venda, beneficiario);
		validacaoComumBeneficiarioProduto2(beneficiario);
		validarIdadeTitular(venda, IDADE_MINIMA_TITULAR_PRODUTO_2,
				IDADE_MAXIMA_TITULAR_PRODUTO_2);
	}
	
	private void validacaoEspecificasProduto2(Venda venda,
			Beneficiario beneficiario) throws ServiceException {
		validarVazio(venda.getEsporte(), "Informe o Esporte.");
		validarVazio(venda.getEsporte().getId(), "Informe o Esporte.");
		venda.setIdEsporte(venda.getEsporte().getId());
		validarVazio(beneficiario.getEsporte(), "Informe o Esporte.");
		validarVazio(beneficiario.getEsporte().getId(), "Informe o Esporte");
		beneficiario.setIdEsporte(beneficiario.getEsporte().getId());
	}
	
	private void validacaoComumProduto3(Venda venda, Beneficiario beneficiario)
			throws ServiceException {
		validacaoComumProduto(venda, beneficiario);
		validacaoComumBeneficiarioProduto3(beneficiario);
		validarIdadeTitular(venda, IDADE_MINIMA_TITULAR_PRODUTO_3,
				IDADE_MAXIMA_TITULAR_PRODUTO_3);
	}
	
	private void validacaoComumBeneficiarioProduto3(Beneficiario beneficiario)
			throws ServiceException {

		validarValorDataMaiorDataAtual(beneficiario.getDataNascimento(), "A Data de Nascimento deve ser menor do que a data atual");
		validarValorDataMaiorDataAtual(beneficiario.getDataExpedicao(), "A Data de Expedição do RG deve ser menor do que a data atual");
		validarAposentadoInvalidez(beneficiario);
		validarProfissaoRisco(beneficiario);
		validarPortadorHIV(beneficiario);
		validarCPFBeneficiario(beneficiario);
	}
	
	private void validacaoComumBeneficiarioProduto2(Beneficiario beneficiario)
			throws ServiceException {

		validarValorDataMaiorDataAtual(beneficiario.getDataNascimento(), "A Data de Nascimento deve ser menor do que a data atual");
		validarValorDataMaiorDataAtual(beneficiario.getDataExpedicao(), "A Data de Expedição do RG deve ser menor do que a data atual");
		validarAposentadoInvalidez(beneficiario);
		validarPortadorHIV(beneficiario);
		validarCPFBeneficiario(beneficiario);
	}

	private void validacaoComumBeneficiarioProduto1(Beneficiario beneficiario)
			throws ServiceException {

		validarValorDataMaiorDataAtual(beneficiario.getDataNascimento(), "A Data de Nascimento deve ser menor do que a data atual");
		validarValorDataMaiorDataAtual(beneficiario.getDataExpedicao(), "A Data de Expedição do RG deve ser menor do que a data atual");
		validarAposentadoInvalidezProduto1(beneficiario);
		validarEsporteRisco(beneficiario);
		validarProfissaoRisco(beneficiario);
		validarCPFBeneficiario(beneficiario);
	}

	private void validarValorDataMaiorDataAtual(Date data, String erro) throws ServiceException {
		if (data.after(new Date())) {
			mensagemErro(erro);
		}
		
	}

	private void validarAposentadoInvalidez(Beneficiario beneficiario)
			throws ServiceException {

		if (beneficiario.getFlagAposentadoInvalidez()) {
			mensagemErro("Venda não permitida a Aposentado por Invalidez.");
		}
	}

	private void validarAposentadoInvalidezProduto1(Beneficiario beneficiario)
			throws ServiceException {

		if (!beneficiario.getGrauParentesco().equals(GrauParentesco.FILHO)
				&& !beneficiario.getGrauParentesco().equals(
						GrauParentesco.ENTEADO)
				&& beneficiario.getFlagAposentadoInvalidez()) {
			mensagemErro("Venda não permitida a Aposentado por Invalidez.");
		}
	}

	private void validarPortadorHIV(Beneficiario beneficiario)
			throws ServiceException {

		if (beneficiario.getFlagHiv() != null && beneficiario.getFlagHiv()) {
			mensagemErro("Produto não permite Beneficiário ("
					+ beneficiario.getNome() + ") portador de HIV");
		}
	}

	private void validarEsporteRisco(Beneficiario beneficiario)
			throws ServiceException {

		if (beneficiario.getEsporte().getFlagRisco()) {
			mensagemErro("Produto não permite Beneficiário ("
					+ beneficiario.getNome()
					+ ") que pratique Esporte de Risco.");
		}
	}

	private void validarProfissaoRisco(Beneficiario beneficiario)
			throws ServiceException {

		if (beneficiario.getProfissao().getFlagRisco()) {
			mensagemErro("Produto não permite Beneficiário ("
					+ beneficiario.getNome() + ") com Profissão de Risco.");
		}
	}

	private void validarPlanoFamiliar(Venda venda, Plano planoSelecionado)
			throws ServiceException {

		if (planoSelecionado.getTipoPlano().getNomeTipoPlano()
				.equals(TipoPlanoEnum.FAMILIAR.getValor())) {

			boolean isConjugeCadastrado = Boolean.FALSE;
			int countDependente = 0;
			for (Beneficiario beneficiario : venda.getBeneficiarioList()) {

				if (beneficiario.getTipoBeneficiario().equals(
						TipoBeneficiario.DEPENDENTE.getValor())) {

					countDependente++;
					if (beneficiario.getGrauParentesco().equals(
							GrauParentesco.CONJUGE)) {

						isConjugeCadastrado = Boolean.TRUE;
						break;
					}
				}
			}
			if (!isConjugeCadastrado && planoSelecionado.getProduto().getCodigoValidacaoProduto().equalsIgnoreCase(ValidacaoVendaProduto.VALIDACAO_1.getCodigo())) {
				mensagemErro("O Plano Familiar requer um Cônjuge.");
			}
			if (countDependente == 0) {
				mensagemErro("O Plano Familiar precisa de ao menos um Dependente.");
			}
		}
	}

	private void validarIdadeTitular(Venda venda, Integer idadeMinima,
			Integer idadeMax) throws ServiceException {

		Integer idadeTitular = new Integer(DateUtils.getIdadeByNascimento(venda
				.getClienteCampanha().getContatoMailing().getDataNascimento()));

		if (idadeTitular < idadeMinima || idadeTitular > idadeMax) {
			mensagemErro("O Titular deve ter idade entre " + idadeMinima
					+ " e " + idadeMax + " anos.");
		}
	}

	private void validarIdadeDependentePlanoFamiliar(Beneficiario beneficiario,
			Plano planoSelecionado) throws ServiceException {

		if (beneficiario.getTipoBeneficiario().equals(
				TipoBeneficiario.DEPENDENTE.getValor())
				&& planoSelecionado.getTipoPlano().getNomeTipoPlano()
						.equals(TipoPlanoEnum.FAMILIAR.getValor())) {

			Integer idadeDependente = new Integer(
					DateUtils.getIdadeByNascimento(beneficiario
							.getDataNascimento()));

			if ((GrauParentesco.CONJUGE.equals(beneficiario.getGrauParentesco()))
					&& (idadeDependente < IDADE_MINIMA_CONJUGE || idadeDependente > IDADE_MAXIMA_CONJUGE)) {
				mensagemErro("Idade do Dependente (" + beneficiario.getNome()
						+ ") inválida. Idade deve ser entre " + IDADE_MINIMA_CONJUGE +  " e " + IDADE_MAXIMA_CONJUGE + " anos.");
			}
			if ((GrauParentesco.FILHO.equals(beneficiario.getGrauParentesco()) || GrauParentesco.ENTEADO.equals(beneficiario.getGrauParentesco()))
					&& (idadeDependente > IDADE_MAXIMA_FILHO_ENTEADO)) {
				mensagemErro("Idade do Dependente (" + beneficiario.getNome()
						+ ") inválida. Idade deve ser até " + IDADE_MAXIMA_FILHO_ENTEADO + " anos.");
			}
		}
	}

	private void validarCamposObrigatoriosVenda(Venda venda,
			Beneficiario beneficiario) throws ServiceException {

		validarVazio(venda, "Informe uma Venda.");
		validarVazio(venda.getProfissao(), "Informe a Profissão.");
		validarVazio(venda.getProfissao().getId(), "Informe a Profissão");
		venda.setIdProfissao(venda.getProfissao().getId());
		//validarVazio(venda.getEsporte(), "Informe o Esporte.");
		//validarVazio(venda.getEsporte().getId(), "Informe o Esporte.");
		//venda.setIdEsporte(venda.getEsporte().getId());
		validarVazio(venda.getEstadoCivil(), "Informe o Estado Civil.");
		validarVazio(venda.getEstadoCivil().getId(), "Informe o Estado Civil.");
		venda.setIdEstadoCivil(venda.getEstadoCivil().getId());
		validarVazio(venda.getEvento(), "Informe a Tabulação.");
		validarVazio(venda.getEvento().getId(),
				"Informe a Tabulação.");
		venda.setIdEvento(venda.getEvento().getId());
		validarVazio(beneficiario.getGrauParentesco(),
				"Informe o Grau de Parentesco.");

		validarVazio(venda.getClienteCampanha(), "Informe o Cliente.");
		validarVazio(venda.getClienteCampanha().getId(),
				"Informe o Id do Cliente.");
		venda.setIdClienteCampanha(venda.getClienteCampanha().getId());
		validarVazio(venda.getClienteCampanha().getContatoMailing(),
				"Informe o Contato-Mailing.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getRg(), 
				"Informe o RG.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getId(),
				"Informe o id do Contato-Mailing.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getOrgaoExpedidor(), 
				"Informe o Orgão Expedidor.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getDataExpedicao(), 
				"Informe a Data da Expedição.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getNome(),
				"Informe o Nome do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing()
				.getDataNascimento(),
				"Informe a Data de Nascimento do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getCpf(),
				"Informe o CPF do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing()
				.getEndereco(), "Informe o Endereço do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing()
				.getNumeroEndereco(), "Informe o Número do Endereço do Cliente.");
		validarVazio(
				venda.getClienteCampanha().getContatoMailing().getBairro(),
				"Informe o Bairro do Cliente.");
		validarVazio(
				venda.getClienteCampanha().getContatoMailing().getCidade(),
				"Informe a Cidade do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getUf(),
				"Informe o Estado do Cliente..");
		validarVazio(venda.getClienteCampanha().getContatoMailing().getCep(),
				"Informe o CEP do Cliente.");
		validarVazio(
				venda.getClienteCampanha().getContatoMailing().getCartao(),
				"Informe o Cartão de Crédito do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing()
				.getValidadeCartao(),
				"Informe a Validade do Cartão de Crédito do Cliente.");
		validarVazio(venda.getClienteCampanha().getContatoMailing()
				.getDiaVencecimentoFatura(),
				"Informe o dia de vencimento da Fatura do Cliente.");
		validarVazio(venda.getFlagVendaFechada(),
				"Informe o status do contato. Venda ou Não Venda?");
	}

	private void validarCamposObrigatoriosBeneficiario(Beneficiario beneficiario)
			throws ServiceException {

		validarVazio(beneficiario, "Informe o Beneficiario");
		validarVazio(beneficiario.getNome(), "Informe o Nome.");
		
		String[] nomes = beneficiario.getNome().trim().split(" ");
		
		if (nomes.length < 2) {
			mensagemErro("Campo Nome deve ser formado por Nome e Sobrenome");
		}
		
		validarVazio(beneficiario.getCpf(), "Informe o CPF.");
		beneficiario.setCpf(CpfUtils.removeMask(beneficiario.getCpf()));
		validarVazio(beneficiario.getDataNascimento(),
				"Informe a Data de Nascimento.");
		validarVazio(beneficiario.getRg(), "Informe o RG.");
		validarVazio(beneficiario.getOrgaoExpedidor(),
				"Informe o Orgão Expedidor.");
		validarVazio(beneficiario.getDataExpedicao(),
				"Informe a Data de Expedição.");
		validarVazio(beneficiario.getGrauParentesco(),
				"Informe o Grau de Parentesco.");
		validarVazio(beneficiario.getGrauParentesco().getId(),
				"Informe o Grau de Parentesco.");
		beneficiario.setIdGrauParentesco(beneficiario.getGrauParentesco()
				.getId());
		if (beneficiario.getTipoBeneficiario().equals(
				TipoBeneficiario.DEPENDENTE.getValor())) {
			validarVazio(beneficiario.getVenda(), "Informe a Venda.");
			validarVazio(beneficiario.getVenda().getId(),
					"Informe o Id da Venda.");
			beneficiario.setIdVenda(beneficiario.getVenda().getId());
		}
		validarVazio(beneficiario.getProfissao(), "Informe a Profissão.");
		validarVazio(beneficiario.getProfissao().getId(),
				"Informe a  Profissão.");
		beneficiario.setIdProfissao(beneficiario.getProfissao().getId());
		//validarVazio(beneficiario.getEsporte(), "Informe o Esporte.");
		//validarVazio(beneficiario.getEsporte().getId(),
			//	"Informe o Esporte");
		//beneficiario.setIdEsporte(beneficiario.getEsporte().getId());
		validarVazio(beneficiario.getBeneficiarioPlanoList(),
				"Informe o Plano.");
		validarVazio(beneficiario.getTipoBeneficiario(),
				"Informe o Tipo do Beneficiário.");
		validarVazio(beneficiario.getSexo(), "Informe o Sexo.");
		validarVazio(beneficiario.getFlagAposentadoInvalidez(),
				"Informe se Dependente é Aposentado por Invalidez.");

	}

	private void validarEmail(Venda venda) throws ServiceException {

		if (venda.getClienteCampanha().getContatoMailing().getFlagRecebeOfertasEmail() != null
				&& venda.getClienteCampanha().getContatoMailing().getFlagRecebeOfertasEmail()
				&& StringUtils.isEmpty(venda.getClienteCampanha().getContatoMailing().getEmail())) {
			mensagemErro("Para receber ofertas por email, informe um email válido.");
		}
		
		if (!EmailUtils.isValido(venda.getClienteCampanha().getContatoMailing().getEmail())) {
			mensagemErro("Email com formato inválido.");
		}
	}

	private void validarTelefones(Venda venda) throws ServiceException {

		if (!CollectionUtils.isEmpty(venda.getClienteCampanha()
				.getTelefoneClienteList())) {

			for (TelefoneCliente telefone : venda.getClienteCampanha()
					.getTelefoneClienteList()) {

				Boolean isCelularValido = TelefoneUtils.validarCelular(telefone
						.getDdd() + telefone.getTelefone());

				if (telefone.getFlagRecebeSms() && !isCelularValido) {
					mensagemErro("O Telefone "
							+ TelefoneUtils.insertCharactersPhone(telefone
									.getDdd() + telefone.getTelefone())
							+ " não é celular, não suporta o recebimento de SMS, desmarque essa opção.");
				} else if (!isCelularValido
						&& !TelefoneUtils.validarFixo(telefone.getDdd()
								+ telefone.getTelefone())) {
					mensagemErro("Formato do número de telefone "
							+ TelefoneUtils.insertCharactersPhone(telefone
									.getDdd() + telefone.getTelefone())
							+ " inválido");
				}
			}
		}
	}

	private void validarCPFUnicidade(Venda venda, Beneficiario beneficiario)
			throws ServiceException {

		if (beneficiario.getTipoBeneficiario().equals(
				TipoBeneficiario.DEPENDENTE.getValor())) {

			if (fillLeftZeros(venda.getClienteCampanha().getContatoMailing().getCpf(), 11)
					.equals(fillLeftZeros(beneficiario.getCpf(), 11))) {

				Integer idadeDependente = new Integer(
						DateUtils.getIdadeByNascimento(beneficiario
								.getDataNascimento()));

				if ((beneficiario.getGrauParentesco().equals(
						GrauParentesco.FILHO)
						|| beneficiario.getGrauParentesco().equals(
								GrauParentesco.ENTEADO))
						&& (idadeDependente >= 18 && idadeDependente <= 24)) {
					mensagemErro("O CPF do Dependente não pode ser o mesmo do Titular.");
				}
				if (beneficiario.getGrauParentesco().equals(
						GrauParentesco.CONJUGE)) {
					mensagemErro("O CPF do Dependente não pode ser o mesmo do Titular.");
				}
			}
		}
	}

	private void validarCPFBeneficiario(Beneficiario beneficiario)
			throws ServiceException {

		if (!CpfUtils.validateCPF(beneficiario.getCpf())) {
			mensagemErro("CPF do(a) Beneficiário(a) " + beneficiario.getNome()
					+ " não é válido.");
		} else {
			beneficiario.setCpf(CpfUtils.removeMask(beneficiario.getCpf()));
		}
	}

	private void validarCPFInformadoEMailing(Venda venda)
			throws ServiceException {

		ContatoMailing contatoMailingPersistent = null;
		try {
			contatoMailingPersistent = contatoMailingService.findById(venda
					.getClienteCampanha().getContatoMailing().getId());
		} catch (ValidationException e) {
			mensagemErro("Contato Mailing não encontrado no momento da validação de CPF.");
		}

		if (!CpfUtils.validateCPF(fillLeftZeros(venda.getClienteCampanha()
				.getContatoMailing().getCpf(), 11))) {
			mensagemErro("CPF do cliente não é válido.");
		}
		if (contatoMailingPersistent != null) {
			if (!CpfUtils.removeMask(
					venda.getClienteCampanha().getContatoMailing().getCpf())
					.equals(contatoMailingPersistent.getCpf())) {
				mensagemErro("CPF informado não confere com Cpf do titular enviado pelo Bradesco!");
			}
		} else {
			mensagemErro("Contato Mailing não encontrado no momento da validação de CPF.");
		}
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	@SuppressWarnings("rawtypes")
	private void validarVazio(Collection collection, String mensagem)
			throws ServiceException {

		if (collection == null || CollectionUtils.isEmpty(collection)) {
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

	private String fillLeftZeros(String cpf, int length) {
		if(cpf == null) return null;
		
		if(cpf.length() < length){
			String zeros = "";
			
			for(int i=cpf.length(); i < length; i++){
				zeros+="0";
			}
			
			return zeros + cpf;
		}
		
		return cpf;
	}
}
