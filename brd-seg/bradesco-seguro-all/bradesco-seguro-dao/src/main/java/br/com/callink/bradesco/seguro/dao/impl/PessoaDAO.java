package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.FetchType;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IPessoaDAO;
import br.com.callink.bradesco.seguro.entity.Pessoa;

public class PessoaDAO extends GenericHibernateDAOImpl<Pessoa> implements IPessoaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findByExampleExact(Pessoa pessoa) {

		Criteria criteria = createCriteria();
		criteria.add(Example.create(pessoa).enableLike(MatchMode.EXACT));
		return (List<Pessoa>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findPessoasWithPessoaColaborador() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("SELECT p FROM Pessoa p ");
		stringBuilder
				.append("INNER JOIN FETCH p.pessoaColaboradorList as colaborador");
		stringBuilder.append(" WHERE colaborador.idPessoa = p.id ");

		Query query = session().createQuery(stringBuilder.toString());

		return query.list();
	}
	
	public BigInteger findIdPessoa(BigInteger idPessoaCorporativo) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("idPessoaCorporativo", idPessoaCorporativo));
		criteria.setProjection(Projections.projectionList().add(Projections.property("id").as("id")));
		return (BigInteger) criteria.uniqueResult();
	}

}
