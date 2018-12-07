package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.SortOrder;
import org.springframework.util.CollectionUtils;

import br.com.callink.bradesco.seguro.entity.Cobertura;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.TipoPlano;
import br.com.callink.bradesco.seguro.enums.TipoPlanoEnum;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;
import br.com.callink.bradesco.seguro.service.IGrauParentescoService;
import br.com.callink.bradesco.seguro.service.IPlanoService;
import br.com.callink.bradesco.seguro.service.IProdutoService;
import br.com.callink.bradesco.seguro.service.ITipoPlanoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.selectitem.GrauParentescoSelectItem;
import br.com.callink.bradesco.seguro.web.faces.selectitem.ProdutoSelectItem;
import br.com.callink.bradesco.seguro.web.faces.selectitem.TipoPlanoSelectItem;
import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;

@ManagedBean
@ViewScoped
public class PlanoBB extends GenericCrudBB<Plano, IPlanoService<Plano>> {
	
	private static final long serialVersionUID = 8928402435287747063L;

	
	@SuppressWarnings("rawtypes")
	@EJB
	private IPlanoService planoService;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private ITipoPlanoService tipoPlanoService;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private IProdutoService produtoService;
	
	@EJB
	private IGrauParentescoService<GrauParentesco> grauParentescoService;
	

	private Boolean sucesso;
	private Boolean coberturaSucesso;
	private Produto produto;
	private TipoPlano tipoPlano;
	private TipoPlanoSelectItem tipoPlanoSelectItem;
	private ProdutoSelectItem produtoSelectItem;
	private Plano pojoPesquisa;
	private Cobertura cobertura;
	private GrauParentescoSelectItem grauParentescoSelectItem;
	private String showCobertura = "hidden";
	
	private static enum SortColumnName {
		NOME, CODIGO, PRODUTO, TIPO, CAPITAL,
		DATA_INICIO, DATA_FINAL;
	}
	
	private SortOrderUtil sortOrder;
	

