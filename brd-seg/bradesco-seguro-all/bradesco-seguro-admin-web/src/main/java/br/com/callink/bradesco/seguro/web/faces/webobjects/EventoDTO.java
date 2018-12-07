package br.com.callink.bradesco.seguro.web.faces.webobjects;

import java.io.Serializable;

import br.com.callink.bradesco.seguro.entity.Evento;


public class EventoDTO implements Serializable {
	
	private static final long serialVersionUID = -5422459196746550041L;
	private Evento evento;
	private boolean associado = Boolean.FALSE;
	private boolean wasSaved = Boolean.FALSE;
	private boolean wasLoaded = Boolean.FALSE;

	public EventoDTO() {
	}

	public EventoDTO(Evento evento) {
		super();
		this.evento = evento;
	}

	public boolean isAssociado() {
		return associado;
	}

	public void setAssociado(boolean associado) {
		this.associado = associado;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public boolean isWasSaved() {
		return wasSaved;
	}

	public void setWasSaved(boolean wasSaved) {
		this.wasSaved = wasSaved;
	}

	public boolean isWasLoaded() {
		return wasLoaded;
	}

	public void setWasLoaded(boolean wasLoaded) {
		this.wasLoaded = wasLoaded;
	}

}
