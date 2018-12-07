package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

@Entity
@Table(name = "TB_AGENTE")
@XmlRootElement
public class Agente implements IdentifiableEntity<BigInteger> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_AGENTE")
	private BigInteger id;

	@Column(name = "NOME_AGENTE")
	private String nomeAgente;
	
	@Override
	public BigInteger getId() {
		return id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getNomeAgente() {
		return nomeAgente;
	}

	public void setNomeAgente(String nomeAgente) {
		this.nomeAgente = nomeAgente;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agente other = (Agente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "br.com.callink.bradesco.seguro.entity.Agente[ id=" + id + " ]";
	}
}
