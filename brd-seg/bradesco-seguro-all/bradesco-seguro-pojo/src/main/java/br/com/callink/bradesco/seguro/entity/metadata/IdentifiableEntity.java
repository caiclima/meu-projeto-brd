package br.com.callink.bradesco.seguro.entity.metadata;

import java.io.Serializable;

/**
 * Representa uma entity que possui identidade (PK).
 * 
 * @author neppo.oldamar
 * @param T - tipo
 */
public interface IdentifiableEntity<T> extends Serializable {
	T getId();
	
	void setId(T id);
}