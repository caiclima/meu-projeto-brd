package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;

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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
*
* @author swb.thiagohenrique
*/
@Entity
@Table(name = "TB_EVENTO_CAMPANHA")
public class EventoCampanha implements IdentifiableEntity<BigInteger>, Cloneable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @Column(name = "ID_EVENTO_CAMPANHA")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_EVENTO", referencedColumnName="ID_EVENTO")
	private Evento evento;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_CAMPANHA", referencedColumnName="ID_CAMPANHA")
	private Campanha campanha;
    
    public EventoCampanha() {
	}

	public EventoCampanha(Evento evento, Campanha campanha) {
		super();
		this.evento = evento;
		this.campanha = campanha;
	}

	@Override
	public BigInteger getId() {
		return this.id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
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
		EventoCampanha other = (EventoCampanha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public EventoCampanha clone(Evento evento, Campanha campanha) {
		EventoCampanha clone = new EventoCampanha();
		clone.evento = this.evento;
		clone.campanha = this.campanha;
		return clone;
	}
	
}
