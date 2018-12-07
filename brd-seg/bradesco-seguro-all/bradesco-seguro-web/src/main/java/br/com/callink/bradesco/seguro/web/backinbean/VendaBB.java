package br.com.callink.bradesco.seguro.web.backinbean;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.component.UIExtendedDataTable;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.NumberUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dto.ScriptAuditoriaDTO;
import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;
import br.com.callink.bradesco.seguro.entity.Cobertura;
import br.com.callink.bradesco.seguro.entity.Esporte;
import br.com.callink.bradesco.seguro.entity.EstadoCivil;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.Profissao;
import br.com.callink.bradesco.seguro.entity.Rejeicao;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.enums.ProfissaoEnum;
import br.com.callink.bradesco.seguro.enums.Sexo;
import br.com.callink.bradesco.seguro.enums.TipoBeneficiario;
import br.com.callink.bradesco.seguro.enums.TipoPlanoEnum;
import br.com.callink.bradesco.seguro.enums.UF;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.IEsporteService;
import br.com.callink.bradesco.seguro.service.IEstadoCivilService;
import br.com.callink.bradesco.seguro.service.IEventoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IPlanoService;
import br.com.callink.bradesco.seguro.service.IProdutoService;
import br.com.callink.bradesco.seguro.service.IProfissaoService;
import br.com.callink.bradesco.seguro.service.IRejeicaoService;
import br.com.callink.bradesco.seguro.service.ITelefoneClienteService;
import br.com.callink.bradesco.seguro.service.ITipoEventoService;
import br.com.callink.bradesco.seguro.service.IUsuarioService;
import br.com.callink.bradesco.seguro.service.IVendaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ScriptAuditoriaTemplate;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.lyricall.LyricallUrlParameters;
import br.com.callink.bradesco.seguro.web.selectitem.EsporteSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.EstadoCivilSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.PlanoSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.ProdutoSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.ProfissaoSelectItem;
import br.com.callink.bradesco.seguro.web.util.TelefoneValidation;

@SuppressWarnings("rawtypes")
@ManagedBean
@ViewScoped
public class VendaBB extends GenericCrudBB<Venda, IVendaService> {

    private static final long serialVersionUID = -5730543916528852496L;

    private static final Integer INDEX_CPF_PRIMEIROS_DIGITOS_INICIO = 0;
    private static final Integer INDEX_CPF_PRIMEIROS_DIGITOS_FIM = 3;
    private static final Integer INDEX_CPF_DIGITAVEL_INICIO = 3;
    private static final Integer INDEX_CPF_DIGITAVEL_FIM = 9;
    private static final Integer INDEX_CPF_DIGITO_VERIFICADOR_INICIO = 9;
    private static final Integer INDEX_CPF_DIGITO_VERIFICADOR_FIM = 11;

    @Inject
    private BeneficiarioBB beneficiarioBB;

    @EJB
    private IClienteCampanhaService clienteCampanhaService;

    @EJB
    private IVendaService<Venda> vendaService;

    @EJB
    private IProdutoService<Produto> produtoService;

    @EJB
    private IPlanoService<Plano> planoService;

    @EJB
    private IProfissaoService<Profissao> profissaoService;

    @EJB
    private IEsporteService<Esporte> esporteService;

    @EJB
    private IEstadoCivilService<EstadoCivil> estadoCivilService;

    @EJB
    private ITelefoneClienteService<TelefoneCliente> telefoneClienteService;

    @EJB
    private IUsuarioService usuarioService;

    @EJB
    private IRejeicaoService rejeicaoService;
    
    @EJB
    private ITipoEventoService tipoEventoService;
    
    @EJB
    private IEventoService eventoService;
    
    @EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

    //parametros query string
    private String qsParamIdClienteCampanha;
    private String qsParamCallURL;
    
    private String cpfInicio;
    private String cpfMeio;
    private String cpfFim;
    private String idade;
    private String novoTelefone;
    private List<TelefoneCliente> telefones;
    private BigDecimal valorPlanoSelecionado;
    private Sexo[] sexoList;
    private List<EstadoCivil> estadoCivilList;
    private List<Plano> planoList;
    private List<Evento> eventoList;
    private ClienteCampanha clienteCampanha;
    private int currentVencimentoPage = 1;
    private String showHiv = "hidden";
    private String showConfirma = "hidden";
    private String showFinaliza = "none";
    private String showFinalizaVenda = "none";
    private String showAbaDependente = "hidden";
    private String showDadosEmpresa = "hidden";
    private String showEsporte = "hidden";
    private Boolean renderEsporte = Boolean.FALSE;
    private String tipoPlano = "Individual";
    private Boolean sucesso;
    private boolean habilitarAbaDependente = Boolean.FALSE;
    private LyricallUrlParameters lyricallUrlParameters;
    private TipoPlanoEnum tipoPlanoEnum;
    private UF[] estadoList;
    private Collection<Object> selectionEvento;
    private Collection<Object> selectionTipoEvento; 
    private String styleEvento = "";

    private Produto produtoSelecionado;
    private Plano planoSelecionado;
    private TelefoneCliente telefoneCliente;

    private ProdutoSelectItem produtoSelectItem;
    private PlanoSelectItem planoSelectItem;
    private ProfissaoSelectItem profissaoSelectItem;
    private EsporteSelectItem esporteSelectItem;
    private EstadoCivilSelectItem estadoCivilSelectItem;
    
    private List<TipoEvento> tipoEventoList; 
    private TipoEvento tipoEventoSelecionado;

