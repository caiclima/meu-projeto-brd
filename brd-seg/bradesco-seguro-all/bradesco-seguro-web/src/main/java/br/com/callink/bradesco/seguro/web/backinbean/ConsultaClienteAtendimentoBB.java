package br.com.callink.bradesco.seguro.web.backinbean;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dto.ClienteCampanhaDTO;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.IEventoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.CallUrlLyricall;



/**
 * 
 * @author swb.thiagohenrique
 * 
 */
@ManagedBean
@ViewScoped
public class ConsultaClienteAtendimentoBB extends GenericBB {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(ConsultaClienteAtendimentoBB.class);

	private String callURL;
	private String userID;
	private String nome;
	private String cnpj;
	private String urlAspect;
	private List<ClienteCampanhaDTO> dados;
	
	
	private String idServicoAspect;
	private HistoricoContato historicoContato;
	private CallUrlLyricall callUrlLyricall;
	
	
	// PARAMETROS QUERY STRING
	private String qsParamIdClienteCampanha;
	private String qsParamUserId;
	private String qsParamLyricallScript;
	private String qsParamCallType;
	private String qsParamPhoneNumber;
	private String qsParamCallURL;
	private String qsParamCallId;
	private String qsParamCallManual;
	private String qsParamHashAtendimento;
	
//	@EJB
//	private ILoginService loginService;
	
	@EJB
	private IEventoService eventoService;

