package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.ProdutoEvento;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.enums.TipoFinalizacao;
import br.com.callink.bradesco.seguro.service.IEventoService;
import br.com.callink.bradesco.seguro.service.IProdutoEventoService;
import br.com.callink.bradesco.seguro.service.IProdutoService;
import br.com.callink.bradesco.seguro.service.ITipoEventoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.selectitem.TipoEventoSelectItem;
import br.com.callink.bradesco.seguro.web.faces.webobjects.ProdutoDTO;

@ManagedBean
@ViewScoped
public class EventoBB extends GenericCrudBB<Evento, IEventoService<Evento>> {
	
	private static final long serialVersionUID = 8806132117733701960L;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private IEventoService eventoService;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private ITipoEventoService tipoEventoService;
	
	@EJB
	private IProdutoService<Produto> produtoService;
	
	@EJB
	private IProdutoEventoService produtoEventoService;
	
	private TipoEventoSelectItem tipoEventoSelectItem;
	
	private TipoFinalizacao[] tipoFinalizacaoItens;
	
	private TipoEvento tipoEvento;
	
	private int currentEventoPage = 1;
	
	private Boolean sucesso;

	private SortOrder nomeOrder = SortOrder.unsorted;
	
	private SortOrder tipoEventoOrder = SortOrder.unsorted;
	
	private SortOrder idPABXOrder = SortOrder.unsorted;
	
	private SortOrder codigoPABXOrder = SortOrder.unsorted;
	
	private SortOrder codigoClienteOrder = SortOrder.unsorted;
	
	private SortOrder idOrder = SortOrder.unsorted;
	
	private Produto produto;
	
	private List<Produto> produtos;
	
	private Date dataCadastroInicio;
	
	private Date dataCadastroFim;
	
	private Map<BigInteger, ProdutoDTO> produtosDTO;
	
	private List<ProdutoDTO> produtosDTOList;
	
	@PostConstruct
	public void init() throws Exception {
		tipoFinalizacaoItens = TipoFinalizacao.values();
		setEntity(new Evento());
	}

	@Override
	public void novoRegistro() throws Exception {
		
		setEntity(new Evento());
		setSucesso(Boolean.FALSE);
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		
		getEntity().setTipoEvento(getTipoEvento());
		ServiceResponse serviceResponse = getService().buscarPorExemplo(getEntity(), this.dataCadastroInicio, this.dataCadastroFim);
		addMsgs(serviceResponse);
		setDados((List<Evento>) serviceResponse.getData());
		aposPesquisar();
	}
	
	public void chamarPopup(Evento evento) throws Exception {
		
		if (evento.getId() == null) {
			novoRegistro();
		}
		setEntity(evento);
		setSucesso(Boolean.FALSE);
	}

