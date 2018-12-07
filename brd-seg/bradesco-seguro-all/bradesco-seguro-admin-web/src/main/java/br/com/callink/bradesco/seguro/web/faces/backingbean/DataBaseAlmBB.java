package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.entity.DataBaseAlm;
import br.com.callink.bradesco.seguro.enums.SimNao;
import br.com.callink.bradesco.seguro.service.IDataBaseAlmService;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;

/**
 * 
 * Representa o Backing Bean do Tipo iOF
 *
 */

@ManagedBean
@ViewScoped
public class DataBaseAlmBB extends GenericCrudBB<DataBaseAlm, IDataBaseAlmService> {

	private static final long serialVersionUID = -878776612337944890L;
	
	private SimNao flagEnabled;
	private int renderizarItemModal;
	private List<SimNao> simNaoList;
	
	@EJB(beanName = "DataBaseAlmService")
	private IDataBaseAlmService dataBaseAlmService;

	@PostConstruct
	public void init() throws Exception {
		setEntity(new DataBaseAlm());
		//getEntity().setFlagEnabled(Boolean.TRUE);
	}

	@Override
	public void novoRegistro() throws Exception {
		setEntity(new DataBaseAlm());
		//getEntity().setFlagEnabled(Boolean.TRUE);
		setFlagEnabled(SimNao.SIM);
	}
	
	@Override
	public String editar(DataBaseAlm entity) {
		setModoAtualizacao(true);
		setEntity(entity);
		
		/*if (entity.getFlagEnabled() != null && !entity.getFlagEnabled()) {
			setFlagEnabled(SimNao.NAO);
		} else if (entity.getFlagEnabled() != null && entity.getFlagEnabled()) {
			setFlagEnabled(SimNao.SIM);
		}*/
		
		return null;
	}
	
	@Override
	public void salvar() throws Exception {
		
		if (getFlagEnabled() == null || getFlagEnabled().getValor() == null) {
			throw new ValidationException("Escolha o status!");
		}

		//getEntity().setFlagEnabled(getFlagEnabled().getValor());
		ServiceResponse serviceResponse = getService().save(getEntity());

		addMsgs(serviceResponse);
		setEntity(new DataBaseAlm());
		getDados().add((DataBaseAlm) serviceResponse.getData());
		setModoNovo(false);
	}
	
	@Override
	public void atualizar() throws Exception {
		
		if (getFlagEnabled() == null || getFlagEnabled().getValor() == null) {
			throw new ValidationException("Escolha o status!");
		}
		//getEntity().setFlagEnabled(getFlagEnabled().getValor());
		
		ServiceResponse serviceResponse = getService().update(getEntity());
		
		addMsgs(serviceResponse);
		setEntity(new DataBaseAlm());
		setFlagEnabled(null);
		setModoAtualizacao(false);
	}
	
	@Override
	public void excluir(DataBaseAlm dataBaseAlm) throws Exception {
		//dataBaseAlm.setFlagEnabled(Boolean.FALSE);
		getService().update(dataBaseAlm);
		pesquisar();
	}

	@Override
	protected boolean isPesquisarTodos() {
		final DataBaseAlm e = getEntity();
		return StringUtils.isEmpty(e.getNomeDataBaseAlm());
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		
		antesPesquisar();
		
		ServiceResponse serviceResponse = getService().findByExample(getEntity());
		
		addMsgs(serviceResponse);
		setDados((List<DataBaseAlm>) serviceResponse.getData());
		aposPesquisar();
		
	}
	
	public boolean renderizaTelas(int tela) {
		
		if(renderizarItemModal == tela) {
			return true;
		}
		return false;
	}

	public int getRenderizarItemModal() {
		return renderizarItemModal;
	}

	public void setRenderizarItemModal(int renderizarItemModal) {
		this.renderizarItemModal = renderizarItemModal;
	}

	public SimNao getFlagEnabled() {
		return flagEnabled;
	}

	public void setFlagEnabled(SimNao flagEnabled) {
		this.flagEnabled = flagEnabled;
		
		/*if (this.flagEnabled != null) {
			switch (this.flagEnabled) {
				case SIM: {
					getEntity().setFlagEnabled(true);
				}break;
				case NAO: {
					getEntity().setFlagEnabled(false);
				} break;
				default: {
					getEntity().setFlagEnabled(true);
				}
			}
		} else {
			getEntity().setFlagEnabled(null);
		}*/
	}

	public List<SelectItem> getSimNaoList() {
		if (simNaoList == null) {
			simNaoList = new ArrayList<SimNao>();
			simNaoList.add(SimNao.NAO);
			simNaoList.add(SimNao.SIM);
		}

		return JSFUtils.toSelectItemConsulta(simNaoList);
	}

	public void setSimNaoList(List<SimNao> simNaoList) {
		this.simNaoList = simNaoList;
	}

	@Override
	protected IDataBaseAlmService getService() {
		return dataBaseAlmService;
	}
}
