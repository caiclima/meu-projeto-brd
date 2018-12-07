package br.com.callink.bradesco.seguro.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import br.com.callink.bradesco.seguro.entity.HistoricoContato;

/**
 * Representa um Evento de Contato ao Cliente.
 * 
 * @author michael
 * @see HistoricoContato
 */
public class EventoContatoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger idHistoricoContato;
	private Integer cnPessoa;
	private String cnpj;
	private Date dataContato;
	private String idTipoContato;
	private Integer idTipoAssunto;
	private String eventoCodigoExterno;
	private String campanhaCodigoExterno;
	private String detalhamentoEvento;
	private String nomeUsuario;
	private Integer tipoFinalizacao;
	private String tipoAgendamento;

	public Integer getCnPessoa() {
		return cnPessoa;
	}

	public void setCnPessoa(Integer cnPessoa) {
		this.cnPessoa = cnPessoa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
	}

	public String getIdTipoContato() {
		return idTipoContato;
	}

	public void setIdTipoContato(String idTipoContato) {
		this.idTipoContato = idTipoContato;
	}

	public Integer getIdTipoAssunto() {
		return idTipoAssunto;
	}

	public void setIdTipoAssunto(Integer idTipoAssunto) {
		this.idTipoAssunto = idTipoAssunto;
	}

	public String getEventoCodigoExterno() {
		return eventoCodigoExterno;
	}

	public void setEventoCodigoExterno(String eventoCodigoExterno) {
		this.eventoCodigoExterno = eventoCodigoExterno;
	}

	public String getCampanhaCodigoExterno() {
		return campanhaCodigoExterno;
	}

	public void setCampanhaCodigoExterno(String campanhaCodigoExterno) {
		this.campanhaCodigoExterno = campanhaCodigoExterno;
	}

	public String getDetalhamentoEvento() {
		return detalhamentoEvento;
	}

	public void setDetalhamentoEvento(String detalhamentoEvento) {
		this.detalhamentoEvento = detalhamentoEvento;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Integer getTipoFinalizacao() {
		return tipoFinalizacao;
	}

	public void setTipoFinalizacao(Integer tipoFinalizacao) {
		this.tipoFinalizacao = tipoFinalizacao;
	}

	public String getTipoAgendamento() {
		return tipoAgendamento;
	}

	public void setTipoAgendamento(String tipoAgendamento) {
		this.tipoAgendamento = tipoAgendamento;
	}

	public BigInteger getIdHistoricoContato() {
		return idHistoricoContato;
	}

	public void setIdHistoricoContato(BigInteger idHistoricoContato) {
		this.idHistoricoContato = idHistoricoContato;
	}
}