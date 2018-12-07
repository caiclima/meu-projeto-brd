package br.com.callink.bradesco.seguro.entity.metadata;

import java.io.Serializable;

/**
 * Interface para entidades que poderão ser removidas logicamente no banco de dados.
 * @author swb_alisson
 *
 */
public interface IRemovableEntity extends Serializable {
	Boolean getFlagRemoved();
	
	void setFlagRemoved(Boolean removed);
}