	@EJB
	private IClienteCampanhaService clienteCampanhaService;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@PostConstruct
	public void init() throws Exception {
		
		Map<String, String> request = getExternalContext().getRequestParameterMap();
		
		setQsParamCallType(request.get("CallType"));
		setQsParamPhoneNumber(request.get("phoneNumber"));
		setQsParamCallURL(request.get("callURL"));
		setQsParamLyricallScript(request.get("lyricallScript"));
		setQsParamCallId(request.get("callId"));
		setQsParamUserId(request.get("userID"));
		setQsParamCallManual(request.get("callManual"));
		setQsParamHashAtendimento(request.get("hashAtendimento"));
		
		setUserID(getQsParamUserId());
		setCallURL(getQsParamCallURL());
		
		
		
		ParametroSistema callUrlAspect = parametroSistemaService.buscarPorNome(ParametroSistema.PARAMETRO_RETORNO_LIGACAO_ENTRANTE);
		setUrlAspect(callUrlAspect == null ? null : callUrlAspect.getValorParametroSistema());
		
		setHistoricoContato(new HistoricoContato()); 
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception{
		if (StringUtils.isEmpty(userID) || StringUtils.isEmpty(callURL)) {
			limpar();
			fail("Erro_Parametros_Aspect_Consulta");
		}
		try {
			ServiceResponse response = clienteCampanhaService.consultarClienteAtendimentoReceptivo(nome, getCnpj(), userID);
			addMsgs(response);
			setDados((List<ClienteCampanhaDTO>) response.getData());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			failMsg("Ocorreu um erro ao efetuar a Consulta. Tente novamente! Caso o erro persista, contacte o Administrador do Sistema.");
		}
	}
	
public void FinalizarPadrao(){
		
		try {
			
			ParametroSistema idClienteCampanhaClienteNaoIdentificado = parametroSistemaService.buscarPorNome(ParametroSistema.ID_CLIENTE_CAMPANHA_NAO_IDENTIFICADO);
			ClienteCampanha clienteCampanhaClienteNaoIdentificado = clienteCampanhaService.findById(new BigInteger(idClienteCampanhaClienteNaoIdentificado.getValorParametroSistema())); // VALIDAR 
			
			ParametroSistema idEventoFinalizacaoPadrao = parametroSistemaService.buscarPorNome(ParametroSistema.ID_EVENTO_FINALIZACAO_PADRAO);
			Evento eventoFinalizacaoPadrao = eventoService.findById(new BigInteger(idEventoFinalizacaoPadrao.getValorParametroSistema()));
			
			//Login login = loginService.findByLogin(getQsParamUserId(), Boolean.TRUE);
			
			historicoContato.setIdClienteCampanha(clienteCampanhaClienteNaoIdentificado.getId());
			historicoContato.setDataContato(new Date());
			historicoContato.setDataFimContato(new Date());
			//historicoContato.setIdUsuario(login.getIdUsuario());
			//historicoContato.setCallId(null);
			historicoContato.setLogUid(getQsParamUserId());
			historicoContato.setLogDate(historicoContato.getDataContato());
			
			historicoContato.setIdEvento(eventoFinalizacaoPadrao.getId());
			historicoContato.setFlagEnabled(Boolean.TRUE);
			historicoContato.setDuracaoSegundosContato(BigInteger.ZERO);
			historicoContato.setLogSystem("Pesquisa de Cliente");
			historicoContato.setLogHost(getUsuarioHost());
			historicoContato.setLogObs("INSERIDO PELO ConsultaClienteCampanha.xhmtl - Finalização padrão");
			historicoContato.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
			
			if(getQsParamCallManual().equals("null")) {
				setQsParamCallManual("0");
			}
			callUrlLyricall = new CallUrlLyricall(eventoFinalizacaoPadrao, getQsParamCallURL(), getQsParamLyricallScript());
			((HttpServletResponse)getExternalContext().getResponse()).sendRedirect(callUrlLyricall.getUrl(getQsParamCallManual()));
			
		} catch (ServiceException e) {
			failMsg("Ocorreu um erro ao efetuar ao tentar finalizar atendimento.");
			failMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (ValidationException e) {
			failMsg("Ocorreu um erro ao efetuar ao tentar finalizar atendimento.");
			failMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			failMsg("Ocorreu um erro ao efetuar ao tentar finalizar atendimento.");
			failMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			failMsg("Ocorreu um erro ao efetuar ao tentar finalizar atendimento.");
			failMsg(e.getMessage());
			logger.error(e.getMessage(), e);
		} 
	}
	
	public void limpar(){
		setNome(null);
		setCnpj(null);
		setDados(new ArrayList<ClienteCampanhaDTO>());
	}

	// get . set
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<ClienteCampanhaDTO> getDados() {
		return dados;
	}

	public void setDados(List<ClienteCampanhaDTO> dados) {
		this.dados = dados;
	}

	public String getCallURL() {
		return callURL;
	}

	public void setCallURL(String callURL) {
		this.callURL = callURL;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUrlAspect() {
		return urlAspect;
	}

	public void setUrlAspect(String urlAspect) {
		this.urlAspect = urlAspect;
	}

	public final String getIdServicoAspect() {
		return idServicoAspect;
	}

	public final void setIdServicoAspect(String idServicoAspect) {
		this.idServicoAspect = idServicoAspect;
	}

	public final HistoricoContato getHistoricoContato() {
		return historicoContato;
	}

	public final void setHistoricoContato(HistoricoContato historicoContato) {
		this.historicoContato = historicoContato;
	}

	public final CallUrlLyricall getCallUrlLyricall() {
		return callUrlLyricall;
	}

	public final void setCallUrlLyricall(CallUrlLyricall callUrlLyricall) {
		this.callUrlLyricall = callUrlLyricall;
	}

	public final String getQsParamIdClienteCampanha() {
		return qsParamIdClienteCampanha;
	}

	public final void setQsParamIdClienteCampanha(String qsParamIdClienteCampanha) {
		this.qsParamIdClienteCampanha = qsParamIdClienteCampanha;
	}

	public final String getQsParamUserId() {
		return qsParamUserId;
	}

	public final void setQsParamUserId(String qsParamUserId) {
		this.qsParamUserId = qsParamUserId;
	}

	public final String getQsParamLyricallScript() {
		return qsParamLyricallScript;
	}

	public final void setQsParamLyricallScript(String qsParamLyricallScript) {
		this.qsParamLyricallScript = qsParamLyricallScript;
	}

	public final String getQsParamCallType() {
		return qsParamCallType;
	}

	public final void setQsParamCallType(String qsParamCallType) {
		this.qsParamCallType = qsParamCallType;
	}

	public final String getQsParamPhoneNumber() {
		return qsParamPhoneNumber;
	}

	public final void setQsParamPhoneNumber(String qsParamPhoneNumber) {
		this.qsParamPhoneNumber = qsParamPhoneNumber;
	}

	public final String getQsParamCallURL() {
		return qsParamCallURL;
	}

	public final void setQsParamCallURL(String qsParamCallURL) {
		this.qsParamCallURL = qsParamCallURL;
	}

	public final String getQsParamCallId() {
		return qsParamCallId;
	}

	public final void setQsParamCallId(String qsParamCallId) {
		this.qsParamCallId = qsParamCallId;
	}

	public final String getQsParamCallManual() {
		return qsParamCallManual;
	}

	public final void setQsParamCallManual(String qsParamCallManual) {
		this.qsParamCallManual = qsParamCallManual;
	}

	public final String getQsParamHashAtendimento() {
		return qsParamHashAtendimento;
	}

	public final void setQsParamHashAtendimento(String qsParamHashAtendimento) {
		this.qsParamHashAtendimento = qsParamHashAtendimento;
	}

//	public final ILoginService getLoginService() {
//		return loginService;
//	}
//
//	public final void setLoginService(ILoginService loginService) {
//		this.loginService = loginService;
//	}

	public final Logger getLogger() {
		return logger;
	}
}