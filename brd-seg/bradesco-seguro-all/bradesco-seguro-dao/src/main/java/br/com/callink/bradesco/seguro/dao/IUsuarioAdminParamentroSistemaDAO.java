package br.com.callink.bradesco.seguro.dao;

import br.com.callink.bradesco.seguro.entity.UsuarioAdminParametroSistema;

public interface IUsuarioAdminParamentroSistemaDAO extends IGenericDAO<UsuarioAdminParametroSistema>{
	
	UsuarioAdminParametroSistema buscarUsuarioAdminPorLogin(String login);
}
