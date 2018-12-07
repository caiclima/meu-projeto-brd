package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.PessoaColaborador;

public interface IPessoaColaboradorDAO extends IGenericDAO<PessoaColaborador> {
	
	public List<PessoaColaborador> findByExampleExact(PessoaColaborador object);

	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
}