	@Override
	public void excluir(Evento evento) throws Exception {
		
		ServiceResponse response = new ServiceResponse();
		
		try{
			getService().delete(evento);
		} catch (EJBTransactionRolledbackException e){
			//TODO: TO catch the right exception on the service; 
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        getService().removerLogicamente(evento, getUsuarioHost(), getUsuarioLogado());
		    }
		}
		getDados().remove(evento);
		addMsgs(response);
		novoRegistro();
		
	}

	public void salvar() throws Exception {
		
		boolean isNovoRegistro = Boolean.FALSE;
		if (getEntity().getId() == null || getEntity().getId().compareTo(BigInteger.ZERO) == 0) {
			isNovoRegistro = Boolean.TRUE;
		}
		ServiceResponse serviceResponse = getService().salvarOuAtualizar(getEntity(), getUsuarioHost(), getUsuarioLogado());
		addMsgs(serviceResponse);
		if (isNovoRegistro) {
			getDados().add((Evento) serviceResponse.getData());
		}
		setEntity(new Evento());
		setSucesso(Boolean.TRUE);
	}

	public TipoEventoSelectItem getTipoEventoSelectItem() throws ServiceException {
		
		if (tipoEventoSelectItem == null) {
			tipoEventoSelectItem = new TipoEventoSelectItem();
		}
		fillTipoEventoSelectItem();
		return tipoEventoSelectItem;
	}

	@SuppressWarnings("unchecked")
	private void fillTipoEventoSelectItem() throws ServiceException {
		
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setFlagEnabled(Boolean.TRUE);
		tipoEvento.setFlagRemoved(Boolean.FALSE);
		ServiceResponse serviceResponse = tipoEventoService.findByExampleOrderByNome(tipoEvento);
		tipoEventoSelectItem.setListAndOrder((List<TipoEvento>)serviceResponse.getData());
	}

	public void setTipoEventoSelectItem(
			TipoEventoSelectItem tipoEventoSelectItem) {
		this.tipoEventoSelectItem = tipoEventoSelectItem;
	}

	public int getCurrentEventoPage() {
		return currentEventoPage;
	}

	public void setCurrentEventoPage(int currentEventoPage) {
		this.currentEventoPage = currentEventoPage;
	}

	public void sortByNome() {

		tipoEventoOrder = SortOrder.unsorted;
		idPABXOrder = SortOrder.unsorted;
		codigoPABXOrder = SortOrder.unsorted;
		codigoClienteOrder = SortOrder.unsorted;

		setNomeOrder(nomeOrder.equals(SortOrder.ascending) ? SortOrder.descending
				: SortOrder.ascending);
	}

	public void sortByTipoEvento() {
		
		nomeOrder = SortOrder.unsorted;
		idPABXOrder = SortOrder.unsorted;
		codigoPABXOrder = SortOrder.unsorted;
		codigoClienteOrder = SortOrder.unsorted;

		setTipoEventoOrder(tipoEventoOrder.equals(SortOrder.ascending) ? SortOrder.descending
				: SortOrder.ascending);
	}

	public void sortByIdPABX() {
		
		nomeOrder = SortOrder.unsorted;
		tipoEventoOrder = SortOrder.unsorted;
		codigoPABXOrder = SortOrder.unsorted;
		codigoClienteOrder = SortOrder.unsorted;

		setIdPABXOrder(idPABXOrder.equals(SortOrder.ascending) ? SortOrder.descending
				: SortOrder.ascending);
	}

	public void sortByCodigoPABX() {
	
		nomeOrder = SortOrder.unsorted;
		idPABXOrder = SortOrder.unsorted;
		tipoEventoOrder = SortOrder.unsorted;
		codigoClienteOrder = SortOrder.unsorted;

		setCodigoPABXOrder(codigoPABXOrder.equals(SortOrder.ascending) ? SortOrder.descending
				: SortOrder.ascending);
	}

	public void sortByCodigoCliente() {
	
		nomeOrder = SortOrder.unsorted;
		idPABXOrder = SortOrder.unsorted;
		tipoEventoOrder = SortOrder.unsorted;
		codigoPABXOrder = SortOrder.unsorted;

		setCodigoClienteOrder(codigoClienteOrder.equals(SortOrder.ascending) ? SortOrder.descending
				: SortOrder.ascending);
	}
	
	public void associarProduto(Evento evento){
		
		setEntity(evento);
		this.produto = new Produto();
		this.produtosDTOList = new ArrayList<ProdutoDTO>();
		this.produtosDTO = new HashMap<BigInteger, ProdutoDTO>();
		carregarAssociacoes();
	}
	
	public void limparDadosAssociacao(){
		
		if (this.produtos != null) { this.produtos.clear(); }
		if (this.produtosDTO != null) { this.produtosDTO.clear(); }
		if (this.produtosDTOList != null) { this.produtosDTOList.clear(); }
		setEntity(new Evento());
	}
	
	public void associarProdutoEvento(){
		
		ServiceResponse serviceResponse = null;
		try {

			if (!CollectionUtils.isEmpty(this.produtosDTOList)) {
				
				List<ProdutoEvento> produtoEventoList = new ArrayList<ProdutoEvento>();
				for(ProdutoDTO produtoDTO: this.produtosDTOList){
					if (produtoDTO.getSelecionado()){
						produtoEventoList.add(new ProdutoEvento(produtoDTO.getProduto(), getEntity()));
					}
				}
				getEntity().setProdutoEventoList(produtoEventoList);
				serviceResponse = produtoEventoService.associarEventoProduto(getEntity(), getUsuarioHost(), getUsuarioLogado());
				addMsgs(serviceResponse);
			}
		} catch (ServiceException e) {
			addMsgs(serviceResponse);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void carregarAssociacoes(){
		
		ServiceResponse serviceResponse = null;
		List<ProdutoEvento> produtoEventoList = new ArrayList<ProdutoEvento>();
		if (this.produtosDTO != null) { this.produtosDTO.clear(); }
		if (this.produtos != null) { this.produtos.clear(); }
		if (this.produtosDTOList != null) { this.produtosDTOList.clear(); }
		
		try {
			serviceResponse = produtoService.findByExample(this.produto);
			this.produtos = (List<Produto>) serviceResponse.getData();
			serviceResponse = produtoEventoService.buscarPorEvento(getEntity());;
			produtoEventoList.addAll((List<ProdutoEvento>) serviceResponse.getData());
			
			if (!CollectionUtils.isEmpty(this.produtos)){
				for (Produto produto : this.produtos) {
					this.produtosDTO.put(produto.getId(), new ProdutoDTO(produto));
				}
			}
			if (!CollectionUtils.isEmpty(produtoEventoList)){
				for (ProdutoEvento produtoEvento : produtoEventoList) {
					this.produtosDTO.put(produtoEvento.getProduto().getId(), 
							new ProdutoDTO(produtoEvento.getProduto(), Boolean.TRUE));
				}
			}
		} catch (ServiceException e) {
			addMsgs(serviceResponse);
		}
		this.produtosDTOList.addAll(this.produtosDTO.values());
	}
	
	public void associarTodosProdutos() {
		
		if (!CollectionUtils.isEmpty(this.produtosDTOList)){
			for (ProdutoDTO produtoDTO : this.produtosDTOList) {
				produtoDTO.setSelecionado(Boolean.TRUE);
			}
			associarProdutoEvento();
		}
	}
	
	public void desassociarTodosProdutos() {
		
		if (!CollectionUtils.isEmpty(this.produtosDTOList)){
			for (ProdutoDTO produtoDTO : this.produtosDTOList) {
				produtoDTO.setSelecionado(Boolean.FALSE);
			}
			associarProdutoEvento();
		}
	}
	
	public List<Produto> filtrarProdutoPorNome(String nome) {
		
		List<Produto> produtosFiltrados = new ArrayList<>();
		if(nome != null && !nome.isEmpty()){
			for (Produto produto : this.produtos) {
				if(produto.getNome().toLowerCase().startsWith(nome.toLowerCase())){
					produtosFiltrados.add(produto);
				}
			}
		}
		return produtosFiltrados;
	}
	
	public SortOrder getNomeOrder() {
		return nomeOrder;
	}

	public void setNomeOrder(SortOrder nomeOrder) {
		this.nomeOrder = nomeOrder;
	}

	public SortOrder getTipoEventoOrder() {
		return tipoEventoOrder;
	}

	public void setTipoEventoOrder(SortOrder tipoEventoOrder) {
		this.tipoEventoOrder = tipoEventoOrder;
	}

	public SortOrder getIdPABXOrder() {
		return idPABXOrder;
	}

	public void setIdPABXOrder(SortOrder idPABXOrder) {
		this.idPABXOrder = idPABXOrder;
	}

	public SortOrder getCodigoPABXOrder() {
		return codigoPABXOrder;
	}

	public void setCodigoPABXOrder(SortOrder codigoPABXOrder) {
		this.codigoPABXOrder = codigoPABXOrder;
	}

	public SortOrder getCodigoClienteOrder() {
		return codigoClienteOrder;
	}

	public void setCodigoClienteOrder(SortOrder codigoClienteOrder) {
		this.codigoClienteOrder = codigoClienteOrder;
	}

	public SortOrder getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(SortOrder idOrder) {
		this.idOrder = idOrder;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IEventoService<Evento> getService() {
		return eventoService;
	}

	public TipoFinalizacao[] getTipoFinalizacaoItens() {
		return tipoFinalizacaoItens;
	}

	public void setTipoFinalizacaoItens(TipoFinalizacao[] tipoFinalizacaoItens) {
		this.tipoFinalizacaoItens = tipoFinalizacaoItens;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Date getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(Date dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public Date getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(Date dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}

	public Map<BigInteger, ProdutoDTO> getProdutosDTO() {
		return produtosDTO;
	}

	public void setProdutosDTO(Map<BigInteger, ProdutoDTO> produtosDTO) {
		this.produtosDTO = produtosDTO;
	}

	public List<ProdutoDTO> getProdutosDTOList() {
		return produtosDTOList;
	}

	public void setProdutosDTOList(List<ProdutoDTO> produtosDTOList) {
		this.produtosDTOList = produtosDTOList;
	}

}
