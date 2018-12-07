package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

@Entity
@Table(name = "TB_EMAIL")
public class Email implements IdentifiableEntity<BigInteger>, ILog {

	private static final long serialVersionUID = 1L;

	public Email() {
		
    }
    
    public Email(BigInteger idEmail) {
        this.id = idEmail;
    }
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMAIL", unique = true, nullable = false)
    private BigInteger id;
    
    @Column(name = "ASSUNTO", length = 200)
    private String assunto;
    
    @Column(name = "REMETENTE", length = 200)
    private String remetente;
    
    @Column(name = "DESTINATARIO")
    private String destinatario;
    
    @Column(name = "CONTEUDO")
    private String conteudo;
    
    @Column(name = "DATA_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;
    
    //Flag para sinalizar se o e-mail está sendo gerado pelo sistema. Se for true o sistema que envia o e-mail.
    private transient boolean emailSistema;
   
    /**
     * indica que o envio do email está pendente - deixar esta Flag TRUE 
     * quando se deseja que o email seja enviado pela Thread de envio de emails
     */
    @Column(name = "FLAG_ENVIO_PENDENTE")
    private Boolean flagEnvioPendente;
    /**
     * FLAG QUE INDICA SE HOUVE ERRO NO ENVIO DO EMAIL
     * A thread de envio ignora todos emails com esta flag true
     */
    @Column(name = "FLAG_ERRO_ENVIO")
    private Boolean flagErroEnvio;
    
    /**
     * flag que indica se o email está sendo enviado pelo GBO (true) ou sendo recebido pelo GBO (gbo)
     */
    @Column(name = "FLAG_ENVIO")
    private Boolean flagEnvio;
    
    /**
     * PARA EMAILS RECEBIDOS, INDICA SE O EMAIL JA FOI LIDO POR ALGUM ATENDENTE
     */
    @Column(name = "FLAG_LIDO")
    private Boolean flagLido;
    
    /**
     * Flag que representa um e-mail que não será apresentado.
     */
    @Column(name = "FLAG_DESATIVADO")
    private Boolean flagDesativado = Boolean.FALSE;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PAI", referencedColumnName = "ID_EMAIL")
    private Email pai;
    
    @Column(name="destinatario_para_exibicao")
    private String destinatarioParaExibicao;
    
    @Basic(optional = false)
    @Column(name = "LOG_UID")
    private String logUid;
    
    @Basic(optional = false)
    @Column(name = "LOG_HOST")
    private String logHost;
    
    @Basic(optional = false)
    @Column(name = "LOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    
    @Basic(optional = false)
    @Column(name = "LOG_SYSTEM")
    private String logSystem;
    
    @Basic(optional = false)
    @Column(name = "LOG_OBS")
    private String logObs;
    
    @Basic(optional = false)
    @Column(name = "LOG_TRANSACTION")
    private BigInteger logTransaction;
    
    private transient StringBuffer mensagemTxt;
    private transient StringBuffer mensagemHtml;
    private transient String descricaoLog;
    

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDataEnvio() {
        return dataEnvio == null ? null : new Date(dataEnvio.getTime());
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio == null ? null : new Date(dataEnvio.getTime());
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public Boolean getFlagEnvio() {
        return flagEnvio;
    }

    public void setFlagEnvio(Boolean flagEnvio) {
        this.flagEnvio = flagEnvio;
    }


    public Boolean getFlagEnvioPendente() {
        return flagEnvioPendente;
    }

    public void setFlagEnvioPendente(Boolean flagEnvioPendente) {
        this.flagEnvioPendente = flagEnvioPendente;
    }

    public Boolean getFlagErroEnvio() {
        return flagErroEnvio;
    }

    public void setFlagErroEnvio(Boolean flagErroEnvio) {
        this.flagErroEnvio = flagErroEnvio;
    }

    public Boolean getFlagLido() {
        return flagLido;
    }

    public void setFlagLido(Boolean flagLido) {
        this.flagLido = flagLido;
    }

    public StringBuffer getMensagemHtml() {
        if (mensagemHtml == null) {
            mensagemHtml = new StringBuffer();
        }
        return mensagemHtml;
    }

    public void setMensagemHtml(StringBuffer mensagemHtml) {
        this.mensagemHtml = mensagemHtml;
    }

    public StringBuffer getMensagemTxt() {
        if (mensagemTxt == null) {
            mensagemTxt = new StringBuffer();
        }
        return mensagemTxt;
    }

    public void setMensagemTxt(StringBuffer mensagemTxt) {
        this.mensagemTxt = mensagemTxt;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Email other = (Email) obj;
        if (this.id == null || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public Email getPai() {
        return pai;
    }

    public void setPai(Email pai) {
        this.pai = pai;
    }

    public String getDescricaoLog() {
        return descricaoLog;
    }

    public void setDescricaoLog(String descricaoLog) {
        this.descricaoLog = descricaoLog;
    }

    public String getDestinatarioParaExibicao() {
        return destinatarioParaExibicao;
    }

    public void setDestinatarioParaExibicao(String destinatarioParaExibicao) {
        this.destinatarioParaExibicao = destinatarioParaExibicao;
    }

    public boolean isEmailSistema() {
        return emailSistema;
    }

    public void setEmailSistema(boolean emailSistema) {
        this.emailSistema = emailSistema;
    }
    
    public Boolean getFlagDesativado() {
		return flagDesativado;
	}

	public void setFlagDesativado(Boolean flagDesativado) {
		this.flagDesativado = flagDesativado;
	}
	
	public static String getSqlFromEmail() {
		return " TB_Email  AS Email with(nolock) ";
	}
	

	public String getLogUid() {
		return logUid;
	}

	public void setLogUid(String logUid) {
		this.logUid = logUid;
	}

	public String getLogHost() {
		return logHost;
	}

	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogSystem() {
		return logSystem;
	}

	public void setLogSystem(String logSystem) {
		this.logSystem = logSystem;
	}

	public String getLogObs() {
		return logObs;
	}

	public void setLogObs(String logObs) {
		this.logObs = logObs;
	}

	public BigInteger getLogTransaction() {
		return logTransaction;
	}

	public void setLogTransaction(BigInteger logTransaction) {
		this.logTransaction = logTransaction;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + "]";
	}

}

