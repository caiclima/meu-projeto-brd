package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Dominio;

public interface IDominioDAO extends IGenericDAO<Dominio> {

	public List<Dominio> findByExampleExact(Dominio object);

	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	public BigInteger findIdDominio(BigInteger idDominioCorporativo);
}
