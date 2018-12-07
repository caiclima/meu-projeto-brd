package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author michael
 * 
 */
@Entity
@Table(name = "TB_DATABASE_ALM")
public class DataBaseAlm implements IdentifiableEntity<BigInteger>,Comparable<DataBaseAlm> {
	
	private static final long serialVersionUID = -9162497672713663044L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID_DATABASE_ALM")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Basic(optional = false)
	@Column(name = "NOME_DATABASE_ALM")
	private String nomeDataBaseAlm;
	
	/*@Basic(optional = false)
	@Column(name = "FLAG_ENABLED")
	private Boolean flagEnabled;*/
	
	public DataBaseAlm() {
		super();
	}
	
	public DataBaseAlm(BigInteger id, String nomeDataBaseAlm,Boolean flagEnabled) {
		this();
		this.id = id;
		this.nomeDataBaseAlm = nomeDataBaseAlm;
		//this.flagEnabled = flagEnabled;
	}

	@Override
	public BigInteger getId() {
		return this.id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the nomeDataBaseAlm
	 */
	public String getNomeDataBaseAlm() {
		return nomeDataBaseAlm;
	}

	/**
	 * @param nomeDataBaseAlm the nomeDataBaseAlm to set
	 */
	public void setNomeDataBaseAlm(String nomeDataBaseAlm) {
		this.nomeDataBaseAlm = nomeDataBaseAlm;
	}

	@Override
	public String toString() {
		return " [id=" + id + ", nomeCargo=" + nomeDataBaseAlm + "]";
	}

	/**
	 * @return the flagEnabled
	 */
	/*public Boolean getFlagEnabled() {
		return flagEnabled;
	}

	*//**
	 * @param flagEnabled the flagEnabled to set
	 *//*
	public void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}*/

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataBaseAlm other = (DataBaseAlm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(DataBaseAlm o) {
		return this.getNomeDataBaseAlm().compareTo(o.getNomeDataBaseAlm());
	}

	
}