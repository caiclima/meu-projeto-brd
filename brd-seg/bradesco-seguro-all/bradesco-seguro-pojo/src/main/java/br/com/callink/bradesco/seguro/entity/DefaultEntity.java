package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;

public abstract class DefaultEntity implements IdentifiableEntity<BigInteger>, ILog {

	private static final long serialVersionUID = 1L;

}
