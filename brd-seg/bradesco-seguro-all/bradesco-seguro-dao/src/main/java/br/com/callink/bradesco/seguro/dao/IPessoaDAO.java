package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Pessoa;

public interface IPessoaDAO extends IGenericDAO<Pessoa> {

	public List<Pessoa> findByExampleExact(Pessoa object);

	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	public List<Pessoa> findPessoasWithPessoaColaborador();
	
	public BigInteger findIdPessoa(BigInteger idPessoaCorporativo);

}
