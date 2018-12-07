package br.com.callink.bradesco.seguro.web.backinbean;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dto.ClienteCampanhaDTO;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.ITelefoneClienteService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse.Level;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse.Message;



/**
 * 
 * @author michael
 * 
 */
@ManagedBean
@ViewScoped
public class ConsultaClienteCampanhaBB extends GenericBB {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(ConsultaClienteCampanhaBB.class);

	private String callURL;
	private String userID;
	private String nome;
	private String cnpj;
	private String idServicoAspect;
	private String urlAspect;
	private List<ClienteCampanhaDTO> dados;
	
	private String cnpjAtualizacao;
	private BigInteger idClienteCampanha;

	@EJB
	private IClienteCampanhaService clienteCampanhaService;
	
	@EJB
	private ITelefoneClienteService telefoneService;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@PostConstruct
	public void init() throws Exception {
		ParametroSistema idServicoAspect = parametroSistemaService.buscarPorNome(ParametroSistema.PARAMETRO_ID_SERVICO_ASPECT);
		setIdServicoAspect(idServicoAspect == null ? null : idServicoAspect.getValorParametroSistema());
		
		ParametroSistema callUrlAspect = parametroSistemaService.buscarPorNome(ParametroSistema.PARAMETRO_CALL_URL_ASPECT);
		setUrlAspect(callUrlAspect == null ? null : callUrlAspect.getValorParametroSistema());
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception{
		if(StringUtils.isEmpty(idServicoAspect) || StringUtils.isEmpty(userID) || StringUtils.isEmpty(callURL)){
			limpar();
			fail("Erro_Parametros_Aspect_Consulta");
		}
		
		if(StringUtils.isEmpty(nome) && StringUtils.isEmpty(cnpj)){
			limpar();
			fail("Erro_Pelo_Menos_Um_Filtro");
		}
		
		try{
			ServiceResponse response = clienteCampanhaService.consultarClienteCampanha(nome, cnpj, userID);
			addMsgs(response);
			setDados((List<ClienteCampanhaDTO>) response.getData());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			failMsg("Ocorreu um erro ao efetuar a Consulta. Tente novamente! Caso o erro persista, contacte o Administrador do Sistema.");
		}
	}
	
	public void limpar(){
		setNome(null);
		setCnpj(null);
		setDados(null);
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

	public String getIdServicoAspect() {
		return idServicoAspect;
	}

	public void setIdServicoAspect(String idServicoAspect) {
		this.idServicoAspect = idServicoAspect;
	}

	public String getUrlAspect() {
		return urlAspect;
	}

	public void setUrlAspect(String urlAspect) {
		this.urlAspect = urlAspect;
	}
	
	public String getUrlCuc(){
		String urlCuc = "";
			try {
				urlCuc = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CUC_TRIBANCO).getValorParametroSistema();
				String usuarioCUC = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_CALLINK_ACESSO_CUC).getValorParametroSistema();
				String senhaCuc = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_CALLINK_ACESSO_CUC).getValorParametroSistema();
				String usuarioCanalCUC = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_CANAL_CALLINK_ACESSO_CUC).getValorParametroSistema();
				String senhaCanalCuc = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_CANAL_ACESSO_CUC_TRIBANCO).getValorParametroSistema();
				
				
				urlCuc = urlCuc.replaceAll("#USUARIO_CALLINK#", usuarioCUC)
							   .replaceAll("#SENHA_CALLINK#"  , senhaCuc)
							   .replaceAll("#USUARIO#"        , usuarioCanalCUC)
							   .replaceAll("#SENHA#"          , senhaCanalCuc);
							   //.replaceAll("#CPFCNPJ#"        , cnpj);
				
				
			} catch (Exception e) {
				logger.error("Erro ao abrir CUC do cliente.", e);
				urlCuc = "";
			}
		return urlCuc;
	}
	
	public String atualizaCliente(){
		
		try {
			ServiceResponse serviceResponse = telefoneService.atualizarTelefoneCliente(getCnpjAtualizacao(), getIdClienteCampanha(), getUserID(), getUserID());
			
			for(Message msg : serviceResponse.getMessages()){
				if(msg.getLevel().equals(Level.WARNING)){
					logger.warn(msg.getMessage());
					addWarnMessage("aviso_Atualizar_Cliente",msg.getMessage());
				} else if(msg.getLevel().equals(Level.ERROR)){
					logger.error(msg.getMessage());
					addErrorMessage("error_Atualizar_Cliente",msg.getMessage());
				} else if(msg.getLevel().equals(Level.INFO)){
					logger.info(msg.getMessage());
					addInfoMessage("sucesso_Atualizar_Cliente",msg.getMessage());
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorMessage("error_Atualizar_Cliente");
		}
		
		return null;
	}

	/**
	 * @return the cnpjAtualizacao
	 */
	public String getCnpjAtualizacao() {
		return cnpjAtualizacao;
	}

	/**
	 * @param cnpjAtualizacao the cnpjAtualizacao to set
	 */
	public void setCnpjAtualizacao(String cnpjAtualizacao) {
		this.cnpjAtualizacao = cnpjAtualizacao;
	}

	/**
	 * @return the idClienteCampanha
	 */
	public BigInteger getIdClienteCampanha() {
		return idClienteCampanha;
	}

	/**
	 * @param idClienteCampanha the idClienteCampanha to set
	 */
	public void setIdClienteCampanha(BigInteger idClienteCampanha) {
		this.idClienteCampanha = idClienteCampanha;
	}
	
	
}