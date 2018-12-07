package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.DataBaseAlm;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.EventoCampanha;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.ICampanhaService;
import br.com.callink.bradesco.seguro.service.IDataBaseAlmService;
import br.com.callink.bradesco.seguro.service.IEventoCampanhaService;
import br.com.callink.bradesco.seguro.service.IEventoService;
import br.com.callink.bradesco.seguro.service.ITipoEventoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.faces.selectitem.DataBaseAlmSelectItem;
import br.com.callink.bradesco.seguro.web.faces.utils.JSFUtils;
import br.com.callink.bradesco.seguro.web.faces.utils.SortOrderUtil;
import br.com.callink.bradesco.seguro.web.faces.webobjects.EventoDTO;


@ManagedBean
@ViewScoped
public class CampanhaBB extends GenericCrudBB<Campanha, ICampanhaService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7235032119160712424L;

	private String nome;

	private static final BigInteger QTD_MAX_AGENDAMENTO_ATENDENTE = BigInteger.valueOf(999l);
	private static final BigInteger QTD_MAX_AGENDAMENTO_CLIENTE = BigInteger.valueOf(999l);
	private static final BigInteger NUM_MAX_DIAS_AGENDAMENTO = BigInteger.valueOf(999l);
	
	private List<EventoDTO> eventosDTO;
	private List<EventoDTO> todosEventosDTO;
	private List<DataBaseAlm> todosDataBaseAlm;
	
	private List<Evento> eventos;
	private List<TipoEvento> tipoEventos;
	private String eventoSelecionado;
	private String tipoEventoSelecionado;
	private String idEventoSelecionado;
	private String idTipoEventoSelecionado;
	
	private BigInteger lastIdCampanha = null;
	private int currentEventoPage = 1;
	private int currentCampanhaPage = 1;
	
	private String statusTemporario;
	
	private Boolean sucesso;
	
	@EJB
	private ICampanhaService campanhaService;
	
	@EJB
	private IEventoCampanhaService eventoCampanhaService;
	
	@EJB
	private IDataBaseAlmService dataBaseAlmService;
	
	@EJB
	private IEventoService<Evento> eventoService;
	
	@EJB
	private ITipoEventoService<TipoEvento> tipoEventoService;
	
	private DataBaseAlmSelectItem dataBaseAlmSelectItem;
	
	// ordenação grid
	// BUG RELATED IN CLIENT SIDE http://community.jboss.org/thread/162865
	// AGUARDANDO VERSÃO FINAL CORRIGIDA
	private static enum SortColumnName {
		NOME, NOME_EVENTO, DATA_INICIO, DATA_FIM, SIGLA;
	}

	private SortOrderUtil sortOrderUtil;

	@PostConstruct
	public void init() throws Exception {
		novoRegistro();
		statusTemporario = "";
		
		this.sortOrderUtil = new SortOrderUtil(SortColumnName.NOME.name(),
				SortColumnName.NOME_EVENTO.name(),
				SortColumnName.DATA_INICIO.name(),
				SortColumnName.DATA_FIM.name(),
				SortColumnName.SIGLA.name());
		
		setEntity(new Campanha());
	}
	
	@Override
	public void novoRegistro() {
		setEntity(new Campanha());
		getEntity().setFlagEnabled(Boolean.TRUE);
		//getEntity().setFlagPrecisaAgendMailing(Boolean.FALSE);
		
		// valores padrões para uma nova campanha
		getEntity().setQtdAgendamentoAtendente(QTD_MAX_AGENDAMENTO_ATENDENTE);
		getEntity().setQtdAgendamentoCliente(QTD_MAX_AGENDAMENTO_CLIENTE);
		getEntity().setNumMaximoDiasAgendamento(NUM_MAX_DIAS_AGENDAMENTO);

		defaultValuesVinculacaoEventoCampanha();
		
		setSucesso(Boolean.FALSE);
	}
	
	public List<Evento> filterEventosByNome(String search){
		List<Evento> eventosFiltrados = new ArrayList<>();
		
		if(search != null && !search.isEmpty()){
			for (Evento obj : eventos) {
				if(obj.getNomeEvento().toLowerCase().startsWith(search.toLowerCase())){
					eventosFiltrados.add(obj);
				}
			}
		}
		return eventosFiltrados;
	}
	
	public List<TipoEvento> filterTipoEventosByNome(String search){
		List<TipoEvento> tipoEventosFiltrados  = new ArrayList<>();
		
		if(search != null && !search.isEmpty()){
			for (TipoEvento obj : tipoEventos) {
				if(obj.getNomeTipoEvento().toLowerCase().startsWith(search.toLowerCase())){
					tipoEventosFiltrados.add(obj);
				}
			}
		}
		return tipoEventosFiltrados;
	}
	
	public void limparDadosAssociacao(){
		eventosDTO = null;
		eventoSelecionado = null;
		tipoEventoSelecionado = null;
		idEventoSelecionado = null;
		idTipoEventoSelecionado = null;
	}
	
	public void associarOuDesassociarEventos(){
		
		try{
			if(eventosDTO != null){
				List<EventoCampanha> eventoCampanhaList = buildEventoCampanhaAssociadosFromEventoDTO();
				getEntity().setEventoCampanhaList(eventoCampanhaList);
				
				ServiceResponse response = getService().associarEventosNaCampanha(getEntity());
				addMsgs(response);
			}
		} catch (ServiceException e) {
			addExceptionMessage(e.getMessage());
		}
		setEntity(new Campanha());
	}
	
	@SuppressWarnings("unchecked")
	public void carregarAssociacoes(){
		
		ServiceResponse response = null;
		
		try {
			BigInteger idEvento = null;
			BigInteger idTipoEvento = null;
			
			if(this.idEventoSelecionado != null){
				idEvento = new BigInteger(idEventoSelecionado);
			}
			
			if(this.idTipoEventoSelecionado != null){
				idTipoEvento = new BigInteger(idTipoEventoSelecionado);
			}
			
			// trazer eventos ativos e que não sejam gateway
			response = eventoService.findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(idEvento, idTipoEvento);
			
			List<Evento> eventos = (List<Evento>) response.getData();
			
			response = eventoService.findEventosNaCampanha(getEntity());
			
			List<Evento> naCampanha = (List<Evento>) response.getData();
	
			// cria lista de eventodto sendo que o evento é adicionado na lista
			// para fazer a amarração de campanha com evento é utilizado a
			// ' propriedade associado'
			eventosDTO = new ArrayList<EventoDTO>();
			for (Evento evento : eventos) {
				EventoDTO eventoDTO = new EventoDTO();
				eventoDTO.setAssociado(false);
				eventoDTO.setEvento(evento);
				for(Evento even: naCampanha){
					if(even.getId().equals(eventoDTO.getEvento().getId())){
						eventoDTO.setAssociado(Boolean.TRUE);
					}
				}
				eventosDTO.add(eventoDTO);
			}
			
			todosEventosDTO = new ArrayList<EventoDTO>();
			for (Evento evento : eventos) {
				EventoDTO eventoDTO = new EventoDTO();
				eventoDTO.setAssociado(false);
				eventoDTO.setEvento(evento);
				for(Evento even: naCampanha){
					if(even.getId().equals(eventoDTO.getEvento().getId())){
						eventoDTO.setAssociado(Boolean.TRUE);
					}
				}
				todosEventosDTO.add(eventoDTO);
			}
			
			if(eventosDTO == null || eventosDTO.isEmpty()){
				JSFUtils.addWarnMessage("Não foi encontrado nenhum evento. A pesquisa exibe apenas eventos com flag gatway e enabled iguais a 'SIM'.");
			}
		} catch (ServiceException e) {
			addMsgs(response);
		} catch (ValidationException e ) { 
			addMsgs(response);
		}
	}

	/**
	 * busca campanhas ativas e quando informado um nome no padrão %%
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	public void pesquisar() throws ServiceException {
		ServiceResponse response = getService().findByExample(getEntity());
		if(response != null
				&& response.getData() != null
				&& ((List<Campanha>)response.getData()).size() > 0){
			for(Campanha cmp : ((List<Campanha>) response.getData())){
				if(cmp.getIdDataBaseAlm() != null){
					DataBaseAlm exempl = new DataBaseAlm(cmp.getIdDataBaseAlm(),null,null);
					exempl = todosDataBaseAlm.get(todosDataBaseAlm.indexOf(exempl));
					cmp.setDataBaseAlm(exempl);
				}
			}
		}
		setDados((List<Campanha>) response.getData());
		
		currentEventoPage = 1;
		if (this.getDados() == null || this.getDados().isEmpty()) {
			
			addMsgs(response);
		}

		defaultValuesVinculacaoEventoCampanha();
		this.currentCampanhaPage = 1;

	}
	
	@SuppressWarnings("unchecked")
	public String prepararAssociar(Campanha campanha) {
	
		try{
			novoRegistro();
			setEntity(campanha);
			
			currentEventoPage = 1;
			setSucesso(Boolean.FALSE);
			
			ServiceResponse response = null;
			if(eventos == null || eventos.isEmpty()){
				response = eventoService.findPorEventoOuTipoEventoOuTodosAtivosDeAgentes(null, null);
				eventos = (List<Evento>) response.getData();				
			}
			
			if(tipoEventos == null || tipoEventos.isEmpty()){
				response = tipoEventoService.findAll();
				tipoEventos = (List<TipoEvento>) response.getData();
			}
			
		}catch (Exception e){
			addExceptionMessage(e.getMessage());
		}
		return null;
	}
	
	public String editar(Campanha campanha) {
		
		novoRegistro();
		setEntity(campanha);
		
		this.eventosDTO = null;
		
		currentEventoPage = 1;
		setSucesso(Boolean.FALSE);
			
		return null;
	}

	/**
	 * excluir a campanha recebida como parametro
	 * @throws ServiceException 
	 */
	public void excluir(Campanha campanha) throws ServiceException {
		
		ServiceResponse response = new ServiceResponse();
		
		try{
			getService().delete(campanha);
		} catch (EJBTransactionRolledbackException e){
			//TODO: TO catch the right exception on the service; 
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        getService().removerLogicamente(campanha, getUsuarioHost(), getUsuarioLogado());
		    }
		}
		remove(campanha);
		setEntity(new Campanha());
		addMsgs(response);
	}

	/**
	 * salva a campanha atual
	 */
	public void salvar() throws Exception {
		
		if(getEntity().getDataBaseAlm() != null){
			getEntity().setIdDataBaseAlm(getEntity().getDataBaseAlm().getId());
		}
		
		ServiceResponse serviceResponse = getService().saveOrUpdate(getEntity(),getUsuarioHost(),getUsuarioLogado());
		
		setSucesso(Boolean.TRUE);
		setEntity(new Campanha());
		pesquisar();
		addMsgs(serviceResponse);
	}
	
	public List<EventoDTO> getEventosDTO() throws ServiceException, ValidationException {
		// se não for um novo registro..
		if (getEntity().getId() != null) {
			if (eventosDTO != null) {
				updateIfNecessaryAssociacao();
			}
		}
		lastIdCampanha = getEntity().getId();

		return eventosDTO;
	}

	private List<EventoCampanha> buildEventoCampanhaAssociadosFromEventoDTO() {
		List<EventoCampanha> eventoCampanhaAssociados = new ArrayList<EventoCampanha>();
		EventoCampanha eventoCampanha;
		for (EventoDTO to : todosEventosDTO) {
			for (EventoDTO evtDTO : eventosDTO) {
				if(to.getEvento().equals(evtDTO.getEvento())){
					to.setAssociado(evtDTO.isAssociado());
					break;
				}
			}
		}
		for (EventoDTO evtDTO : todosEventosDTO) {
			if (evtDTO.isAssociado()) {
				eventoCampanha = new EventoCampanha(evtDTO.getEvento(),getEntity());
				eventoCampanhaAssociados.add(eventoCampanha);
			}
		}
		
		return eventoCampanhaAssociados;
	}

	private void updateIfNecessaryAssociacao() throws ServiceException, ValidationException {
		// verifica se a necessidade de atualizar flags que indicam se
		// esta associado/salvo
		if (lastIdCampanha == null || !lastIdCampanha.equals(getEntity().getId())) {
			List<Evento> eventosAssociados = findAllEventosAssociadosACampanhaAtual();

			if (eventosDTO != null) {
				for (EventoDTO evtDTO : eventosDTO) {
					evtDTO.setAssociado(false);
				}

				// p/ os eventos que o usuario pode escolher para a campanha
				// atual verifique se está associado bastanto percorrer a
				// lista de eventos disponiveis e associados e verificar se o id
				// evento e id
				if (eventosAssociados != null) {
					for (EventoDTO evtDTO : eventosDTO) {
						// só é necessário verificar se esta associado caso o
						// evt não foi carregado ou foi salvo
						if (!evtDTO.isWasLoaded() || evtDTO.isWasSaved()) {
							for (Evento evento : eventosAssociados) {
								if (evento.getId().equals(evtDTO.getEvento().getId())) {

									evtDTO.setAssociado(true);
									// foi carregado
									evtDTO.setWasLoaded(true);
								}
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<Evento> findAllEventosAssociadosACampanhaAtual() throws ServiceException, ValidationException {
		
		ServiceResponse response = eventoService.findEventosNaCampanha(getEntity());
		return (List<Evento>) response.getData();
	}

	/*@SuppressWarnings("unchecked")
	private void fillEventosDTO() {
		
		ServiceResponse response = null;
		
		try {
				
			// trazer eventos ativos e que não sejam gateway
			response = eventoService.findAllEventosDeAgenteAtivos();
			
			List<Evento> eventos = (List<Evento>) response.getData();
			
			response = eventoService.findEventosNaCampanha(getEntity());
			
			List<Evento> naCampanha = (List<Evento>) response.getData();
	
			// cria lista de eventodto sendo que o evento é adicionado na lista
			// para fazer a amarração de campanha com evento é utilizado a
			// ' propriedade associado'
			eventosDTO = new ArrayList<EventoDTO>();
			for (Evento evento : eventos) {
				EventoDTO eventoDTO = new EventoDTO();
				eventoDTO.setAssociado(false);
				eventoDTO.setEvento(evento);
				for(Evento ev: naCampanha){
					if(ev.getId().equals(eventoDTO.getEvento().getId())){
						eventoDTO.setAssociado(Boolean.TRUE);
					}
				}
				eventosDTO.add(eventoDTO);
			}
		} catch (ServiceException e) {
			addMsgs(response);
		} catch (ValidationException e ) { 
			addMsgs(response);
		}
			
			
	}
*/
	public void setEventosDTO(List<EventoDTO> eventosDTO) {
		this.eventosDTO = eventosDTO;
	}

	private void defaultValuesVinculacaoEventoCampanha() {
		if (eventosDTO != null) {
			for (EventoDTO evtDTO : eventosDTO) {
				evtDTO.setAssociado(false);
				evtDTO.setWasLoaded(false);
				evtDTO.setWasSaved(true);
			}
		}
		lastIdCampanha = null;
	}

	public void associarTodos() {
		if (eventosDTO != null) {
			for (EventoDTO evtDTO : eventosDTO) {
				evtDTO.setAssociado(true);
			}
		}
	}

	public void desassociarTodos() {
		if (eventosDTO != null) {
			for (EventoDTO evtDTO : eventosDTO) {
				evtDTO.setAssociado(false);
			}
		}
	}

	/**
	 * propriedades p/ paginador
	 */
	public int getCurrentEventoPage() {
		return currentEventoPage;
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

	/**
	 * propriedades filtro
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void sortBy(String columnName) {
		sortOrderUtil.sortBy(columnName);
	}

	public SortOrder getSortOrder(String columnName) {
		return sortOrderUtil.getSortOrder(columnName);
	}
	
	public String getStatusTemporario() {
		return statusTemporario;
	}

	public void setStatusTemporario(String statusTemporario) {
		this.statusTemporario = statusTemporario;
	}
	
	public String limparStatus(){
//		if(getEntity() != null){
//			getEntity().setStatusOs("");
//		}
		return null;
	}

	public String addStatus(){
		if(getEntity() != null){
			//StringBuilder aux = new StringBuilder((getEntity().getStatusOs()!=null?getEntity().getStatusOs():""));
			
			//caso o primeiro caracter for ',' eh retirada
			if(statusTemporario.substring(0, 1).equals(",")){
				statusTemporario = statusTemporario.substring(1, statusTemporario.length());
			}
			//caso o ultimo caracter for ',' eh retirada
			if(statusTemporario.substring(statusTemporario.length()-1, statusTemporario.length()).equals(",")){
				statusTemporario = statusTemporario.substring(0,statusTemporario.length()-1);
			}
			
//			if(statusTemporario != null && statusTemporario.length() > 0){
//				if(getEntity().getStatusOs() != null && getEntity().getStatusOs().trim().length() > 0){
//					aux.append(",");
//				}
//				aux.append(statusTemporario);
//				getEntity().setStatusOs(aux.toString());
//				statusTemporario = "";
//			}
		}
		return null;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<TipoEvento> getTipoEventos() {
		return tipoEventos;
	}
	
	public void setTipoEventos(List<TipoEvento> tipoEventos) {
		this.tipoEventos = tipoEventos;
	}
	
	public String getEventoSelecionado() {
		return eventoSelecionado;
	}

	public void setEventoSelecionado(String eventoSelecionado) {
		if(eventoSelecionado != null && !eventoSelecionado.isEmpty() && eventoSelecionado.contains("-")){
			idEventoSelecionado = eventoSelecionado.split("-")[0].trim();
		}else {
			idEventoSelecionado = null;
		}
		this.eventoSelecionado = eventoSelecionado;
	}
	
	public String getTipoEventoSelecionado() {
		return tipoEventoSelecionado;
	}
	
	public void setTipoEventoSelecionado(String tipoEventoSelecionado) {
		if(tipoEventoSelecionado != null && !tipoEventoSelecionado.isEmpty() && tipoEventoSelecionado.contains("-")){
			idTipoEventoSelecionado = tipoEventoSelecionado.split("-")[0].trim();
		}else {
			idTipoEventoSelecionado = null;
		}
		
		this.tipoEventoSelecionado = tipoEventoSelecionado;
	}
	
	public String setIdEventoSelecionado() {
		return this.idEventoSelecionado;
	}
	
	public void setIdEventoSelecionado(String idEventoSelecionado) {
		this.idEventoSelecionado = idEventoSelecionado;
	}
	
	public String getIdTipoEventoSelecionado() {
		return idTipoEventoSelecionado;
	}
	
	public void setIdTipoEventoSelecionado(String idTipoEventoSelecionado) {
		this.idTipoEventoSelecionado = idTipoEventoSelecionado;
	}

	public String getIdEventoSelecionado() {
		return idEventoSelecionado;
	}

	@Override
	protected boolean isPesquisarTodos() {
		final Campanha e = getEntity();
		return StringUtils.isEmpty(e.getNomeCampanha());
	}

	@Override
	protected ICampanhaService getService() {
		return campanhaService;
	}

	/**
	 * @return the dataBaseAlmSelectItem
	 */
	public DataBaseAlmSelectItem getDataBaseAlmSelectItem() throws ServiceException  {
		if (dataBaseAlmSelectItem == null) {
			dataBaseAlmSelectItem = new DataBaseAlmSelectItem();
			fillDataBaseSelectItem();
		}
		return dataBaseAlmSelectItem;
	}
	
	@SuppressWarnings("unchecked")
	private void fillDataBaseSelectItem() throws ServiceException {
		DataBaseAlm dataBaseAlm = new DataBaseAlm();
		//dataBaseAlm.setFlagEnabled(Boolean.TRUE);
		todosDataBaseAlm = (List<DataBaseAlm>)dataBaseAlmService.findByExample(dataBaseAlm).getData();
		dataBaseAlmSelectItem.setListAndOrder(todosDataBaseAlm);
	}

	/**
	 * @param dataBaseAlmSelectItem the dataBaseAlmSelectItem to set
	 */
	public void setDataBaseAlmSelectItem(DataBaseAlmSelectItem dataBaseAlmSelectItem) {
		this.dataBaseAlmSelectItem = dataBaseAlmSelectItem;
	}
	
	
}
