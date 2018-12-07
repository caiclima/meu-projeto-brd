package br.com.callink.bradesco.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @author michael
 *
 */
public interface IAplicacaoDAO {
	
	/**
	 * Desativa um login de usuário
	 * 
	 * @param codigoDominio
	 * @param login
	 * @return - total linhas afetadas
	 * @throws SQLException
	 */
	int desativaLoginUsuario(Short codigoDominio, String login) throws SQLException;
	
	/**
	 * Busca valor de parâmetro de Sistema
	 * 
	 * @param nomeParametro
	 * @return
	 * @throws SQLException
	 */
	String buscarValorParametroSistema(String nomeParametro) throws SQLException;
	
	/**
	 * Atualiza informações de login de usuário.
	 * 
	 * @param matricula
	 * @param codigoDominio
	 * @param dataInicio
	 * @param dataFim
	 * @param login
	 * @param dataCadastro
	 * @param dataAtualizacao
	 * @return - total de registros afetados
	 * @throws SQLException
	 */
	int atualizaLoginDeUsuario(Integer matricula, Short codigoDominio, Date dataInicio, Date dataFim, String login, Date dataCadastro, Date dataAtualizacao) throws SQLException;
	
	
	/**
	 * Insere novo login de usuário.
	 * 
	 * @param matricula
	 * @param dataInicio
	 * @param codigoDominio
	 * @param dataFim
	 * @param login
	 * @param dataCadastro
	 * @param dataAtualizacao
	 * @param idUsuario
	 * @return - total de registros afetados.
	 * @throws SQLException
	 */
	int inserirLoginDeUsuario(Integer matricula, Date dataInicio, Short codigoDominio, Date dataFim, String login, Date dataCadastro, Date dataAtualizacao, Long idUsuario) throws SQLException;
	
	/**
	 * Busca todos IDs de usuários com seus respectivos CPFs.
	 * @return
	 * @throws SQLException
	 */
	Map<String, Long> buscarIdUsuariosComCPF() throws SQLException;
	
	/**
	 * Busca todos IDs de usuários com suas respectivas matriculas.
	 * 
	 * @return
	 * @throws SQLException
	 */
	Map<Integer, Long> buscarIdUsuariosComMatricula() throws SQLException;
	
	/**
	 * Insere novo usuario.
	 * 
	 * @param nome
	 * @param matricula
	 * @param cpf
	 * @param codCentroCusto
	 * @param descricaoCentroCusto
	 * @param dataCadastro
	 * @param dataAtualizacao
	 * @param idCargo
	 * @return - ID do usuário inserido
	 * @throws SQLException 
	 */
	Long inserirUsuario(String nome, BigInteger matricula, String cpf, BigInteger codCentroCusto, String descricaoCentroCusto, Date dataCadastro, Date dataAtualizacao, Integer idCargo) throws SQLException;
	
	/**
	 * Atualiza informações de usuário
	 * 
	 * @param idUsuario
	 * @param nome
	 * @param codCentroCusto
	 * @param descricaoCentroCusto
	 * @param dataCadastro
	 * @param dataAtualizacao
	 * @param idCargo
	 * @return
	 * @throws SQLException
	 */
	int atualizarUsuario(Long idUsuario, String nome, BigInteger codCentroCusto, String descricaoCentroCusto, Date dataCadastro, Date dataAtualizacao, Integer idCargo) throws SQLException;
	
	/**
	 * Atualiza cargo
	 * @param cargo
	 * @return - retorna ID do cargo inserido
	 * @throws SQLException
	 */
	BigDecimal inserirCargo(String cargo) throws SQLException;
	
	/**
	 * consulta cargos.
	 * 
	 * @param cargo
	 * @return
	 * @throws SQLException
	 */
	Map<String, BigInteger> buscarCargos() throws SQLException;
	
}