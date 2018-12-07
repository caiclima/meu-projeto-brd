package br.com.callink.bradesco.seguro.dto;

import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;

/**
 * Dados diversos de cliente para indicação de clientes
 * @author Gabriel Arvelos
 *
 */
public class ClienteIndicacaoDTO {
	
	private ClienteCampanha clienteCampanha;
	private Campanha campanha;
	private String cnpj;
	private String mensagem;
	
	public ClienteIndicacaoDTO() {}
	
	public ClienteIndicacaoDTO(String cnpj) {
		super();
		this.cnpj = cnpj;
	}
	/**
	 * @return the clienteCampanha
	 */
	public ClienteCampanha getClienteCampanha() {
		return clienteCampanha;
	}
	/**
	 * @param clienteCampanha the clienteCampanha to set
	 */
	public void setClienteCampanha(ClienteCampanha clienteCampanha) {
		this.clienteCampanha = clienteCampanha;
	}
	/**
	 * @return the campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}
	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}
	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public Boolean isNew(){
		if(clienteCampanha != null){
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	/**
	 * @return the temMensagem
	 */
	public Boolean temMensagem() {
		return mensagem != null && !mensagem.isEmpty();
	}
	
}