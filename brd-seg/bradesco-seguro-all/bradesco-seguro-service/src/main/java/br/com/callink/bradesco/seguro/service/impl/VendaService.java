package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IVendaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.Rejeicao;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.enums.TipoBeneficiario;
import br.com.callink.bradesco.seguro.enums.TipoPlanoEnum;
import br.com.callink.bradesco.seguro.service.IBeneficiarioPlanoService;
import br.com.callink.bradesco.seguro.service.IBeneficiarioService;
import br.com.callink.bradesco.seguro.service.IContatoMailingService;
import br.com.callink.bradesco.seguro.service.IHistoricoContatoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IRejeicaoService;
import br.com.callink.bradesco.seguro.service.IVendaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;
import br.com.callink.bradesco.seguro.service.utils.ValidacaoVendaUtils;

@Stateless
@Local(IVendaService.class)
public class VendaService extends GenericCrudServiceImpl<Venda> implements IVendaService<Venda> {

	@Inject
	private IVendaDAO dao;

	@EJB
	private IContatoMailingService contatoMailingService;

	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	@EJB
	private IBeneficiarioService<Beneficiario> beneficiarioService;
	
	@EJB
	private IBeneficiarioPlanoService<BeneficiarioPlano>  beneficiarioPlanoService;
	
	@EJB
	private ValidacaoVendaUtils validacaoVendaUtils;
        
    @EJB
    private IRejeicaoService<Rejeicao> rejeicaoService;
    
    @EJB
    private IHistoricoContatoService<HistoricoContato> historicoContatoService;

