package br.com.callink.bradesco.seguro.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IUsuarioAdminParamentroSistemaDAO;
import br.com.callink.bradesco.seguro.entity.UsuarioAdminParametroSistema;

public class UsuarioAdminParamentroSistemaDAO extends
		GenericHibernateDAOImpl<UsuarioAdminParametroSistema> implements
		IUsuarioAdminParamentroSistemaDAO {

	@Override
	public UsuarioAdminParametroSistema buscarUsuarioAdminPorLogin(String login) {

		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("usuario", login));
		return (UsuarioAdminParametroSistema) criteria.uniqueResult();
	}

}
