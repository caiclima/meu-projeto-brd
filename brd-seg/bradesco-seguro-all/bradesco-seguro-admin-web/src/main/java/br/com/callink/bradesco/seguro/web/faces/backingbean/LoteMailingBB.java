package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.service.ICampanhaService;
import br.com.callink.bradesco.seguro.service.ILoteMailingService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;

/**
 * 
 * @author swb_mateus
 *
 */

@ManagedBean
@ViewScoped
public class LoteMailingBB extends GenericCrudBB<LoteMailing, ILoteMailingService> {

	private static final long serialVersionUID = 3932080720970724729L;
	
	private int currentEventoPage = 1;
	private int currentCampanhaPage = 1;
	
	private LoteMailing loteMailing;
	private List<LoteMailing> listPojos;
	private List<Campanha> listCampanhas;
	private Date ateDataImportacao;
	private Date ateDataInicioMailing;
	private Date ateDataFimMailing;
	private String nomeCampanha;
	private Boolean flagFinalizado;
	
	@EJB
	private transient ILoteMailingService loteMailingService;

	@EJB
	private transient ICampanhaService campanhaService;
	
	private static enum SortColumnName {
		NOME, ID, ID_CAMPANHA, DESIGNACAO, 
		QTD_IMPORTADA, QTD_REJEITADA, 
		DATA_INICIO, DATA_FIM, ATIVO, FINALIZACAO;
	}
	
	private SortOrderUtil sortOrder;
	
	@PostConstruct
	public void init() throws ValidationException {
		setEntity(new LoteMailing());
		this.sortOrder = new SortOrderUtil(SortColumnName.NOME.name(),
				SortColumnName.ID.name(),
				SortColumnName.ID_CAMPANHA.name(),
				SortColumnName.NOME.name(),
				SortColumnName.DESIGNACAO.name(),
				SortColumnName.DATA_INICIO.name(),
				SortColumnName.DATA_FIM.name(),
				SortColumnName.QTD_IMPORTADA.name(),
				SortColumnName.QTD_REJEITADA.name(),
				SortColumnName.ATIVO.name(),
				SortColumnName.FINALIZACAO.name());
		pesquisar();
	}
	
	
	@Override
	public void salvar() {
	}

	/**
	 * efetua a pesquisa das propostas.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void pesquisar() throws ValidationException {
		try {
//			setDados(getService().findLoteMailingByDateAndPojo(getEntity(), ateDataImportacao, ateDataInicioMailing, ateDataFimMailing));
			setEntity(new LoteMailing());
			getEntity().setFlagMailingFinalizado(getFlagFinalizado());
			setDados((List<LoteMailing>) getService().findByExample(getEntity()).getData());
		} catch (ServiceException ex) {
			addErrorMessage("ERRO_consulta_lote_mailing", ex);
		}
	}
	
	public void limpaCamapanha() {
		this.loteMailing.setCampanha(new Campanha());
		this.loteMailing.setIdCampanha(null);
	}
	
	public void pesquisaCampanha() {
		try {
			this.listCampanhas = campanhaService.findCampanhasByNome(this.nomeCampanha);
		} catch (ServiceException ex) {
			addErrorMessage("ERRO_consulta_camapanha", ex);
		}
	}
	
	public void selecionarCampanha(Campanha campanha) {
		this.loteMailing.setCampanha(campanha);
		this.loteMailing.setIdCampanha(campanha.getId());
	}
	
	public void confirmarFinalizacaoLoteMailing(LoteMailing loteMailing) {
		this.loteMailing = loteMailing;
	}
	
	public String finalizar(LoteMailing loteMailing) throws ValidationException {
		setLogPojo(loteMailing);
		loteMailing.setLogObs("Lote Mailing está sendo finalizado pelo tela de Administração do sistema.");
		try {
			loteMailingService.finalizaMailing(loteMailing, getUsuarioHost(), getUsuarioLogado());
			addInfoMessage("INFO_mailing_finalizado", loteMailing.getDesignacao());
			pesquisar();
		} catch (ServiceException ex) {
			addExceptionMessage(ex.getMessage());
		}
		return null;
	}
	
	/**
	 * propriedades p/ paginador
	 */
	public int getCurrentEventoPage() {
		return currentEventoPage;
	}
	
	public void visualizar(LoteMailing loteMailing) {
		this.loteMailing = loteMailing;
	}
	
	public void fecharVisualizacaoLoteMailing() {
	}
	
	public void setCurrentEventoPage(int currentEventoPage) {
		this.currentEventoPage = currentEventoPage;
	}

	public int getCurrentCampanhaPage() {
		return currentCampanhaPage;
	}

	public void setCurrentCampanhaPage(int currentCampanhaPage) {
		this.currentCampanhaPage = currentCampanhaPage;
	}

	@Override
	protected boolean isPesquisarTodos() {
		return true;
	}

	@Override
	protected ILoteMailingService getService() {
		return loteMailingService;
	}

	//GETTERS AND SETTERS
	public LoteMailing getLoteMailing() {
		return loteMailing;
	}

	public void setLoteMailing(LoteMailing loteMailing) {
		this.loteMailing = loteMailing;
	}

	public List<LoteMailing> getListPojos() {
		return listPojos;
	}

	public void setListPojos(List<LoteMailing> listPojos) {
		this.listPojos = listPojos;
	}

	public List<Campanha> getListCampanhas() {
		return listCampanhas;
	}

	public void setListCampanhas(List<Campanha> listCampanhas) {
		this.listCampanhas = listCampanhas;
	}

	public Date getAteDataImportacao() {
		return ateDataImportacao != null ? new Date(ateDataImportacao.getTime()) : null;
	}

	public void setAteDataImportacao(Date ateDataImportacao) {
		this.ateDataImportacao = ateDataImportacao != null ? new Date(ateDataImportacao.getTime()) : null;
	}

	public Date getAteDataInicioMailing() {
		return ateDataInicioMailing != null ? new Date(ateDataInicioMailing.getTime()) : null;
	}

	public void setAteDataInicioMailing(Date ateDataInicioMailing) {
		this.ateDataInicioMailing = ateDataInicioMailing != null ? new Date(ateDataInicioMailing.getTime()) : null;
	}

	public Date getAteDataFimMailing() {
		return ateDataFimMailing != null ? new Date(ateDataFimMailing.getTime()) : null;
	}

	public void setAteDataFimMailing(Date ateDataFimMailing) {
		this.ateDataFimMailing = ateDataFimMailing != null ? new Date(ateDataFimMailing.getTime()) : null;
	}

	public String getNomeCampanha() {
		return nomeCampanha;
	}

	public void setNomeCampanha(String nomeCampanha) {
		this.nomeCampanha = nomeCampanha;
	}
	
	public void sortBy(String columnName) {
		sortOrder.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrder.getSortOrder(columnName);
	}


	@Override
	public void novoRegistro() throws Exception {
		
	}


	public Boolean getFlagFinalizado() {
		return flagFinalizado;
	}


	public void setFlagFinalizado(Boolean flagFinalizado) {
		this.flagFinalizado = flagFinalizado;
	}

}