	@Override
	protected IVendaDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodasVendas() throws ServiceException {

		List<Venda> vendas = getDAO().findAll();
		ServiceResponse serviceReponse = ServiceResponseFactory
				.createWithData(vendas);

		if (CollectionUtils.isEmpty(vendas)) {
			serviceReponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceReponse;
	}

	@Override
	public ServiceResponse buscarVendaPorId(BigInteger idVenda)
			throws ServiceException {

		validarVazio(idVenda, "Informe o id da Vebda.");

		Venda venda = getDAO().findByPK(idVenda);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(venda);

		if (venda == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarPorExemplo(Venda venda)
			throws ServiceException {

		validarVazio(venda, "Informe uma Venda.");

		List<Venda> vendas = getDAO().findByExample(venda);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(vendas);

		if (CollectionUtils.isEmpty(vendas)) {
			serviceResponse.addWarning("Nenhum registro encontrado!");
		}
		
		return serviceResponse;
	}
	
	private Venda salvarVenda(Venda venda, Boolean eventoVenda, Boolean salvarBeneficiario, String usuarioHost, String usuarioLogado
			, String chaveAtendimento, Boolean finaliza, String telefone, List<TelefoneCliente> telefones) throws ServiceException {
		ContatoMailing contatoMailing = null;
		Set<Beneficiario> beneficiarioList = null;
		Beneficiario beneficiario = null;
		Produto produto  = null;
		
		try {
			contatoMailing = contatoMailingService.findById(venda.getClienteCampanha().getContatoMailing().getId());
		} catch (ValidationException e) {
			throw new ServiceException("Erro ao buscar Contato Mailing para validações.");
		}
		
		atualizarContatoMailing(venda, contatoMailing, usuarioHost, usuarioLogado, telefones);
		
		if (eventoVenda || salvarBeneficiario) {
			beneficiarioList = venda.getBeneficiarioList();
			
			beneficiario = new ArrayList<Beneficiario>(beneficiarioList).get(0);
			
			produto = new ArrayList<BeneficiarioPlano>(beneficiario.getBeneficiarioPlanoList()).get(0).getPlano().getProduto();
		}
		
		getDAO().saveOrUpdate(venda);
		
		if (StringUtils.isEmpty(venda.getCodigoApolice()) && eventoVenda && finaliza) {
			preencherCodigoApolice(venda, produto, contatoMailing);
			getDAO().update(venda);
		}
		
		atualizarHistoricoContatato(venda, chaveAtendimento, usuarioHost, usuarioLogado, telefone);
		
		venda.setBeneficiarioList(beneficiarioList);
		
		if (eventoVenda || salvarBeneficiario) {
			preencherValoresBeneficiariosESalvar(venda, beneficiarioList, usuarioHost, usuarioLogado);
		}
		
		return venda;
	}

	private void atualizarHistoricoContatato(Venda venda, String chaveAtendimento, String usuarioHost, String usuarioLogado, String telefone) throws ServiceException {
		
		HistoricoContato historicoContato = null;
		
		for (HistoricoContato historico : venda.getClienteCampanha().getHistoricoContatoList()) {
			if (historico.getChaveAtendimento().equalsIgnoreCase(chaveAtendimento)) {
				historicoContato = historico;
				break;
			}
		}
		
		for (TelefoneCliente telefoneCliente : venda.getClienteCampanha().getTelefoneClienteList()) {
			if ((telefoneCliente.getDdd()+telefoneCliente.getTelefone()).equalsIgnoreCase(telefone)) {
				historicoContato.setIdTelefoneCliente(telefoneCliente.getId());
				break;
			}
		}
		
		historicoContato.setIdEvento(venda.getIdEvento());
		historicoContato.setDataFimContato(new Date());
		historicoContato.setFlagEnabled(Boolean.TRUE);
		historicoContato.setChaveAtendimento(chaveAtendimento);
		
		historicoContatoService.atualizar(historicoContato, usuarioHost, usuarioLogado);
	}

	@Override
	public ServiceResponse salvarOuAtualizar(Venda venda, String usuarioHost, String usuarioLogado, String chaveAtendimento, String telefone, 
			Rejeicao rejeicao, List<TelefoneCliente> telefones) throws ServiceException {
		
		setDadosVenda(venda, usuarioHost, usuarioLogado);
		Set<Beneficiario> beneficiarioList = null;
		BeneficiarioPlano titularPlano = verificarIntegridadePlanosDependentes(venda);
		verificarAlteracoesPlanos(venda, titularPlano);
		Boolean eventoVenda = (venda.getEvento() != null && venda.getEvento().getFlagVenda());
		Boolean eventoImplicito = (venda.getEvento() != null  && venda.getEvento().getFlagImplicitoUsuario());
		
		if (venda.getEvento() == null || venda.getEvento().getId() == null) {
			mensagemErro("Informe um Evento para processar a tabulação do Cliente!");
		}
		
		if (eventoVenda || eventoImplicito) {
			antesSalvar(venda);
		} else {
			beneficiarioList = venda.getBeneficiarioList();
			removerObjetosTransientesAndValidarEvento(venda);
		}
		venda.setFlagVendaFechada(Boolean.FALSE);
		
		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(salvarVenda(venda, eventoVenda, eventoImplicito, usuarioHost, usuarioLogado, chaveAtendimento
				, Boolean.FALSE, telefone, telefones));
		serviceResponse.addInfo("Registro salvo com sucesso.");
		
		if (!eventoVenda && !eventoImplicito) {
			venda.setBeneficiarioList(beneficiarioList);
			if(rejeicao != null && !venda.getEvento().getTipoEvento().getNomeTipoEvento().equalsIgnoreCase(buscarNomeTipoEvento())){
	            rejeicao.setFlagTratado(Boolean.TRUE);
	            rejeicaoService.update(rejeicao, usuarioHost, usuarioLogado);
	        }
		}
		
		return serviceResponse;
		
	}
	
	private void setDadosVenda(Venda venda, String usuarioHost, String usuarioLogado) {
		if (!CollectionUtils.isEmpty(venda.getBeneficiarioList()) && venda.getBeneficiarioList().size() == 1){
			for (Beneficiario beneficiarioTemp : venda.getBeneficiarioList()) {
				beneficiarioTemp.setGrauParentesco(GrauParentesco.TITULAR);
				beneficiarioTemp.setTipoBeneficiario(TipoBeneficiario.TITULAR.getValor());
			}
		} 
		
		setLogPojo(venda, usuarioHost, usuarioLogado);
		venda.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
	}
	
	@Override
	public ServiceResponse finalizarVenda(Venda venda, Rejeicao rejeicao, String usuarioHost, String usuarioLogado
			, String chaveAtendimento, String telefone, List<TelefoneCliente> telefones) throws ServiceException {
		setDadosVenda(venda, usuarioHost, usuarioLogado);
		Set<Beneficiario> beneficiarioList = null;
		BeneficiarioPlano titularPlano = verificarIntegridadePlanosDependentes(venda);
		Boolean eventoVenda = venda.getEvento() != null && venda.getEvento().getFlagVenda();
		
		if (venda.getEvento() == null || venda.getEvento().getId() == null) {
			mensagemErro("Informe um Evento para processar a tabulação do Cliente!");
		}
		
		if (eventoVenda) {
			antesFinalizar(venda);
			venda.setFlagVendaFechada(Boolean.TRUE);
			venda.setDataEnvio(null);
		} else {
			beneficiarioList = venda.getBeneficiarioList();
			removerObjetosTransientesAndValidarEvento(venda);
		}
		
		venda.setDataVenda(new Date());
		
		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(salvarVenda(venda, eventoVenda, Boolean.TRUE, usuarioHost, usuarioLogado
				, chaveAtendimento, Boolean.TRUE, telefone, telefones));
		
		serviceResponse.addInfo("Registro salvo com sucesso.");
		
		verificarTipoPlano(venda, titularPlano);
		
		if (!eventoVenda) {
			venda.setBeneficiarioList(beneficiarioList);
		}
 
		if(rejeicao!=null && !venda.getEvento().getTipoEvento().getNomeTipoEvento().equalsIgnoreCase(buscarNomeTipoEvento())) {
            rejeicao.setFlagTratado(Boolean.TRUE);
            rejeicaoService.update(rejeicao, usuarioHost, usuarioLogado);
        }
                
		return serviceResponse;
	}
	
	private String buscarNomeTipoEvento () {
		String eventoSemResultado = null;
		try {
			eventoSemResultado = parametroSistemaService.buscarValorParametro(ParametroSistema.NOME_TIPO_EVENTO_SEM_RESULTADO);
		} catch (Exception e) {
				throw new RuntimeException("Erro ao buscar parametro " 
						+ ParametroSistema.NOME_TIPO_EVENTO_SEM_RESULTADO + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(eventoSemResultado)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.NOME_TIPO_EVENTO_SEM_RESULTADO 
					+ "' não encontrado! É necessário para o tratamento de Rejeições.");
		}
		
		return eventoSemResultado;
	}
	
	private void removerObjetosTransientesAndValidarEvento(Venda venda) throws ServiceException {
		venda.setBeneficiarioList(null);
		
		if (venda.getEsporte() != null && venda.getEsporte().getId() == null) {
			venda.setEsporte(null);
		} else {
			venda.setIdEsporte(venda.getEsporte().getId());
		}
		
		if (venda.getProfissao() != null && venda.getProfissao().getId() == null) {
			venda.setProfissao(null);
		} else {
			venda.setIdProfissao(venda.getProfissao().getId());
		}
		
		if (venda.getEstadoCivil() != null && venda.getEstadoCivil().getId() == null) {
			venda.setEstadoCivil(null);
		} else {
			venda.setIdEstadoCivil(venda.getEstadoCivil().getId());
		}
		
		if (venda.getEvento() == null || venda.getEvento().getId() == null) {
			mensagemErro("Informe um Evento para processar a tabulação do Cliente!");
		} else {
			venda.setIdEvento(venda.getEvento().getId());
		}
		
		if (venda.getClienteCampanha() != null) {
			venda.setIdClienteCampanha(venda.getClienteCampanha().getId());
		}
	}

	private void antesFinalizar(Venda venda) throws ServiceException {
		validacaoVendaUtils.validarClienteVendaDependente(venda);
	}
	
	@Override
	protected void antesSalvar(Venda venda) throws ServiceException {
		validacaoVendaUtils.validarClienteVenda(venda);
	}
	
	private void preencherValoresBeneficiariosESalvar(Venda venda, Set<Beneficiario> beneficiarioList, String usuarioHost, String usuarioLogado) throws ServiceException {
		 for (Beneficiario beneficiario : beneficiarioList) {
			 if (beneficiario.getGrauParentesco() == null ||  beneficiario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.TITULAR.getValor())) {
				 beneficiario.setGrauParentesco(GrauParentesco.TITULAR);
				 beneficiario.setTipoBeneficiario(TipoBeneficiario.TITULAR.getValor());
				 beneficiario.setIdVenda(venda.getId());
				 //beneficiarioPlanoList = beneficiario.getBeneficiarioPlanoList();
				 beneficiarioService.salvarOuAtualizar(beneficiario, usuarioHost, usuarioLogado);
			 }
		 }
	}

	private void preencherCodigoApolice(Venda venda, Produto produto, ContatoMailing contatoMailing) {
		
		String codigoBradescoProtecao = null;
		
		try {
			codigoBradescoProtecao = parametroSistemaService.buscarValorParametro(ParametroSistema.CODIGO_BRADESCO_CARTOES);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.CODIGO_BRADESCO_CARTOES + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(codigoBradescoProtecao)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.CODIGO_BRADESCO_CARTOES 
					+ "' não encontrado! É necessário do mesmo para criar o código da Apólice.");
		}
		
		String siglaCallink = null;
		
		try {
			siglaCallink = parametroSistemaService.buscarValorParametro(ParametroSistema.SIGLA_CALLINK);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.SIGLA_CALLINK + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(siglaCallink)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.SIGLA_CALLINK 
					+ "' não encontrado! É necessário do mesmo para criar o código da Apólice.");
		}
		
		Integer mes = DateUtils.getMesData(new Date());
		String sequencial = null;
		
		if (venda.getId().toString().length() == 1) {
			sequencial = "00" + venda.getId(); 
		} else if (venda.getId().toString().length() == 2)
			sequencial = "0" + venda.getId();
		else {
			sequencial = venda.getId().toString();
		}
		
		StringBuilder codigoApolice = new StringBuilder(); 
		codigoApolice.append(codigoBradescoProtecao);
		codigoApolice.append(produto.getCodigo());
		codigoApolice.append(siglaCallink);
		codigoApolice.append(contatoMailing.getIdLoteMailing());
		codigoApolice.append(produto.getSigla());
		codigoApolice.append(((StringUtils.toString(mes).length() == 2) ? mes : "0" + mes));
		codigoApolice.append(sequencial);		
		
		venda.setCodigoApolice(codigoApolice.toString());
	}
	