    private String scriptProduto;
    private String usuarioAuditor;
    private String senhaAuditor;
    private Rejeicao rejeicao;
    private Profissao profissaoAposentadoInvalidez;

	private Boolean flagUpdate = Boolean.FALSE;

    @PostConstruct
    public void init() throws Exception {
    	estadoList = UF.values();
        initLyricallParams();
        sexoList = Sexo.values();
        buscarDadosCliente();
        preencherCpf();
        calcularIdade();
        preencherTelefones();
        beneficiarioBB.setVendaBB(this);
        atualizarHiv();
        
        Map<String, String> request = getExternalContext().getRequestParameterMap();
        setQsParamIdClienteCampanha(request.get("IdClienteCampanha"));
        setQsParamCallURL(request.get("callURL"));
        
        getProdutoSelectItem();
    }

	@SuppressWarnings("unchecked")
	private void buscarDadosCliente() throws Exception {

        clienteCampanha = clienteCampanhaService.buscarClienteCampanhaPorIdEChaveAtendimento(
        		new BigInteger(lyricallUrlParameters.getIdClienteCampanha()),lyricallUrlParameters.getChaveAtendimento(), getApplicationHost() 
        		, lyricallUrlParameters.getLoginUsuario(), lyricallUrlParameters.getTelefone());
		
		if (clienteCampanha.getVendaList() != null && !clienteCampanha.getVendaList().isEmpty()) {
			
			setEntity(new ArrayList<Venda>(clienteCampanha.getVendaList()).get(0));
			getEntity().setClienteCampanha(clienteCampanha);
			
			preencherDadosVenda();
			
			List<Rejeicao> rejeicaoList = rejeicaoService.findRejeicaoByVenda(getEntity().getId());
			
			if (!CollectionUtils.isEmpty(rejeicaoList)) {
				rejeicao = rejeicaoList.get(0);
			}

		} else {
			newVenda();
		}
		
	}

    private void initLyricallParams() throws Exception {

        try {
            lyricallUrlParameters = new LyricallUrlParameters(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap());
        } catch (Exception ex) {
            String erro = "Parametro(s): %s Requerido(s)<br>%s?%s<br>";
            throw new Exception(String.format(erro, ex.getMessage(), getRequest().getRequestURI(), getRequest().getQueryString()));
        }
    }
       
    public void finalizaAtendimento() {
        try {
        	
            if (getEntity().getEvento().getFlagVenda()) {
                usuarioService.validaAuditor(usuarioAuditor, senhaAuditor);
            }
            
            vendaService.finalizarVenda(
            		getEntity(), rejeicao, getApplicationHost(), lyricallUrlParameters.getLoginUsuario(), 
            		lyricallUrlParameters.getChaveAtendimento(), lyricallUrlParameters.getTelefone(), getTelefones());

           redirecionar(); 
        } catch (Exception ex) {
           error(ex);
        }
    }
 
    private void redirecionar() throws Exception {
    	 log("URL-redirecionamento: "+lyricallUrlParameters.getUrl(getEntity().getEvento()));
         getExternalContext().encodeResourceURL(lyricallUrlParameters.getUrl(getEntity().getEvento()));
         getExternalContext().setResponseStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
         getExternalContext().setResponseHeader("Location", lyricallUrlParameters.getUrl(getEntity().getEvento()));  
	}

	private void preencherDadosVenda() {
    	List<Beneficiario> beneficiarioList = null;
    	
    	selectionEvento = new ArrayList<Object>();
    	selectionEvento.add(getEntity().getEvento());
    	selectionTipoEvento = new ArrayList<Object>();
    	selectionTipoEvento.add(getEntity().getEvento().getTipoEvento());
    	tipoEventoSelecionado = getEntity().getEvento().getTipoEvento();
    	styleEvento = "rf-edt-r-sel rf-edt-r-act";
    	
    	valorPlanoSelecionado = getEntity().getValorTotal();
    	flagUpdate = Boolean.TRUE;
    	
    	if (getEntity().getBeneficiarioList() != null && !getEntity().getBeneficiarioList().isEmpty()) {
    		beneficiarioList = new ArrayList<Beneficiario>();
    	}
    	
		for (Beneficiario benefiricario : getEntity().getBeneficiarioList()) {
			if (benefiricario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.TITULAR.getValor())) {
				planoSelecionado = new ArrayList<BeneficiarioPlano>(benefiricario.getBeneficiarioPlanoList()).get(0).getPlano();
				produtoSelecionado = new ArrayList<BeneficiarioPlano>(benefiricario.getBeneficiarioPlanoList()).get(0).getPlano().getProduto();
				
			} else {
				beneficiarioList.add(benefiricario);
			}
		}	
		
		beneficiarioBB.setDados(beneficiarioList);
		
