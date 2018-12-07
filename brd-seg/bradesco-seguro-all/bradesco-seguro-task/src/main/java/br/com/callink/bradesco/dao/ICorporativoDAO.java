package br.com.callink.bradesco.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.entity.PessoaColaborador;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.task.dto.LoginCoorporativoDTO;

/**
 * 	
 * @author neppo.oldamar
 *
 */
public interface ICorporativoDAO {
	
	/**
	 * Busca logins de usuários.
	 * 
	 * @param idOperacao
	 * @param codigosDominios
	 * @return
	 * @throws SQLException
	 */
	List<LoginCoorporativoDTO> buscarLoginsDeUsuario(int idOperacao, Set<Short> codigosDominios) throws SQLException;

	/**
	 * Efetua consulta de usuários que pertencem a operação informada pelo ID e os cargos informados pelo ID.
	 * 
	 * @param idOperacao
	 * @param cargos
	 * @return
	 * @throws SQLException
	 */
	Set<Usuario> buscarUsuarios(String idOperacao, String idCargos) throws SQLException;
	
	/**
	 * Efetua consulta de cargos.
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<Cargo> buscarCargos() throws SQLException;
	
	/**
	 * Efetua consulta de dominios.
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<Dominio> buscarDominios() throws SQLException;
	
	Set<PessoaColaborador> buscarPessoas(String idOperacao, String idCargos) throws SQLException;
}