	private BeneficiarioPlano verificarIntegridadePlanosDependentes(Venda venda) throws ServiceException {
		
		validarVazio(venda, "Informe a Venda");
		validarVazio(venda.getBeneficiarioList(), "Informe um Beneficiário.");
		
		BeneficiarioPlano titularPlano = null;
		
		for (Beneficiario beneficiario : venda.getBeneficiarioList()) {
			if (beneficiario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.TITULAR.getValor())) {
				titularPlano = new ArrayList<BeneficiarioPlano>(beneficiario.getBeneficiarioPlanoList()).get(0);
				break;
			}
		}
		
		validarVazio(titularPlano, "Informe não cadastrado.");
		validarVazio(venda.getBeneficiarioList(), "Informe o Plano do Titular.");
		
		return titularPlano;
	}
	
	private void verificarAlteracoesPlanos(Venda venda, BeneficiarioPlano titularPlano) throws ServiceException {
		if (titularPlano.getPlano() != null && titularPlano.getPlano().getTipoPlano() != null
					&& titularPlano.getPlano().getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())) {
			BeneficiarioPlano beneficiarioPlano = null;
			
		    for (Beneficiario beneficiario : venda.getBeneficiarioList()) {
		    	if (beneficiario.getTipoBeneficiario() != null && beneficiario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.DEPENDENTE.getValor())) {
		    	    beneficiarioPlano = new ArrayList<BeneficiarioPlano>(beneficiario.getBeneficiarioPlanoList()).get(0);
		    	    
		    	    if (!beneficiarioPlano.getPlano().equals(titularPlano.getPlano())) {
		    	    	beneficiarioPlano.setPlano(titularPlano.getPlano());
		    	    	beneficiarioPlanoService.salvarOuAtualizar(beneficiarioPlano);
		    	    }
			     }
		    }
		}
	}
	
	//TODO - realizar essa implementacao, como spurg de dados por exemplo - melhoria
	private void verificarTipoPlano(Venda venda, BeneficiarioPlano titularPlano) throws ServiceException{
		
		//TODO - teoricamente o tipo plano e seu nome sao not null
		if ( titularPlano.getPlano() != null && titularPlano.getPlano().getTipoPlano() != null 
					&& titularPlano.getPlano().getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.INDIVIDUAL.getValor())){
			for (Beneficiario beneficiario: venda.getBeneficiarioList()){
				if (beneficiario.getTipoBeneficiario() != null && beneficiario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.DEPENDENTE.getValor())) {
					beneficiarioService.remover(beneficiario);
				}
			}
			
			venda.getBeneficiarioList().clear();
			venda.getBeneficiarioList().add(titularPlano.getBeneficiario());
		}
	}
	
	private void atualizarContatoMailing(Venda venda, ContatoMailing contatoMailing, String usuarioHost, 
			String usuarioLogado, List<TelefoneCliente> telefones) throws ServiceException {
		
		ContatoMailing contatoVenda = venda.getClienteCampanha().getContatoMailing();
		contatoMailingService.atualizarContatoMailing(contatoVenda, contatoMailing, usuarioHost, usuarioLogado, telefones);
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}
	
	@Override
	public void updateDataEnvioNuvem(Date dataEnvio) throws ServiceException {
		try {
			getDAO().updateDataEnvioNuvem(dataEnvio);
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}

}
