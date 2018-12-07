package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.entity.Profissao;
import br.com.callink.bradesco.seguro.service.IProfissaoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;

@SuppressWarnings("rawtypes")
@ManagedBean
@ViewScoped
public class ProfissaoBB extends GenericCrudBB<Profissao, IProfissaoService> {

	private static final long serialVersionUID = 3493458745664646879L;

	@EJB
	private IProfissaoService<Profissao> profissaoService;
	
	private boolean sucesso;
	
	private int currentPage = 1;
	
	private static enum SortColumnName {
		NOME, CODIGO, 
		DESCRICAO, CODIGO_EXTERNO;
	}
	
	private SortOrderUtil sortOrder;
	
	@PostConstruct
	public void init() {
		setEntity(new Profissao());
		this.sortOrder = new SortOrderUtil(SortColumnName.NOME.name(),
				SortColumnName.CODIGO.name(),
				SortColumnName.DESCRICAO.name(),
				SortColumnName.CODIGO_EXTERNO.name());
	}
	
	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	@Override
	protected IProfissaoService getService() {
		return this.profissaoService;
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new Profissao());
		setModoNovo(Boolean.TRUE);
	}
	
	@Override
	protected void antesPesquisar() throws Exception {
		getEntity().setFlagRemoved(Boolean.FALSE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void pesquisar() throws Exception {
		antesPesquisar();
		
		ServiceResponse serviceResponse = getService().findByExample(getEntity());
		
		addMsgs(serviceResponse);
		setDados((List<Profissao>) serviceResponse.getData());
		
		aposPesquisar();
	}
	
	@Override
	public void salvar() throws ServiceException {
		
		ServiceResponse response = getService().salvar(getEntity(), getUsuarioHost(), getUsuarioLogado());
		addMsgs(response);
		setEntity((Profissao) response.getData());
		getDados().add((Profissao) response.getData());
		this.sucesso = Boolean.TRUE;
		init();
	}
	
	@Override
	public void atualizar() throws ServiceException {

		ServiceResponse response = getService().atualizar(getEntity(), getUsuarioHost(), getUsuarioLogado());
		addMsgs(response);
		setEntity((Profissao) response.getData());
		this.sucesso = Boolean.TRUE;
		init();
	}
	
	@Override
	public void excluir(Profissao profissao) throws Exception {

		ServiceResponse response = null;
		try{
			response = getService().remover(profissao, getUsuarioHost(), getUsuarioLogado());
		} catch (EJBTransactionRolledbackException e){
			//TODO: TO catch the right exception on the service; 
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        response = getService().removerLogicamente(profissao, getUsuarioHost(), getUsuarioLogado());
		    }
		}
		remove(profissao);
		addMsgs(response);
		novoRegistro();
	}
	
	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void sortBy(String columnName) {
		sortOrder.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrder.getSortOrder(columnName);
	}
}
