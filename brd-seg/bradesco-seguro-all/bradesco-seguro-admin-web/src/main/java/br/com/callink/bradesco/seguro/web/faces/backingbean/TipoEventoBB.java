package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.ITipoEventoService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;

/**
 * 
 * Representa o Backing Bean do Tipo Evento
 * 
 * @author swb.thiagohenrique
 *
 */

@ManagedBean
@ViewScoped
public class TipoEventoBB extends GenericCrudBB<TipoEvento, ITipoEventoService<TipoEvento>> {

	private static final long serialVersionUID = -878776612337944890L;
	
	@SuppressWarnings("rawtypes")
	@EJB(beanName = "TipoEventoService")
	private ITipoEventoService tipoEventoService;
	
	private static enum SortColumnName {
		ID, NOME;
	}
	
	private SortOrderUtil sortOrder;
	
	@PostConstruct
	public void init() throws Exception {
		setEntity(new TipoEvento());
		
		this.sortOrder = new SortOrderUtil(SortColumnName.ID.name(),
				SortColumnName.NOME.name());
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new TipoEvento());
	}
	
	@Override
	public String editar(TipoEvento tipoEvento) {
		
		setModoAtualizacao(true);
		setEntity(tipoEvento);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void salvar() throws Exception {
		
		antesSalvar();
		ServiceResponse serviceResponse = getService().save(getEntity(),getUsuarioHost(),getUsuarioLogado());
		addMsgs(serviceResponse);
		setEntity(new TipoEvento());
		getDados().add((TipoEvento) serviceResponse.getData());
		setModoNovo(false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void atualizar() throws Exception {
		
		antesSalvar();
		ServiceResponse serviceResponse = getService().update(getEntity(),getUsuarioHost(),getUsuarioLogado());
		addMsgs(serviceResponse);
		setEntity(new TipoEvento());
		setModoAtualizacao(false);
	}
	
	@Override
	public void excluir(TipoEvento tipoEvento) throws Exception {
		
		try{
			tipoEvento.setFlagEnabled(Boolean.FALSE);
			getService().remover(tipoEvento);
		} catch (EJBTransactionRolledbackException e){
			//TODO: do catch in the right exception on the service; 
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        getService().removerLogicamente(tipoEvento);
		    }
		}
		getDados().remove(tipoEvento);
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		
		antesPesquisar();
		getEntity().setFlagRemoved(Boolean.FALSE);
		ServiceResponse serviceResponse = getService().findByExample(getEntity());
		addMsgs(serviceResponse);
		setDados((List<TipoEvento>) serviceResponse.getData());
		aposPesquisar();
	}
	
	@Override
	protected boolean isPesquisarTodos() {
		final TipoEvento e = getEntity();
		return StringUtils.isEmpty(e.getNomeTipoEvento());
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected ITipoEventoService getService() {
		return tipoEventoService;
	}
	
	public void sortBy(String columnName) {
		sortOrder.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrder.getSortOrder(columnName);
	}
}
