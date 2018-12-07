package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import br.com.callink.bradesco.seguro.dao.IPessoaColaboradorDAO;
import br.com.callink.bradesco.seguro.entity.PessoaColaborador;

public class PessoaColaboradorDAO extends
		GenericHibernateDAOImpl<PessoaColaborador> implements
		IPessoaColaboradorDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaColaborador> findByExampleExact(
			PessoaColaborador pessoaColaborador) {

		Criteria criteria = createCriteria();
		criteria.add(Example.create(pessoaColaborador).enableLike(
				MatchMode.EXACT));
		return (List<PessoaColaborador>) criteria.list();
	}

}
