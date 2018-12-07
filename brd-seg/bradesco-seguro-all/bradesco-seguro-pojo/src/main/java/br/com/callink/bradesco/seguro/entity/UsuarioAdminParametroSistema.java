package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

@Entity
@Table(name = "TB_USUARIO_ADMIN_PARAMETRO_SISTEMA")
public class UsuarioAdminParametroSistema implements
		IdentifiableEntity<BigInteger>,
		Comparable<UsuarioAdminParametroSistema>, ILog {

	private static final long serialVersionUID = -7257971743875116389L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_USUARIO")
	private BigInteger id;

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

	@Basic(optional = false)
	@Column(name = "USUARIO")
	private String usuario;

	public UsuarioAdminParametroSistema() {
	}

	public UsuarioAdminParametroSistema(String usuario) {
		this.usuario = usuario;
	}

	public UsuarioAdminParametroSistema(BigInteger id, String logUid,
			String logHost, Date logDate, String logSystem, String logObs,
			BigInteger logTransaction, String usuario) {
		this.id = id;
		this.logUid = logUid;
		this.logHost = logHost;
		this.logDate = logDate;
		this.logSystem = logSystem;
		this.logObs = logObs;
		this.logTransaction = logTransaction;
		this.usuario = usuario;
	}

	@Override
	public BigInteger getId() {
		return id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UsuarioAdminParametroSistema)) {
			return false;
		}
		UsuarioAdminParametroSistema other = (UsuarioAdminParametroSistema) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioAdminParametroSistema [id=" + id + "]";
	}

	@Override
	public int compareTo(UsuarioAdminParametroSistema o) {
		return this.getUsuario().compareTo(o.getUsuario());
	}

}