	@PostConstruct
	public void init() throws Exception {
		setEntity(new Plano());
		setCobertura(new Cobertura());
		this.sortOrder = new SortOrderUtil(SortColumnName.NOME.name(),
				SortColumnName.CODIGO.name(),
				SortColumnName.PRODUTO.name(),
				SortColumnName.TIPO.name(),
				SortColumnName.CAPITAL.name(),
				SortColumnName.DATA_INICIO.name(),
				SortColumnName.DATA_FINAL.name());
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new Plano());
		setProduto(null);
		setSucesso(Boolean.FALSE);
	}
	
	@Override
	protected void antesPesquisar() throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		antesPesquisar();
		
		ServiceResponse serviceResponse = getService().buscarPorExemplo(getPojoPesquisa());
		
		addMsgs(serviceResponse);
		setDados((List<Plano>) serviceResponse.getData());
		aposPesquisar();
	}
	
	/**
	 * edita a plano recebido como parametro
	 */
	public String chamarPopup(Plano plano) throws Exception {
		if (plano.getId() == null) {
			novoRegistro();
		}
		setEntity(plano);
		setProduto(plano.getProduto());
		setSucesso(Boolean.FALSE);
		
		return null;
	}

	/**
	 * excluir a plano recebido como parametro
	 * @throws Exception 
	 */
	@Override
	public void excluir(Plano plano) throws Exception {
		
		ServiceResponse serviceResponse;
		try {
			serviceResponse = planoService.remover(plano);
			remove(plano);
			addMsgs(serviceResponse);
		} catch (EJBTransactionRolledbackException e){
			super.excluir(plano);
		}
	}
	
	public void excluirCobertura(Integer index) {
		getEntity().getCoberturaList().remove(index.intValue());
	}
	
	public void criarCobertura() throws ServiceException{
		setCobertura(new Cobertura());
	}
	
	public void salvarCobertura(){
		
		if (CollectionUtils.isEmpty(getEntity().getCoberturaList())){
			getEntity().setCoberturaList(new ArrayList<Cobertura>());
		}
		boolean isCobeturaSalvaSucesso= Boolean.FALSE;
		if (getCobertura().getGrauParentesco() != null
				&& getCobertura().getValorDemaisAcidentes() != null
				&& getCobertura().getValorDIHMotivoAcidentes() != null
				&& getCobertura().getValorSorteio() != null
				&& getCobertura().getValorTransporteColetivo() != null
				&& getCobertura().getValorVeiculoParticularTaxisPedestres() != null) {
			getEntity().getCoberturaList().add(getCobertura());
			isCobeturaSalvaSucesso = Boolean.TRUE;
			setCobertura(new Cobertura());
		}
		if(!isCobeturaSalvaSucesso){
			JSFUtils.addErrorMessage("Todos os campos são obrigatórios.");
		}
		setCoberturaSucesso(isCobeturaSalvaSucesso);
	}
	
	/**
	 * salva o plano atual
	 */
	@SuppressWarnings("unchecked")
	public void salvar() throws ValidationException {
		
		try {
			
			//validarFormulario();
			if(getEntity() != null && getEntity().getProduto() != null && getEntity().getProduto().getCodigoValidacaoProduto() == null) {
				getEntity().setProduto(((List<Produto>) produtoService.findByExample(getEntity().getProduto()).getData()).get(0));
			}
			ServiceResponse serviceResponse = getService().salvar(getEntity(), getUsuarioHost(), getUsuarioLogado());
			finalizarSaveUpdate(serviceResponse);
		} catch (ServiceException ex) {
			throw new ValidationException(ex.getLocalizedMessage());
		}
		
	}
	
	public void atualizarCobertura() {
		if (getEntity().getTipoPlano() != null && getEntity().getTipoPlano().getId() != null) {
			setShowCobertura("none");
		} else {
			setShowCobertura("hidden");
		}
	}
	
	private void finalizarSaveUpdate(ServiceResponse serviceResponse){
		Plano plano = null;
		if (serviceResponse.getData() != null) {
			plano = (Plano) serviceResponse.getData();
			
			if (getDados().contains(plano)) {
				getDados().set(getDados().indexOf(plano), plano);
			} else {
				getDados().add(plano);
			}
			
			setEntity(new Plano());
		}
		
		addMsgs(serviceResponse);
		
		setSucesso(Boolean.TRUE);
		showCobertura = "hidden";
	}
	
	@SuppressWarnings("unchecked")
	public void changeProduto() throws Exception {
		try {
			showCobertura = "hidden";
			if(getEntity().getProduto() != null) {
				setProduto(((List<Produto>) produtoService.findByExample(getEntity().getProduto()).getData()).get(0));
				getEntity().setProduto(getProduto());
			} else {
				setProduto(null);
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		return;
	}
	
	@SuppressWarnings("unchecked")
	public void changeProdutoPesquisa() throws Exception {
		try {
			if(getPojoPesquisa().getProduto() != null) {
				setProduto(((List<Produto>) produtoService.findByExample(getPojoPesquisa().getProduto()).getData()).get(0));
				getPojoPesquisa().setProduto(getProduto());
			} else {
				setProduto(null);
			}
		} catch (ServiceException e) {
			throw new Exception(e);
		}
		return;
	}
	
	public  void carregarDetalhesPlano(Plano plano){
		setEntity(plano);
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	public Boolean getCoberturaSucesso() {
		return coberturaSucesso;
	}

	public void setCoberturaSucesso(Boolean coberturaSucesso) {
		this.coberturaSucesso = coberturaSucesso;
	}

	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IPlanoService<Plano> getService() {
		return planoService;
	}	
	
	public ProdutoSelectItem getProdutoSelectItem() throws ServiceException {
		if (produtoSelectItem == null) {
			produtoSelectItem = new ProdutoSelectItem();
		}
		fillProdutoSelectItem();

		return produtoSelectItem;
	}
	
	@SuppressWarnings("unchecked")
	private void fillProdutoSelectItem() throws ServiceException {
		Produto produto = new Produto();
		produto.setFlagEnabled(Boolean.TRUE);
		ServiceResponse serviceResponse = produtoService.findByExampleOrderByNome(produto);
		produtoSelectItem.setListAndOrder((List<Produto>)serviceResponse.getData());
		
	}

	public void setProdutoSelectItem(ProdutoSelectItem produtoSelectItem) {
		this.produtoSelectItem = produtoSelectItem;
	}
	
	public TipoPlanoSelectItem getTipoPlanoSelectItem() throws ServiceException {
		if (tipoPlanoSelectItem == null) {
			tipoPlanoSelectItem = new TipoPlanoSelectItem();
		}
		fillTipoPlanoSelectItem();

		return tipoPlanoSelectItem;
	}

	@SuppressWarnings("unchecked")
	private void fillTipoPlanoSelectItem() throws ServiceException {
		TipoPlano tipoPlano = new TipoPlano();
		tipoPlano.setFlagEnabled(Boolean.TRUE);
		ServiceResponse serviceResponse = tipoPlanoService.findByExampleOrderByNome(tipoPlano);
		tipoPlanoSelectItem.setListAndOrder((List<TipoPlano>)serviceResponse.getData());
		
	}
	
	@SuppressWarnings("unchecked")
	private void fillGrauParentescoSelectItem() throws ServiceException {
		ServiceResponse serviceResponse = null;
		List<GrauParentesco> grauParentescoList = null;
		GrauParentesco grauParentesco = null;
		
		if (getEntity() != null && getEntity().getTipoPlano() != null && getEntity().getTipoPlano().getId() != null 
				&& getEntity().getTipoPlano().getNomeTipoPlano().equalsIgnoreCase(TipoPlanoEnum.INDIVIDUAL.getValor())) {
			grauParentesco = grauParentescoService.findById(GrauParentesco.TITULAR.getId());
			grauParentescoList = new ArrayList<GrauParentesco>();
			grauParentescoList.add(grauParentesco);
		} else {
			serviceResponse = grauParentescoService.findAll();
			grauParentescoList = (List<GrauParentesco>) serviceResponse.getData();
		}
		
		grauParentescoSelectItem.setListAndOrder(grauParentescoList);
	}

	public void setTipoPlanoSelectItem(TipoPlanoSelectItem tipoPlanoSelectItem) {
		this.tipoPlanoSelectItem = tipoPlanoSelectItem;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Boolean getValidacao1() {
		if(getProduto() != null && getProduto().getCodigoValidacaoProduto() != null) {
			return getProduto().getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_1.getCodigo());
		} else {
			return Boolean.FALSE;
		}
	}
	
	public Boolean getValidacao2() {
		if(getProduto() != null && getProduto().getCodigoValidacaoProduto() != null) {
			return getProduto().getCodigoValidacaoProduto().equals(ValidacaoVendaProduto.VALIDACAO_2.getCodigo());
		} else {
			return Boolean.FALSE;
		}
	}

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public Plano getPojoPesquisa() {
		if(pojoPesquisa == null) {
			pojoPesquisa = new Plano();
		}
		return pojoPesquisa;
	}

	public void setPojoPesquisa(Plano pojoPesquisa) {
		this.pojoPesquisa = pojoPesquisa;
	}
	
	public Cobertura getCobertura() {
		return cobertura;
	}

	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;
	}

	public GrauParentescoSelectItem getGrauParentescoSelectItem() throws ServiceException {
		if (grauParentescoSelectItem == null) {
			grauParentescoSelectItem = new GrauParentescoSelectItem();
		}
		fillGrauParentescoSelectItem();

		return grauParentescoSelectItem;
	}

	public void setGrauParentescoSelectItem(GrauParentescoSelectItem grauParentescoSelectItem) {
		this.grauParentescoSelectItem = grauParentescoSelectItem;
	}

	public void sortBy(String columnName) {
		sortOrder.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrder.getSortOrder(columnName);
	}

	public String getShowCobertura() {
		return showCobertura;
	}

	public void setShowCobertura(String showCobertura) {
		this.showCobertura = showCobertura;
	}
	
}
