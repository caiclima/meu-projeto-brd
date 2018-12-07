package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.UsuarioAdminParametroSistema;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IUsuarioAdminParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

/**
 * 
 * @author michael
 *
 */
@ManagedBean
@ViewScoped
public class ParametroSistemaBB extends GenericCrudBB<ParametroSistema, IParametroSistemaService<ParametroSistema>> {
	
	private static final long serialVersionUID = 1101303561479779002L;

	@EJB
	private IParametroSistemaService<ParametroSistema> paramentroSistemaService;
	
	@EJB
	private IUsuarioAdminParametroSistemaService<UsuarioAdminParametroSistema> usuarioAdminParamentroSistemaService;
	
	private boolean isUsuarioAdminParametroSistema = Boolean.FALSE;

	@PostConstruct
	public void init() throws Exception {
		setEntity(new ParametroSistema());
		isUsuarioAdmin();
	}

	private void isUsuarioAdmin() throws ServiceException {
		
		ServiceResponse serviceResponse = this.usuarioAdminParamentroSistemaService.buscarUsuarioAdminPorLogin(getUsuarioLogado());
		if (serviceResponse.getData() != null){
			isUsuarioAdminParametroSistema = Boolean.TRUE;
		}
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new ParametroSistema());
	}
	
	@Override
	public void salvar() throws Exception {

		antesSalvar();
		ServiceResponse response = getService().salvar(getEntity(), getUsuarioHost(), getUsuarioLogado());
		addMsgs(response);
		setEntity((ParametroSistema) response.getData());
		getDados().add((ParametroSistema) response.getData());
		aposSalvar();
		setModoNovo(false);
	}
	
	@Override
	protected void antesSalvar() {
		
		if (getEntity() != null && getEntity().getFlagAdminRole() == null){
			getEntity().setFlagAdminRole(Boolean.FALSE);
		}
	}
	
	@Override
	public void atualizar() throws Exception {
		
		ServiceResponse response = null;
		try{
			response = getService().atualizar(getEntity(), getUsuarioHost(), getUsuarioLogado());
			addMsgs(response);
			setEntity((ParametroSistema) response.getData());
			setModoAtualizacao(false);
		}finally{
			aposSalvar();
		}
	}

	@Override
	protected void aposSalvar() throws Exception {
		
		setEntity(new ParametroSistema());
		pesquisar();
	}

	@Override
	protected final IParametroSistemaService<ParametroSistema> getService() {
		return this.paramentroSistemaService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void pesquisar() throws Exception {
		
		setModoNovo(false);
		setModoAtualizacao(false);
		antesPesquisar();
		
		ServiceResponse response = null;
		response = getService().findByExample(getEntity());

		addMsgs(response);
		setDados((List<ParametroSistema>) response.getData());
	}
	
	@Override
	protected void antesPesquisar() throws Exception {
		
		if (getEntity() != null){
			if (StringUtils.isEmpty(getEntity().getNomeParametroSistema())){
				getEntity().setNomeParametroSistema(null);
			}
			if (StringUtils.isEmpty(getEntity().getValorParametroSistema())){
				getEntity().setValorParametroSistema(null);
			}
			if (StringUtils.isEmpty(getEntity().getDescricao())){
				getEntity().setDescricao(null);
			}
			if (!isUsuarioAdminParametroSistema){
				getEntity().setFlagAdminRole(Boolean.FALSE);
			} 
		}
	}

	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	public boolean getUsuarioAdminParametroSistema() {
		return isUsuarioAdminParametroSistema;
	}

	public void setUsuarioAdminParametroSistema(
			boolean isUsuarioAdminParametroSistema) {
		this.isUsuarioAdminParametroSistema = isUsuarioAdminParametroSistema;
	}
}