package br.com.callink.bradesco.seguro.web.backinbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.callink.bradesco.seguro.entity.ILog;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;
import br.com.callink.bradesco.seguro.service.IGenericCrudService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * Managed-Bean genérico. para operações CRUD. Aplica Template Method Design
 * pattern para as operações de CRUD.
 * 
 * @author michael
 * @param T - tipo da entidade
 * @param S - tipo do Servico
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class GenericCrudBB<T extends IdentifiableEntity, S extends IGenericCrudService> extends GenericBB {
	
	private static final long serialVersionUID = 1L;
	private List<T> dados = new ArrayList<T>();
	private T entity;
	private boolean modoAtualizacao;
	private boolean modoNovo;

	protected abstract boolean isPesquisarTodos();
	protected abstract S getService();
	public abstract void novoRegistro() throws Exception;

	public final void novo() throws Exception{
		setModoAtualizacao(false);
		setModoNovo(true);
		novoRegistro();
	}
	
	public void cancelar() throws Exception{
		setModoAtualizacao(false);
		setModoNovo(false);
		novoRegistro();
		setDados(null);
	}
	
	/**
	 * Template method para a ação 'Salvar'
	 * 
	 * @throws Exception
	 */
	public void salvar() throws Exception {
		antesSalvar();
		ServiceResponse response = getService().save(getEntity());
		addMsgs(response);
		setEntity((T) response.getData());
		getDados().add((T) response.getData());
		aposSalvar();
		setModoNovo(false);
	}

	protected void antesSalvar(){}
	protected void aposSalvar() throws Exception{}

	/**
	 * Template method para a ação 'Buscar' todos os registros.
	 * 
	 * @param todos
	 *            - indica se é para buscar todos os registros ou registros por
	 *            exemplo, baseado nos dados do formulario.
	 * 
	 * @throws Exception
	 */
	public void pesquisar() throws Exception {
		setModoNovo(false);
		setModoAtualizacao(false);
		antesPesquisar();

		ServiceResponse response = null;

		if (isPesquisarTodos()) {
			response = getService().findAll();
		} else {
			response = getService().findByExample(getEntity());
		}

		addMsgs(response);
		setDados((List<T>) response.getData());
		aposPesquisar();
	}

	protected void antesPesquisar() throws Exception{}
	protected void aposPesquisar(){}

	/**
	 * Template method para a ação 'Excluir'
	 * 
	 * @throws Exception
	 */
	public void excluir(T entity) throws Exception {
		setModoNovo(false);
		setModoAtualizacao(false);
		antesExcluir();
		ServiceResponse response = getService().delete(entity);
		aposExcluir();
		remove(entity);
		addMsgs(response);
		novoRegistro();
	}
	
	protected void remove(T entity){
		getDados().remove(entity);
	}
	
	protected ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
	
	protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected Map getApplicationMap() {
        return getExternalContext().getApplicationMap();
    }

    protected Map getRequestMap() {
        return getExternalContext().getRequestMap();
    }

    protected Map getSessionMap() {
        return getExternalContext().getSessionMap();
    }

    protected String getUsuarioLogado() {
        return (String) getSessionMap().get("userLogin");
    }

    protected String getUsuarioHost() {
        return (String) getSessionMap().get("userHost");
    }
    
    protected void setLogPojo(T pojo) {
        if (pojo instanceof ILog) {
            ILog log = (ILog) pojo;
            log.setLogHost(getUsuarioHost());
            log.setLogObs(pojo.toString());
            log.setLogSystem("BRADESCO-SEGURO-ADMIN-WEB");
            log.setLogUid(getUsuarioLogado());
            log.setLogDate(new Date());
        }
    }
    
    protected String getApplicationHost() {
        return super.getUsuarioHost();
    }

	protected void antesExcluir(){}
	protected void aposExcluir(){}

	/**
	 * Template method para a ação 'Atualizar'
	 * 
	 * @throws Exception
	 */
	public void atualizar() throws Exception {
		antesSalvar();
		ServiceResponse response = null;
		
		try{
			response = getService().update(getEntity());
			addMsgs(response);
			setEntity((T) response.getData());
			setModoAtualizacao(false);
		}finally{
			aposSalvar();
		}
	}
	
	public String editar(T entity){
		setModoAtualizacao(true);
		setModoNovo(false);
		setEntity(entity);
		return null;
	}

	// getters . setters
	public List<T> getDados() {
		return dados;
	}

	public void setDados(List<T> dados) {
		this.dados = dados;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public boolean isModoAtualizacao() {
		return modoAtualizacao;
	}
	public void setModoAtualizacao(boolean modoAtualizacao) {
		this.modoAtualizacao = modoAtualizacao;
	}
	public boolean isModoNovo() {
		return modoNovo;
	}
	public void setModoNovo(boolean modoNovo) {
		this.modoNovo = modoNovo;
	}
}