		this.cpfMeio = StringUtils.substring(getEntity().getClienteCampanha().getContatoMailing().getCpf(), INDEX_CPF_DIGITAVEL_INICIO, INDEX_CPF_DIGITAVEL_FIM);
	}

	private void preencherTelefones() {
			if(telefones == null || telefones.isEmpty()){
				setTelefones(new ArrayList<TelefoneCliente>(getEntity().getClienteCampanha().getTelefoneClienteList()));
			}
    }

    private void preencherCpf() {
    	String cpf = getEntity().getClienteCampanha().getContatoMailing().getCpf();
    	cpf = fillLeftZeros(cpf, 11);
    	
		this.cpfInicio = StringUtils.substring(cpf, INDEX_CPF_PRIMEIROS_DIGITOS_INICIO, INDEX_CPF_PRIMEIROS_DIGITOS_FIM);
		this.cpfFim = StringUtils.substring(cpf, INDEX_CPF_DIGITO_VERIFICADOR_INICIO, INDEX_CPF_DIGITO_VERIFICADOR_FIM);
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

	protected void antesSalvar(Boolean finalizarVenda) throws ServiceException {
		String idTabulacaoImplicita = null;
		
		if (!finalizarVenda) {
    		
    		try {
    			idTabulacaoImplicita = parametroSistemaService.buscarValorParametro(ParametroSistema.CONFIRMAÇAO_VENDA_FAMILIAR_SEM_DEPENDENTES);
    		} catch (Exception e) {
    			throw new RuntimeException("Erro ao buscar parametro " 
    					+ ParametroSistema.CONFIRMAÇAO_VENDA_FAMILIAR_SEM_DEPENDENTES + ". Necessário para confirmar uma Venda. Erro: " + e.getMessage(), e);
    		}
    		
    		getEntity().setEvento(eventoService.findById(new BigInteger(idTabulacaoImplicita)));
    	}
		
		Beneficiario titular = new Beneficiario();
		Set<Beneficiario> dependentes = null;
		
		if (CollectionUtils.isEmpty(getEntity().getBeneficiarioList())) {
			titular.setBeneficiarioPlanoList(new HashSet<BeneficiarioPlano>());
			titular.getBeneficiarioPlanoList().add(new BeneficiarioPlano(titular));
		} else { 
			dependentes = new HashSet<Beneficiario>();
			for (Beneficiario beneficiario : getEntity().getBeneficiarioList()) {
				if (beneficiario.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.TITULAR.getValor())) {
					titular = beneficiario;
				} else {
					dependentes.add(beneficiario);
				}
			}
		}
		
		titular.setCpf(cpfInicio + cpfMeio + cpfFim);
		titular.setDataExpedicao(getEntity().getClienteCampanha().getContatoMailing().getDataExpedicao());
		titular.setDataNascimento(getEntity().getClienteCampanha().getContatoMailing().getDataNascimento());
		titular.setEsporte(getEntity().getEsporte());
		titular.setFlagAposentadoInvalidez(getEntity().getFlagAposentadoInvalidez());
		titular.setFlagHiv(getEntity().getFlagHiv());
		titular.setNome(getEntity().getClienteCampanha().getContatoMailing().getNome());
		titular.setOrgaoExpedidor(getEntity().getClienteCampanha().getContatoMailing().getOrgaoExpedidor());
		titular.setProfissao(getEntity().getProfissao());
		titular.setRg(getEntity().getClienteCampanha().getContatoMailing().getRg());
		titular.setSexo(getEntity().getClienteCampanha().getContatoMailing().getSexo());
		titular.setVenda(getEntity());
        titular.setTipoBeneficiario(TipoBeneficiario.TITULAR.getValor()); 
        titular.setGrauParentesco(GrauParentesco.TITULAR);
		
		for (BeneficiarioPlano beneficiarioPlano : titular.getBeneficiarioPlanoList()) {
			if (titular.equals(beneficiarioPlano.getBeneficiario())) {
				beneficiarioPlano.setBeneficiario(titular);
				beneficiarioPlano.setPlano(planoSelecionado);
				break;
			}
		}
		Set<Beneficiario> beneficiarioSet = new HashSet<Beneficiario>();
		beneficiarioSet.add(titular);
		
		if (!CollectionUtils.isEmpty(dependentes)) {
			beneficiarioSet.addAll(dependentes);
		}
		
		getEntity().setBeneficiarioList(beneficiarioSet);
	}
	
    @SuppressWarnings("unchecked")
	public void salvar(Boolean finalizarVenda) throws Exception {
    	setSucesso(Boolean.FALSE);

        antesSalvar(finalizarVenda);

        ServiceResponse serviceResponse = null;
        serviceResponse = getService().salvarOuAtualizar(
        			getEntity(), getApplicationHost(), lyricallUrlParameters.getLoginUsuario(), lyricallUrlParameters.getChaveAtendimento(),
        			lyricallUrlParameters.getTelefone(), rejeicao, getTelefones());
        
        setEntity((Venda) serviceResponse.getData());

        addMsgs(serviceResponse);
        setSucesso(Boolean.TRUE);
        
        aposSalvar(finalizarVenda);
    }
    
    protected void aposSalvar(Boolean finalizarVenda) throws Exception {
    	 atualizarConfirma();
         atualizarFinaliza();
         habilitarAbaDependente();
         
         if (getEntity().getEvento() != null && getEntity().getEvento().getFlagImplicitoUsuario()) {
        	 getEntity().setEvento(null);
        	 selectionEvento = null;
         }
         
         if (finalizarVenda) {
             if (getEntity().getEvento() != null && getEntity().getEvento().getFlagVenda()) {
                 geraScript();
             } else {
            	 try {
            		 redirecionar();
            	 } catch (Exception e) {
            		 error(e);
            	 }
             }
         }
    }
	
	private void newVenda() {
		setEntity(new Venda(Boolean.FALSE
				, Boolean.FALSE, Boolean.FALSE));
		getEntity().setBeneficiarioList(new HashSet<Beneficiario>());
		getEntity().setClienteCampanha(clienteCampanha);
	}
	
	public void calcularIdade() {
		if (getEntity().getClienteCampanha().getContatoMailing().getDataNascimento() != null) {
			idade = DateUtils.getIdadeByNascimento(getEntity().getClienteCampanha().getContatoMailing().getDataNascimento());
		}
	}
	
	public void habilitarAbaDependente(){
		if (planoSelecionado != null && planoSelecionado.getTipoPlano() != null 
					&& planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())
					&& getEntity().getId() != null) {
			//this.habilitarAbaDependente = Boolean.TRUE;
			setShowAbaDependente("none");
		} else {
			//this.habilitarAbaDependente = Boolean.FALSE;
			setShowAbaDependente("hidden");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillProdutoSelectItem() throws ServiceException {
		if (produtoSelectItem.size() == 1) {
			ServiceResponse serviceResponse = produtoService.findEnabledAndNotRemoved();
			produtoSelectItem.setListAndOrder((List<Produto>)serviceResponse.getData());
			
			if (!StringUtils.isEmpty(getEntity().getClienteCampanha().getContatoMailing().getProduto()) && produtoSelecionado == null) {
				for (Produto produto : (List<Produto>)serviceResponse.getData()) {
					if (produto.getNome().equalsIgnoreCase(getEntity().getClienteCampanha().getContatoMailing().getProduto())) {
						produtoSelecionado = produto;
						break;
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillPlanosSelectItem() throws ServiceException, ValidationException {
        if (produtoSelecionado != null && produtoSelecionado.getId() != null) {
            ServiceResponse serviceResponse = planoService.buscarPlanoPorProdutoAndIdade(produtoSelecionado, this.idade);
            planoSelectItem.setListAndOrder((List<Plano>) serviceResponse.getData());
        } else {
            planoSelectItem.setListAndOrder(null);
        }

    }

    @SuppressWarnings("unchecked")
    private void fillProfissoesSelectItem() throws ServiceException {
        ServiceResponse serviceResponse = profissaoService.findEnabledAndNotRemoved();
        List<Profissao> profissoes = (List<Profissao>) serviceResponse.getData();
        if (this.profissaoAposentadoInvalidez == null
        		&& !CollectionUtils.isEmpty(profissoes)){
        	for(Profissao profissao: profissoes){
        		if (profissao.getNome().equalsIgnoreCase(ProfissaoEnum.APOSENTADO_INVALIDEZ.getValor())
        				&& profissao.getCodigo().equals(ProfissaoEnum.APOSENTADO_INVALIDEZ.getCodigo())) {
        			this.profissaoAposentadoInvalidez = profissao;
        		}
        	}
        }
        profissaoSelectItem.setListAndOrder((List<Profissao>) serviceResponse.getData());
    }

    @SuppressWarnings("unchecked")
    private void fillEsportesSelectItem() throws ServiceException {
        ServiceResponse serviceResponse = esporteService.findEnabledAndNotRemoved();
        esporteSelectItem.setListAndOrder((List<Esporte>) serviceResponse.getData());
    }

    @SuppressWarnings("unchecked")
    private void fillEstadosCivisSelectItem() throws ServiceException {
        ServiceResponse serviceResponse = estadoCivilService.findAll();
        estadoCivilSelectItem.setListAndOrder((List<EstadoCivil>) serviceResponse.getData());
    }

    public void adicionarTelefone() throws ServiceException, ValidationException {
        if (getClienteCampanha() == null || getClienteCampanha().getId() == null) {
            throw new ValidationException("Cliente Campanha Inválido.");
        }

        if (StringUtils.isEmpty(getNovoTelefone())) {
            throw new ValidationException("Telefone vazio.");
        }

        TelefoneCliente telefoneCliente = TelefoneValidation.getInstance().validarTelefone(getNovoTelefone());

        if (telefoneCliente == null) {
            throw new ValidationException("Telefone inválido.");
        }

        telefoneCliente.setIdClienteCampanha(getClienteCampanha().getId());

        telefoneCliente.setFlagNovo(Boolean.TRUE);
        telefoneCliente.setFlagEnabled(Boolean.TRUE);
        telefoneCliente.setDataCadastro(new Date());
        telefoneCliente.setFlagBloqueadoProcon(Boolean.FALSE);
        telefoneCliente.setFlagPreferencial(Boolean.FALSE);
        telefoneCliente.setFlagInvalido(Boolean.FALSE);
        telefoneCliente.setSequencia(BigInteger.ZERO);
        telefoneCliente.setIdClienteCampanha(clienteCampanha.getId());

        telefoneClienteService.save(telefoneCliente, getApplicationHost(), lyricallUrlParameters.getLoginUsuario());

        getTelefones().add(telefoneCliente);
        novoTelefone = "";
        info("Telefone Cadastrado com Sucesso! ");
    }

    public void updateTelefone(TelefoneCliente telefoneCliente) throws ServiceException, ValidationException {

        telefoneClienteService.update(telefoneCliente, getApplicationHost(), lyricallUrlParameters.getLoginUsuario());
	}
	
	public void calcularPlanoSelecionadoAndSetarTipoPlano() {
		
		if (produtoSelecionado != null && planoSelecionado != null && planoSelecionado.getTipoPlano() != null) {
			
			if (planoSelecionado.getTipoPlano() != null 
					&& planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())) {
				setTipoPlano(TipoPlanoEnum.FAMILIAR.getValor());
			} else {
				setTipoPlano(TipoPlanoEnum.INDIVIDUAL.getValor());
			}
			
			valorPlanoSelecionado = BigDecimal.ZERO;
			BigDecimal valorConjuge = BigDecimal.ZERO;
			
			if (ValidacaoVendaProduto.VALIDACAO_1.getCodigo().equalsIgnoreCase(produtoSelecionado.getCodigoValidacaoProduto())) {
				valorPlanoSelecionado = (planoSelecionado.getValorPremio() != null) ? planoSelecionado.getValorPremio() : BigDecimal.ZERO;
			} else {
				//protecao plus e dhi - validacao 2 e validacao 3
				if (planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.INDIVIDUAL.getValor())) {
					valorPlanoSelecionado = (planoSelecionado.getValorTitular() != null) ? planoSelecionado.getValorTitular() : BigDecimal.ZERO;;
				} else {
					//tipo familiar
					if (!CollectionUtils.isEmpty(getEntity().getBeneficiarioList())) {
						for (Beneficiario beneficiario : getEntity().getBeneficiarioList()) {
							//TODO - melhorar isso aqui, pensar em um enum
							if (beneficiario.getGrauParentesco().equals(GrauParentesco.FILHO) || beneficiario.getGrauParentesco().equals(GrauParentesco.ENTEADO)) {
								valorPlanoSelecionado = valorPlanoSelecionado.add(((planoSelecionado.getValorPorFilho() != null) ? planoSelecionado.getValorPorFilho() : BigDecimal.ZERO));
							}
							
							if (beneficiario.getGrauParentesco().equals(GrauParentesco.CONJUGE)) {
								valorConjuge = valorConjuge.add(((planoSelecionado.getValorTitularConjuge() != null) ? planoSelecionado.getValorTitularConjuge() : BigDecimal.ZERO)); 
							}
						}
					}
					
					if (valorConjuge.compareTo(BigDecimal.ZERO) == 0) {
						valorPlanoSelecionado = valorPlanoSelecionado.add(((planoSelecionado.getValorTitular()!= null) ? planoSelecionado.getValorTitular() : BigDecimal.ZERO));
					} else {
						valorPlanoSelecionado = valorPlanoSelecionado.add(valorConjuge);
					}
				}
			}
		}
		
		getEntity().setValorTotal(valorPlanoSelecionado);
		habilitarAbaDependente();
		atualizarConfirma();
		atualizarFinaliza();
	}
	
	public void atualizarConfirma() {
		if (planoSelecionado != null && planoSelecionado.getTipoPlano() != null 
					&& planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())
					&& getEntity().getId() == null) {
			 setShowConfirma("none");
		} else {
			 setShowConfirma("hidden");
		}
	}
	
	public void atualizarFinaliza() {
		if ((getEntity().getId() != null && getEntity().getEvento() != null)
					|| (planoSelecionado != null && planoSelecionado.getTipoPlano() != null 
						&& planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.INDIVIDUAL.getValor()) 
						&& getEntity().getId() == null && getEntity().getEvento() != null)
					|| (getEntity().getEvento() != null && !getEntity().getEvento().getFlagVenda())) {
			
			if (!getEntity().getEvento().getFlagVenda()) {
				 setShowFinaliza("block");
				 setShowFinalizaVenda("none");
			} else if (getEntity().getEvento().getFlagVenda()) {
				 setShowFinaliza("none");
				 setShowFinalizaVenda("block");
			}
		} else {
			 setShowFinaliza("none");
			 setShowFinalizaVenda("none");
		}
		
	}

	
	public void atualizarHiv() {
		if (!flagUpdate) {
			planoSelecionado = null;
			getEntity().setValorTotal(BigDecimal.ZERO);
		}
		
        if (produtoSelecionado != null && produtoSelecionado.getCodigoValidacaoProduto() != null 
        			&& (produtoSelecionado.getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_2.getCodigo()) 
        					|| produtoSelecionado.getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_3.getCodigo()))) {
        	beneficiarioBB.setShowHiv("none");
            setShowHiv("none");
        } else {
        	beneficiarioBB.setShowHiv("hidden");
            setShowHiv("hidden");
        }
        
        atualizarCamposTela();
        atualizarConfirma();
        atualizarFinaliza();
        habilitarAbaDependente();
        
        flagUpdate = Boolean.FALSE;
    }
	
	private void atualizarCamposTela() {
		atualizarDadosEmpresa();
		atualizarEsporte();
	}

	private void atualizarDadosEmpresa() {
		if (produtoSelecionado != null && produtoSelecionado.getCodigoValidacaoProduto() != null 
    			&& produtoSelecionado.getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_3.getCodigo())) {
			setShowDadosEmpresa("none");
	    } else {
	        setShowDadosEmpresa("hidden");
	    }
	}
	
	private void atualizarEsporte() {
		if (produtoSelecionado != null && produtoSelecionado.getCodigoValidacaoProduto() != null 
    			&& produtoSelecionado.getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_3.getCodigo())) {
			setShowEsporte("hidden");
			setRenderEsporte(Boolean.FALSE);
	    } else {
	        setShowEsporte("none");
	        setRenderEsporte(Boolean.TRUE);
	    }
	}

	public void atualizarInvalidez() throws ServiceException {

		if (getEntity().getFlagAposentadoInvalidez()) {
			getEntity().setProfissao(this.profissaoAposentadoInvalidez);
		} else {
			getEntity().setProfissao(null);
		} 
	}
	
	public void atualizarInvalidezCombo() throws ServiceException {

		if (getEntity().getProfissao().equals(this.profissaoAposentadoInvalidez)) {
			getEntity().setFlagAposentadoInvalidez(Boolean.TRUE);
		} else {
			getEntity().setFlagAposentadoInvalidez(Boolean.FALSE);
		} 
	}
	
	public void atualizarTelefone() throws ServiceException {
        novoTelefone = "";

    }

    public void geraScript() throws Exception {
    	
    	ScriptAuditoriaDTO scr = null;

    	if (!CollectionUtils.isEmpty(planoSelecionado.getCoberturaList())) {
			Cobertura coberturaTitular = null;
			Cobertura coberturaConjuge = null;
			Cobertura coberturaFilho = null;
			Cobertura coberturaEnteado = null;
			
			for (Cobertura cobertura : planoSelecionado.getCoberturaList()) {
				if (cobertura.getGrauParentesco().equals(GrauParentesco.CONJUGE)) {
					coberturaConjuge = cobertura;
				} else if (cobertura.getGrauParentesco().equals(GrauParentesco.FILHO)) {
					coberturaFilho = cobertura;
				} else if (cobertura.getGrauParentesco().equals(GrauParentesco.ENTEADO)) {
					coberturaEnteado = cobertura;
				} else if (cobertura.getGrauParentesco().equals(GrauParentesco.TITULAR)) {
					coberturaTitular = cobertura;
				}
			}
		
	        scr = new ScriptAuditoriaDTO();
	        scr.setAposentadoInvalidez(getEntity().getFlagAposentadoInvalidez());
	        scr.setClienteNome(getEntity().getClienteCampanha().getContatoMailing().getNome());
	        scr.setClientePlano(planoSelecionado.getTipoPlano().getNomeTipoPlano());
	        scr.setClienteProduto(produtoSelecionado.getNome());
	        scr.setClienteCodigoValidacaoProduto(produtoSelecionado.getCodigoValidacaoProduto());
	        scr.setClienteValor(valorPlanoSelecionado.toPlainString());
	        scr.setEsporteRisco(getEntity().getEsporte().getFlagRisco());
	        scr.setProfissaoRisco(getEntity().getProfissao().getFlagRisco());
	
	        for (Beneficiario dpd : getEntity().getBeneficiarioList()) {
	            if (dpd.getGrauParentesco().equals(GrauParentesco.CONJUGE) && planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())) {
	                scr.addDependente(dpd.getNome(), dpd.getGrauParentesco().getDescricao(), NumberUtils.formatDecimalMask(coberturaConjuge.getValorTransporteColetivo())
	                		, NumberUtils.formatDecimalMask(coberturaConjuge.getValorDIHMotivoAcidentes()), NumberUtils.formatDecimalMask(coberturaConjuge.getValorSorteio()));
	            } else if (dpd.getGrauParentesco().equals(GrauParentesco.FILHO) && planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())) {
	                scr.addDependente(dpd.getNome(), dpd.getGrauParentesco().getDescricao(), NumberUtils.formatDecimalMask(coberturaFilho.getValorTransporteColetivo())
	                		, NumberUtils.formatDecimalMask(coberturaFilho.getValorDIHMotivoAcidentes()), NumberUtils.formatDecimalMask(coberturaFilho.getValorSorteio()));
	            } else if (dpd.getGrauParentesco().equals(GrauParentesco.ENTEADO) && planoSelecionado.getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.FAMILIAR.getValor())) { 
	            	scr.addDependente(dpd.getNome(), dpd.getGrauParentesco().getDescricao(), NumberUtils.formatDecimalMask(coberturaEnteado.getValorTransporteColetivo())
	                		, NumberUtils.formatDecimalMask(coberturaEnteado.getValorDIHMotivoAcidentes()), NumberUtils.formatDecimalMask(coberturaEnteado.getValorSorteio()));
	            } else if (dpd.getTipoBeneficiario().equalsIgnoreCase(TipoBeneficiario.TITULAR.getValor())) {
	            	scr.setValorDIH(NumberUtils.formatDecimalMask(coberturaTitular.getValorDIHMotivoAcidentes()));
	            	scr.setValorSorteio(NumberUtils.formatDecimalMask(coberturaTitular.getValorSorteio()));
	            	scr.setClienteValorCobertura(NumberUtils.formatDecimalMask(coberturaTitular.getValorTransporteColetivo()));
	            }
	        }
    	}
        
        this.scriptProduto = ScriptAuditoriaTemplate.getScript(scr);
        getEntity().setScriptAuditoria(scriptProduto);
    }

    public void validaAuditor() {
        try {
            usuarioService.validaAuditor(usuarioAuditor, senhaAuditor);
        } catch (ServiceException ex) {
            error(ex);
        }
    }

    @Override
    protected boolean isPesquisarTodos() {
        return false;
    }

    @Override
    protected IVendaService getService() {
        return vendaService;
    }

    @Override
    public void novoRegistro() throws Exception {

    }

    public ProdutoSelectItem getProdutoSelectItem() throws ServiceException {
        if (produtoSelectItem == null) {
            produtoSelectItem = new ProdutoSelectItem();
        }
        fillProdutoSelectItem();

        return produtoSelectItem;
    }

    public void setProdutoSelectItem(ProdutoSelectItem produtoSelectItem) {
        this.produtoSelectItem = produtoSelectItem;
    }

    public PlanoSelectItem getPlanoSelectItem() throws ServiceException, ValidationException {
        if (planoSelectItem == null) {
            planoSelectItem = new PlanoSelectItem();
        }
        fillPlanosSelectItem();

        return planoSelectItem;
    }

    public void setPlanoSelectItem(PlanoSelectItem planoSelectItem) {
        this.planoSelectItem = planoSelectItem;
    }

    public ProfissaoSelectItem getProfissaoSelectItem() throws ServiceException {
        if (profissaoSelectItem == null) {
            profissaoSelectItem = new ProfissaoSelectItem();
        }
        fillProfissoesSelectItem();

        return profissaoSelectItem;
    }

    public void setProfissaoSelectItem(ProfissaoSelectItem profissaoSelectItem) {
        this.profissaoSelectItem = profissaoSelectItem;
    }

    public EsporteSelectItem getEsporteSelectItem() throws ServiceException {
        if (esporteSelectItem == null) {
            esporteSelectItem = new EsporteSelectItem();
        }
        fillEsportesSelectItem();

        return esporteSelectItem;
    }

    public void setEsporteSelectItem(EsporteSelectItem esporteSelectItem) {
        this.esporteSelectItem = esporteSelectItem;
    }

    public EstadoCivilSelectItem getEstadoCivilSelectItem() throws ServiceException {
        if (estadoCivilSelectItem == null) {
            estadoCivilSelectItem = new EstadoCivilSelectItem();
        }
        fillEstadosCivisSelectItem();

        return estadoCivilSelectItem;
    }

    public void setEstadoCivilSelectItem(EstadoCivilSelectItem estadoCivilSelectItem) {
        this.estadoCivilSelectItem = estadoCivilSelectItem;
    }

    public Sexo[] getSexoList() {
        return sexoList;
    }

    public void setSexoList(Sexo[] sexoList) {
        this.sexoList = sexoList;
    }

    @SuppressWarnings("unchecked")
	public List<TipoEvento> getTipoEventoList() {
        if (tipoEventoList == null) {
            try {
                tipoEventoList = tipoEventoService.findTiposEventosComEventos(getEntity().getClienteCampanha().getCampanha());
            } catch (ServiceException ex) {
                error(ex);
            }
        }
        return tipoEventoList;
    }
    
    @SuppressWarnings("unchecked")
   	public List<Evento> getEventoList() {
    	if (tipoEventoSelecionado != null && tipoEventoSelecionado.getId() != null) {
	        try {
	            eventoList = eventoService.findEventosNaCampanhaByTipo(tipoEventoSelecionado, getEntity().getClienteCampanha().getCampanha());
	        } catch (ServiceException ex) {
	            error(ex);
	        }
    	}
	    return eventoList;
   }

    public void setTipoEventoList(List<TipoEvento> tipoEventoList) {
        this.tipoEventoList = tipoEventoList;
    }

    public TipoEvento getTipoEventoSelecionado() {
        return tipoEventoSelecionado;
    }

    public void setTipoEventoSelecionado(TipoEvento tipoEventoSelecionado) {
        this.tipoEventoSelecionado = tipoEventoSelecionado;
    }
    
    public void selectionTipoEventoListener(AjaxBehaviorEvent event) {
        try {
            UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
            List<TipoEvento> ddsTable = (List<TipoEvento>) dataTable.getValue();
            Integer index = (Integer) dataTable.getSelection().iterator().next();
            tipoEventoSelecionado = ddsTable.get(index);
            selectionEvento = null;
            getEntity().setEvento(null);
            atualizarFinaliza();
        } catch (Exception e) {
            error(e);
        }
    }
    
    public void selectionEventoListener(AjaxBehaviorEvent event) {
        try {
            UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
            List<Evento> ddsTable = (List<Evento>) dataTable.getValue();
            Integer index = (Integer) dataTable.getSelection().iterator().next();
            getEntity().setEvento(ddsTable.get(index));
            atualizarFinaliza();
        } catch (Exception e) {
            error(e);
        }
    }
    
    public UF[] getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(UF[] estadoList) {
		this.estadoList = estadoList;
	}

	public List<TelefoneCliente> getTelefones() {
        if (telefones == null) {
            telefones = new ArrayList<TelefoneCliente>();
            for (int t = 5; t > 0; t--) {
                telefones.add(new TelefoneCliente());
            }
        }
        return new ArrayList<TelefoneCliente>(telefones);
    }

    public void setTelefones(List<TelefoneCliente> telefones) {
        this.telefones = telefones;
    }

    public String getCpfInicio() {
        return cpfInicio;
    }

    public void setCpfInicio(String cpfInicio) {
        this.cpfInicio = cpfInicio;
    }

    public String getCpfMeio() {
        return cpfMeio;
    }

    public void setCpfMeio(String cpfMeio) {
        this.cpfMeio = cpfMeio;
    }

    public String getCpfFim() {
        return cpfFim;
    }

    public List<EstadoCivil> getEstadoCivilList() {
        return estadoCivilList;
    }

    public void setEstadoCivilList(List<EstadoCivil> estadoCivilList) {
        this.estadoCivilList = estadoCivilList;
    }

    public List<Plano> getPlanoList() {
        return planoList;
    }

    public void setPlanoList(List<Plano> planoList) {
        this.planoList = planoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public ClienteCampanha getClienteCampanha() {
        return clienteCampanha;
    }

    public void setClienteCampanha(ClienteCampanha clienteCampanha) {
        this.clienteCampanha = clienteCampanha;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public Plano getPlanoSelecionado() {
        return planoSelecionado;
    }

    public void setPlanoSelecionado(Plano planoSelecionado) {
        this.planoSelecionado = planoSelecionado;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setCpfFim(String cpfFim) {
        this.cpfFim = cpfFim;
    }

    public BigDecimal getValorPlanoSelecionado() {
        return valorPlanoSelecionado;
    }

    public void setValorPlanoSelecionado(BigDecimal valorPlanoSelecionado) {
        this.valorPlanoSelecionado = valorPlanoSelecionado;
    }

    public String getNovoTelefone() {
        return novoTelefone;
    }

    public void setNovoTelefone(String novoTelefone) {
        this.novoTelefone = novoTelefone;
    }

    public int getCurrentVencimentoPage() {
        return currentVencimentoPage;
    }

    public void setCurrentVencimentoPage(int currentVencimentoPage) {
        this.currentVencimentoPage = currentVencimentoPage;
    }

    public BeneficiarioBB getBeneficiarioBB() {
		return beneficiarioBB;
	}

	public void setBeneficiarioBB(BeneficiarioBB beneficiarioBB) {
		this.beneficiarioBB = beneficiarioBB;
	}

	public Rejeicao getRejeicao() {
		return rejeicao;
	}

	public void setRejeicao(Rejeicao rejeicao) {
		this.rejeicao = rejeicao;
	}

	public String getShowHiv() {
        return showHiv;
    }

    public void setShowHiv(String showHiv) {
        this.showHiv = showHiv;
    }

    public String getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(String tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public TelefoneCliente getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(TelefoneCliente telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public String getScriptProduto() {
        return scriptProduto;
    }

    public void setScriptProduto(String scriptProduto) {
        this.scriptProduto = scriptProduto;
    }

    public String getUsuarioAuditor() {
        return usuarioAuditor;
    }

    public void setUsuarioAuditor(String usuarioAuditor) {
        this.usuarioAuditor = usuarioAuditor;
    }

    public String getSenhaAuditor() {
        return senhaAuditor;
    }

    public void setSenhaAuditor(String senhaAuditor) {
        this.senhaAuditor = senhaAuditor;
    }


	public String isRejeicao() {
        if (rejeicao != null) {
            warn(String.format("Tratativa de rejeição: %s", rejeicao.getDescricao()));
        }
        return "";
    }

	public boolean isHabilitarAbaDependente() {
		return habilitarAbaDependente;
	}

	public void setHabilitarAbaDependente(boolean habilitarAbaDependente) {
		this.habilitarAbaDependente = habilitarAbaDependente;
	}

	public LyricallUrlParameters getLyricallUrlParameters() {
		return lyricallUrlParameters;
	}

	public void setLyricallUrlParameters(LyricallUrlParameters lyricallUrlParameters) {
		this.lyricallUrlParameters = lyricallUrlParameters;
	}

	public TipoPlanoEnum getTipoPlanoEnum() {
		return tipoPlanoEnum;
	}

	public void setTipoPlanoEnum(TipoPlanoEnum tipoPlanoEnum) {
		this.tipoPlanoEnum = tipoPlanoEnum;
	}

	public String getShowConfirma() {
		return showConfirma;
	}

	public void setShowConfirma(String showConfirma) {
		this.showConfirma = showConfirma;
	}

	public String getShowFinaliza() {
		return showFinaliza;
	}

	public void setShowFinaliza(String showFinaliza) {
		this.showFinaliza = showFinaliza;
	}
	
	public String getShowFinalizaVenda() {
		return showFinalizaVenda;
	}

	public void setShowFinalizaVenda(String showFinalizaVenda) {
		this.showFinalizaVenda = showFinalizaVenda;
	}

	public String getShowAbaDependente() {
		return showAbaDependente;
	}

	public void setShowAbaDependente(String showAbaDependente) {
		this.showAbaDependente = showAbaDependente;
	}
	
	public Collection<Object> getSelectionEvento() {
		return selectionEvento;
	}

	public void setSelectionEvento(Collection<Object> selectionEvento) {
		this.selectionEvento = selectionEvento;
	}

	public Collection<Object> getSelectionTipoEvento() {
		return selectionTipoEvento;
	}

	public void setSelectionTipoEvento(Collection<Object> selectionTipoEvento) {
		this.selectionTipoEvento = selectionTipoEvento;
	}

	public String getStyleEvento() {
		return styleEvento;
	}

	public void setStyleEvento(String styleEvento) {
		this.styleEvento = styleEvento;
	}

	/**
	 * Retorna a URL de chamada para recuperacao dos telefones do cliente via
	 * javascript. Essa é uma necessidade para contornar o problema de cross
	 * domain com AJAX.
	 * 
	 * @return
	 */
	public String getUrlTelefonesCliente() {

		try {
			String url = getQsParamCallURL()
					+ "/UDAgentPL/opt/http/forms/scripts/clkwacrossdomain.jsp?targetURL="
					+ URLEncoder.encode(
							"http://" + getExternalContext().getRequestServerName() + ":" + getExternalContext().getRequestServerPort()
									+ getExternalContext().getRequestContextPath() + "/getTelefonesCliente?idClienteCampanha="
									+ getQsParamIdClienteCampanha(), "UTF-8") + "&jsField=top.adminFrame.hdfPhoneList";

			return url;
		} catch (UnsupportedEncodingException ex) {
			error(ex);
		}
		return "";
	}

	public String getQsParamCallURL() {
		return qsParamCallURL;
	}

	public void setQsParamCallURL(String qsParamCallURL) {
		this.qsParamCallURL = qsParamCallURL;
	}
	
	public String getQsParamIdClienteCampanha() {
		return qsParamIdClienteCampanha;
	}

	public void setQsParamIdClienteCampanha(String qsParamIdClienteCampanha) {
		this.qsParamIdClienteCampanha = qsParamIdClienteCampanha;
	}

	public String getShowDadosEmpresa() {
		return showDadosEmpresa;
	}

	public void setShowDadosEmpresa(String showDadosEmpresa) {
		this.showDadosEmpresa = showDadosEmpresa;
	}

	public String getShowEsporte() {
		return showEsporte;
	}

	public void setShowEsporte(String showEsporte) {
		this.showEsporte = showEsporte;
	}
	
	public Boolean getRenderEsporte() {
		return renderEsporte;
	}

	public void setRenderEsporte(Boolean renderEsporte) {
		this.renderEsporte = renderEsporte;
	}
	
}
