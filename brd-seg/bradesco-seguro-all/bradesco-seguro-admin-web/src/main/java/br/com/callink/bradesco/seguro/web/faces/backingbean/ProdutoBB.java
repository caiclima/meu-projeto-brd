package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;
import br.com.callink.bradesco.seguro.service.IProdutoService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;


@ManagedBean
@ViewScoped
public class ProdutoBB extends GenericCrudBB<Produto, IProdutoService<Produto>> {

	private static final long serialVersionUID = 8928402435287747063L;

	
	@SuppressWarnings("rawtypes")
	@EJB
	private IProdutoService produtoService;
	
	private Boolean flagEnabled;
	private Boolean sucesso;
	
	private static enum SortColumnName {
		NOME, CODIGO, DESCRICAO,
		DATA_INICIO, DATA_FINAL;
	}
	
	private SortOrderUtil sortOrder;
	
	@PostConstruct
	public void init() throws Exception {
		setEntity(new Produto());
		getEntity().setFlagEnabled(Boolean.TRUE);
		
		this.sortOrder = new SortOrderUtil(SortColumnName.NOME.name(),
				SortColumnName.CODIGO.name(),
				SortColumnName.DESCRICAO.name(),
				SortColumnName.DATA_INICIO.name(),
				SortColumnName.DATA_FINAL.name());
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new Produto());
		getEntity().setFlagEnabled(Boolean.TRUE);
		setFlagEnabled(Boolean.TRUE);
		setSucesso(Boolean.FALSE);
	}
	
	@Override
	protected void antesPesquisar() throws Exception {
		getEntity().setFlagEnabled(getFlagEnabled());
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		antesPesquisar();
		
		ServiceResponse serviceResponse = getService().findByExample(getEntity());
		
		addMsgs(serviceResponse);
		setDados((List<Produto>) serviceResponse.getData());
		aposPesquisar();
	}
	
	/**
	 * edita a produto recebido como parametro
	 */
	public String chamarPopup(Produto produto) throws Exception {
		if (produto.getId() == null) {
			novoRegistro();
		}
		setEntity(produto);
		setSucesso(Boolean.FALSE);
		
		return null;
	}

	/**
	 * excluir a produto recebido como parametro
	 */
	@Override
	public void excluir(Produto produto) throws Exception {
		
		super.excluir(produto);
		
	}
	
//	private void validarFormulario() throws ValidationException {
//		if (getEntity() == null) {
//			throw new ValidationException("Não foi possível atualizar");
//		}
//		if (StringUtils.isEmpty(getEntity().getDescricao())) {
//			throw new ValidationException("O campo descricao é obrigatório");
//		}
//		if (StringUtils.isEmpty(getEntity().getCodigo())) {
//			throw new ValidationException("O campo código é obrigatório");
//		}
//		if (getEntity().getDataInicioVigencia() == null) {
//			throw new ValidationException("O campo Data Início Vigência é obrigatório");
//		}
//		/*
//		 * DataFimVigencia é opcional
//		 */
//	}
	
//	private void validarFormularioAtualizacao() throws ValidationException {
//		validarFormulario();
//		if (getEntity().getId() == null) {
//			throw new ValidationException("Não foi possível atualizar");
//		}
//	}

	/**
	 * salva o produto atual
	 */
	public void salvar() throws Exception {
		
		//validarFormulario();
		ServiceResponse serviceResponse = getService().save(getEntity(), getUsuarioHost(), getUsuarioLogado());
		finalizarSaveUpdate(serviceResponse);
	}
	
	/**
	 * atualiza o produto atual
	 */
	public void atualizar() throws Exception {
		
		//validarFormularioAtualizacao();
		ServiceResponse serviceResponse = getService().update(getEntity(), getUsuarioHost(), getUsuarioLogado());
		finalizarSaveUpdate(serviceResponse);
	}
	
	private void finalizarSaveUpdate(ServiceResponse serviceResponse){
		Produto produto = null;
		if (serviceResponse.getData() != null) {
			produto = (Produto) serviceResponse.getData();
			
			if (getDados().contains(produto)) {
				getDados().set(getDados().indexOf(produto), produto);
			} else {
				getDados().add(produto);
			}
			
			setEntity(new Produto());
			setFlagEnabled(null);
		}
		
		addMsgs(serviceResponse);
		
		setSucesso(Boolean.TRUE);
	}

	public Boolean getFlagEnabled() {
		return flagEnabled;
	}

	public void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IProdutoService<Produto> getService() {
		return produtoService;
	}

	public ValidacaoVendaProduto[] getTipoProdutoSelectItem() {
		return ValidacaoVendaProduto.values();
	}

	public void sortBy(String columnName) {
		sortOrder.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrder.getSortOrder(columnName);
	}
}